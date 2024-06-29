package me.levansj01.verus.command.impl;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AlertsCommand extends BaseCommand {

   public AlertsCommand() {
      super(EnumMessage.ALERTS_COMMAND.get());
   }

   private void update(PlayerData var1) {
      StorageEngine var2 = StorageEngine.getInstance();
      if (var2.isConnected()) {
         var2.getDatabase().updateAlerts(var1);
      }

   }

   public void execute(CommandSender var1, String[] var2) {
      if (!BukkitUtil.hasPermission(var1, "verus.staff") && !BukkitUtil.hasPermission(var1, "verus.staff.alerts") && !BukkitUtil.hasPermission(var1, "verus.alerts")) {
         var1.sendMessage(ChatColor.RED + this.getPermissionMessage());
      } else if (var1 instanceof Player) {
         Player var3 = (Player)var1;
         PlayerData var4 = DataManager.getInstance().getPlayer(var3);
         if (var4 != null) {
            boolean var10001;
            if (BukkitUtil.isDev(var3) && var2.length == 1 && var2[0].toLowerCase().equalsIgnoreCase("debug")) {
               if (!var4.isDebug()) {
                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var4.setDebug(var10001);
               this.update(var4);
               BukkitUtil.setMeta(var3, "verus.admin", var4.isDebug());
               StringBuilder var6 = (new StringBuilder()).append(VerusPlugin.COLOR).append("You are ");
               String var10002;
               if (var4.isDebug()) {
                  var10002 = "now";
               } else {
                  var10002 = "no longer";
               }

               var1.sendMessage(var6.append(var10002).append(" in debug mode").toString());
               return;
            }

            if (!var4.isAlerts()) {
               var10001 = true;
            } else {
               var10001 = false;
            }

            var4.setAlerts(var10001);
            this.update(var4);
            BukkitUtil.setMeta(var3, "verus.alerts", var4.isAlerts());
            EnumMessage var10000;
            if (var4.isAlerts()) {
               var10000 = EnumMessage.ALERTS_ENABLED_COMMAND;
            } else {
               var10000 = EnumMessage.ALERTS_DISABLED_COMMAND;
            }

            EnumMessage var5 = var10000;
            var1.sendMessage(VerusPlugin.COLOR + var5.get());
         } else {
            var3.sendMessage(ChatColor.RED + "Your data is not currently loaded.");
         }
      }

   }
}
