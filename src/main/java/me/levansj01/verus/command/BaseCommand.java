package me.levansj01.verus.command;

import java.util.List;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.BukkitUtil;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand extends Command {
   private boolean database;
   private String usageMessage;

   public void setUsageMessage(String var1) {
      this.usageMessage = var1;
   }

   public BaseCommand(String var1, String var2, String var3, List<String> var4) {
      super(var1, var2, var3, var4);
   }

   public BaseCommand(String var1) {
      super(var1);
   }

   public boolean execute(CommandSender var1, String var2, String[] var3) {
      if (this.getPermission() != null && !BukkitUtil.hasPermission(var1, this.getPermission())) {
         var1.sendMessage(ChatColor.RED + this.getPermissionMessage());
         return false;
      } else if (this.database && !StorageEngine.getInstance().isConnected()) {
         var1.sendMessage(ChatColor.RED + "Please connect to a database to use this command.");
         return false;
      } else {
         this.execute(var1, var3);
         return false;
      }
   }

   public abstract void execute(CommandSender var1, String[] var2);

   public String getUsageMessage() {
      return this.usageMessage;
   }

   public void setDatabase(boolean var1) {
      this.database = var1;
   }
}
