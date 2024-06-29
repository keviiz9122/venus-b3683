package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInArmAnimation extends VPacket {
   private static final int count = count();
   protected int hand = 0;

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int getHand() {
      return this.hand;
   }

   public int ordinal() {
      return count;
   }
}
