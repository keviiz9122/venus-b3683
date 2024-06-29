package me.levansj01.launcher;

import java.lang.reflect.Method;
import org.bukkit.plugin.java.JavaPlugin;

public class VerusLauncher extends JavaPlugin {
   private static VerusLauncher plugin;
   private VerusLaunch launch = null;

   public static VerusLauncher getPlugin() {
      return plugin;
   }

   public void onLoad() {
      plugin = this;
   }

   public void onEnable() {
      try {
         Class<?> clazz = Class.forName("me.levansj01.verus.VerusPlugin");
         Method method = clazz.getDeclaredMethod("launch", VerusLauncher.class);
         Object obj = clazz.newInstance();
         method.invoke(obj, this);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }
}
