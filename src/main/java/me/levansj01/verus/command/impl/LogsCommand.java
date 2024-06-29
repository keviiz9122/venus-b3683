package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class LogsCommand extends BaseCommand {

   public void execute(CommandSender var1, String[] var2) {
      if (!BukkitUtil.hasPermission(var1, "verus.staff") && !BukkitUtil.hasPermission(var1, "verus.staff.logs")) {
         var1.sendMessage(ChatColor.RED + this.getPermissionMessage());
      } else if (var2.length == 2) {
         int var3;
         try {
            var3 = Integer.parseInt(var2[1]);
         } catch (Throwable var6) {
            String var5 = var2[1];
            if (var5.equalsIgnoreCase("all")) {
               this.getLogs(var1, var2[0]);
            } else if (var5.startsWith("verbose") && BukkitUtil.hasPermission(var1, "verus.admin")) {
               String var10002 = var2[0];
               short var10004;
               if (var5.equals("verbose1")) {
                  var10004 = 0;
               } else {
                  var10004 = 500;
               }

               this._getLogs(var1, var10002, true, var10004);
            } else {
               var1.sendMessage(this.getUsageMessage());
            }

            return;
         }

         this.getLogs(var1, var2[0], var3);
      } else if (var2.length == 1) {
         this.getLogs(var1, var2[0], 200);
      } else {
         var1.sendMessage(this.getUsageMessage());
      }

   }

   public List<String> tabComplete(CommandSender var1, String var2, String[] var3) throws IllegalArgumentException {
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

         if (var3.length == 1) {
            String var9 = var3[var3.length - 1];
            if (StringUtil.startsWithIgnoreCase(var8, var9)) {
               var5.add(var8);
            }
         }
      } while (true);
   }

   private void getLogs(CommandSender var1, String var2, int var3) {
      this._getLogs(var1, var2, false, var3);
   }

   public LogsCommand() {
      super(EnumMessage.LOGS_COMMAND.get());
      this.setDatabase(true);
      this.setUsageMessage(ChatColor.RED + "Usage: /" + this.getName() + " <name> <amount/all>");
   }

   private void getLogs(CommandSender var1, String var2) {
      this._getLogs(var1, var2, false, 0);
   }

   private void _getLogs(CommandSender var1, String var2, boolean var3, int var4) {
      Database var5 = StorageEngine.getInstance().getDatabase();
      var5.getUUID(var2, (var4x) -> {
         if (var4x == null) {
            var1.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
         } else {
            if (var4 != 0) {
               var5.getLogs(var4x, var4, (var3x) -> {
                  if (!var3x.iterator().hasNext()) {
                     var1.sendMessage(ChatColor.RED + EnumMessage.PLAYER_NO_LOGS.get());
                  } else {
                     AlertManager.getInstance().uploadLogs(var1, var4x, var3x, var3);
                  }

               });
            } else {
               var5.getLogs(var4x, (var3x) -> {
                  if (!var3x.iterator().hasNext()) {
                     var1.sendMessage(ChatColor.RED + EnumMessage.PLAYER_NO_LOGS.get());
                  } else {
                     AlertManager.getInstance().uploadLogs(var1, var4x, var3x, var3);
                  }

               });
            }

         }
      });
   }
}
