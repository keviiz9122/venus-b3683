package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInSteerVehicle extends VPacket {
   protected double forward;
   private static final int count = count();
   protected double strafe;

   public double getForward() {
      return this.forward;
   }

   public int ordinal() {
      return count;
   }

   public double getStrafe() {
      return this.strafe;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }
}
