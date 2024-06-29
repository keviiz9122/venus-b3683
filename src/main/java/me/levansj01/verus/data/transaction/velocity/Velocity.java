package me.levansj01.verus.data.transaction.velocity;

import me.levansj01.verus.util.java.MathUtil;

/** @deprecated */
@Deprecated
public class Velocity {
   private final long timestamp;
   private double x;
   private final double originalY;
   private double y;
   private boolean attenuated = false;
   private double z;
   private Double hypotSquaredXZ = null;
   private final int ticks;
   private final boolean explosion;

   public double getOriginalY() {
      return this.originalY;
   }

   public Velocity(int var1, long var2, double var4, double var6, double var8, boolean var10) {
      this.ticks = var1;
      this.timestamp = var2;
      this.x = var4;
      this.y = var6;
      this.originalY = var6;
      this.z = var8;
      this.explosion = var10;
   }

   public int getTicks() {
      return this.ticks;
   }

   public boolean isExplosion() {
      return this.explosion;
   }

   public boolean isAttenuated() {
      return this.attenuated;
   }

   public double getZ() {
      return this.z;
   }

   public ClientVelocity getClient() {
      return new ClientVelocity(this.x, this.y, this.z, this.ticks, this.timestamp, this.explosion);
   }

   public double getHypotSquaredXZ() {
      if (this.hypotSquaredXZ == null) {
         this.hypotSquaredXZ = MathUtil.hypotSquared(this.x, this.z);
      }

      return this.hypotSquaredXZ;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public double getX() {
      return this.x;
   }

   public double getY() {
      return this.y;
   }

   /** @deprecated */
   @Deprecated
   public boolean attenuate(boolean var1) {
      if (var1) {
         this.y = 0.0D;
      } else if (this.y > 0.0D) {
         this.y -= Math.min(this.y, 0.08D);
      }

      this.y *= 0.9800000190734863D;
      this.x *= 0.9100000262260437D;
      this.z *= 0.9100000262260437D;
      if (var1) {
         this.x *= 0.6000000238418579D;
         this.z *= 0.6000000238418579D;
      }

      this.hypotSquaredXZ = null;
      this.attenuated = true;
      boolean var10000;
      if (Math.abs(MathUtil.highestAbs(this.x, this.y, this.z)) <= 0.001D) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }
}
