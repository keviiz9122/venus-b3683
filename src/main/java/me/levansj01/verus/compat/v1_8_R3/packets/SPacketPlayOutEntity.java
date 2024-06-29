package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook;

public class SPacketPlayOutEntity extends VPacketPlayOutEntity {
   private static final Field id_field = SafeReflection.access(PacketPlayOutEntity.class, "a");
   private static final Field y_field = SafeReflection.access(PacketPlayOutEntity.class, "c");
   private static final Field yaw_field = SafeReflection.access(PacketPlayOutEntity.class, "e");
   private static final Field pitch_field = SafeReflection.access(PacketPlayOutEntity.class, "f");
   private static final Field z_field = SafeReflection.access(PacketPlayOutEntity.class, "d");
   private static final Field ground_field = SafeReflection.access(PacketPlayOutEntity.class, "g");
   private static final Field x_field = SafeReflection.access(PacketPlayOutEntity.class, "b");
   private static final Field look_field = SafeReflection.access(PacketPlayOutEntity.class, "h");

   public PacketPlayOutEntity create() {
      PacketPlayOutEntity var1 = new PacketPlayOutEntity();
      SafeReflection.set(id_field, var1, this.id);
      SafeReflection.set(x_field, var1, this.x);
      SafeReflection.set(y_field, var1, this.y);
      SafeReflection.set(z_field, var1, this.z);
      SafeReflection.set(yaw_field, var1, this.yaw);
      SafeReflection.set(pitch_field, var1, this.pitch);
      SafeReflection.set(ground_field, var1, this.ground);
      SafeReflection.set(look_field, var1, this.look);
      return var1;
   }

   public void accept(PacketPlayOutEntity var1) {
      this.id = (Integer)SafeReflection.fetch(id_field, var1);
      this.x = (Byte)SafeReflection.fetch(x_field, var1);
      this.y = (Byte)SafeReflection.fetch(y_field, var1);
      this.z = (Byte)SafeReflection.fetch(z_field, var1);
      this.yaw = (Byte)SafeReflection.fetch(yaw_field, var1);
      this.pitch = (Byte)SafeReflection.fetch(pitch_field, var1);
      this.ground = (Boolean)SafeReflection.fetch(ground_field, var1);
      this.look = (Boolean)SafeReflection.fetch(look_field, var1);
      this.pos = var1 instanceof PacketPlayOutRelEntityMoveLook || var1 instanceof PacketPlayOutRelEntityMove;
   }
}
