package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import java.math.BigInteger;
import me.levansj01.verus.compat.packets.VPacketPlayOutKeepAlive;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutKeepAlive;

public class SPacketPlayOutKeepAlive extends VPacketPlayOutKeepAlive {
   private static final Field id_field = SafeReflection.access(PacketPlayOutKeepAlive.class, "a");

   public void accept(PacketPlayOutKeepAlive var1) {
      this.id = BigInteger.valueOf(((Integer)SafeReflection.fetch(id_field, var1)).longValue());
   }
}
