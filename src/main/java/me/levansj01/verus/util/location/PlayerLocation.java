package me.levansj01.verus.util.location;

import me.levansj01.verus.util.java.MathHelper;

/** @deprecated */
@Deprecated
public class PlayerLocation implements ILocationGround {
   private double y;
   private long timestamp;
   private float yaw;
   private double z;
   private Boolean ground;
   private float pitch;
   private int tickTime;
   private double x;
   private boolean teleport;

   public PlayerLocation up() {
      return this.add(0.0D, 1.0D, 0.0D);
   }

   public double distanceXZ(IVector3d var1) {
      return (double)MathHelper.sqrt_double(this.distanceXZSquared(var1));
   }

   public Boolean getGround() {
      return this.ground;
   }

   public PlayerLocation clone() {
      return new PlayerLocation(this.timestamp, this.tickTime, this.x, this.y, this.z, this.yaw, this.pitch, this.ground, this.teleport);
   }

   public void setYaw(float var1) {
      this.yaw = var1;
   }

   public double getZ() {
      return this.z;
   }

   public boolean isGround() {
      return this.ground != null && this.ground;
   }

   public void setPitch(float var1) {
      this.pitch = var1;
   }

   public double getX() {
      return this.x;
   }

   public PlayerLocation add(double var1, double var3, double var5) {
      return new PlayerLocation(this.timestamp, this.tickTime, this.x + var1, this.y + var3, this.z + var5, this.yaw, this.pitch, this.ground, this.teleport);
   }

   public void setTimestamp(long var1) {
      this.timestamp = var1;
   }

   public int getTickTime() {
      return this.tickTime;
   }

   public void setTickTime(int var1) {
      this.tickTime = var1;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         PlayerLocation var2 = (PlayerLocation)var1;
         if (this.timestamp != var2.timestamp) {
            return false;
         } else if (this.tickTime != var2.tickTime) {
            return false;
         } else if (Double.compare(var2.x, this.x) != 0) {
            return false;
         } else if (Double.compare(var2.y, this.y) != 0) {
            return false;
         } else if (Double.compare(var2.z, this.z) != 0) {
            return false;
         } else if (Float.compare(var2.yaw, this.yaw) != 0) {
            return false;
         } else {
            return Float.compare(var2.pitch, this.pitch) == 0;
         }
      } else {
         return false;
      }
   }

   public void setTeleport(boolean var1) {
      this.teleport = var1;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public float getPitch() {
      return this.pitch;
   }

   public double distanceXZSquared(IVector3d var1) {
      return Math.pow(this.x - var1.getX(), 2.0D) + Math.pow(this.z - var1.getZ(), 2.0D);
   }

   public int hashCode() {
      int var1 = (int)(this.timestamp ^ this.timestamp >>> 32);
      var1 = 31 * var1 + this.tickTime;
      long var2 = Double.doubleToLongBits(this.x);
      var1 = 31 * var1 + (int)(var2 ^ var2 >>> 32);
      var2 = Double.doubleToLongBits(this.y);
      var1 = 31 * var1 + (int)(var2 ^ var2 >>> 32);
      var2 = Double.doubleToLongBits(this.z);
      var1 = 31 * var1 + (int)(var2 ^ var2 >>> 32);
      var1 = 31 * var1 + (this.yaw != 0.0F ? Float.floatToIntBits(this.yaw) : 0);
      var1 = 31 * var1 + (this.pitch != 0.0F ? Float.floatToIntBits(this.pitch) : 0);
      return var1;
   }

   public double getY() {
      return this.y;
   }

   public boolean isTeleport() {
      return this.teleport;
   }

   public float getYaw() {
      return this.yaw;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setGround(Boolean var1) {
      this.ground = var1;
   }

   public PlayerLocation(long var1, int var3, double var4, double var6, double var8, float var10, float var11, Boolean var12, boolean var13) {
      this.timestamp = var1;
      this.tickTime = var3;
      this.x = var4;
      this.y = var6;
      this.z = var8;
      this.yaw = var10;
      this.pitch = var11;
      this.ground = var12;
      this.teleport = var13;
   }
}
