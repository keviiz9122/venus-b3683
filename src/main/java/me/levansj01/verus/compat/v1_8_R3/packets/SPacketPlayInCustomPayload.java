package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;

public class SPacketPlayInCustomPayload extends VPacketPlayInCustomPayload {
   public void accept(PacketPlayInCustomPayload var1) {
      this.channel = var1.a();

      try {
         this.size = var1.b().capacity();
      } catch (NullPointerException | IllegalStateException var3) {
      }

   }
}
