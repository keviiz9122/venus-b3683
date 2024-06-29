package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import net.minecraft.server.v1_8_R3.PacketPlayInClientCommand;

public class SPacketPlayInClientCommand extends VPacketPlayInClientCommand {
   public void accept(PacketPlayInClientCommand var1) {
      this.command = VPacketPlayInClientCommand.ClientCommand.values()[var1.a().ordinal()];
   }
}
