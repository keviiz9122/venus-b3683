package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInHeldItemSlot extends VPacket {
   protected int slot;
   private static final int count = count();

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int getSlot() {
      return this.slot;
   }

   public int ordinal() {
      return count;
   }
}
