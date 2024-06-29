package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutExplosion;

public class SPacketPlayOutExplosion extends VPacketPlayOutExplosion {
   private static final Field motY_field = SafeReflection.access(PacketPlayOutExplosion.class, "g");
   private static final Field motZ_field = SafeReflection.access(PacketPlayOutExplosion.class, "h");
   private static final Field motX_field = SafeReflection.access(PacketPlayOutExplosion.class, "f");

   public void accept(PacketPlayOutExplosion var1) {
      this.motX = (Float)SafeReflection.fetch(motX_field, var1);
      this.motY = (Float)SafeReflection.fetch(motY_field, var1);
      this.motZ = (Float)SafeReflection.fetch(motZ_field, var1);
   }
}
