package me.levansj01.verus.util.java;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.location.IVector3d;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.util.location.Vector3d;

public class MathUtil {
   public static float relEntityRoundLook(float var0) {
      return (float)net.minecraft.server.v1_8_R3.MathHelper.d(var0 * 256.0F / 360.0F);
   }

   public static double highest(Iterable<? extends Number> var0) {
      Double var1 = null;
      Iterator var2 = var0.iterator();

      while(true) {
         Number var3;
         do {
            if (!var2.hasNext()) {
               return (Double)JavaV.firstNonNull(var1, 0.0D);
            }

            var3 = (Number)var2.next();
         } while(var1 != null && !(var3.doubleValue() > var1));

         var1 = var3.doubleValue();
      }
   }

   public static boolean onGround(double var0) {
      return var0 % 0.015625D == 0.0D;
   }

   public static double deviationSquared(Iterable<? extends Number> var0) {
      double var1 = 0.0D;
      int var3 = 0;

      for(Iterator var4 = var0.iterator(); var4.hasNext(); ++var3) {
         Number var5 = (Number)var4.next();
         var1 += var5.doubleValue();
      }

      double var10 = var1 / (double)var3;
      double var6 = 0.0D;

      Number var9;
      for(Iterator var8 = var0.iterator(); var8.hasNext(); var6 += Math.pow(var9.doubleValue() - var10, 2.0D)) {
         var9 = (Number)var8.next();
      }

      return var6 == 0.0D ? 0.0D : var6 / (double)(var3 - 1);
   }

   public static double fastSqrt(double var0) {
      return Double.longBitsToDouble((Double.doubleToLongBits(var0) - 4503599627370496L >> 1) + 2305843009213693952L);
   }

   public static double getLuckyAura(PlayerLocation var0, IVector3d var1) {
      float var2 = var0.getPitch();
      double var3 = var0.distanceXZ(var1);
      return Math.tan(Math.toRadians((double)var2)) * var3 - var0.getY() + var1.getY();
   }

   public static double average(Iterable<? extends Number> var0) {
      double var1 = 0.0D;
      int var3 = 0;

      for(Iterator var4 = var0.iterator(); var4.hasNext(); ++var3) {
         Number var5 = (Number)var4.next();
         var1 += var5.doubleValue();
      }

      return var1 / (double)var3;
   }

   public static <T> T max(Iterable<T> var0, ToDoubleFunction<T> var1) {
      double var2 = Double.MIN_VALUE;
      Object var4 = null;
      Iterator var5 = var0.iterator();

      while(var5.hasNext()) {
         Object var6 = var5.next();
         double var7 = var1.applyAsDouble(var6);
         if (var7 > var2) {
            var4 = var6;
            var2 = var7;
         }
      }

      return var4;
   }

   public static boolean isScientificNotation(String var0) {
      try {
         new BigDecimal(var0);
      } catch (NumberFormatException var2) {
         return false;
      }

      return var0.toUpperCase().contains("E");
   }

   public static double hypot(double... var0) {
      return Math.sqrt(hypotSquared(var0));
   }

   public static int toInt(float var0) {
      return (int)((double)(new BigDecimal((double)var0)).setScale(5, RoundingMode.UP).floatValue() * 10000.0D);
   }

   public static double lowestAbs(Iterable<? extends Number> var0) {
      Double var1 = null;
      Iterator var2 = var0.iterator();

      while(true) {
         Number var3;
         do {
            if (!var2.hasNext()) {
               return (Double)JavaV.firstNonNull(var1, 0.0D);
            }

            var3 = (Number)var2.next();
         } while(var1 != null && !(Math.abs(var3.doubleValue()) < Math.abs(var1)));

         var1 = var3.doubleValue();
      }
   }

   public static double positiveSmaller(Number var0, Number var1) {
      return Math.abs(var0.doubleValue()) < Math.abs(var1.doubleValue()) ? var0.doubleValue() : var1.doubleValue();
   }

   public static int getLog(double var0) {
      if (var0 == 0.0D) {
         return 0;
      } else {
         int var2;
         for(var2 = 0; var0 < 1.0D; ++var2) {
            var0 *= 10.0D;
         }

         return var2;
      }
   }

