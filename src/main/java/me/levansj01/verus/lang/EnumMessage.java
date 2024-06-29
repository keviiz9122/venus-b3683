package me.levansj01.verus.lang;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.type.Loader;
import net.md_5.bungee.api.ChatColor;

public enum EnumMessage {
   CHECK_LOGS_COMMAND("command.commands.name.checklogs", Loader.getChecklogsCommand()),
   LOG_FORMAT("log.format", "({date} | {timestamp}) {player} failed {checkType} Type {subType} VL: {vl} | Ping: {ping}ms Lag: {lag}ms"),
   CHEAT("cheat", "cheat"),
   VIOLATIONS("violations", "violations"),
   REMOVE_CMD_ARGUMENT("command.commands.arguments.removecmd", "removecmd"),
   PLAYER("player", "player"),
   ALERTS_ENABLED_COMMAND("alerts.enabled", "You are now viewing alerts"),
   DISCORD_LOG("discord.log", "```scala\n{log}\n```\n```scala\nClient Version: {version}\nClient Data: {client}\nClient Brand: {brand}\n```"),
   INFO_ARGUMENT("command.commands.arguments.info", "info"),
   LOGS_COMMAND("command.commands.name.logs", Loader.getLogsCommand()),
   INFO_DESCRIPTION("command.commands.info_description", "View useful information about a player"),
   TOGGLE_CHECK_COMMAND("command.commands.name.togglecheck", "togglecheck"),
   PING_OTHER("command.ping.other", VerusPlugin.COLOR + "{player}'s ping is " + ChatColor.GRAY + "{ping}"),
   ADD_CMD_ARGUMENT("command.commands.arguments.addcmd", "addcmd"),
   RECENT_LOGS_COMMAND("command.commands.name.recentlogs", Loader.getRecentlogsCommand()),
   DISCORD_BAN("discord.ban", "```scala\nPlayer: {player}\nDetected By: {checkType} {subType}\nClient Version: {version}\nClient Data: {client}\nClient Brand: {brand}\n```"),
   ADD_CMD_DESCRIPTION("command.commands.addcmd_description", "Add a per-check command"),
   REMOVE_CMD_DESCRIPTION("command.commands.removecmd_description", "Remove a per-check command"),
   CHECK_ARGUMENT("command.commands.arguments.check", "check"),
   GUI_DESCRIPTION("command.commands.gui_description", "View and control " + VerusPlugin.getNameFormatted()),
   CHECK_CMDS_COMMAND("command.commands.name.checkcmds", "checkcmds"),
   SET_VL_COMMAND("command.commands.name.setvl", "setvl"),
   COMMAND_PLAYER_NEVER_LOGGED_ON("command.player_never_logged_on", "This player has never logged onto the server"),
   LANG_COMMAND("command.commands.name.lang", "reloadlang"),
   TOP_DESCRIPTION("command.commands.top_description", "Gather players with the most violations"),
   LIST_CMDS_ARGUMENT("command.commands.arguments.listcmds", "listcmds"),
   PING_SELF("command.ping.self", VerusPlugin.COLOR + "Your ping is " + ChatColor.GRAY + "{ping}"),
   RESTART_DESCRIPTION("command.commands.restart_description", "Restart and automatically update"),
   ALERTS_COMMAND("command.commands.name.alerts", Loader.getAlertsCommand()),
   LIST_CMDS_DESCRIPTION("command.commands.listcmds_description", "View per-check commands"),
   COMMANDS_DESCRIPTION("command.commands.commands_description", "View all " + VerusPlugin.getNameFormatted() + " related commands"),
   PING_COMMAND("command.commands.name.ping", Loader.getPingCommand()),
   RESTART_ARGUMENT("command.commands.arguments.restart", "restart"),
   GUI_ARGUMENT("command.commands.arguments.gui", "gui"),
   COMMANDS_ARGUMENT("command.commands.arguments.commands", "commands"),
   UPDATE_CONFIG_COMMAND("command.commands.name.updateconfig", "updateconfig"),
   COMMAND_PLAYER_NOT_FOUND("command.player_not_found", "Player not found"),
   VIEW_PLAYER_INFO("command.top.view_player_info", "Click to view {player}'s info"),
   FOCUS_COMMAND("command.commands.name.vfocus", "vfocus"),
   PLAYER_NO_LOGS("command.logs.nologs", "This player has no logs."),
   MANUAL_BAN_COMMAND("command.commands.name.manualban", Loader.getManualbanCommand()),
   ALERTS_DISABLED_COMMAND("alerts.disabled", "You are no longer viewing alerts"),
   COMMAND_PERMISSION("command.permission", ChatColor.RED + "You do not have permission to do this."),
   TOP_ARGUMENT("command.commands.arguments.top", "top"),
   CHECK_DESCRIPTION("command.commands.check_description", "Check a players' " + VerusPlugin.getNameFormatted() + " related bans");

   private final String location;
   private final String fallback;
   private String value;

   private EnumMessage(String var3, String var4) {
      this.location = var3;
      this.fallback = var4;
   }


   public void setValue(String var1) {
      this.value = var1;
   }

   public String get() {
      return this.value == null ? this.fallback : this.value;
   }

   public String getFallback() {
      return this.fallback;
   }

   public String getLocation() {
      return this.location;
   }
}
