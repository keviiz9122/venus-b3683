package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInSetCreativeSlot extends VPacket {
   private static final int count = count();

   public int ordinal() {
      return count;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }
}
