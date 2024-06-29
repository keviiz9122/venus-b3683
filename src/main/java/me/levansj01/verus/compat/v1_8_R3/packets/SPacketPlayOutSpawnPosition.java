package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnPosition;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnPosition;

public class SPacketPlayOutSpawnPosition extends VPacketPlayOutSpawnPosition {
   public void accept(PacketPlayOutSpawnPosition var1) {
      this.position = new BlockPosition(var1.position.getX(), var1.position.getY(), var1.position.getZ());
   }
}
