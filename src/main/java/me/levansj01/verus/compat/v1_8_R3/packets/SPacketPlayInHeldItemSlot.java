package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInHeldItemSlot;

public class SPacketPlayInHeldItemSlot extends VPacketPlayInHeldItemSlot {
   public void accept(PacketPlayInHeldItemSlot var1) {
      this.slot = var1.a();
   }
}
