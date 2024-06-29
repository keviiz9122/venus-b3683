package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.util.location.IPacketLocation;
import me.levansj01.verus.util.location.PacketLocation;

public abstract class VPacketPlayInFlying extends VPacket implements IPacketLocation {
   protected float pitch;
   protected double x;
   protected boolean look;
   protected double z;
   protected boolean teleport;
   protected float yaw;
   protected boolean pos;
   protected boolean ground;
   private static final int count = count();
   protected double y;

   public void setTeleport(boolean var1) {
      this.teleport = var1;
   }

   public PacketLocation from(PacketLocation var1) {
      double var2;
      double var4;
      double var6;
      if (this.pos) {
         var2 = this.x;
         var4 = this.y;
         var6 = this.z;
      } else {
         var2 = var1.getX();
         var4 = var1.getY();
         var6 = var1.getZ();
      }

      float var8;
      float var9;
      if (this.look) {
         var8 = this.yaw;
         var9 = this.pitch;
      } else {
         var8 = var1.getYaw();
         var9 = var1.getPitch();
      }

      return new PacketLocation(var2, var4, var6, var8, var9, this.ground, this.pos, this.look, this.teleport);
   }

   public float getYaw() {
      return this.yaw;
   }

   public double getZ() {
      return this.z;
   }

   public double getX() {
      return this.x;
   }

   public boolean isTeleport() {
      return this.teleport;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public boolean isGround() {
      return this.ground;
   }

   public boolean isLook() {
      return this.look;
   }

   public double getY() {
      return this.y;
   }

   public int ordinal() {
      return count;
   }

   public boolean isPos() {
      return this.pos;
   }

   public float getPitch() {
      return this.pitch;
   }
}
