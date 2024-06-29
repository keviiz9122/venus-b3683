package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;

public abstract class VPacketPlayOutSpawnPosition extends VPacket {
   protected BlockPosition position;
   private static final int count = count();

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public BlockPosition getPosition() {
      return this.position;
   }

   public int ordinal() {
      return count;
   }
}
