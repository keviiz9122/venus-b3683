package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutExplosion extends VPacket {
   protected float motY;
   private static final int count = count();
   protected float motX;
   protected float motZ;

   public float getMotY() {
      return this.motY;
   }

   public float getMotZ() {
      return this.motZ;
   }

   public int ordinal() {
      return count;
   }

   public float getMotX() {
      return this.motX;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }
}
