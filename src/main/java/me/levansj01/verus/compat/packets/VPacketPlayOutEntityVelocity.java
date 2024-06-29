package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutEntityVelocity extends VPacket {
   protected int motY;
   private static final int count = count();
   protected int motZ;
   protected int id;
   protected int motX;

   public int ordinal() {
      return count;
   }

   public int getMotX() {
      return this.motX;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int getMotZ() {
      return this.motZ;
   }

   public int getMotY() {
      return this.motY;
   }

   public int getId() {
      return this.id;
   }
}
