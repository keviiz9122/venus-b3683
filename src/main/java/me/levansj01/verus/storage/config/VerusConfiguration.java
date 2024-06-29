package me.levansj01.verus.storage.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.storage.database.pool.ConnectionType;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.mongodb.MongoCredential;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class VerusConfiguration {
   private YamlConfiguration checkConfiguration = new YamlConfiguration();
   private String[] banMessage = new String[]{"&9&lVerus &8> &f{player} &7has been removed from the network"};
   private boolean vanishPing;
   private boolean checkPersistence;
   private boolean ignoreLag = true;
   private boolean heavyChecks = true;
   private int pingTimeout;
   private boolean fixSlimeBlocks;
   private String mongoDatabase;
   private boolean persistence = true;
   private File file;
   private boolean heavyPullback;
   private String anticheatName;
   private boolean butterflyBans;
   private List<String> banCommands;
   private boolean directMemory;
   private boolean pingCommand;
   private boolean mongoAuthEnabled;
   private boolean butterflyAlerts = true;
   private String alertMessage;
   private MongoCredential mongoCredential;
   private int persistenceSeconds;
   private boolean geyser;
   private int sqlPort;
   private String mongoHost;
   private int persistenceMins;
   private int logsCleanupDays;
   private boolean discordBans;
   private boolean banAnnouncement;
   private int bansCleanupDays;
   private boolean alertClick = true;
   private boolean bypassEnabled;
   private String sqlUsername;
   private boolean blockLog;
   private boolean sendStats = true;
   private boolean databaseCheckPersistence;
   private boolean mongoEnabled;
   private boolean announcement;
   private boolean schemBans = true;
   private String alertClickCommand;
   private String checkCommandsDatabaseName = "checkCommands";
   private int sqlPushSeconds;
   private File checkFile;
   private boolean pingKickCheck = true;
   private String announcementMessage;
   private boolean hardDisable = true;
   private String recentlogsMessage;
   private boolean sqlEnabled;
   private String alertClickHover;
   private boolean unloadedChunkFix = true;
   private String geyserPrefix = "*";
   private boolean bypassAlerts = true;
   private String sqlDatabase;
   private String secretKeyPath;
   private boolean discordLogs;
   private ConnectionType sqlConnectionType;
   private boolean scrambleBanId;
   private int heavyTicks;
   private YamlConfiguration configuration = new YamlConfiguration();
   private String messagingChannel;
   private boolean randomBanId;
   private String sqlHost;
   private String sqlPassword;
   private boolean slimePushFix;
   private boolean cleanup = true;
   private String alertCertainty;
   private String bypassPermission = "verus.bypass";
   private int sqlPool;
   private boolean bungeeBans;
   private YamlConfiguration banConfiguration = new YamlConfiguration();
   private boolean moreTransactions = VerusTypeLoader.isEnterprise();
   private boolean compression;
   private String sqlDatabaseType;
   private boolean pingKick = true;
   private boolean autoBan = true;
   private boolean schem = true;
   private String discordBansUrl;
   private String checkDatabaseName = "checks";
   private String discordLogsUrl;
   private int mongoPort;
   private int maxClicksPerSecond;

   public void setCheckPersistence(boolean var1) {
      this.checkPersistence = var1;
   }

   public void setBypassAlerts(boolean var1) {
      this.bypassAlerts = var1;
   }

   public int getPersistenceSeconds() {
      return this.persistenceSeconds;
   }

   public void setAlertCertainty(String var1) {
      this.alertCertainty = var1;
   }

   public String getRecentlogsMessage() {
      return this.recentlogsMessage;
   }

   public boolean isScrambleBanId() {
      return this.scrambleBanId;
   }

   public void setButterflyAlerts(boolean var1) {
      this.butterflyAlerts = var1;
   }

   public boolean isUnloadedChunkFix() {
      return this.unloadedChunkFix;
   }

   public File getFile() {
      return this.file;
   }

   public boolean isMongoEnabled() {
      return this.mongoEnabled;
   }

   public void loadConfig(YamlConfiguration var1, File var2) {
      try {
         var1.load(var2);
      } catch (Throwable var4) {
         VerusLauncher.getPlugin().getLogger().severe("Failed to load " + var2.getName() + " " + var4.getClass().getSimpleName() + ": " + var4.getMessage());
         return;
      }
   }

   public void readConfig() {
      Logger var1 = VerusLauncher.getPlugin().getLogger();
      this.anticheatName = this.configuration.getString("verus.name", "&9&lVerus");
      this.alertCertainty = this.configuration.getString("verus.alerts.certainty", "&7failed");
      this.alertMessage = this.configuration.getString("verus.alerts.message", "{name} &8> &f{player} &7{certainty} &f{cheat} &7VL[&9{vl}&7]");
      this.alertClick = this.configuration.getBoolean("verus.alerts.click.enabled", this.alertClick);
      this.alertClickCommand = this.configuration.getString("verus.alerts.click.command", "/tp {player}");
      this.alertClickHover = this.configuration.getString("verus.alerts.click.hover", "Click to teleport to {player}.");
      this.banCommands = this.readList(this.configuration.get("verus.bans.commands", new String[]{"ban {player} &9&lVerus &8> &fCheating"}));
      this.banCommands = (List)this.banCommands.stream().map((var0) -> {
         return ChatColor.translateAlternateColorCodes('&', var0);
      }).collect(Collectors.toList());
      Object var2 = this.configuration.get("verus.bans.message", this.banMessage);
      if (var2 instanceof String) {
         this.banMessage = new String[]{(String)var2};
      } else if (var2 instanceof List) {
         this.banMessage = (String[])((List)var2).toArray(new String[0]);
      } else {
         this.banMessage = (String[])var2;
      }

      this.autoBan = this.configuration.getBoolean("verus.bans.enabled", this.autoBan);
      this.banAnnouncement = this.configuration.getBoolean("verus.bans.announce", this.banAnnouncement);
      this.randomBanId = this.configuration.getBoolean("verus.bans.randomize", this.randomBanId);
      this.scrambleBanId = this.configuration.getBoolean("verus.bans.scramble", this.scrambleBanId);
      this.recentlogsMessage = this.configuration.getString("verus.recentlogs.message", "{time} {name} &7Failed &f{type} Type {subType} &7VL: &f{vl} &7P: &f{ping}");
      this.geyser = this.configuration.getBoolean("verus.geyser.enabled", this.geyser);
      this.geyserPrefix = this.configuration.getString("verus.geyser.prefix", this.geyserPrefix);
      this.bungeeBans = this.configuration.getBoolean("verus.bungee.bans", this.bungeeBans);
      this.secretKeyPath = this.configuration.getString("verus.bungee.secretKeyPath", this.secretKeyPath);
      this.messagingChannel = this.configuration.getString("verus.bungee.messagingChannel", this.messagingChannel);
      this.bypassEnabled = this.configuration.getBoolean("verus.permissions.bypass.enabled", this.bypassEnabled);
      this.bypassPermission = this.configuration.getString("verus.permissions.bypass.permission", this.bypassPermission);
      this.bypassAlerts = this.configuration.getBoolean("verus.permissions.bypass.alerts", this.bypassAlerts);
      this.persistence = this.configuration.getBoolean("verus.checks.persistence.enabled", this.persistence);
      this.persistenceMins = this.configuration.getInt("verus.checks.persistence.mins", this.persistenceMins);
      this.persistenceSeconds = this.configuration.getInt("verus.checks.persistence.seconds", this.persistenceSeconds);
      this.schemBans = this.configuration.getBoolean("verus.checks.schemprinter.bans", this.schemBans);
      this.schem = this.configuration.getBoolean("verus.checks.schemprinter.enabled", this.schem);
      this.butterflyBans = this.configuration.getBoolean("verus.checks.butterflyclicking.bans", this.butterflyBans);
      this.butterflyAlerts = this.configuration.getBoolean("verus.checks.butterflyclicking.enabled", this.butterflyAlerts);
      this.heavyChecks = this.configuration.getBoolean("verus.checks.heavy.enabled", this.heavyChecks);
      this.heavyTicks = this.configuration.getInt("verus.checks.heavy.ticks", this.heavyTicks);
      this.heavyPullback = this.configuration.getBoolean("verus.checks.heavy.pullback", this.heavyPullback);
      this.hardDisable = this.configuration.getBoolean("verus.checks.hardDisable.enabled");
      this.pingKick = this.configuration.getBoolean("verus.checks.pingKick.enabled", this.pingKick);
      this.pingKickCheck = this.configuration.getBoolean("verus.checks.pingKick.combat", this.pingKickCheck);
      this.pingTimeout = this.configuration.getInt("verus.checks.pingKick.timeout", this.pingTimeout);
      this.pingTimeout = Math.max(15, Math.min(45, this.pingTimeout));
      this.slimePushFix = this.configuration.getBoolean("verus.checks.speed.slimePushFix", this.slimePushFix);
      boolean var10001;
      if (!VerusTypeLoader.isEnterprise() && !this.configuration.getBoolean("verus.checks.reach.moreTransactions", this.moreTransactions)) {
         var10001 = false;
      } else {
         var10001 = true;
      }

      this.moreTransactions = var10001;
      this.ignoreLag = this.configuration.getBoolean("verus.checks.reach.ignoreLag", this.ignoreLag);
      this.unloadedChunkFix = this.configuration.getBoolean("verus.checks.fly.unloadedChunkFix", this.unloadedChunkFix);
      this.fixSlimeBlocks = this.configuration.getBoolean("verus.checks.fly.fixSlimeBlocks", this.fixSlimeBlocks);
      this.maxClicksPerSecond = this.configuration.getInt("verus.checks.autoclicker.maxcps", this.maxClicksPerSecond);
      if (!this.schemBans || !this.schem) {
         var1.warning("Disabling Schematica Printer checks may also allow scaffold and other cheats to bypass");
      }

      this.pingCommand = this.configuration.getBoolean("verus.commands.ping.enabled", this.pingCommand);
      this.vanishPing = this.configuration.getBoolean("verus.commands.ping.vanish", this.vanishPing);
      this.cleanup = this.configuration.getBoolean("verus.database.cleanup.enabled", this.cleanup);
      this.logsCleanupDays = this.configuration.getInt("verus.database.cleanup.logs.days", this.logsCleanupDays);
      this.bansCleanupDays = this.configuration.getInt("verus.database.cleanup.bans.days", this.bansCleanupDays);
      this.mongoEnabled = this.configuration.getBoolean("verus.mongo.enabled", this.mongoEnabled);
      this.mongoHost = this.configuration.getString("verus.mongo.host", this.mongoHost);
      this.mongoPort = this.configuration.getInt("verus.mongo.port", this.mongoPort);
      this.mongoDatabase = this.configuration.getString("verus.mongo.database", this.mongoDatabase);
      this.mongoAuthEnabled = this.configuration.getBoolean("verus.mongo.auth.enabled", this.mongoAuthEnabled);
      if (this.mongoAuthEnabled) {
         String var3 = this.configuration.getString("verus.mongo.auth.username", "verus");
         String var4 = this.configuration.getString("verus.mongo.auth.password", "");
         String var5 = this.configuration.getString("verus.mongo.auth.database", this.mongoDatabase);
         this.mongoCredential = MongoCredential.createCredential(var3, var5, var4.toCharArray());
      }

      this.sqlEnabled = this.configuration.getBoolean("verus.sql.enabled", this.sqlEnabled);
      this.sqlHost = this.configuration.getString("verus.sql.host", this.sqlHost);
      this.sqlPushSeconds = this.configuration.getInt("verus.sql.push.seconds", this.sqlPushSeconds);
      this.sqlPort = this.configuration.getInt("verus.sql.port", this.sqlPort);
      this.sqlPool = this.configuration.getInt("verus.sql.pool", this.sqlPool);
      this.sqlDatabase = this.configuration.getString("verus.sql.database", this.sqlDatabase);
      this.sqlDatabaseType = this.configuration.getString("verus.sql.type", this.sqlDatabaseType);
      this.sqlUsername = this.configuration.getString("verus.sql.auth.username", this.sqlUsername);
      this.sqlPassword = this.configuration.getString("verus.sql.auth.password", this.sqlPassword);
      this.discordLogs = this.configuration.getBoolean("verus.discord.logs.enabled", this.discordLogs);
      this.discordLogsUrl = this.configuration.getString("verus.discord.logs.url", this.discordLogsUrl);
      this.discordBans = this.configuration.getBoolean("verus.discord.bans.enabled", this.discordBans);
      this.discordBansUrl = this.configuration.getString("verus.discord.bans.url", this.discordBansUrl);
      if (this.heavyPullback && NMSManager.getInstance().getServerVersion() != ServerVersion.v1_8_R3) {
         this.heavyPullback = false;
         var1.warning("Heavy pullback is only available on 1.8.8 server version");
      }

      label75: {
         try {
            this.sqlConnectionType = ConnectionType.valueOf(this.sqlDatabaseType.toUpperCase());
         } catch (IllegalArgumentException var6) {
            var1.warning("Invalid SQL type: " + this.sqlDatabaseType + ", reverting to MySQL");
            this.sqlConnectionType = ConnectionType.MYSQL;
            break label75;
         }
      }

      if (!this.moreTransactions) {
         if (this.ignoreLag) {
            var1.severe("ignoreLag is enabled but moreTransactions is disabled, this may cause reach false positives.");
         } else {
            var1.info("moreTransactions is disabled, sending more transactions may improve the accuracy of reach checks");
         }
      }

      if (!this.heavyChecks) {
         var1.warning("Heavy checks are not enabled, it is recommended that you enable this if you wish to block spoof-ground bypasses");
      }

      if (!this.pingKick && !this.pingKickCheck) {
         var1.warning("Ping kick/combat is not enabled, it is recommended that you enable it to avoid ping spoof bypasses");
      }

      if (!this.mongoEnabled && !this.sqlEnabled) {
         if (this.persistence) {
            var1.info("You must be connected to a database in order to use check data persistence");
            this.persistence = false;
         }
      } else if (!this.persistence) {
         var1.warning("Check persistence is not enabled, it is recommended that you enable it to avoid relog bypasses");
      }

   }

   public boolean isFixSlimeBlocks() {
      return this.fixSlimeBlocks;
   }

   public int getLogsCleanupDays() {
      return this.logsCleanupDays;
   }

   public String getSqlDatabaseType() {
      return this.sqlDatabaseType;
   }

   public void setCompression(boolean var1) {
      this.compression = var1;
   }

   public String getAlertClickHover() {
      return this.alertClickHover;
   }

   public void setPingTimeout(int var1) {
      this.pingTimeout = var1;
   }

   public void setMongoEnabled(boolean var1) {
      this.mongoEnabled = var1;
   }

   public boolean isBlockLog() {
      return this.blockLog;
   }

   public void setCleanup(boolean var1) {
      this.cleanup = var1;
   }

   public boolean isBanAnnouncement() {
      return this.banAnnouncement;
   }

   public int getSqlPushSeconds() {
      return this.sqlPushSeconds;
   }

   public void setAlertClickCommand(String var1) {
      this.alertClickCommand = var1;
   }

   public boolean isButterflyBans() {
      return this.butterflyBans;
   }

   public boolean isHeavyPullback() {
      return this.heavyPullback;
   }

   public void setMongoCredential(MongoCredential var1) {
      this.mongoCredential = var1;
   }

   public YamlConfiguration getCheckConfiguration() {
      return this.checkConfiguration;
   }

   public String getSqlDatabase() {
      return this.sqlDatabase;
   }

   public String getBypassPermission() {
      return this.bypassPermission;
   }

   public String getMongoDatabase() {
      return this.mongoDatabase;
   }

   public String getCheckCommandsDatabaseName() {
      return this.checkCommandsDatabaseName;
   }

   public boolean isDiscordLogs() {
      return this.discordLogs;
   }

   public int getPingTimeout() {
      return this.pingTimeout;
   }

   public void setHeavyChecks(boolean var1) {
      this.heavyChecks = var1;
   }

   public boolean isDirectMemory() {
      return this.directMemory;
   }

   public boolean isPingCommand() {
      return this.pingCommand;
   }

   public void setSqlPassword(String var1) {
      this.sqlPassword = var1;
   }

   public void setDiscordLogs(boolean var1) {
      this.discordLogs = var1;
   }

   public void setSchem(boolean var1) {
      this.schem = var1;
   }

   public String getSqlPassword() {
      return this.sqlPassword;
   }

   public void setCheckDatabaseName(String var1) {
      this.checkDatabaseName = var1;
   }

   public boolean isBungeeBans() {
      return this.bungeeBans;
   }

   public boolean isRandomBanId() {
      return this.randomBanId;
   }

   public void setAlertClickHover(String var1) {
      this.alertClickHover = var1;
   }

   public void setPersistence(boolean var1) {
      this.persistence = var1;
   }

   public boolean isMoreTransactions() {
      return this.moreTransactions;
   }

   public void setPersistenceSeconds(int var1) {
      this.persistenceSeconds = var1;
   }

   public boolean isSlimePushFix() {
      return this.slimePushFix;
   }

   public void setButterflyBans(boolean var1) {
      this.butterflyBans = var1;
   }

   public boolean isAnnouncement() {
      return this.announcement;
   }

   public int getSqlPort() {
      return this.sqlPort;
   }

   public long getPersistenceMillis() {
      return TimeUnit.MINUTES.toMillis((long)this.persistenceMins) + TimeUnit.SECONDS.toMillis((long)this.persistenceSeconds);
   }

   public void setSqlPort(int var1) {
      this.sqlPort = var1;
   }

   public void setSqlUsername(String var1) {
      this.sqlUsername = var1;
   }

   public File getCheckFile() {
      return this.checkFile;
   }

   public boolean isAutoBan() {
      return this.autoBan;
   }

   public void setScrambleBanId(boolean var1) {
      this.scrambleBanId = var1;
   }

   public boolean isDiscordBans() {
      return this.discordBans;
   }

   public String getDiscordLogsUrl() {
      return this.discordLogsUrl;
   }

   public void setBansCleanupDays(int var1) {
      this.bansCleanupDays = var1;
   }

   public boolean isSqlEnabled() {
      return this.sqlEnabled;
   }

   public void setSchemBans(boolean var1) {
      this.schemBans = var1;
   }

   public void saveConfig(YamlConfiguration var1, File var2) {
      try {
         var1.save(var2);
      } catch (Throwable var4) {
         VerusLauncher.getPlugin().getLogger().severe("Failed to save " + var2.getName() + " " + var4.getClass().getSimpleName() + ": " + var4.getMessage());
         return;
      }
   }

   public void setVanishPing(boolean var1) {
      this.vanishPing = var1;
   }

   public void setSqlConnectionType(ConnectionType var1) {
      this.sqlConnectionType = var1;
   }

   public void setHeavyPullback(boolean var1) {
      this.heavyPullback = var1;
   }

   public void setHardDisable(boolean var1) {
      this.hardDisable = var1;
   }

   public void setSecretKeyPath(String var1) {
      this.secretKeyPath = var1;
   }

   public void setMongoHost(String var1) {
      this.mongoHost = var1;
   }

   public boolean isPersistence() {
      return this.persistence;
   }

   public void setBanConfiguration(YamlConfiguration var1) {
      this.banConfiguration = var1;
   }

   public boolean isPingKick() {
      return this.pingKick;
   }

   public void setIgnoreLag(boolean var1) {
      this.ignoreLag = var1;
   }

   public void setSqlDatabase(String var1) {
      this.sqlDatabase = var1;
   }

   public void setPingKick(boolean var1) {
      this.pingKick = var1;
   }

   public void setDiscordBans(boolean var1) {
      this.discordBans = var1;
   }

   public boolean isButterflyAlerts() {
      return this.butterflyAlerts;
   }

   public void setFixSlimeBlocks(boolean var1) {
      this.fixSlimeBlocks = var1;
   }

   public void setUnloadedChunkFix(boolean var1) {
      this.unloadedChunkFix = var1;
   }

   public String getSqlHost() {
      return this.sqlHost;
   }

   public void setGeyserPrefix(String var1) {
      this.geyserPrefix = var1;
   }

   public int getSqlPool() {
      return this.sqlPool;
   }

   public void setSqlPushSeconds(int var1) {
      this.sqlPushSeconds = var1;
   }

   public void setSendStats(boolean var1) {
      this.sendStats = var1;
   }

   public void saveConfig() {
      this.saveConfig(this.configuration, this.file);
   }

   public void setDiscordLogsUrl(String var1) {
      this.discordLogsUrl = var1;
   }

   public String getAlertCertainty() {
      return this.alertCertainty;
   }

   public boolean isSendStats() {
      return this.sendStats;
   }

   public void setDatabaseCheckPersistence(boolean var1) {
      this.databaseCheckPersistence = var1;
   }

   public void setRandomBanId(boolean var1) {
      this.randomBanId = var1;
   }

   public void enable() {
      this.setupConfig();
      this.readConfig();
   }

   public void setCheckConfiguration(YamlConfiguration var1) {
      this.checkConfiguration = var1;
   }

   public <T> List<T> readList(Object var1) {
      return var1 instanceof List ? (List)var1 : Arrays.asList((Object[])var1);
   }

   public int getMaxClicksPerSecond() {
      return this.maxClicksPerSecond;
   }

   public void setBanMessage(String[] var1) {
      this.banMessage = var1;
   }

   public String[] getBanMessage() {
      return this.banMessage;
   }

   public VerusConfiguration() {
      boolean var10001;
      if (!VerusTypeLoader.isEnterprise()) {
         var10001 = true;
      } else {
         var10001 = false;
      }

      this.randomBanId = var10001;
      this.secretKeyPath = "plugins" + File.separator + "Verus" + File.separator + "secretKey";
      String var1;
      if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2)) {
         var1 = "bungeecord:main";
      } else {
         var1 = "BungeeCord";
      }

      this.messagingChannel = var1;
      this.persistenceMins = 60;
      this.logsCleanupDays = 90;
      this.bansCleanupDays = 730;
      this.pingTimeout = 20;
      this.heavyTicks = 5;
      this.maxClicksPerSecond = 20;
      this.mongoHost = "localhost";
      this.mongoDatabase = "Verus";
      this.mongoPort = 27017;
      this.sqlUsername = "verus";
      this.sqlPassword = "";
      this.sqlHost = "localhost";
      this.sqlDatabase = "Verus";
      this.sqlDatabaseType = ConnectionType.MYSQL.name();
      this.sqlPushSeconds = 10;
      this.sqlPort = 3306;
      this.sqlPool = 4;
      this.discordLogsUrl = "";
      this.discordBansUrl = "";
   }

   public void setDiscordBansUrl(String var1) {
      this.discordBansUrl = var1;
   }

   public int getHeavyTicks() {
      return this.heavyTicks;
   }

   public boolean isSchemBans() {
      return this.schemBans;
   }

   public void setAnnouncementMessage(String var1) {
      this.announcementMessage = var1;
   }

   public boolean isSchem() {
      return this.schem;
   }

   public boolean isBypassAlerts() {
      return this.bypassAlerts;
   }

   public void setMongoAuthEnabled(boolean var1) {
      this.mongoAuthEnabled = var1;
   }

   public int getMongoPort() {
      return this.mongoPort;
   }

   public void setMessagingChannel(String var1) {
      this.messagingChannel = var1;
   }

   public YamlConfiguration getConfiguration() {
      return this.configuration;
   }

   public void setMoreTransactions(boolean var1) {
      this.moreTransactions = var1;
   }

   public boolean isCheckPersistence() {
      return this.checkPersistence;
   }

   public boolean isGeyser() {
      return this.geyser;
   }

   public void setBanAnnouncement(boolean var1) {
      this.banAnnouncement = var1;
   }

   public void setHeavyTicks(int var1) {
      this.heavyTicks = var1;
   }

   public void setMaxClicksPerSecond(int var1) {
      this.maxClicksPerSecond = var1;
   }

   public void setAlertClick(boolean var1) {
      this.alertClick = var1;
   }

   public boolean isHardDisable() {
      return this.hardDisable;
   }

   public void setSqlDatabaseType(String var1) {
      this.sqlDatabaseType = var1;
   }

   public YamlConfiguration getBanConfiguration() {
      return this.banConfiguration;
   }

   public void setPingCommand(boolean var1) {
      this.pingCommand = var1;
   }

   public boolean isBypassEnabled() {
      return this.bypassEnabled;
   }

   public void setSqlHost(String var1) {
      this.sqlHost = var1;
   }

   public String getMongoHost() {
      return this.mongoHost;
   }

   public boolean isMongoAuthEnabled() {
      return this.mongoAuthEnabled;
   }

   public String getGeyserPrefix() {
      return this.geyserPrefix;
   }

   public void setBypassPermission(String var1) {
      this.bypassPermission = var1;
   }

   private void setupConfig() {
      VerusLauncher var1 = VerusLauncher.getPlugin();
      this.file = new File(var1.getDataFolder(), "verus.yml");
      this.checkFile = new File(var1.getDataFolder(), "checks.yml");
      this.loadConfig(this.configuration, this.file);
      this.loadConfig(this.checkConfiguration, this.checkFile);
      this.checkConfiguration.options().copyHeader(true).header("Please do not edit this configuration, please use /verus gui to configure checks");
      this.configuration.options().copyDefaults(true).copyHeader(true).header("Verus - Unauthorized distribution of this program could lead to legal prosecution\n\n-------------------------------------------------------------------------------------\nname - Display name of anti-cheat in alert messages\nsendStats - Controls whether stats are sent to our session server\n\nalerts certainty - Placeholder in alert message\nalerts message - Alert message\nalerts click enabled - Whether alerts should be clickable\nalerts click command - Command executed when alert is clicked\nalerts click hover - Message shown when alerts are hovered over\n\nbans commands - Commands executed when a player is banned\nbans message - Ban announcement message which is broadcast to the server\nbans enabled - Whether bans are enabled\nbans announce - Whether bans should be announced and broadcast to the server\nbans randomize - Generate a random Cheat-ID e.g Cheat-5A\nbans scramble - Generate a scrambled Cheat-ID e.g Cheat-J7Dq\n\nrecentlogs message - Message format of /recentlogs command\n\ngeyser enable - Whether Verus should ignore players with the geyser prefix\ngeyser prefix - The player name prefix which Verus should ignore players with\n\nbungee bans - If you have VerusLink installed on BungeeCord, you may enable this so that the ban commands are executed on BungeeCord instead of the server\nbungee secretKeyPath - Path of secret key used in VerusLink\nbungee messagingChannel - Plugin messaging channel used by VerusLink\n\npermissions bypass enabled - Whether the bypass permission (which stops a player being banned) should be enabled\npermissions bypass permission - Permission node of bypass permission\npermissions bypass alerts - Whether bypassing players should set off alerts\n\ncommands ping enabled - Whether the /ping command should be enabled and registered\ncommands ping vanish - If enabled, players will not be able to check ping for vanished players\n\nchecks persistence enabled - Whether check persistence should be enabled, this means that VLs are saved so when a player relogs or switches servers their VLs are preserved\nchecks persistence mins - How many minutes should violation data be saved for \nchecks persistence seconds - How many seconds should violation data be saved for \n\nchecks schemprinter bans - Whether checks which are flagged by schematica printer should ban\nchecks schemprinter alerts - Whether checks which are flagged by schematica printer should alert\n\nchecks butterflyclicking bans - Whether checks which can be flagged by butterfly clicking should ban\nchecks butterflyclicking alerts - Whether checks which can be flagged by butterfly clicking should alert\n\nchecks heavy enabled - Whether checks which can be more performance heavy should be enabled\nchecks heavy ticks - How often in client ticks (50ms) should the server check the player for ground spoof in Fly 4A\n\nchecks hardDisable enabled - Whether checks which have both alerts and bans disabled should not be loaded in when a player joins\n\nchecks pingKick enabled - Whether players with incredibly high ping should be kicked from the server, this prevents ping spoofers from disabling parts of the anticheat\nchecks pingKick combat - Whether BadPackets Type G7 should be enabled, so when a player attacks with impossibly high ping they are flagged\nchecks pingKick timeout - The time in seconds (1s = 1000ms) after which a player should be kicked\n\nchecks speed modifier item - Whether speed checks should check the players current item for NMS modifiers which affect player speed\nchecks speed modifier armor - Whether speed checks should check the players armor for NMS modifiers\nchecks speed slimePushFix - Whether players that have been pushed by a slime should be ignored by Speed A, if disabled falses could be caused\n\nchecks reach moreTransactions - Whether more transaction packets should be sent to the client to improve reach accuracy\nchecks reach ignoreLag - Whether players which are lagging should be ignored, allows for the disabling of some reach checks\n\nchecks fly unloadedChunkFix - Whether players which are moving as if they are in an unloaded chunk should be ignored by Fly Type I, allows for a glide bypass.\nchecks fly fixSlimeBlocks - Whether slime block velocity should be same as default, can create self damage bypasses.\n\nchecks autoclicker maxcps - The amount of cps before AutoClicker X will flag\n\ndatabase cleanup enabled - Whether the logs and bans should expire after a certain amount of time so that the database does not become too large\ndatabase cleanup logs days - After how many days should logs expire\ndatabase cleanup bans days - After how many days should bans expire\n\ndiscord logs enabled - Display logs via discord (May get limited due to the amount of logs)\ndiscord logs url - Webhook URL\ndiscord bans enabled - Display bans via discord\ndiscord bans url - Webhook URL\n-------------------------------------------------------------------------------------\n");
      this.configuration.addDefault("verus.name", "&9&lVerus");
      this.configuration.addDefault("verus.sendstats", this.sendStats);
      this.configuration.addDefault("verus.alerts.certainty", "&7failed");
      this.configuration.addDefault("verus.alerts.message", "{name} &8> &f{player} &7{certainty} &f{cheat} &7VL[&9{vl}&7]");
      this.configuration.addDefault("verus.alerts.click.enabled", this.alertClick);
      this.configuration.addDefault("verus.alerts.click.command", "/tp {player}");
      this.configuration.addDefault("verus.alerts.click.hover", "&9Click to teleport to {player}.");
      this.configuration.addDefault("verus.bans.commands", new String[]{"ban {player} &9&lVerus &8> &fCheating"});
      this.configuration.addDefault("verus.bans.message", this.banMessage);
      this.configuration.addDefault("verus.bans.enabled", true);
      this.configuration.addDefault("verus.bans.announce", false);
      this.configuration.addDefault("verus.bans.randomize", this.randomBanId);
      this.configuration.addDefault("verus.bans.scramble", this.scrambleBanId);
      this.configuration.addDefault("verus.recentlogs.message", "{time} {name} &7Failed &f{type} Type {subType} &7VL: &f{vl} &7P: &f{ping}");
      this.configuration.addDefault("verus.geyser.enabled", this.geyser);
      this.configuration.addDefault("verus.geyser.prefix", this.geyserPrefix);
      if (this.configuration.contains("verus.badlionapi")) {
         this.configuration.set("verus.badlionapi", (Object)null);
      }

      this.configuration.addDefault("verus.bungee.bans", this.bungeeBans);
      this.configuration.addDefault("verus.bungee.secretKeyPath", this.secretKeyPath);
      this.configuration.addDefault("verus.bungee.messagingChannel", this.messagingChannel);
      this.configuration.addDefault("verus.permissions.bypass.enabled", this.bypassEnabled);
      this.configuration.addDefault("verus.permissions.bypass.permission", this.bypassPermission);
      this.configuration.addDefault("verus.permissions.bypass.alerts", this.bypassAlerts);
      this.configuration.addDefault("verus.commands.ping.enabled", this.pingCommand);
      this.configuration.addDefault("verus.commands.ping.vanish", this.vanishPing);
      this.configuration.addDefault("verus.checks.persistence.enabled", this.persistence);
      this.configuration.addDefault("verus.checks.persistence.mins", this.persistenceMins);
      this.configuration.addDefault("verus.checks.persistence.seconds", this.persistenceSeconds);
      this.configuration.addDefault("verus.checks.schemprinter.bans", this.schemBans);
      this.configuration.addDefault("verus.checks.schemprinter.enabled", this.schem);
      this.configuration.addDefault("verus.checks.butterflyclicking.bans", this.butterflyBans);
      this.configuration.addDefault("verus.checks.butterflyclicking.enabled", this.butterflyAlerts);
      this.configuration.addDefault("verus.checks.heavy.enabled", this.heavyChecks);
      this.configuration.addDefault("verus.checks.heavy.ticks", this.heavyTicks);
      this.configuration.addDefault("verus.checks.heavy.pullback", this.heavyPullback);
      this.configuration.addDefault("verus.checks.hardDisable.enabled", this.hardDisable);
      this.configuration.addDefault("verus.checks.pingKick.enabled", this.pingKick);
      this.configuration.addDefault("verus.checks.pingKick.combat", this.pingKickCheck);
      this.configuration.addDefault("verus.checks.pingKick.timeout", this.pingTimeout);
      if (this.configuration.contains("verus.checks.speed.itemModifier")) {
         this.configuration.set("verus.checks.speed.itemModifier", (Object)null);
      }

      if (this.configuration.contains("verus.checks.speed.modifier")) {
         this.configuration.set("verus.checks.speed.modifier", (Object)null);
      }

      this.configuration.addDefault("verus.checks.speed.slimePushFix", this.slimePushFix);
      this.configuration.addDefault("verus.checks.reach.moreTransactions", this.moreTransactions);
      this.configuration.addDefault("verus.checks.reach.ignoreLag", this.ignoreLag);
      this.configuration.addDefault("verus.checks.fly.unloadedChunkFix", this.unloadedChunkFix);
      this.configuration.addDefault("verus.checks.fly.fixSlimeBlocks", this.fixSlimeBlocks);
      this.configuration.addDefault("verus.checks.autoclicker.maxcps", this.maxClicksPerSecond);
      this.configuration.addDefault("verus.database.cleanup.enabled", this.cleanup);
      this.configuration.addDefault("verus.database.cleanup.logs.days", this.logsCleanupDays);
      this.configuration.addDefault("verus.database.cleanup.bans.days", this.bansCleanupDays);
      this.configuration.addDefault("verus.mongo.enabled", this.mongoEnabled);
      this.configuration.addDefault("verus.mongo.host", this.mongoHost);
      this.configuration.addDefault("verus.mongo.port", this.mongoPort);
      this.configuration.addDefault("verus.mongo.database", this.mongoDatabase);
      this.configuration.addDefault("verus.mongo.auth.enabled", this.mongoAuthEnabled);
      this.configuration.addDefault("verus.mongo.auth.username", "verus");
      this.configuration.addDefault("verus.mongo.auth.password", "");
      this.configuration.addDefault("verus.mongo.auth.database", this.configuration.getString("verus.mongo.database", this.mongoDatabase));
      this.configuration.addDefault("verus.sql.enabled", this.sqlEnabled);
      this.configuration.addDefault("verus.sql.host", this.sqlHost);
      this.configuration.addDefault("verus.sql.push.seconds", this.sqlPushSeconds);
      this.configuration.addDefault("verus.sql.port", this.sqlPort);
      this.configuration.addDefault("verus.sql.pool", this.sqlPool);
      this.configuration.addDefault("verus.sql.database", this.sqlDatabase);
      this.configuration.addDefault("verus.sql.type", this.sqlDatabaseType);
      this.configuration.addDefault("verus.sql.auth.username", this.sqlUsername);
      this.configuration.addDefault("verus.sql.auth.password", this.sqlPassword);
      this.configuration.addDefault("verus.discord.logs.enabled", this.discordLogs);
      this.configuration.addDefault("verus.discord.logs.url", this.discordLogsUrl);
      this.configuration.addDefault("verus.discord.bans.enabled", this.discordBans);
      this.configuration.addDefault("verus.discord.bans.url", this.discordBansUrl);
      this.saveConfig(this.configuration, this.file);
      this.saveConfig(this.checkConfiguration, this.checkFile);
   }

   public void setAnnouncement(boolean var1) {
      this.announcement = var1;
   }

   public MongoCredential getMongoCredential() {
      return this.mongoCredential;
   }

   public String getSqlUsername() {
      return this.sqlUsername;
   }

   public ConnectionType getSqlConnectionType() {
      return this.sqlConnectionType;
   }

   public void setSqlPool(int var1) {
      this.sqlPool = var1;
   }

   public void setCheckCommandsDatabaseName(String var1) {
      this.checkCommandsDatabaseName = var1;
   }

   public String getAlertMessage() {
      return this.alertMessage;
   }

   public void setGeyser(boolean var1) {
      this.geyser = var1;
   }

   public void setBanCommands(List<String> var1) {
      this.banCommands = var1;
   }

   public String getAnticheatName() {
      return this.anticheatName;
   }

   public int getPersistenceMins() {
      return this.persistenceMins;
   }

   public String getMessagingChannel() {
      return this.messagingChannel;
   }

   public boolean isAlertClick() {
      return this.alertClick;
   }

   public int getBansCleanupDays() {
      return this.bansCleanupDays;
   }

   public void disable() {
   }

   public boolean isDatabaseCheckPersistence() {
      return this.databaseCheckPersistence;
   }

   public String getCheckDatabaseName() {
      return this.checkDatabaseName;
   }

   public void setPingKickCheck(boolean var1) {
      this.pingKickCheck = var1;
   }

   public void setDirectMemory(boolean var1) {
      this.directMemory = var1;
   }

   public void setAutoBan(boolean var1) {
      this.autoBan = var1;
   }

   public void setBungeeBans(boolean var1) {
      this.bungeeBans = var1;
   }

   public void setConfiguration(YamlConfiguration var1) {
      this.configuration = var1;
   }

   public void setMongoPort(int var1) {
      this.mongoPort = var1;
   }

   public boolean isHeavyChecks() {
      return this.heavyChecks;
   }

   public String getAlertClickCommand() {
      return this.alertClickCommand;
   }

   public List<String> getBanCommands() {
      return this.banCommands;
   }

   public void setCheckFile(File var1) {
      this.checkFile = var1;
   }

   public void setBypassEnabled(boolean var1) {
      this.bypassEnabled = var1;
   }

   public String getSecretKeyPath() {
      return this.secretKeyPath;
   }

   public void setMongoDatabase(String var1) {
      this.mongoDatabase = var1;
   }

   public void setFile(File var1) {
      this.file = var1;
   }

   public void setAnticheatName(String var1) {
      this.anticheatName = var1;
   }

   public void setLogsCleanupDays(int var1) {
      this.logsCleanupDays = var1;
   }

   public boolean isVanishPing() {
      return this.vanishPing;
   }

   public void setSlimePushFix(boolean var1) {
      this.slimePushFix = var1;
   }

   public boolean isCompression() {
      return this.compression;
   }

   public void setAlertMessage(String var1) {
      this.alertMessage = var1;
   }

   public void setRecentlogsMessage(String var1) {
      this.recentlogsMessage = var1;
   }

   public String getAnnouncementMessage() {
      return this.announcementMessage;
   }

   public void setPersistenceMins(int var1) {
      this.persistenceMins = var1;
   }

   public void setBlockLog(boolean var1) {
      this.blockLog = var1;
   }

   public boolean isCleanup() {
      return this.cleanup;
   }

   public String getDiscordBansUrl() {
      return this.discordBansUrl;
   }

   public void setSqlEnabled(boolean var1) {
      this.sqlEnabled = var1;
   }

   public boolean isPingKickCheck() {
      return this.pingKickCheck;
   }

   public boolean isIgnoreLag() {
      return this.ignoreLag;
   }
}
