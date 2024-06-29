package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInCustomPayload extends VPacket {
   private static final int count = count();
   protected int size;
   protected String channel;

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int ordinal() {
      return count;
   }

   public String getChannel() {
      return this.channel;
   }

   public int getSize() {
      return this.size;
   }
}
