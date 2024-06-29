package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutSpawnEntityLiving extends VPacket {
   protected int id;
   private static final int count = count();

   public int getId() {
      return this.id;
   }

   public int ordinal() {
      return count;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }
}
