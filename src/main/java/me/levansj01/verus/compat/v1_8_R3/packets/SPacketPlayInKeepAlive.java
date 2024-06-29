package me.levansj01.verus.compat.v1_8_R3.packets;

import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import net.minecraft.server.v1_8_R3.PacketPlayInKeepAlive;

public class SPacketPlayInKeepAlive extends VPacketPlayInKeepAlive {
   public void accept(PacketPlayInKeepAlive var1) {
      this.id = BigInteger.valueOf((long)var1.a());
   }
}
