package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.tracker.BasicLocation;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;

public abstract class VPacketPlayOutNamedEntitySpawn extends VPacket {
   protected double x;
   protected int id;
   protected byte pitch;
   protected byte yaw;
   protected double z;
   private static final int count = count();
   protected double y;

   public double getX() {
      return this.x;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public void setYaw(byte var1) {
      this.yaw = var1;
   }

   public double getZ() {
      return this.z;
   }

   public void setPitch(byte var1) {
      this.pitch = var1;
   }

   public void setY(double var1) {
      this.y = var1;
   }

   public int ordinal() {
      return count;
   }

   public void setId(int var1) {
      this.id = var1;
   }

   public IServerLocation location() {
      return new BasicLocation(this.x, this.y, this.z);
   }

   public int getId() {
      return this.id;
   }

   public double getY() {
      return this.y;
   }

   public void setX(double var1) {
      this.x = var1;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public void setZ(double var1) {
      this.z = var1;
   }

   public PacketLocation toLocation(long var1) {
      return new PacketLocation(this.x, this.y, this.z, var1, Long.MAX_VALUE);
   }

   public byte getPitch() {
      return this.pitch;
   }
}
