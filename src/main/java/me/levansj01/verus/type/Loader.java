package me.levansj01.verus.type;

import me.levansj01.launcher.VerusLauncher;

public abstract class Loader {
   private static final String username = VerusLauncher.getPlugin().getConfig().getString("launcher.username", "");
   public static String logsCommand = "logs";
   public static String pingCommand = "ping";
   public static String recentlogsCommand = "recentlogs";
   public static String manualbanCommand = "manualban";
   public static String alertsCommand = "alerts";
   public static String checklogsCommand = "checklogs";

   public static String getUsername() {
      return username;
   }

   public static String getLogsCommand() {
      return logsCommand;
   }

   public static String getPingCommand() {
      return pingCommand;
   }

   public static String getRecentlogsCommand() {
      return recentlogsCommand;
   }

   public abstract void load();

   public static String getAlertsCommand() {
      return alertsCommand;
   }

   public static String getChecklogsCommand() {
      return checklogsCommand;
   }

   public static String getManualbanCommand() {
      return manualbanCommand;
   }
}