   public static double highest(double var0, double var2, double var4) {
      return var0 > var2 ? (var0 > var4 ? var0 : var4) : (var2 > var4 ? var2 : var4);
   }

   public static Double getMinimumAngle(PacketLocation var0, PlayerLocation... var1) {
      Double var2 = null;
      PlayerLocation[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PlayerLocation var6 = var3[var5];
         double var7 = var0.getX() - var6.getX();
         double var9 = var0.getZ() - var6.getZ();
         float var11 = (float)(Math.atan2(var9, var7) * 180.0D / 3.141592653589793D) - 90.0F;
         double var12 = getDistanceBetweenAngles360((double)var6.getYaw(), (double)var11);
         if (var2 == null || var2 > var12) {
            var2 = var12;
         }
      }

      return var2;
   }

   public static double highestAbs(Iterable<? extends Number> var0) {
      Double var1 = null;
      Iterator var2 = var0.iterator();

      while(true) {
         Number var3;
         do {
            if (!var2.hasNext()) {
               return (Double)JavaV.firstNonNull(var1, 0.0D);
            }

            var3 = (Number)var2.next();
         } while(var1 != null && !(Math.abs(var3.doubleValue()) > Math.abs(var1)));

         var1 = var3.doubleValue();
      }
   }

   private static String multiply(String var0, int var1) {
      StringBuilder var2 = new StringBuilder();

      while(var1-- > 0) {
         var2.append(var0);
      }

      return var2.toString();
   }

   public static int gcd(long var0, int var2, int var3) {
      return (long)var3 <= var0 ? var2 : gcd(var0, var3, var2 % var3);
   }

   public static double common(Queue<Float> var0) {
      HashSet var1 = new HashSet();
      double var2 = 0.0D;
      Iterator var4 = var0.iterator();

      while(var4.hasNext()) {
         float var5 = (Float)var4.next();
         if (!var1.add(var5)) {
            ++var2;
         }
      }

      return var2 / (double)(var0.size() + 1);
   }

   public static float[] getRotationFromPosition(PlayerLocation var0, PacketLocation var1) {
      double var2 = var1.getX() - var0.getX();
      double var4 = var1.getZ() - var0.getZ();
      double var6 = var1.getY() + 0.81D - var0.getY() - 1.2D;
      float var8 = (float)Math.sqrt(var2 * var2 + var4 * var4);
      float var9 = (float)(Math.atan2(var4, var2) * 180.0D / 3.141592653589793D) - 90.0F;
      float var10 = (float)(-(Math.atan2(var6, (double)var8) * 180.0D / 3.141592653589793D));
      return new float[]{var9, var10};
   }

   public static int lowestInt(int... var0) {
      Integer var1 = null;
      int[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var2[var4];
         if (var1 == null || var5 < var1) {
            var1 = var5;
         }
      }

      return (Integer)JavaV.firstNonNull(var1, 0);
   }

   public static float getHeight(PlayerLocation var0, PlayerLocation var1) {
      return (float)var0.distanceXZ(var1) * (float)Math.cos(Math.toRadians((double)var0.getPitch()));
   }

   public static int lcd(long var0, int var2, int var3) {
      return (long)var3 <= var0 ? var2 : gcd(var0, var3, var2 % var3);
   }

   public static <T> T max(Iterable<T> var0, Predicate<T> var1, ToDoubleFunction<T> var2) {
      double var3 = Double.MIN_VALUE;
      Object var5 = null;
      Iterator var6 = var0.iterator();

      while(var6.hasNext()) {
         Object var7 = var6.next();
         if (var1.test(var7)) {
            double var8 = var2.applyAsDouble(var7);
            if (var8 > var3) {
               var5 = var7;
               var3 = var8;
            }
         }
      }

      return var5;
   }

   public static double totalAbs(Iterable<? extends Number> var0) {
      double var1 = 0.0D;

      Number var4;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 += Math.abs(var4.doubleValue())) {
         var4 = (Number)var3.next();
      }

