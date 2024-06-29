package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.data.transaction.tracker.ServerLocation;

public abstract class VPacketPlayOutEntity extends VPacket {
   protected byte pitch;
   protected boolean ground;
   protected int y;
   protected boolean look;
   protected byte yaw;
   protected int id;
   private static final int count;
   protected int z;
   protected int x;
   protected boolean pos;
   public static final double DIVIDER;

   public IServerLocation move() {
      return new ServerLocation(this.x, this.y, this.z);
   }

   public byte getPitch() {
      return this.pitch;
   }

   public String toString() {
      return "VPacketPlayOutEntity(id=" + this.getId() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", pitch=" + this.getPitch() + ", yaw=" + this.getYaw() + ", ground=" + this.isGround() + ", look=" + this.isLook() + ", pos=" + this.isPos() + ")";
   }

   static {
      DIVIDER = NMSManager.getInstance().getServerVersion().after(ServerVersion.v1_8_R3) ? 4096.0D : 32.0D;
      count = count();
   }

   public boolean isPos() {
      return this.pos;
   }

   public PacketLocation move(PacketLocation var1) {
      return var1.add((double)this.x / DIVIDER, (double)this.y / DIVIDER, (double)this.z / DIVIDER);
   }

   public int getX() {
      return this.x;
   }

   public boolean moves() {
      return this.pos && (this.x != 0 || this.y != 0 || this.z != 0);
   }

   public boolean isGround() {
      return this.ground;
   }

   public int getZ() {
      return this.z;
   }

   public boolean isLook() {
      return this.look;
   }

   public int getId() {
      return this.id;
   }

   public int ordinal() {
      return count;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public byte getYaw() {
      return this.yaw;
   }

   public int getY() {
      return this.y;
   }
}
