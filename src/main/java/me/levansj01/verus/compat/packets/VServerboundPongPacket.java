package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;

public abstract class VServerboundPongPacket extends VPacket implements Transactionable {
   private static final int count = count();
   protected int id;

   public short id() {
      return (short)this.id;
   }

   public void handle(PacketHandler var1) {
      var1.handleIn(this);
   }

   public int ordinal() {
      return count;
   }

   public int getId() {
      return this.id;
   }
}