      return var1;
   }

   public static double lowest(Iterable<? extends Number> var0) {
      Double var1 = null;
      Iterator var2 = var0.iterator();

      while(true) {
         Number var3;
         do {
            if (!var2.hasNext()) {
               return (Double)JavaV.firstNonNull(var1, 0.0D);
            }

            var3 = (Number)var2.next();
         } while(var1 != null && !(var3.doubleValue() < var1));

         var1 = var3.doubleValue();
      }
   }

   public static int lcd(int var0, int var1) {
      return BigInteger.valueOf((long)var0).gcd(BigInteger.valueOf((long)var1)).intValueExact();
   }

   public static float[] getBlockRotations(PlayerLocation var0, BlockPosition var1) {
      double var2 = (double)var1.getX() + 0.5D - var0.getX();
      double var4 = (double)var1.getY() + 0.5D - (var0.getY() + 1.6200000047683716D);
      double var6 = (double)var1.getZ() + 0.5D - var0.getZ();
      float var8 = (float)Math.sqrt(var2 * var2 + var6 * var6);
      float var9 = (float)Math.toDegrees(Math.atan2(var2, var6)) - 90.0F;
      float var10 = (float)(-Math.toDegrees(Math.atan2(var4, (double)var8)));
      return new float[]{var9, var10};
   }

   public static double kurtosis(Iterable<? extends Number> var0) {
      double var1 = 0.0D;
      double var3 = 0.0D;

      for(Iterator var5 = var0.iterator(); var5.hasNext(); ++var3) {
         Number var6 = (Number)var5.next();
         var1 += var6.doubleValue();
      }

      if (var3 < 3.0D) {
         return 0.0D;
      } else {
         double var15 = var3 * (var3 + 1.0D) / ((var3 - 1.0D) * (var3 - 2.0D) * (var3 - 3.0D));
         double var7 = 3.0D * Math.pow(var3 - 1.0D, 2.0D) / ((var3 - 2.0D) * (var3 - 3.0D));
         var1 /= var3;
         double var9 = 0.0D;
         double var11 = 0.0D;

         Number var14;
         for(Iterator var13 = var0.iterator(); var13.hasNext(); var11 += Math.pow(var1 - var14.doubleValue(), 4.0D)) {
            var14 = (Number)var13.next();
            var9 += Math.pow(var1 - var14.doubleValue(), 2.0D);
         }

         var9 /= var3;
         var11 /= Math.pow(var9, 2.0D);
         return var15 * var11 - var7;
      }
   }

   public static double total(Iterable<? extends Number> var0) {
      double var1 = 0.0D;

      Number var4;
      for(Iterator var3 = var0.iterator(); var3.hasNext(); var1 += var4.doubleValue()) {
         var4 = (Number)var3.next();
      }

      return var1;
   }

   public static double deviation(Iterable<? extends Number> var0) {
      return Math.sqrt(deviationSquared(var0));
   }

   public static double average(Number... var0) {
      return average((Iterable)Arrays.asList(var0));
   }

   public static double highestAbs(Number... var0) {
      return highestAbs((Iterable)Arrays.asList(var0));
   }

   public static float[] getRotationsBlockOld(double var0, PlayerLocation var2, BlockPosition var3) {
      double var4 = (double)var3.getX() - var2.getX();
      double var6 = (double)var3.getY() - (var2.getY() + var0);
      double var8 = (double)var3.getZ() - var2.getZ();
      float var10 = (float)Math.sqrt(var4 * var4 + var8 * var8);
      float var11 = (float)(Math.atan2(var8, var4) * 180.0D / 3.141592653589793D) - 90.0F;
      float var12 = (float)(-(Math.atan2(var6, (double)var10) * 180.0D / 3.141592653589793D));
      return new float[]{var11, var12};
   }

   public static double getDistanceBetweenAngles(float var0, float var1) {
      float var2 = Math.abs(var0 - var1) % 360.0F;
      if (var2 > 180.0F) {
         var2 = 360.0F - var2;
      }

      return (double)var2;
   }

   public static double relEntityRoundPos(double var0) {
      return (double)net.minecraft.server.v1_8_R3.MathHelper.floor(var0 * 32.0D) / 32.0D;
   }

   public static double variance(Number var0, Iterable<? extends Number> var1) {
      return Math.sqrt(varianceSquared(var0, var1));
   }

   public static double zeros(Queue<Float> var0) {
      double var1 = 0.0D;
      Iterator var3 = var0.iterator();

      while(var3.hasNext()) {
         float var4 = (Float)var3.next();
         if (var4 == 0.0F) {
            ++var1;
         }
      }

      return var1 / (double)(var0.size() + 1);
   }

   public static double lowest(Number... var0) {
      return lowest((Iterable)Arrays.asList(var0));
   }

   public static <T> T min(ToDoubleFunction<T> var0, T... var1) {
      double var2 = Double.MAX_VALUE;
      Object var4 = null;
      Object[] var5 = var1;
      int var6 = var1.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object var8 = var5[var7];
         double var9 = var0.applyAsDouble(var8);
         if (var9 < var2) {
            var4 = var8;
            var2 = var9;
         }
      }

      return var4;
   }

   public static double getDistanceBetweenAngles360(double var0, double var2) {
      double var4 = Math.abs(var0 % 360.0D - var2 % 360.0D);
      var4 = Math.min(360.0D - var4, var4);
      return Math.abs(var4);
   }

   public static double hypotSquared(double... var0) {
      double var1 = 0.0D;
      double[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         double var6 = var3[var5];
         var1 += Math.pow(var6, 2.0D);
      }

      return var1;
   }

   public static double varianceSquared(Number var0, Iterable<? extends Number> var1) {
      double var2 = 0.0D;
      int var4 = 0;

      for(Iterator var5 = var1.iterator(); var5.hasNext(); ++var4) {
         Number var6 = (Number)var5.next();
         var2 += Math.pow(var6.doubleValue() - var0.doubleValue(), 2.0D);
      }

      return var2 == 0.0D ? 0.0D : var2 / (double)(var4 - 1);
   }

   public static <T> T min(Iterable<T> var0, ToDoubleFunction<T> var1) {
      double var2 = Double.MAX_VALUE;
      Object var4 = null;
      Iterator var5 = var0.iterator();

      while(var5.hasNext()) {
         Object var6 = var5.next();
         double var7 = var1.applyAsDouble(var6);
         if (var7 < var2) {
            var4 = var6;
            var2 = var7;
         }
      }

      return var4;
   }

   public static double range(Iterable<? extends Number> var0) {
      return highest(var0) - lowest(var0);
   }

   public static double lowestAbs(Number... var0) {
      return lowestAbs((Iterable)Arrays.asList(var0));
   }

   public static float[][] getRotationFromPositions(Double[] var0, Vector3d[] var1, Vector3d[] var2) {
      float[][] var3 = new float[var0.length * var1.length * var2.length][];
      int var4 = 0;
      Vector3d[] var5 = var2;
      int var6 = var2.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Vector3d var8 = var5[var7];
         Vector3d[] var9 = var1;
         int var10 = var1.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            Vector3d var12 = var9[var11];
            Double[] var13 = var0;
            int var14 = var0.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               double var16 = var13[var15];
               double var18 = var8.getX() - var12.getX();
               double var20 = var8.getZ() - var12.getZ();
               double var22 = var8.getY() - (var12.getY() + var16);
               float var24 = (float)Math.sqrt(var18 * var18 + var20 * var20);
               float var25 = (float)(Math.atan2(var20, var18) * 180.0D / 3.141592653589793D) - 90.0F;
               float var26 = (float)(-(Math.atan2(var22, (double)var24) * 180.0D / 3.141592653589793D));
               var3[var4++] = new float[]{var25, var26};
            }
         }
      }

      return var3;
   }

   public static double highest(Number... var0) {
      return highest((Iterable)Arrays.asList(var0));
   }

   public static int gcd(int var0, int var1) {
      return BigInteger.valueOf((long)var0).gcd(BigInteger.valueOf((long)var1)).intValueExact();
   }
}
