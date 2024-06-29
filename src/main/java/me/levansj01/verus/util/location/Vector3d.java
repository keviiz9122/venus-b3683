package me.levansj01.verus.util.location;

public class Vector3d implements IVector3d {
   protected double y;
   protected double x;
   protected double z;

   public Vector3d subtract(IVector3d var1) {
      this.x -= var1.getX();
      this.y -= var1.getY();
      this.z -= var1.getZ();
      return this;
   }

   public double lengthSquared() {
      return this.x * this.x + this.y * this.y + this.z * this.z;
   }

   public double getY() {
      return this.y;
   }

   public Vector3d copy() {
      return new Vector3d(this.x, this.y, this.z);
   }

   public Vector3d add(Vector3d var1) {
      this.x += var1.x;
      this.y += var1.y;
      this.z += var1.z;
      return this;
   }

   public Vector3d divide(double var1) {
      this.x /= var1;
      this.y /= var1;
      this.z /= var1;
      return this;
   }

   public Vector3d multiply(double var1) {
      this.x *= var1;
      this.y *= var1;
      this.z *= var1;
      return this;
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public double length() {
      return Math.sqrt(this.lengthSquared());
   }

   public Vector3d add(double var1) {
      this.x += var1;
      this.y += var1;
      this.z += var1;
      return this;
   }

   public Vector3d getIntermediateWithZValue(Vector3d var1, double var2) {
      double var4 = var1.x - this.x;
      double var6 = var1.y - this.y;
      double var8 = var1.z - this.z;
      if (var8 * var8 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double var10 = (var2 - this.z) / var8;
         return var10 >= 0.0D && var10 <= 1.0D ? new Vector3d(this.x + var4 * var10, this.y + var6 * var10, this.z + var8 * var10) : null;
      }
   }

   public Vector3d normalize() {
      return this.divide(this.length());
   }

   public Vector3d getIntermediateWithXValue(Vector3d var1, double var2) {
      double var4 = var1.x - this.x;
      double var6 = var1.y - this.y;
      double var8 = var1.z - this.z;
      if (var4 * var4 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double var10 = (var2 - this.x) / var4;
         return var10 >= 0.0D && var10 <= 1.0D ? new Vector3d(this.x + var4 * var10, this.y + var6 * var10, this.z + var8 * var10) : null;
      }
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public double getZ() {
      return this.z;
   }

   public Vector3d(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
   }

   public Vector3d getIntermediateWithYValue(Vector3d var1, double var2) {
      double var4 = var1.x - this.x;
      double var6 = var1.y - this.y;
      double var8 = var1.z - this.z;
      if (var6 * var6 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double var10 = (var2 - this.y) / var6;
         return var10 >= 0.0D && var10 <= 1.0D ? new Vector3d(this.x + var4 * var10, this.y + var6 * var10, this.z + var8 * var10) : null;
      }
   }

   public double distance(Vector3d var1) {
      return Math.sqrt(this.distanceSquared(var1));
   }

   public double distanceSquared(Vector3d var1) {
      return (this.x - var1.x) * (this.x - var1.x) + (this.y - var1.y) * (this.y - var1.y) + (this.z - var1.z) * (this.z - var1.z);
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public double getX() {
      return this.x;
   }

   public String toString() {
      return "Vector3d(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
   }
}
