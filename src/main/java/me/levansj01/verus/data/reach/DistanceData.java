package me.levansj01.verus.data.reach;

import me.levansj01.verus.util.Cuboid;

public class DistanceData {
   private final double vertical;
   private final double z;
   private final double angle;
   private final double extra;
   private final Cuboid hitbox;
   private final double horizontal;
   private final double reach;
   private final double x;
   private final double previousTickExtra;

   public double getZ() {
      return this.z;
   }

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      long var3 = Double.doubleToLongBits(this.getX());
      int var20 = var2 * 59 + (int)(var3 >>> 32 ^ var3);
      long var5 = Double.doubleToLongBits(this.getZ());
      var20 = var20 * 59 + (int)(var5 >>> 32 ^ var5);
      long var7 = Double.doubleToLongBits(this.getHorizontal());
      var20 = var20 * 59 + (int)(var7 >>> 32 ^ var7);
      long var9 = Double.doubleToLongBits(this.getAngle());
      var20 = var20 * 59 + (int)(var9 >>> 32 ^ var9);
      long var11 = Double.doubleToLongBits(this.getExtra());
      var20 = var20 * 59 + (int)(var11 >>> 32 ^ var11);
      long var13 = Double.doubleToLongBits(this.getVertical());
      var20 = var20 * 59 + (int)(var13 >>> 32 ^ var13);
      long var15 = Double.doubleToLongBits(this.getReach());
      var20 = var20 * 59 + (int)(var15 >>> 32 ^ var15);
      long var17 = Double.doubleToLongBits(this.getPreviousTickExtra());
      var20 = var20 * 59 + (int)(var17 >>> 32 ^ var17);
      Cuboid var19 = this.getHitbox();
      int var10000 = var20 * 59;
      int var10001;
      if (var19 == null) {
         var10001 = 43;
      } else {
         var10001 = var19.hashCode();
      }

      var20 = var10000 + var10001;
      return var20;
   }

   public double getVertical() {
      return this.vertical;
   }

   public DistanceData(Cuboid var1, double var2, double var4, double var6, double var8, double var10, double var12, double var14, double var16) {
      this.hitbox = var1;
      this.x = var2;
      this.z = var4;
      this.horizontal = var6;
      this.angle = var8;
      this.extra = var10;
      this.vertical = var12;
      this.reach = var14;
      this.previousTickExtra = var16;
   }

   public double getAngle() {
      return this.angle;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof DistanceData)) {
         return false;
      } else {
         DistanceData var2 = (DistanceData)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else if (Double.compare(this.getX(), var2.getX()) != 0) {
            return false;
         } else if (Double.compare(this.getZ(), var2.getZ()) != 0) {
            return false;
         } else if (Double.compare(this.getHorizontal(), var2.getHorizontal()) != 0) {
            return false;
         } else if (Double.compare(this.getAngle(), var2.getAngle()) != 0) {
            return false;
         } else if (Double.compare(this.getExtra(), var2.getExtra()) != 0) {
            return false;
         } else if (Double.compare(this.getVertical(), var2.getVertical()) != 0) {
            return false;
         } else if (Double.compare(this.getReach(), var2.getReach()) != 0) {
            return false;
         } else if (Double.compare(this.getPreviousTickExtra(), var2.getPreviousTickExtra()) != 0) {
            return false;
         } else {
            Cuboid var3 = this.getHitbox();
            Cuboid var4 = var2.getHitbox();
            if (var3 == null) {
               if (var4 != null) {

                  return false;
               }
            } else if (!var3.equals(var4)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof DistanceData;
   }

   public double getX() {
      return this.x;
   }

   public String toString() {
      return "DistanceData(hitbox=" + this.getHitbox() + ", x=" + this.getX() + ", z=" + this.getZ() + ", horizontal=" + this.getHorizontal() + ", angle=" + this.getAngle() + ", extra=" + this.getExtra() + ", vertical=" + this.getVertical() + ", reach=" + this.getReach() + ", previousTickExtra=" + this.getPreviousTickExtra() + ")";
   }

   public double getHorizontal() {
      return this.horizontal;
   }

   public double getPreviousTickExtra() {
      return this.previousTickExtra;
   }

   public double getReach() {
      return this.reach;
   }

   public double getExtra() {
      return this.extra;
   }

   public Cuboid getHitbox() {
      return this.hitbox;
   }
}
