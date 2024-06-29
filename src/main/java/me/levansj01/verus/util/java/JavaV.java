package me.levansj01.verus.util.java;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import me.levansj01.launcher.VerusLauncher;

public class JavaV {
   public static void executeSafely(Queue<Runnable> var0, Supplier<String> var1) {
      Runnable var2;
      while((var2 = (Runnable)var0.poll()) != null) {
         try {
            var2.run();
         } catch (Throwable var4) {
            VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to run " + var2.getClass().getSimpleName() + (String)var1.get(), var4);
         }
      }

   }

   public static <T> boolean anyMatch(Predicate<T> var0, Iterable<T> var1) {
      Iterator var2 = var1.iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         var3 = var2.next();
      } while(!var0.test(var3));

      return true;
   }

   public static <T> Queue<T> trim(Queue<T> var0, int var1) {
      for(int var2 = var0.size(); var2 > var1; --var2) {
         var0.poll();
      }

      return var0;
   }

   public static <T> T findFirst(Predicate<T> var0, Iterable<T> var1) {
      Iterator var2 = var1.iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = var2.next();
      } while(!var0.test(var3));

      return var3;
   }

   public static <T> boolean allMatch(Predicate<T> var0, Iterable<T> var1) {
      Iterator var2 = var1.iterator();

      Object var3;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         var3 = var2.next();
      } while(var0.test(var3));

      return false;
   }

   public static <T> Stream<T> stream(T... var0) {
      return Arrays.stream(var0);
   }

   public static <T> List<T> findAll(Predicate<T> var0, T... var1) {
      LinkedList var2 = new LinkedList();
      Object[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         if (var0.test(var6)) {
            var2.add(var6);
         }
      }

      return var2;
   }

   public static void shutdownAndAwaitTermination(ExecutorService var0, long var1, TimeUnit var3) {
      var0.shutdown();

      try {
         if (!var0.awaitTermination(var1, var3)) {
            var0.shutdownNow();
            if (!var0.awaitTermination(var1, var3)) {
               System.err.println("Pool did not terminate");
            }
         }
      } catch (InterruptedException var5) {
         var0.shutdownNow();
         Thread.currentThread().interrupt();
      }

   }

   public static <T> T findFirst(Predicate<T> var0, T... var1) {
      Object[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object var5 = var2[var4];
         if (var0.test(var5)) {
            return var5;
         }
      }

      return null;
   }

   public static <T> T firstNonNull(@Nullable T var0, @Nullable T var1) {
      return var0 != null ? var0 : var1;
   }

   public static <T> boolean anyMatch(Predicate<T> var0, T... var1) {
      Object[] var2 = var1;
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object var5 = var2[var4];
         if (var0.test(var5)) {
            return true;
         }
      }

      return false;
   }
}
