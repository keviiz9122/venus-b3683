package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

public class SPacketPlayOutSpawnEntityLiving extends VPacketPlayOutSpawnEntityLiving {
   private static final Field PacketPlayOutSpawnEntityLiving_a = SafeReflection.access(PacketPlayOutSpawnEntityLiving.class, "a");

   public void accept(PacketPlayOutSpawnEntityLiving var1) {
      this.id = (Integer)SafeReflection.fetch(PacketPlayOutSpawnEntityLiving_a, var1);
   }
}
