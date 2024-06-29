package me.levansj01.verus.command.impl;

import com.google.common.collect.Lists;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.command.BaseArgumentCommand;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Ban;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.storage.database.check.CheckValues;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.java.WordUtils;
import me.levansj01.verus.verus2.data.player.TickerType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class VerusCommand extends BaseArgumentCommand {
   private final String FORMAT;
   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM HH:mm z");
   private static final String CMD_FORMAT;

   private void sendCommands(CommandSender var1) {
      var1.sendMessage(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Commands");
      var1.sendMessage(String.format(CMD_FORMAT, EnumMessage.ALERTS_COMMAND.get(), "Enable/Disable alerts"));
      var1.sendMessage(String.format(CMD_FORMAT, EnumMessage.LOGS_COMMAND.get(), "Obtain player logs"));
      var1.sendMessage(String.format(CMD_FORMAT, EnumMessage.RECENT_LOGS_COMMAND.get(), "Obtain a players' last 20 logs"));
      var1.sendMessage(String.format(CMD_FORMAT, EnumMessage.TOGGLE_CHECK_COMMAND.get(), "Toggle check alerts/bans"));
      if (BukkitUtil.hasPermission(var1, "verus.admin")) {
         var1.sendMessage(String.format(CMD_FORMAT, EnumMessage.MANUAL_BAN_COMMAND.get(), "Manually ban a player using " + VerusPlugin.getNameFormatted()));
      }

      if (StorageEngine.getInstance().getVerusConfig().isPingCommand()) {
         var1.sendMessage(String.format(CMD_FORMAT, EnumMessage.ALERTS_COMMAND.get(), "Displays a players' ping"));
      }

      if (VerusTypeLoader.isCustom()) {
         var1.sendMessage(String.format(CMD_FORMAT, EnumMessage.SET_VL_COMMAND.get(), "Set check ban vl"));
      }

   }

   public VerusCommand() {
      super(VerusPlugin.getName());
      this.FORMAT = ChatColor.GRAY + "- " + VerusPlugin.COLOR + "%s's " + WordUtils.capitalize(EnumMessage.VIOLATIONS.get()) + ": " + ChatColor.WHITE + "%s";
      this.setPermission("verus.staff");
      this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.COMMANDS_ARGUMENT.get(), EnumMessage.COMMANDS_DESCRIPTION.get(), (var1, var2) -> {
         this.sendCommands(var1);
      }));
      this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.RESTART_ARGUMENT.get(), EnumMessage.RESTART_DESCRIPTION.get(), (var1, var2) -> {
         if (!BukkitUtil.hasPermission(var1, "verus.restart")) {
            var1.sendMessage(this.getPermissionMessage());
         } else {
            VerusPlugin.restart();
         }
      }));
      this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.GUI_ARGUMENT.get(), EnumMessage.GUI_DESCRIPTION.get(), (var1, var2) -> {
         if (var1 instanceof ConsoleCommandSender) {
            var1.sendMessage(ChatColor.RED + "This command can only be ran by players.");
         } else if (!BukkitUtil.hasPermission(var1, "verus.staff.gui")) {
            var1.sendMessage(this.getPermissionMessage());
         } else {
            MainGUI var3 = GUIManager.getInstance().getMainGui();
            if (var3 == null) {
               var1.sendMessage(ChatColor.RED + "Failed to open GUI.");
            } else {
               var3.openGui((Player)var1);
            }
         }
      }));
      this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.CHECK_ARGUMENT.get(), EnumMessage.CHECK_DESCRIPTION.get(), "(player)", (var1, var2) -> {
         if (var2.length != 2) {
            this.sendHelp(var1);
         } else {
            StorageEngine var3 = StorageEngine.getInstance();
            if (!var3.isConnected()) {
               var1.sendMessage(ChatColor.RED + "Please connect to a database to use this command.");
            } else {
               Database var4 = var3.getDatabase();
               var4.getUUID(var2[1], (var3x) -> {
                  if (var3x == null) {
                     var1.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NEVER_LOGGED_ON.get());
                  } else {
                     var4.getBans(var3x, (var2x) -> {
                        if (!var2x.iterator().hasNext()) {
                           var1.sendMessage(ChatColor.RED + "This player hasn't been banned.");
                        } else {
                           var1.sendMessage(VerusPlugin.COLOR + var2[1] + "'s ban(s):");
                           Iterator var3 = var2x.iterator();

                           while(var3.hasNext()) {
                              Ban var4 = (Ban)var3.next();
                              var1.sendMessage(ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Date: " + ChatColor.WHITE + DATE_FORMAT.format(new Date(var4.getTimestamp())) + VerusPlugin.COLOR + " Type: " + ChatColor.WHITE + var4.getType() + " " + var4.getSubType());
                           }
                        }

                     });
                  }
               });
            }
         }
      }));
      this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.INFO_ARGUMENT.get(), EnumMessage.INFO_DESCRIPTION.get(), "(player)", (var1, var2) -> {
         if (var2.length != 2) {
            this.sendHelp(var1);
         } else {
            Player var3 = Bukkit.getPlayer(var2[1]);
            if (var3 == null) {
               var1.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
            } else {
               PlayerData var4 = DataManager.getInstance().getPlayer(var3);
               if (var4 == null) {
                  var1.sendMessage(ChatColor.RED + EnumMessage.COMMAND_PLAYER_NOT_FOUND.get());
               } else {
                  BaseComponent[] var5 = null;
                  int var6 = (int)Math.floor(var4.getTotalViolations());
                  if (var6 > 0) {
                     var5 = TextComponent.fromLegacyText(ChatColor.GRAY + ChatColor.ITALIC.toString() + "Hover for violations...");
                     String var7 = WordUtils.capitalize(EnumMessage.VIOLATIONS.get());
                     String var8 = "";
                     Check[] var9 = var4.getCheckData().getChecks();
                     int var10 = var9.length;
                     int var11 = 0;

                     while(var11 < var10) {
                        Check var12 = var9[var11];
                        if (!CheckManager.getInstance().isEnabled(var12)) {
                        } else {
                           int var13 = (int)Math.floor(var12.getViolations());
                           if (var13 > 0) {
                              var8 = var8 + VerusPlugin.COLOR + var12.getType().getName() + " " + var12.getSubType() + " " + var7 + ": " + ChatColor.WHITE + var13 + " \n";
                           }
                        }

                        ++var11;
                     }

                     var8 = var8 + "\n" + VerusPlugin.COLOR + var7 + ": " + ChatColor.WHITE + var6;
                     HoverEvent var15 = new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(var8));
                     Arrays.stream(var5).forEach((var1x) -> {
                        var1x.setHoverEvent(var15);
                     });
                  }

                  var1.sendMessage(var4.getInfo());
                  if (var1 instanceof Player) {
                     Player var14 = (Player)var1;
                     if (var5 != null) {
                        var14.spigot().sendMessage(var5);
                     }

                     if (BukkitUtil.isDev(var14)) {
                        var1.sendMessage(ChatColor.GRAY + "Stats: " + ChatColor.WHITE + String.format("(current=%s) (lag=%s / fast=%s) (teleporting=%s / v2=%s) (keepalive=%s %sms / transaction=%s %sms) (ping=%s / maxping=%s) (spawned=%s / ground=%s / vehicle=%s / fly=%s|%s / survival=%s) (gl=%s|%s)", var4.getTotalTicks(), var4.hasLag(), var4.hasFast(), var4.isTeleporting(), var4.isTeleportingV2(), var4.getLastKeepAlive(), var4.getPing(), var4.getTickerMap().get(TickerType.LAST_SENT_TRANSACTION), var4.getTransactionPing(), var4.getPingTicks(), var4.getMaxPingTicks(), var4.isSpawned(), var4.getLocation().getGround(), var4.isVehicle(), var4.canFly(), var4.isFlying(), var4.isSurvival(), NMSManager.getInstance().isGliding(var14), var4.isGliding()));
                     }
                  }

               }
            }
         }
      }));
      this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.TOP_ARGUMENT.get(), EnumMessage.TOP_DESCRIPTION.get(), (var1, var2) -> {
         List var3 = DataManager.getInstance().getMostViolations();
         boolean var4 = false;
         int var5 = 0;

         do {
            if (var5 >= Math.min(var3.size(), 10)) {
               if (!var4) {
                  var1.sendMessage(ChatColor.RED + "No players with " + EnumMessage.VIOLATIONS.get() + " were found.");
               }

               return;
            }

            PlayerData var6 = (PlayerData)var3.get(var5);
            if (var6 != null && var6.getTotalViolations() > 1.0D) {
               if (var5 == 0) {
                  var1.sendMessage(VerusPlugin.COLOR + "Top Cheaters (" + WordUtils.capitalize(EnumMessage.VIOLATIONS.get()) + "): ");
               }

               String var7 = var6.getName();
               BaseComponent[] var8 = TextComponent.fromLegacyText(String.format(this.FORMAT, var6.getName(), (int)Math.floor(var6.getTotalViolations())));
               HoverEvent var9 = new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(EnumMessage.VIEW_PLAYER_INFO.get().replace("{player}", var7)));
               ClickEvent var10 = new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, "/" + VerusPlugin.getName() + " info " + var7);
               Arrays.stream(var8).forEach((var2x) -> {
                  var2x.setHoverEvent(var9);
                  var2x.setClickEvent(var10);
               });
               if (var1 instanceof Player) {
                  ((Player)var1).spigot().sendMessage(var8);
               } else {
                  var1.sendMessage(TextComponent.toLegacyText(var8));
               }

               var4 = true;
            }

            ++var5;
         } while (true);
      }));
      if (VerusTypeLoader.isDev()) {
         this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.LIST_CMDS_ARGUMENT.get(), EnumMessage.LIST_CMDS_DESCRIPTION.get(), "(checkType) (subType)", (var1, var2) -> {
            if (var2.length != 3) {
               this.sendHelp(var1);
            } else {
               String var3 = var2[1];

               CheckType var4;
               try {
                  var4 = CheckType.valueOf(var3);
               } catch (Throwable var11) {
                  var1.sendMessage(ToggleCheckCommand.validTypesMessage);
                  return;
               }

               String var5 = var4.ordinal() + "" + var2[2];
               CheckValues var6 = CheckManager.getInstance().getValues(var5);
               if (var6 == null) {
                  var1.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
               } else if (!var6.hasCommands()) {
                  var1.sendMessage(ChatColor.RED + "This check does not contain any per-check commands!");
               } else {
                  int var7 = 0;
                  String var8 = VerusPlugin.COLOR + "Commands for " + ChatColor.WHITE + var3 + " " + var2[2] + "\n";
                  Iterator var9 = var6.getCommands().iterator();

                  do {
                     if (!var9.hasNext()) {
                        var1.sendMessage(var8);
                        return;
                     }

                     String var10 = (String)var9.next();
                     var8 = var8 + String.format(ChatColor.GRAY + "- " + VerusPlugin.COLOR + "'%s' %s\n", ChatColor.WHITE + var10, ChatColor.GRAY + ChatColor.ITALIC.toString() + "(Index: " + var7++ + ")");
                  } while (true);
               }
            }
         }));
         this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.ADD_CMD_ARGUMENT.get(), EnumMessage.ADD_CMD_DESCRIPTION.get(), "(checkType) (subType) (command)", (var1, var2) -> {
            if (var2.length < 4) {
               this.sendHelp(var1);
            } else {
               String var3 = var2[1];

               CheckType var4;
               try {
                  var4 = CheckType.valueOf(var3);
               } catch (Throwable var9) {
                  var1.sendMessage(ToggleCheckCommand.validTypesMessage);
                  return;
               }

               String var5 = var4.ordinal() + "" + var2[2];
               CheckValues var6 = CheckManager.getInstance().getValues(var5);
               if (var6 == null) {
                  var1.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
               } else {
                  String[] var7 = new String[var2.length - 3];
                  System.arraycopy(var2, 3, var7, 0, var7.length);
                  String var8 = String.join(" ", var7);
                  if (var8.contains(",")) {
                     var1.sendMessage(ChatColor.RED + "You cannot include ',' in a per-check command!");
                  } else if (var6.hasCommand(var8)) {
                     var1.sendMessage(ChatColor.RED + "This per-check command already exists!");
                  } else {
                     CheckManager.getInstance().addCommand(var6, var8);
                     var1.sendMessage(String.format(VerusPlugin.COLOR + "Added '%s' to %s %s.", ChatColor.WHITE + var8 + VerusPlugin.COLOR, ChatColor.WHITE + var3, var2[2] + VerusPlugin.COLOR));
                  }
               }
            }
         }));
         this.addArgument(new BaseArgumentCommand.CommandArgument(EnumMessage.REMOVE_CMD_ARGUMENT.get(), EnumMessage.REMOVE_CMD_DESCRIPTION.get(), "(checkType) (subType) (index)", (var1, var2) -> {
            if (var2.length != 4) {
               this.sendHelp(var1);
            } else {
               String var3 = var2[1];

               CheckType var4;
               try {
                  var4 = CheckType.valueOf(var3);
               } catch (Throwable var10) {
                  var1.sendMessage(ToggleCheckCommand.validTypesMessage);
                  return;
               }

               String var5 = var4.ordinal() + "" + var2[2];
               CheckValues var6 = CheckManager.getInstance().getValues(var5);
               if (var6 == null) {
                  var1.sendMessage(ChatColor.RED + "A check with that ID does not exist!");
               } else {
                  int var7;
                  try {
                     var7 = Integer.parseInt(var2[3]);
                  } catch (Throwable var9) {
                     var1.sendMessage(ChatColor.RED + "Please enter a valid value!");
                     return;
                  }

                  if (var7 + 1 > var6.getCommandsSize()) {
                     var1.sendMessage(ChatColor.RED + "That per-check command does not exist!");
                  } else {
                     String var8 = var6.getCommand(var7);
                     CheckManager.getInstance().removeCommand(var6, var7);
                     var1.sendMessage(String.format(VerusPlugin.COLOR + "Removed '%s' from %s %s.", ChatColor.WHITE + var8 + VerusPlugin.COLOR, ChatColor.WHITE + var3, var2[2] + VerusPlugin.COLOR));
                  }
               }
            }
         }));
      }

   }

   static {
      CMD_FORMAT = ChatColor.GRAY + "- " + VerusPlugin.COLOR + "/%s: " + ChatColor.WHITE + "%s";
   }

   public List<String> tabComplete(CommandSender var1, String var2, String[] var3) throws IllegalArgumentException {
      if (var3.length == 1 && BukkitUtil.hasPermission(var1, "verus.staff")) {
         ArrayList var11 = Lists.newArrayList();
         StringUtil.copyPartialMatches(var3[0], this.arguments.keySet(), var11);
         Collections.sort(var11);
         return var11;
      } else {
         if (var3.length == 2) {
            String var4 = var3[0];
            if (var4.equalsIgnoreCase(EnumMessage.LIST_CMDS_ARGUMENT.get()) || var4.equalsIgnoreCase(EnumMessage.ADD_CMD_ARGUMENT.get()) || var4.equalsIgnoreCase(EnumMessage.REMOVE_CMD_ARGUMENT.get())) {
               ArrayList var12 = Lists.newArrayList();
               StringUtil.copyPartialMatches(var3[1], ToggleCheckCommand.validCheckTypes, var12);
               Collections.sort(var12);
               return var12;
            }

            if (var3[0].equalsIgnoreCase(EnumMessage.INFO_ARGUMENT.get()) || var3[0].equalsIgnoreCase(EnumMessage.CHECK_ARGUMENT.get())) {
               Player var10000;
               if (var1 instanceof Player) {
                  var10000 = (Player)var1;
               } else {
                  var10000 = null;
               }

               Player var5 = var10000;
               ArrayList var6 = new ArrayList();
               Iterator var7 = var1.getServer().getOnlinePlayers().iterator();

               do {
                  Player var8;
                  String var9;
                  do {
                     if (!var7.hasNext()) {
                        var6.sort(String.CASE_INSENSITIVE_ORDER);
                        return var6;
                     }

                     var8 = (Player)var7.next();
                     var9 = var8.getName();
                  } while(var5 != null && !var5.canSee(var8));

                  String var10 = var3[var3.length - 1];
                  if (StringUtil.startsWithIgnoreCase(var9, var10)) {
                     var6.add(var9);
                  }
               } while (true);
            }
         }

         return super.tabComplete(var1, var2, var3);
      }
   }
}
