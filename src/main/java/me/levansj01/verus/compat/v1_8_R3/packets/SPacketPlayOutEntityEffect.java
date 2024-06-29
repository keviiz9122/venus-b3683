package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityEffect;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEffect;

public class SPacketPlayOutEntityEffect extends VPacketPlayOutEntityEffect {
   private static final Field b_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "b");
   private static final Field c_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "c");
   private static final Field a_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "a");
   private static final Field d_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "d");
   private static final Field e_field = SafeReflection.access(PacketPlayOutEntityEffect.class, "e");

   public void accept(PacketPlayOutEntityEffect var1) {
      this.id = (Integer)SafeReflection.fetch(a_field, var1);
      this.effect = (Byte)SafeReflection.fetch(b_field, var1);
      this.amplifier = (Byte)SafeReflection.fetch(c_field, var1);
      this.duration = (Integer)SafeReflection.fetch(d_field, var1);
      this.flags = (Byte)SafeReflection.fetch(e_field, var1);
   }
}
