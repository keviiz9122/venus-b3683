package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;

public class SPacketPlayOutPosition extends VPacketPlayOutPosition {
   private static final Field z_field = SafeReflection.access(PacketPlayOutPosition.class, "c", "z");
   private static final Field flags_field = SafeReflection.access(PacketPlayOutPosition.class, "f", "flags");
   private static final Field yaw_field = SafeReflection.access(PacketPlayOutPosition.class, "d", "yaw");
   private static final Field y_field = SafeReflection.access(PacketPlayOutPosition.class, "b", "y");
   private static final Field pitch_field = SafeReflection.access(PacketPlayOutPosition.class, "e", "pitch");
   private static final Field x_field = SafeReflection.access(PacketPlayOutPosition.class, "a", "x");

   public void accept(PacketPlayOutPosition var1) {
      this.x = (Double)SafeReflection.fetch(x_field, var1);
      this.y = (Double)SafeReflection.fetch(y_field, var1);
      this.z = (Double)SafeReflection.fetch(z_field, var1);
      this.yaw = (Float)SafeReflection.fetch(yaw_field, var1);
      this.pitch = (Float)SafeReflection.fetch(pitch_field, var1);
      Set var2 = (Set)SafeReflection.fetch(flags_field, var1);
      this.flags = (Set)var2.stream().map((var0) -> {
         return VPacketPlayOutPosition.TeleportFlag.values()[var0.ordinal()];
      }).collect(Collectors.toSet());
   }
}
