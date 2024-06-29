package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutAbilities;

public class SPacketPlayOutAbilities extends VPacketPlayOutAbilities {
   private static final Field f_field = SafeReflection.access(PacketPlayOutAbilities.class, "f");
   private static final Field e_field = SafeReflection.access(PacketPlayOutAbilities.class, "e");

   public void accept(PacketPlayOutAbilities var1) {
      this.isInvulnerable = var1.a();
      this.isFlying = var1.b();
      this.canFly = var1.c();
      this.canInstantlyBuild = var1.d();
      this.walkSpeed = (Float)SafeReflection.fetch(e_field, var1);
      this.flySpeed = (Float)SafeReflection.fetch(f_field, var1);
   }
}
