package me.levansj01.verus;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.levansj01.launcher.VerusLaunch;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.api.API;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.command.BaseCommandHelp;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.discord.DiscordManager;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.lang.EnumMessage;
import me.levansj01.verus.listener.DataListener;
import me.levansj01.verus.messaging.MessagingHandler;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.task.ReportTask;
import me.levansj01.verus.task.ServerTickTask;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.java.SafeReflection;
import me.levansj01.verus.util.java.WordUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.help.HelpMap;
import org.bukkit.permissions.ServerOperator;

public class VerusPlugin implements VerusLaunch {
   private HelpMap helpMap;
   private ReportTask reportTask;
   private VerusTypeLoader typeLoader;
   private SimpleCommandMap commandMap;
   private List<Command> commands;
   private final Server server = Bukkit.getServer();
   private static String name;
   private DataListener dataListener;
   public static ChatColor COLOR;
   private boolean serverShutdown;
   private VerusLauncher plugin;

   private void unregisterListeners() {
      if (this.dataListener != null) {
         HandlerList.unregisterAll(this.dataListener);
      }

      HandlerList.unregisterAll(VerusLauncher.getPlugin());
   }

   private void unregisterCommands() {
      if (this.commandMap != null) {
         Map var1 = SafeReflection.getKnownCommands(this.commandMap);
         var1.values().removeAll(this.commands);
         Iterator var2 = this.commands.iterator();

         while(var2.hasNext()) {
            Command var3 = (Command)var2.next();
            var3.unregister(this.commandMap);
         }
      }

      if (this.helpMap != null) {
         this.helpMap.getHelpTopics().removeIf((var0) -> {
            return var0 instanceof BaseCommandHelp;
         });
      }

   }

   public VerusTypeLoader getTypeLoader() {
      return this.typeLoader;
   }

   public static String getNameFormatted() {
      return WordUtils.capitalize(name);
   }

   static {
      COLOR = ChatColor.BLUE;
      name = "verus";
   }

   public List<Command> getCommands() {
      return this.commands;
   }

   public Server getServer() {
      return this.server;
   }

   private void killClassLoader() {
   }

   // $FF: synthetic method
   private static void lambda$killClassLoader$5(Class var0, ClassLoader var1) {
   }

   public void setServerShutdown(boolean var1) {
      this.serverShutdown = var1;
   }

   public static void setName(String var0) {
      name = var0;
   }

   public static int getType() {
      return 1;
   }

   public void shutdown() {
      ServerTickTask.getInstance().cancel();
      DataManager.disable();
      this.unregisterListeners();
      DiscordManager.getInstance().stop();
      StorageEngine.getInstance().stopConfig();
      CheckManager.getInstance().disable();
      GUIManager.getInstance().disable();
      StorageEngine.getInstance().stop();
      VerusConfiguration var1 = StorageEngine.getInstance().getVerusConfig();
      if (var1 != null && var1.isBungeeBans()) {
         MessagingHandler.getInstance().disable();
      }

      API.check();
      API var2 = API.getAPI();
      if (var2 != null) {
         var2.disable();
      }

      if (this.reportTask != null) {
         this.reportTask.end();
      }

      if (ServerTickTask.getInstance().getBukkitTask() != null) {
         ServerTickTask.getInstance().getBukkitTask().cancel();
      }

      this.unregisterCommands();
      this.plugin.getLogger().info(getNameFormatted() + " shutdown successfully");
      this.typeLoader.setCheckClasses((Class[])null);
      this.killClassLoader();
   }

   // $FF: synthetic method
   private static boolean lambda$killClassLoader$6(String var0) {
      return false;
   }

   public static long getBuild() {
      return 3863L;
   }

   private void kill(Field[] var1, ClassLoader var2) {
   }

   private void registerListeners() {
      this.getServer().getPluginManager().registerEvents(this.dataListener = new DataListener(), this.plugin);
   }

   public DataListener getDataListener() {
      return this.dataListener;
   }

   public boolean isServerShutdown() {
      return this.serverShutdown;
   }

   public ReportTask getReportTask() {
      return this.reportTask;
   }

   public HelpMap getHelpMap() {
      return this.helpMap;
   }

   public void launch(VerusLauncher var1) {
      long var2 = System.currentTimeMillis();
      this.plugin = var1;
      this.typeLoader = new VerusTypeLoader();
      StorageEngine var4 = StorageEngine.getInstance();
      var4.startConfig();
      VerusTypeLoader.loader();

      try {
         NMSManager.getInstance();
      } catch (Throwable var10) {
         var10.printStackTrace();
         Bukkit.shutdown();
         return;
      }

      var4.start();
      CheckManager.getInstance().enable(this);
      DataManager.enable(this);
      VerusConfiguration var5 = var4.getVerusConfig();
      if (var5.isBungeeBans()) {
         MessagingHandler.getInstance().enable(this);
      }

      if (var5.isDiscordBans() || var5.isDiscordLogs()) {
         DiscordManager.getInstance().start();
      }

      this.registerListeners();
      this.registerCommands();
      ServerTickTask.getInstance().schedule();
      if (var4.getVerusConfig().isSendStats()) {
         this.reportTask = new ReportTask();
         this.reportTask.start();
      }

      API var6 = API.getAPI();
      if (var6 != null) {
         var6.enable(this);
      }

      long var7 = System.currentTimeMillis() - var2;
      String var9 = String.format("%s b%s launched successfully in %sms", getNameFormatted(), getBuild(), var7);
      Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp).forEach((var1x) -> {
         var1x.sendMessage(ChatColor.GREEN + var9);
      });
      this.plugin.getLogger().info(var9 + "\nBy running this plugin, you agree to be bound by our TOS");
   }

   public static void restart() {
      VerusLauncher var0 = VerusLauncher.getPlugin();
      Bukkit.getPluginManager().disablePlugin(var0);
      Bukkit.getPluginManager().enablePlugin(var0);
   }

   public static String getName() {
      return name;
   }

   private void registerCommands() {
      this.commands = this.typeLoader.getCommands();
      this.commands.addAll(this.typeLoader.getBaseCommands());
      if (!StorageEngine.getInstance().getVerusConfig().isPingCommand()) {
         this.commands.removeIf((var0) -> {
            return var0.getName().equals(Loader.getPingCommand());
         });
      }

      this.commands.forEach((var0) -> {
         var0.setPermissionMessage(EnumMessage.COMMAND_PERMISSION.get());
      });
      this.commandMap = SafeReflection.getCommandMap();
      this.helpMap = this.server.getHelpMap();
      Map var1 = SafeReflection.getKnownCommands(this.commandMap);
      this.commandMap.registerAll(name, this.commands);
      this.commands.forEach((var2) -> {
         var1.put(var2.getName(), var2);
         this.helpMap.addTopic(new BaseCommandHelp(var2));
      });
      NMSManager.getInstance().syncCommands(this.commands);
   }

   public SimpleCommandMap getCommandMap() {
      return this.commandMap;
   }

   public VerusLauncher getPlugin() {
      return this.plugin;
   }
}
