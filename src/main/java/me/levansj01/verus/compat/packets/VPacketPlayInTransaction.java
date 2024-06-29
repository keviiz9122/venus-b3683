package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;

public abstract class VPacketPlayInTransaction extends VPacket implements Transactionable {
   protected short id;
   private static final int count = count();

   public short getId() {
      return this.id;
   }

   public void handle(PacketHandler var1) {
      var1.handleIn(this);
   }

   public int ordinal() {
      return count;
   }

   public short id() {
      return this.id;
   }
}
