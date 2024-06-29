package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;

public class SPacketPlayInEntityAction extends VPacketPlayInEntityAction {
   public void accept(PacketPlayInEntityAction var1) {
      this.action = VPacketPlayInEntityAction.PlayerAction.values()[var1.b().ordinal()];
      this.value = var1.c();
   }
}
