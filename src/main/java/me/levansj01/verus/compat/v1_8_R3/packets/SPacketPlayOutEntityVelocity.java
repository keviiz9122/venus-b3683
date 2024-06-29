package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;

public class SPacketPlayOutEntityVelocity extends VPacketPlayOutEntityVelocity {
   private static final Field id_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "a", "id");
   private static final Field motZ_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "d", "z");
   private static final Field motY_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "c", "y");
   private static final Field motX_field = SafeReflection.access(PacketPlayOutEntityVelocity.class, "b", "x");

   public void accept(PacketPlayOutEntityVelocity var1) {
      this.id = (Integer)SafeReflection.fetch(id_field, var1);
      this.motX = (Integer)SafeReflection.fetch(motX_field, var1);
      this.motY = (Integer)SafeReflection.fetch(motY_field, var1);
      this.motZ = (Integer)SafeReflection.fetch(motZ_field, var1);
   }
}
