package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;

public abstract class VPacketPlayOutTransaction extends VPacket implements Transactionable {
   protected int window;
   protected short id;
   protected boolean accepted;
   private static final int count = count();

   public int ordinal() {
      return count;
   }

   public int getWindow() {
      return this.window;
   }

   public short getId() {
      return this.id;
   }

   public short id() {
      return this.id;
   }

   public boolean valid() {
      return this.window == 0 && !this.accepted;
   }

   public void handle(PacketHandler var1) {
      var1.handleOut(this);
   }

   public boolean isAccepted() {
      return this.accepted;
   }
}
