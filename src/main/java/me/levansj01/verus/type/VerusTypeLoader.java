package me.levansj01.verus.type;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.command.BaseCommand;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.util.java.SafeReflection;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;

public class VerusTypeLoader {
   private Class<? extends Check>[] checkClasses = null;
   private Class<?>[] classes = null;
   private static final VerusType verusType = VerusType.values()[VerusPlugin.getType()];
   private static Class<?>[] _classLoaded = null;

   private synchronized Class<? extends Check>[] getCheckClasses() {
      if (this.checkClasses == null) {
         Class[] var1 = this.getClassInfos();
         ArrayList var2 = new ArrayList(var1.length);
         Class[] var3 = var1;
         int var4 = var1.length;
         int var5 = 0;

         while(var5 < var4) {
            Class var6 = var3[var5];

            label38: {
               try {
                  if (Check.class.isAssignableFrom(var6) && !Modifier.isAbstract(var6.getModifiers())) {
                     Class var7 = var6.asSubclass(Check.class);
                     Check var8 = (Check)var7.newInstance();
                     if (var8.valid() && !var8.getUnsupportedServers().contains(NMSManager.getInstance().getServerVersion())) {
                        var2.add(var7);
                     }
                  }
               } catch (Throwable var9) {
                  if (isDev()) {
                     VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Failed to load " + var6.getName() + ": ", var9);
                  }
                  break label38;
               }
            }

            ++var5;
         }

         this.checkClasses = (Class[])var2.toArray(new Class[0]);
      }

      return this.checkClasses;
   }

   public static VerusType getVerusType() {
      return verusType;
   }

   public void setCheckClasses(Class<? extends Check>[] var1) {
      this.checkClasses = var1;
   }

   public static boolean isDev() {
      boolean var10000;
      if (verusType == VerusType.DEV) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public Check[] loadChecks() {
      Class[] var1 = this.getCheckClasses();
      ArrayList var2 = new ArrayList(var1.length);
      Class[] var3 = var1;
      int var4 = var1.length;
      int var5 = 0;

      while(var5 < var4) {
         Class var6 = var3[var5];

         label27: {
            try {
               var2.add((Check)var6.newInstance());
            } catch (Throwable var8) {
               if (isDev()) {
                  var8.printStackTrace();
               }
               break label27;
            }
         }

         ++var5;
      }

      return (Check[])var2.toArray(new Check[0]);
   }

   public static boolean isCustom() {
      return verusType.afterOrEq(VerusType.CUSTOM);
   }

   public static boolean isDebug() {
      boolean var10000;
      if (verusType == VerusType.ENTERPRISE && Loader.getUsername().equals("PGxPO") && Bukkit.getPort() == 27979) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public List<BaseCommand> getBaseCommands() {
      ArrayList var1 = new ArrayList();
      Class[] var2 = this.getClassInfos();
      int var3 = var2.length;
      int var4 = 0;

      do {
         if (var4 >= var3) {
            return var1;
         }

         Class var5 = var2[var4];
         if (BaseCommand.class.isAssignableFrom(var5) && !Modifier.isAbstract(var5.getModifiers())) {
            label37: {
               try {
                  var1.add((BaseCommand)var5.asSubclass(BaseCommand.class).newInstance());
               } catch (Throwable var7) {
                  if (isDev()) {
                     var7.printStackTrace();
                  }
                  break label37;
               }
            }
         }

         ++var4;
      } while (true);
   }

   public static boolean isEnterprise() {
      return verusType.afterOrEq(VerusType.ENTERPRISE);
   }

   public void setClasses(Class<?>[] var1) {
      this.classes = var1;
   }

   private synchronized Class<?>[] getClassInfos() {
      if (this.classes == null) {
         if (_classLoaded == null) {
            try {
               ClassLoader var1 = this.getClass().getClassLoader();
               Vector var2 = (Vector)SafeReflection.getLocalField((Class)ClassLoader.class, var1, (String[])("classes"));
               this.classes = (Class[])var2.toArray(new Class[0]);
            } catch (Throwable var3) {
               throw new RuntimeException("Backup classloader method has failed, JVM unsupported.", var3);
            }
         } else {
            this.classes = _classLoaded;
         }

         _classLoaded = null;
      }

      return this.classes;
   }

   public static void loader() {
      try {
         ((Loader)Class.forName("me.jacob.verus" + ".Launcher").asSubclass(Loader.class).newInstance()).load();
      } catch (Throwable var1) {
         if (!(var1 instanceof ClassNotFoundException)) {
            var1.printStackTrace();
         }

         return;
      }
   }

   public List<Command> getCommands() {
      ArrayList var1 = new ArrayList();
      Class[] var2 = this.getClassInfos();
      int var3 = var2.length;
      int var4 = 0;

      do {
         if (var4 >= var3) {
            return var1;
         }

         Class var5 = var2[var4];
         if (Command.class.isAssignableFrom(var5) && !Modifier.isAbstract(var5.getModifiers())) {
            label37: {
               try {
                  var1.add((Command)var5.asSubclass(Command.class).newInstance());
               } catch (Throwable var7) {
                  if (isDev()) {
                     VerusLauncher.getPlugin().getLogger().log(Level.WARNING, "Failed to load " + var5.getName() + ": ", var7);
                  }
                  break label37;
               }
            }
         }

         ++var4;
      } while (true);
   }
}
