package me.levansj01.verus.data.transaction.velocity;

public class BasicVelocity {
   private final double x;
   private final double z;
   private final double y;
   private final boolean explosion;

   public BasicVelocity(double var1, double var3, double var5, boolean var7) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.explosion = var7;
   }

   public double getRoundedX() {
      double var10000;
      if (this.isExplosion()) {
         var10000 = this.getX();
      } else {
         var10000 = this.round(this.getX());
      }

      return var10000;
   }

   public String toString() {
      return "BasicVelocity(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", explosion=" + this.isExplosion() + ")";
   }

   public boolean isExplosion() {
      return this.explosion;
   }

   public double getY() {
      return this.y;
   }

   public double getRoundedZ() {
      double var10000;
      if (this.isExplosion()) {
         var10000 = this.getZ();
      } else {
         var10000 = this.round(this.getZ());
      }

      return var10000;
   }

   public double getZ() {
      return this.z;
   }

   private double round(double var1) {
      return (double)((int)(var1 * 8000.0D)) / 8000.0D;
   }

   public double getRoundedY() {
      double var10000;
      if (this.isExplosion()) {
         var10000 = this.getY();
      } else {
         var10000 = this.round(this.getY());
      }

      return var10000;
   }

   public double getX() {
      return this.x;
   }
}
