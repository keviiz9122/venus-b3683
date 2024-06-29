package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayInAbilities;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayInAbilities;

public class SPacketPlayInAbilities extends VPacketPlayInAbilities {
   private static final Field e_field = SafeReflection.access(PacketPlayInAbilities.class, "e");
   private static final Field f_field = SafeReflection.access(PacketPlayInAbilities.class, "f");

   public void accept(PacketPlayInAbilities var1) {
      this.abilities = new PlayerAbilities(var1.a(), var1.isFlying(), var1.c(), var1.d(), (Float)SafeReflection.fetch(e_field, var1), (Float)SafeReflection.fetch(f_field, var1));
   }
}
