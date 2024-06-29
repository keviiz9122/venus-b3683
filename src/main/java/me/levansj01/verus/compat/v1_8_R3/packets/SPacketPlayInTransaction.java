package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInTransaction;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;

public class SPacketPlayInTransaction extends VPacketPlayInTransaction {
   public void accept(PacketPlayInTransaction var1) {
      this.id = var1.b();
   }
}
