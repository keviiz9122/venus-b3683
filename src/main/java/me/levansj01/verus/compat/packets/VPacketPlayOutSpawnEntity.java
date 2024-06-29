package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutSpawnEntity extends VPacket {
   private static final int count = count();
   protected int id;

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
