package me.levansj01.verus.command.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Log;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class RecentLogsCommand extends BaseCommand {
   private static final DateFormat FORMAT = new SimpleDateFormat("dd/MM HH:mm");

   public void execute(CommandSender var1, String[] var2) {
      if (var2.length == 1) {
         if (!BukkitUtil.hasPermission(var1, "verus.staff") && !BukkitUtil.hasPermission(var1, "verus.staff.logs.recent")) {
            var1.sendMessage(ChatColor.RED + this.getPermissionMessage());
         } else {
            StorageEngine.getInstance().getDatabase().getUUID(var2[0], (var3) -> {
               if (var3 == null) {
                  var1.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
               } else {
                  StorageEngine.getInstance().getDatabase().getLogs(var3, 10, (var4) -> {
                     if (!var4.iterator().hasNext()) {
                        var1.sendMessage(ChatColor.RED + "This player has no logs");
                     } else {
                        String var5 = ChatColor.GRAY + "(Gathering logs for " + var2[0] + "/" + var3 + "...)\n";
                        long var6 = System.currentTimeMillis();
                        Iterator var8 = var4.iterator();

                        while(var8.hasNext()) {
                           Log var9 = (Log)var8.next();
                           var5 = var5 + this.getFormattedRecentLogs(var9, var6) + "\n";
                        }

                        var1.sendMessage(var5);
                     }

                  });
               }
            });
         }
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

   public RecentLogsCommand() {
      super(EnumMessage.RECENT_LOGS_COMMAND.get());
      this.setDatabase(true);
      this.setUsageMessage(ChatColor.RED + "Usage: /" + this.getName() + " <name>");
   }

   public String getFormattedRecentLogs(Log var1, long var2) {
      return ChatColor.translateAlternateColorCodes('&', StorageEngine.getInstance().getVerusConfig().getRecentlogsMessage().replace("{date}", ChatColor.GRAY + "[" + ChatColor.WHITE + FORMAT.format(var2) + ChatColor.GRAY + "]").replace("{time}", ChatColor.GRAY + "(" + ChatColor.WHITE + me.levansj01.verus.util.java.StringUtil.differenceAsTime(var2 - var1.getTimestamp()) + ChatColor.GRAY + ")").replace("{name}", VerusPlugin.COLOR + var1.getName()).replace("{type}", var1.getType()).replace("{subType}", var1.getSubType()).replace("{vl}", String.valueOf(var1.getViolations())).replace("{ping}", String.valueOf(var1.getPing())).replace("{lag}", String.valueOf(var1.getLag())));
   }
}
