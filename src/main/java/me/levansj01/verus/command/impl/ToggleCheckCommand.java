package me.levansj01.verus.command.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class ToggleCheckCommand extends BaseCommand {
   public static final List<String> validCheckTypes = (List)Arrays.stream(CheckType.values()).filter(CheckType::valid).map(Enum::name).collect(Collectors.toList());
   public static final String validTypesMessage;
   private static final String FORMAT;

   public ToggleCheckCommand() {
      super(EnumMessage.TOGGLE_CHECK_COMMAND.get());
      this.setPermission("verus.staff.toggle");
      this.setUsageMessage(ChatColor.RED + "Usage: /togglecheck <checkType> <subType> <alerts>");
   }

   public List<String> tabComplete(CommandSender var1, String var2, String[] var3) throws IllegalArgumentException {
      if (var3.length == 1 && BukkitUtil.hasPermission(var1, "verus.staff.vl")) {
         ArrayList var4 = Lists.newArrayList();
         StringUtil.copyPartialMatches(var3[0], validCheckTypes, var4);
         Collections.sort(var4);
         return var4;
      } else {
         return super.tabComplete(var1, var2, var3);
      }
   }

   static {
      validTypesMessage = String.format(ChatColor.RED + "Valid types: %s", String.join(", ", validCheckTypes));
      FORMAT = VerusPlugin.COLOR + "Updated %s state for %s from %s to %s";
   }

   public void execute(CommandSender var1, String[] var2) {
      if (var2.length != 3) {
         var1.sendMessage(this.getUsageMessage());
      } else {
         if (BukkitUtil.hasPermission(var1, this.getPermission()) || BukkitUtil.hasPermission(var1, "verus.admin")) {
            String var3 = var2[0];

            CheckType var4;
            try {
               var4 = CheckType.valueOf(var3);
            } catch (Throwable var9) {
               var1.sendMessage(validTypesMessage);
               return;
            }

            String var5 = var4.ordinal() + "" + var2[1];
            CheckValues var6 = CheckManager.getInstance().getValues(var5);
            if (var6 == null) {
               var1.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
               return;
            }

            boolean var7 = Boolean.parseBoolean(var2[2]);
            boolean var8;
            Object[] var10;
            CheckManager var10000;
            String var10001;
            boolean var10002;
            StringBuilder var10005;
            boolean var10006;
            if (var7) {
               var8 = var6.isAlert();
               var10000 = CheckManager.getInstance();
               if (!var8) {
                  var10002 = true;
               } else {
                  var10002 = false;
               }

               var10000.setEnabled(var5, var10002);
               var10001 = FORMAT;
               var10 = new Object[]{"Alert", ChatColor.WHITE + var3 + " " + var2[1] + VerusPlugin.COLOR, ChatColor.WHITE + String.valueOf(var8) + VerusPlugin.COLOR, null};
               var10005 = (new StringBuilder()).append(ChatColor.WHITE);
               if (!var8) {
                  var10006 = true;
               } else {
                  var10006 = false;
               }

               var10[3] = var10005.append(String.valueOf(var10006)).append(VerusPlugin.COLOR).toString();
               var1.sendMessage(String.format(var10001, var10));
            } else {
               var8 = var6.isPunish();
               var10000 = CheckManager.getInstance();
               if (!var8) {
                  var10002 = true;
               } else {
                  var10002 = false;
               }

               var10000.setAutoban(var5, var10002);
               var10001 = FORMAT;
               var10 = new Object[]{"Bannable", ChatColor.WHITE + var3 + " " + var2[1] + VerusPlugin.COLOR, ChatColor.WHITE + String.valueOf(var8) + VerusPlugin.COLOR, null};
               var10005 = (new StringBuilder()).append(ChatColor.WHITE);
               if (!var8) {
                  var10006 = true;
               } else {
                  var10006 = false;
               }

               var10[3] = var10005.append(String.valueOf(var10006)).append(VerusPlugin.COLOR).toString();
               var1.sendMessage(String.format(var10001, var10));
            }
         }

      }
   }
}
