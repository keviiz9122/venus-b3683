package me.levansj01.verus.util.java;

import com.google.common.base.Joiner;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.DataWatcher.WatchableObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;

public class SafeReflection {
   public static <T> void setLocalField(Class var0, Object var1, String var2, T var3) {
      set(access(var0, var2), var1, var3);
   }

   public static Field access(Class var0, String... var1) {
      String[] var2 = var1;
      int var3 = var1.length;
      int var4 = 0;

      while(var4 < var3) {
         String var5 = var2[var4];

         try {
            Field var6 = var0.getDeclaredField(var5);
            var6.setAccessible(true);
            return var6;
         } catch (NoSuchFieldException var7) {
            ++var4;
         }
      }

      throw new IllegalArgumentException(var0.getSimpleName() + ":" + Joiner.on(",").join(var1));
   }

   public static Field access(Class var0, String var1) {
      try {
         Field var2 = var0.getDeclaredField(var1);
         var2.setAccessible(true);
         return var2;
      } catch (NoSuchFieldException var3) {
         throw new IllegalArgumentException(var0.getSimpleName() + ":" + var1, var3);
      }
   }

   public static Map<String, Command> getKnownCommands(SimpleCommandMap var0) {
      return (Map)getLocalField((Class)SimpleCommandMap.class, var0, (String[])("knownCommands"));
   }

   public static PacketPlayOutNamedEntitySpawn spawn(int var0, UUID var1, int var2, int var3, int var4, byte var5, byte var6, int var7, DataWatcher var8, List<WatchableObject> var9) {
      throw new UnsupportedOperationException();
   }

   public static Method access(Class<?> var0, String var1, Class<?>... var2) {
      try {
         Method var3 = var0.getDeclaredMethod(var1, var2);
         var3.setAccessible(true);
         return var3;
      } catch (NoSuchMethodException var4) {
         throw new IllegalArgumentException(var0.getSimpleName() + ":" + var1 + "(", var4);
      }
   }

   public static <T> Constructor<T> constructor(Class<T> var0, Class<?>... var1) {
      try {
         Constructor var2 = var0.getDeclaredConstructor(var1);
         var2.setAccessible(true);
         return var2;
      } catch (NoSuchMethodException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public static <T> T fetch(Field var0, Object var1) {
      try {
         return var0.get(var1);
      } catch (IllegalAccessException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public static Method access(String[] var0, String var1, Class<?>... var2) {
      ClassNotFoundException var3 = null;
      String[] var4 = var0;
      int var5 = var0.length;
      int var6 = 0;

      while(var6 < var5) {
         String var7 = var4[var6];

         try {
            Class var8 = Class.forName(var7);
            Method var9 = var8.getDeclaredMethod(var1, var2);
            var9.setAccessible(true);
            return var9;
         } catch (NoSuchMethodException | ClassNotFoundException var10) {
            var3 = var10;
            ++var6;
         }
      }

      throw new IllegalArgumentException(String.join(",", var0) + ":" + var1 + "(", var3);
   }

   public static <T> T getLocalField(String var0, Object var1, String var2) {
      try {
         return fetch(access(Class.forName(var0), var2), var1);
      } catch (ClassNotFoundException var4) {
         throw new IllegalArgumentException(var0 + ":" + var2, var4);
      }
   }

   public static SimpleCommandMap getCommandMap() {
      return (SimpleCommandMap)getLocalField((Class)Bukkit.getServer().getClass(), Bukkit.getServer(), (String[])("commandMap"));
   }

   public static <T> void set(Field var0, Object var1, T var2) {
      try {
         var0.set(var1, var2);
      } catch (IllegalAccessException var4) {
         throw new IllegalArgumentException(var4);
      }
   }

   public static <T> T getLocalField(Class<?> var0, Object var1, String... var2) {
      return fetch(access(var0, var2), var1);
   }

   public static <T> T execute(Method var0, Object var1, Object... var2) {
      try {
         return var0.invoke(var1, var2);
      } catch (InvocationTargetException | IllegalAccessException var4) {
         throw new IllegalArgumentException(var4);
      }
   }

   public static <T> T fetchConstructor(Constructor<T> var0, Object... var1) {
      try {
         return var0.newInstance(var1);
      } catch (IllegalAccessException | InvocationTargetException | InstantiationException var3) {
         throw new IllegalArgumentException(var3);
      }
   }

   public static <T> T getLocalField(String[] var0, Object var1, String var2) {
      ClassNotFoundException var3 = null;
      String[] var4 = var0;
      int var5 = var0.length;
      int var6 = 0;

      while(var6 < var5) {
         String var7 = var4[var6];

         try {
            Class var8 = Class.forName(var7);
            Field var9 = var8.getDeclaredField(var2);
            var9.setAccessible(true);
            return var9.get(var1);
         } catch (NoSuchFieldException | IllegalAccessException | ClassNotFoundException var10) {
            var3 = var10;
            ++var6;
         }
      }

      throw new IllegalArgumentException(String.join(",", var0) + ":" + var2, var3);
   }

   public static Class<?> findClass(String... var0) {
      String[] var1 = var0;
      int var2 = var0.length;
      int var3 = 0;

      while(var3 < var2) {
         String var4 = var1[var3];

         try {
            return Class.forName(var4);
         } catch (ClassNotFoundException var6) {
            ++var3;
         }
      }

      throw new IllegalArgumentException("Could not find a class: " + Joiner.on(", ").join(var0));
   }

   public static <T> T fetch(Field var0, Object var1, Class<T> var2) {
      try {
         return var0.get(var1);
      } catch (IllegalAccessException var4) {
         throw new IllegalArgumentException(var4);
      }
   }
}
