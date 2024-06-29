package me.levansj01.verus.util.java;

import java.util.Random;
import java.util.UUID;

public class MathHelper {
   private static final int SIN_BITS = 12;
   private static final float radFull = 6.2831855F;
   private static final int SIN_MASK = 4095;
   public static final float PId2 = 1.5707964F;
   public static final float PI2 = 6.2831855F;
   private static final int SIN_COUNT = 4096;
   private static final double[] field_181164_e = new double[257];
   public static final float field_180189_a = sqrt_float(2.0F);
   private static final String __OBFID = "CL_00001496";
   public static boolean fastMath = false;
   public static final float deg2Rad = 0.017453292F;
   private static final float radToIndex = 651.8986F;
   private static final float degToIndex = 11.377778F;
   public static final float PI = 3.1415927F;
   private static final float[] SIN_TABLE_FAST = new float[4096];
   private static final float degFull = 360.0F;
   private static final double[] field_181165_f = new double[257];
   private static final int[] multiplyDeBruijnBitPosition;
   private static final float[] SIN_TABLE = new float[65536];
   private static final double field_181163_d = Double.longBitsToDouble(4805340802404319232L);

   public static float wrapAngleTo180_float(float var0) {
      var0 %= 360.0F;
      if (var0 >= 180.0F) {
         var0 -= 360.0F;
      }

      if (var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public static UUID func_180182_a(Random var0) {
      long var1 = var0.nextLong() & -61441L | 16384L;
      long var3 = var0.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
      return new UUID(var1, var3);
   }

   public static double abs_max(double var0, double var2) {
      if (var0 < 0.0D) {
         var0 = -var0;
      }

      if (var2 < 0.0D) {
         var2 = -var2;
      }

      return var0 > var2 ? var0 : var2;
   }

   public static float sin(float var0) {
      return fastMath ? SIN_TABLE_FAST[(int)(var0 * 651.8986F) & 4095] : SIN_TABLE[(int)(var0 * 10430.378F) & '\uffff'];
   }

   public static int func_154354_b(int var0, int var1) {
      if (var1 == 0) {
         return 0;
      } else if (var0 == 0) {
         return var1;
      } else {
         if (var0 < 0) {
            var1 *= -1;
         }

         int var2 = var0 % var1;
         return var2 == 0 ? var0 : var0 + var1 - var2;
      }
   }

   public static int truncateDoubleToInt(double var0) {
      return (int)(var0 + 1024.0D) - 1024;
   }

   public static boolean func_180185_a(float var0, float var1) {
      return abs(var1 - var0) < 1.0E-5F;
   }

   public static double parseDoubleWithDefaultAndMax(String var0, double var1, double var3) {
      return Math.max(var3, parseDoubleWithDefault(var0, var1));
   }

   public static int ceiling_float_int(float var0) {
      int var1 = (int)var0;
      return var0 > (float)var1 ? var1 + 1 : var1;
   }

   public static long func_180187_c(int var0, int var1, int var2) {
      long var3 = (long)(var0 * 3129871) ^ (long)var2 * 116129781L ^ (long)var1;
      var3 = var3 * var3 * 42317861L + var3 * 11L;
      return var3;
   }

   public static float clamp_float(float var0, float var1, float var2) {
      return var0 < var1 ? var1 : (var0 > var2 ? var2 : var0);
   }

   public static int func_180188_d(int var0, int var1) {
      int var2 = (var0 & 16711680) >> 16;
      int var3 = (var1 & 16711680) >> 16;
      int var4 = (var0 & '\uff00') >> 8;
      int var5 = (var1 & '\uff00') >> 8;
      int var6 = (var0 & 255) >> 0;
      int var7 = (var1 & 255) >> 0;
      int var8 = (int)((float)(var2 * var3) / 255.0F);
      int var9 = (int)((float)(var4 * var5) / 255.0F);
      int var10 = (int)((float)(var6 * var7) / 255.0F);
      return var0 & -16777216 | var8 << 16 | var9 << 8 | var10;
   }

   public static int parseIntWithDefaultAndMax(String var0, int var1, int var2) {
      return Math.max(var2, parseIntWithDefault(var0, var1));
   }

   public static int bucketInt(int var0, int var1) {
      return var0 < 0 ? -((-var0 - 1) / var1) - 1 : var0 / var1;
   }

   static {
      int var0;
      for(var0 = 0; var0 < 65536; ++var0) {
         SIN_TABLE[var0] = (float)Math.sin((double)var0 * 3.141592653589793D * 2.0D / 65536.0D);
      }

      multiplyDeBruijnBitPosition = new int[]{0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};

      for(var0 = 0; var0 < 4096; ++var0) {
         SIN_TABLE_FAST[var0] = (float)Math.sin((double)(((float)var0 + 0.5F) / 4096.0F * 6.2831855F));
      }

      for(var0 = 0; var0 < 360; var0 += 90) {
         SIN_TABLE_FAST[(int)((float)var0 * 11.377778F) & 4095] = (float)Math.sin((double)((float)var0 * 0.017453292F));
      }

   }

   public static float abs(float var0) {
      return var0 >= 0.0F ? var0 : -var0;
   }

   public static int calculateLogBaseTwo(int var0) {
      return calculateLogBaseTwoDeBruijn(var0) - (isPowerOfTwo(var0) ? 0 : 1);
   }

   public static int func_154353_e(double var0) {
      return (int)(var0 >= 0.0D ? var0 : -var0 + 1.0D);
   }

   public static float sqrt_double(double var0) {
      return (float)Math.sqrt(var0);
   }

   public static double wrapAngleTo180_double(double var0) {
      var0 %= 360.0D;
      if (var0 >= 180.0D) {
         var0 -= 360.0D;
      }

      if (var0 < -180.0D) {
         var0 += 360.0D;
      }

      return var0;
   }

   public static int floor_float(float var0) {
      int var1 = (int)var0;
      return var0 < (float)var1 ? var1 - 1 : var1;
   }

   public static int roundUpToPowerOfTwo(int var0) {
      int var1 = var0 - 1;
      var1 |= var1 >> 1;
      var1 |= var1 >> 2;
      var1 |= var1 >> 4;
      var1 |= var1 >> 8;
      var1 |= var1 >> 16;
      return var1 + 1;
   }

   public static int getRandomIntegerInRange(Random var0, int var1, int var2) {
      return var1 >= var2 ? var1 : var0.nextInt(var2 - var1 + 1) + var1;
   }

   public static float sqrt_float(float var0) {
      return (float)Math.sqrt((double)var0);
   }

   public static int parseIntWithDefault(String var0, int var1) {
      try {
         return Integer.parseInt(var0);
      } catch (Throwable var3) {
         return var1;
      }
   }

   public static double square(double var0) {
      return var0 * var0;
   }

   public static int func_180181_b(int var0, int var1, int var2) {
      int var3 = (var0 << 8) + var1;
      var3 = (var3 << 8) + var2;
      return var3;
   }

   public static int func_180184_b(int var0, int var1) {
      return (var0 % var1 + var1) % var1;
   }

   public static double func_181161_i(double var0) {
      double var2 = 0.5D * var0;
      long var4 = Double.doubleToRawLongBits(var0);
      var4 = 6910469410427058090L - (var4 >> 1);
      var0 = Double.longBitsToDouble(var4);
      var0 *= 1.5D - var2 * var0 * var0;
      return var0;
   }

   public static long floor_double_long(double var0) {
      long var2 = (long)var0;
      return var0 < (double)var2 ? var2 - 1L : var2;
   }

   public static int clamp_int(int var0, int var1, int var2) {
      return var0 < var1 ? var1 : (var0 > var2 ? var2 : var0);
   }

   public static float randomFloatClamp(Random var0, float var1, float var2) {
      return var1 >= var2 ? var1 : var0.nextFloat() * (var2 - var1) + var1;
   }

   public static double parseDoubleWithDefault(String var0, double var1) {
      try {
         return Double.parseDouble(var0);
      } catch (Throwable var4) {
         return var1;
      }
   }

   private static int calculateLogBaseTwoDeBruijn(int var0) {
      var0 = isPowerOfTwo(var0) ? var0 : roundUpToPowerOfTwo(var0);
      return multiplyDeBruijnBitPosition[(int)((long)var0 * 125613361L >> 27) & 31];
   }

   public static int func_180183_b(float var0, float var1, float var2) {
      return func_180181_b(floor_float(var0 * 255.0F), floor_float(var1 * 255.0F), floor_float(var2 * 255.0F));
   }

   public static double atan2(double var0, double var2) {
      double var4 = var2 * var2 + var0 * var0;
      if (Double.isNaN(var4)) {
         return Double.NaN;
      } else {
         boolean var6 = var0 < 0.0D;
         if (var6) {
            var0 = -var0;
         }

         boolean var7 = var2 < 0.0D;
         if (var7) {
            var2 = -var2;
         }

         boolean var8 = var0 > var2;
         double var9;
         if (var8) {
            var9 = var2;
            var2 = var0;
            var0 = var9;
         }

         var9 = func_181161_i(var4);
         var2 *= var9;
         var0 *= var9;
         double var11 = field_181163_d + var0;
         int var13 = (int)Double.doubleToRawLongBits(var11);
         double var14 = field_181164_e[var13];
         double var16 = field_181165_f[var13];
         double var18 = var11 - field_181163_d;
         double var20 = var0 * var16 - var2 * var18;
         double var22 = (6.0D + var20 * var20) * var20 * 0.16666666666666666D;
         double var24 = var14 + var22;
         if (var8) {
            var24 = 1.5707963267948966D - var24;
         }

         if (var7) {
            var24 = 3.141592653589793D - var24;
         }

         if (var6) {
            var24 = -var24;
         }

         return var24;
      }
   }

   public static int abs_int(int var0) {
      return var0 >= 0 ? var0 : -var0;
   }

   public static double denormalizeClamp(double var0, double var2, double var4) {
      return var4 < 0.0D ? var0 : (var4 > 1.0D ? var2 : var0 + (var2 - var0) * var4);
   }

   public static double getRandomDoubleInRange(Random var0, double var1, double var3) {
      return var1 >= var3 ? var1 : var0.nextDouble() * (var3 - var1) + var1;
   }

   public static float cos(float var0) {
      return fastMath ? SIN_TABLE_FAST[(int)((var0 + 1.5707964F) * 651.8986F) & 4095] : SIN_TABLE[(int)(var0 * 10430.378F + 16384.0F) & '\uffff'];
   }

   private static boolean isPowerOfTwo(int var0) {
      return var0 != 0 && (var0 & var0 - 1) == 0;
   }

   public static int ceiling_double_int(double var0) {
      int var2 = (int)var0;
      return var0 > (double)var2 ? var2 + 1 : var2;
   }

   public static int floor_double(double var0) {
      int var2 = (int)var0;
      return var0 < (double)var2 ? var2 - 1 : var2;
   }

   public static double clamp_double(double var0, double var2, double var4) {
      return var0 < var2 ? var2 : (var0 > var4 ? var4 : var0);
   }

   public static double average(long[] var0) {
      long var1 = 0L;
      long[] var3 = var0;
      int var4 = var0.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         long var6 = var3[var5];
         var1 += var6;
      }

      return (double)(var1 / (long)var0.length);
   }
}
