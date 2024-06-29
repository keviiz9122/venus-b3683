package me.levansj01.verus.util.location;

public class BasicPacketLocation implements ILocation {
   protected final float pitch;
   protected final double z;
   protected final float yaw;
   protected final double x;
   protected final double y;

   public double getZ() {
      return this.z;
   }

   public float getPitch() {
      return this.pitch;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof BasicPacketLocation)) {
         return false;
      } else {
         BasicPacketLocation var2 = (BasicPacketLocation)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else if (Double.compare(this.getX(), var2.getX()) != 0) {
            return false;
         } else if (Double.compare(this.getY(), var2.getY()) != 0) {
            return false;
         } else {
            return Double.compare(this.getZ(), var2.getZ()) == 0;
         }
      }
   }

   public BasicPacketLocation(double var1, double var3, double var5, float var7, float var8) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = var7;
      this.pitch = var8;
   }

   public String toString() {
      return "BasicPacketLocation(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ")";
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof BasicPacketLocation;
   }

   public float getYaw() {
      return this.yaw;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      long var3 = Double.doubleToLongBits(this.getX());
      int var9 = var2 * 59 + (int)(var3 >>> 32 ^ var3);
      long var5 = Double.doubleToLongBits(this.getY());
      var9 = var9 * 59 + (int)(var5 >>> 32 ^ var5);
      long var7 = Double.doubleToLongBits(this.getZ());
      var9 = var9 * 59 + (int)(var7 >>> 32 ^ var7);
      return var9;
   }
}
