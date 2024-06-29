package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutGameStateChange extends VPacket {
   protected int setting;
   protected float value;
   private static final int count = count();

   public int getSetting() {
      return this.setting;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public float getValue() {
      return this.value;
   }

   public int ordinal() {
      return count;
   }
}
