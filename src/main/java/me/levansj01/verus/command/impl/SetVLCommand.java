package me.levansj01.verus.command.impl;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.data.CheckData;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.util.BukkitUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class SetVLCommand extends BaseCommand {

   public void execute(CommandSender var1, String[] var2) {
      if (var2.length >= 2 && var2.length <= 3) {
         if (BukkitUtil.hasPermission(var1, this.getPermission()) || BukkitUtil.hasPermission(var1, "verus.admin")) {
            String var3 = var2[0];

            CheckType var4;
            try {
               var4 = CheckType.valueOf(var3);
            } catch (Throwable var14) {
               var1.sendMessage(ToggleCheckCommand.validTypesMessage);
               return;
            }

            String var5 = var4.ordinal() + "" + var2[1];
            CheckValues var6 = CheckManager.getInstance().getValues(var5);
            if (var6 == null) {
               var1.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
               return;
            }

            int var7 = var6.getMaxViolation();
            if (var2.length == 2) {
               var1.sendMessage(String.format(VerusPlugin.COLOR + "Max violations for %s: %s", ChatColor.WHITE + var3 + " " + var2[1] + VerusPlugin.COLOR, ChatColor.WHITE + String.valueOf(var7)));
               return;
            }

            int var8;
            try {
               int var9 = Integer.parseInt(var2[2]);
               var8 = var9;
            } catch (Throwable var13) {
               var1.sendMessage(ChatColor.RED + "Please enter a valid violation value.");
               return;
            }

            if (var8 < 5) {
               var1.sendMessage(ChatColor.RED + "Max violation must be greater than 4.");
               return;
            }

            Iterator var15 = DataManager.getInstance().getPlayers().iterator();

            while(var15.hasNext()) {
               PlayerData var10 = (PlayerData)var15.next();
               CheckData var11 = var10.getCheckData();
               Check var12 = (Check)var11.getByIdentifier().get(var5);
               if (var12 != null) {
                  var12.setMaxViolation(var8);
               }
            }

            CheckManager.getInstance().setMaxViolation(var5, var8);
            var1.sendMessage(String.format(VerusPlugin.COLOR + "Updated violations for %s from %s to %s", ChatColor.WHITE + var3 + " " + var2[1] + VerusPlugin.COLOR, ChatColor.WHITE + String.valueOf(var7) + VerusPlugin.COLOR, ChatColor.WHITE + String.valueOf(var8) + VerusPlugin.COLOR));
         }

      } else {
         var1.sendMessage(this.getUsageMessage());
      }
   }

   public List<String> tabComplete(CommandSender var1, String var2, String[] var3) throws IllegalArgumentException {
      if (var3.length == 1 && BukkitUtil.hasPermission(var1, "verus.staff.vl")) {
         ArrayList var4 = Lists.newArrayList();
         StringUtil.copyPartialMatches(var3[0], ToggleCheckCommand.validCheckTypes, var4);
         Collections.sort(var4);
         return var4;
      } else {
         return super.tabComplete(var1, var2, var3);
      }
   }

   public SetVLCommand() {
      super(EnumMessage.SET_VL_COMMAND.get());
      this.setPermission("verus.staff.vl");
      this.setUsageMessage(ChatColor.RED + "Usage: /setvl <checkType> <subType> <violations>");
   }
}
