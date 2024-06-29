package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutAttachEntity;

public class SPacketPlayOutAttachEntity extends VPacketPlayOutAttachEntity {
   private static final Field c_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "c");
   private static final Field a_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "a");
   private static final Field b_field = SafeReflection.access(PacketPlayOutAttachEntity.class, "b");

   public void accept(PacketPlayOutAttachEntity var1) {
      this.leash = (byte)(Integer)SafeReflection.fetch(a_field, var1);
      this.entityId = (Integer)SafeReflection.fetch(b_field, var1);
      this.attachId = (Integer)SafeReflection.fetch(c_field, var1);
   }
}
