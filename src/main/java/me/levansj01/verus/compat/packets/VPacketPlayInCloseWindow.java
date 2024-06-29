package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInCloseWindow extends VPacket {
   private static final int count = count();
   protected int window;

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int ordinal() {
      return count;
   }
}
