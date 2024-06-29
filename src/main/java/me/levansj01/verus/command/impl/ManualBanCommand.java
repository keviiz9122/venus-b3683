package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.checks.manual.ManualA;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.Log;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class ManualBanCommand extends BaseCommand {

   public List<String> tabComplete(CommandSender var1, String var2, String[] var3) throws IllegalArgumentException {
      if (BukkitUtil.hasPermission(var1, this.getPermission()) && (var3.length == 1 || var3.length == 2 && var3[0].equalsIgnoreCase("check"))) {
         Player var10000;
         if (var1 instanceof Player) {
            var10000 = (Player)var1;
         } else {
            var10000 = null;
         }

         Player var4 = var10000;
         ArrayList var5 = new ArrayList();
         Iterator var6 = var1.getServer().getOnlinePlayers().iterator();

         do {
            Player var7;
            String var8;
            do {
               if (!var6.hasNext()) {
                  var5.sort(String.CASE_INSENSITIVE_ORDER);
                  return var5;
               }

               var7 = (Player)var6.next();
               var8 = var7.getName();
            } while(var4 != null && !var4.canSee(var7));

            String var9 = var3[var3.length - 1];
            if (StringUtil.startsWithIgnoreCase(var8, var9)) {
               var5.add(var8);
            }
         } while (true);
      } else {
         return super.tabComplete(var1, var2, var3);
      }
   }

   public ManualBanCommand() {
      super(EnumMessage.MANUAL_BAN_COMMAND.get());
      this.setDatabase(true);
      this.setPermission("verus.admin");
      this.setUsageMessage(ChatColor.RED + "Usage: /" + this.getName() + " <name> OR /" + this.getName() + " check <name>");
   }

   public void execute(CommandSender var1, String[] var2) {
      String var3;
      if (var2.length == 1) {
         String var10000;
         if (var1 instanceof Player) {
            var10000 = ((Player)var1).getName();
         } else {
            var10000 = "Console";
         }

         var3 = var10000;
         Player var4 = Bukkit.getPlayer(var2[0]);
         if (var4 != null) {
            PlayerData var5 = DataManager.getInstance().getPlayer(var4);
            ManualA var6 = var5.getCheckData().getManualBanCheck();
            if (var6 != null) {
               var6.setViolations(1.0D);
               var6.handleViolation(var3);
               AlertManager var7 = AlertManager.getInstance();
               var7.run(() -> {
                  if (var7.insertBan(var5, var6)) {
                     var5.setEnabled(false);
                  }

               });
            }
         } else {
            var1.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
         }
      } else if (var2.length == 2 && var2[0].equalsIgnoreCase("check")) {
         var3 = var2[1];
         Database var8 = StorageEngine.getInstance().getDatabase();
         var8.getUUID(var3, (var3x) -> {
            if (var3x == null) {
               var1.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
            } else {
               var8.getManualLogs(var3x, 10, (var3xx) -> {
                  if (!var3xx.iterator().hasNext()) {
                     var1.sendMessage(ChatColor.RED + EnumMessage.PLAYER_NO_LOGS.get());
                  } else {
                     String var4 = ChatColor.GRAY + "(Gathering logs for " + var2[0] + "/" + var3x + "...)\n";
                     long var5 = System.currentTimeMillis();
                     Iterator var7 = var3xx.iterator();

                     while(var7.hasNext()) {
                        Log var8 = (Log)var7.next();
                        String var9 = var8.getData();
                        if (var9 != null) {
                           var4 = var4 + ChatColor.GRAY + "(" + ChatColor.WHITE + me.levansj01.verus.util.java.StringUtil.differenceAsTime(var5 - var8.getTimestamp()) + ChatColor.GRAY + ") " + VerusPlugin.COLOR + var8.getName() + ChatColor.GRAY + " was manual banned by " + ChatColor.WHITE + var8.getData() + "\n";
                        }
                     }

                     var1.sendMessage(var4);
                  }

               });
            }
         });
      } else {
         var1.sendMessage(this.getUsageMessage());
      }

   }
}
