package me.levansj01.verus.compat.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutKeepAlive extends VPacket {
   protected BigInteger id;
   private static final int count = count();

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public long getId() {
      return this.id.longValueExact();
   }

   public int ordinal() {
      return count;
   }
}
