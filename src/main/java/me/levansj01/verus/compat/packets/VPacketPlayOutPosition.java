package me.levansj01.verus.compat.packets;

import java.util.Set;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.transaction.teleport.BasicTeleport;
import me.levansj01.verus.data.transaction.teleport.Teleport;
import me.levansj01.verus.util.location.ILocation;
import me.levansj01.verus.util.location.PlayerLocation;

public abstract class VPacketPlayOutPosition extends VPacket implements ILocation {
   protected Set<VPacketPlayOutPosition.TeleportFlag> flags;
   protected double x;
   protected double z;
   private static final int count = count();
   protected float yaw;
   protected double y;
   protected float pitch;

   public Set<VPacketPlayOutPosition.TeleportFlag> getFlags() {
      return this.flags;
   }

   public int ordinal() {
      return count;
   }

   public float getYaw() {
      return this.yaw;
   }

   public double getZ() {
      return this.z;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public double getY() {
      return this.y;
   }

   public float getPitch() {
      return this.pitch;
   }

   public Teleport toTeleport(int var1, long var2) {
      return new Teleport(this.x, this.y, this.z, this.yaw, this.pitch, this.flags, var1, var2);
   }

   public double getX() {
      return this.x;
   }

   public PlayerLocation toLocation(PlayerData var1) {
      return new PlayerLocation(System.currentTimeMillis(), var1.getTotalTicks(), this.x, this.y, this.z, this.yaw, this.pitch, (Boolean)null, false);
   }

   public BasicTeleport toTeleport() {
      return new BasicTeleport(this.x, this.y, this.z, this.yaw, this.pitch, this.flags);
   }

   public static enum TeleportFlag {
      Z,
      X,
      Y,
      X_ROT,
      Y_ROT;

      public boolean contains(int var1) {
         return (var1 & this.bit()) != 0;
      }

      public int bit() {
         return 1 << this.ordinal();
      }
   }
}
