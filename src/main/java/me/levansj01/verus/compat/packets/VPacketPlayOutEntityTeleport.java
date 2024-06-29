package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.tracker.BasicLocation;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;

public abstract class VPacketPlayOutEntityTeleport extends VPacket {
   protected byte yaw;
   protected double x;
   protected double y;
   protected boolean ground;
   protected double z;
   protected byte pitch;
   private static final int count = count();
   protected int id;

   public double getZ() {
      return this.z;
   }

   public byte getPitch() {
      return this.pitch;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public PacketLocation toLocation(long var1) {
      return new PacketLocation(this.x, this.y + 0.015625D, this.z, var1, Long.MAX_VALUE);
   }

   public double getX() {
      return this.x;
   }

   public IServerLocation location() {
      return new BasicLocation(this.x, this.y, this.z);
   }

   public int getId() {
      return this.id;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public boolean isGround() {
      return this.ground;
   }

   public int ordinal() {
      return count;
   }

   public double getY() {
      return this.y;
   }
}
