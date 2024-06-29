package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockDig;

public class SPacketPlayInBlockDig extends VPacketPlayInBlockDig {
   public void accept(PacketPlayInBlockDig var1) {
      this.blockPosition = new BlockPosition(var1.a().getX(), var1.a().getY(), var1.a().getZ());
      this.face = Direction.values()[var1.b().ordinal()];
      this.type = VPacketPlayInBlockDig.PlayerDigType.values()[var1.c().ordinal()];
   }
}
