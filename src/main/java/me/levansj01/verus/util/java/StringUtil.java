package me.levansj01.verus.util.java;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;

public class StringUtil {
   public static final String LINE;

   public static String differenceAsTime(long var0) {
      long var2 = TimeUnit.MILLISECONDS.toDays(var0);
      long var4 = TimeUnit.HOURS.convert(var0, TimeUnit.MILLISECONDS);
      return var2 == 0L ? (var4 == 0L ? ChatColor.WHITE + String.valueOf(TimeUnit.MINUTES.convert(var0, TimeUnit.MILLISECONDS)).concat(ChatColor.GRAY + " minutes ago") : ChatColor.WHITE + String.valueOf(var4).concat(ChatColor.GRAY + " hours ago")) : ChatColor.WHITE + String.valueOf(var2).concat(ChatColor.GRAY + " days ago");
   }

   public static String plainDifferenceAsTime(long var0) {
      long var2 = TimeUnit.MILLISECONDS.toDays(var0);
      long var4 = TimeUnit.MILLISECONDS.toHours(var0);
      return var2 == 0L ? (var4 == 0L ? TimeUnit.MILLISECONDS.toMinutes(var0) + " mins" : var4 + " hrs") : var2 + " days";
   }

   public static String generateScrambledId(int var0) {
      StringBuilder var1 = new StringBuilder(var0);

      for(int var2 = 0; var2 < var0; ++var2) {
         int var3 = ThreadLocalRandom.current().nextBoolean() ? 97 : 65;
         char var4 = (char)(var3 + ThreadLocalRandom.current().nextInt(20));
         var1.append(var4);
      }

      return var1.toString();
   }

   static {
      LINE = ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "-----------------------------------------";
   }

   public static String formatEffect(PotionEffect var0) {
      String var1 = WordUtils.capitalize(var0.getType().getName().toLowerCase()).replaceAll("_", " ");
      int var2 = var0.getAmplifier() + 1;
      int var3 = var0.getDuration() * 50;
      return formatEffect(var1, var2, (long)var3);
   }

   public static String generateRandomId() {
      return String.valueOf(ThreadLocalRandom.current().nextInt(1, 9)) + (char)(65 + ThreadLocalRandom.current().nextInt(0, 5));
   }

   public static String formatEffect(String var0, int var1, long var2) {
      long var4 = var2 / 60000L;
      long var6 = var2 / 1000L % 60L;
      return String.format("%s %s %d:%02d", var0, var1, var4, var6);
   }
}
