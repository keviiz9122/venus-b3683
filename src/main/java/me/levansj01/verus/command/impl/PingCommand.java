package me.levansj01.verus.command.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class PingCommand extends BaseCommand {

   public void execute(CommandSender var1, String[] var2) {
      if (!BukkitUtil.hasPermission(var1, "verus.ping")) {
         var1.sendMessage(ChatColor.RED + this.getPermissionMessage());
      } else {
         if (var1 instanceof Player) {
            Player var3 = (Player)var1;
            if (var2.length == 1) {
               if (!BukkitUtil.hasPermission(var1, "verus.ping.other")) {
                  var1.sendMessage(ChatColor.RED + this.getPermissionMessage());
                  return;
               }

               Player var4 = Bukkit.getPlayer(var2[0]);
               if (var4 != null) {
                  boolean var5 = true;
                  if (StorageEngine.getInstance().getVerusConfig().isVanishPing()) {
                     var5 = var3.canSee(var4);
                  }

                  PlayerData var6 = DataManager.getInstance().getPlayer(var4);
                  if (var6 != null && var5) {
                     String var7 = EnumMessage.PING_OTHER.get().replace("{player}", var6.getName()).replace("{ping}", var6.getAveragePing() + "ms (" + var6.getAverageTransactionPing() + "ms)");
                     var3.sendMessage(var7);
                  }
               } else {
                  var3.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
               }
            } else {
               PlayerData var8 = DataManager.getInstance().getPlayer(var3);
               if (var8 != null) {
                  String var9 = EnumMessage.PING_SELF.get().replace("{ping}", var8.getAveragePing() + "ms (" + var8.getAverageTransactionPing() + "ms)");
                  var3.sendMessage(var9);
               }
            }
         }

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

   public PingCommand() {
      super(EnumMessage.PING_COMMAND.get());
   }
}
