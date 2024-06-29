package me.levansj01.verus.util.location;

import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.MutableBlockLocation;

public interface ILocation extends IVector3d {
   default boolean sameYawPitch(ILocation var1) {
      return this.getYaw() == var1.getYaw() && this.getPitch() == var1.getPitch();
   }

   default boolean sameXYZ(ILocation var1) {
      return this.sameXZ(var1) && this.getY() == var1.getY();
   }

   default BasicPacketLocation withY(double var1) {
      return new BasicPacketLocation(this.getX(), var1, this.getZ(), this.getYaw(), this.getPitch());
   }

   default ILocation[] interpolate(ILocation var1, int var2) {
      ILocation[] var3 = new ILocation[var2 + 1];
      byte var4 = 0;
      int var13 = var4 + 1;
      var3[var4] = this;
      double var5 = (var1.getX() - this.getX()) / (double)var2;
      double var7 = (var1.getY() - this.getY()) / (double)var2;
      double var9 = (var1.getZ() - this.getZ()) / (double)var2;
      float var11 = (var1.getYaw() - this.getYaw()) / (float)var2;

      for(float var12 = (var1.getPitch() - this.getPitch()) / (float)var2; var13 < var2; ++var13) {
         var3[var13] = new BasicPacketLocation(this.getX() + var5 * (double)var13, this.getY() + var7 * (double)var13, this.getZ() + var9 * (double)var13, this.getYaw() + var11 * (float)var13, this.getPitch() + var12 * (float)var13);
      }

      var3[var2] = var1;
      return var3;
   }

   /** @deprecated */
   @Deprecated
   default Vector3d toEyeVector(boolean var1) {
      return this.toEyeVector(var1 ? 1.5399999618530273D : 1.6200000047683716D);
   }

   default Vector3d getDirection() {
      double var1 = Math.cos(Math.toRadians((double)this.getPitch()));
      return new Vector3d(-var1 * Math.sin(Math.toRadians((double)this.getYaw())), -Math.sin(Math.toRadians((double)this.getPitch())), var1 * Math.cos(Math.toRadians((double)this.getYaw())));
   }

   default BasicPacketLocation pos(ILocation var1) {
      return new BasicPacketLocation(var1.getX(), var1.getY(), var1.getZ(), this.getYaw(), this.getPitch());
   }

   default double distanceSquared(ILocation var1) {
      return Math.pow(this.getX() - var1.getX(), 2.0D) + Math.pow(this.getY() - var1.getY(), 2.0D) + Math.pow(this.getZ() - var1.getZ(), 2.0D);
   }

   float getPitch();

   float getYaw();

   default MutableBlockLocation toBlock() {
      return new MutableBlockLocation((int)Math.floor(this.getX()), (int)Math.floor(this.getY()), (int)Math.floor(this.getZ()));
   }

   default boolean sameXZ(ILocation var1) {
      return this.getX() == var1.getX() && this.getZ() == var1.getZ();
   }

   default Vector3d toVector() {
      return new Vector3d(this.getX(), this.getY(), this.getZ());
   }

   default double distance(ILocation var1) {
      return Math.sqrt(this.distanceSquared(var1));
   }

   default boolean matches(ILocation var1) {
      return this.sameXYZ(var1) && this.sameYawPitch(var1);
   }

   default BasicPacketLocation look(ILocation var1) {
      return new BasicPacketLocation(this.getX(), this.getY(), this.getZ(), var1.getYaw(), var1.getPitch());
   }

   default Vector3d toEyeVector(double var1) {
      return new Vector3d(this.getX(), this.getY() + var1, this.getZ());
   }

   default Cuboid to(ILocation var1, int var2) {
      return this.distanceSquared(var1) > (double)var2 ? new Cuboid(this) : new Cuboid(Math.min(this.getX(), var1.getX()), Math.max(this.getX(), var1.getX()), Math.min(this.getY(), var1.getY()), Math.max(this.getY(), var1.getY()), Math.min(this.getZ(), var1.getZ()), Math.max(this.getZ(), var1.getZ()));
   }
}
