package me.levansj01.verus.data.transaction.tracker;

import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.location.IVector3d;

public class BasicLocation implements IServerLocation {
   protected final double z;
   protected final double y;
   protected final double x;

   public double getY() {
      return this.y;
   }

   public Cuboid hitbox() {
      return (new Cuboid(this.x, this.y, this.z)).hitbox();
   }

   public BasicLocation add(IServerLocation var1) {
      return this.add(var1.getX(), var1.getY(), var1.getZ());
   }

   public double getZ() {
      return this.z;
   }

   public BasicLocation(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof BasicLocation)) {
         return false;
      } else {
         BasicLocation var2 = (BasicLocation)var1;
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

   public BasicLocation add(double var1, double var3, double var5) {
      return new BasicLocation(this.x + var1, this.y + var3, this.z + var5);
   }

   public String toString() {
      return "BasicLocation(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
   }

   public double getX() {
      return this.x;
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

   public BasicLocation interpolate(IVector3d var1, int var2) {
      if (var2 <= 0) {
         return this;
      } else if (var2 >= 3) {
         return new BasicLocation(var1.getX(), var1.getY(), var1.getZ());
      } else {
         double var3 = (double)var2 / 3.0D;
         return new BasicLocation(this.x + (var1.getX() - this.x) * var3, this.y + (var1.getY() - this.y) * var3, this.z + (var1.getZ() - this.z) * var3);
      }
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof BasicLocation;
   }
}
