package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.ServerLocation;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;

public class SPacketPlayOutNamedEntitySpawn extends VPacketPlayOutNamedEntitySpawn {
   private static final Field id_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "a");
   private static final Field pitch_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "g");
   protected int iX;
   private static final Field yaw_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "f");
   protected int iY;
   protected int iZ;
   private static final Field y_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "d");
   private static final Field z_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "e");
   private static final Field x_field = SafeReflection.access(PacketPlayOutNamedEntitySpawn.class, "c");

   public IServerLocation location() {
      return new ServerLocation(this.iX, this.iY, this.iZ);
   }

   public void accept(PacketPlayOutNamedEntitySpawn var1) {
      this.id = (Integer)SafeReflection.fetch(id_field, var1);
      this.iX = (Integer)SafeReflection.fetch(x_field, var1);
      this.iY = (Integer)SafeReflection.fetch(y_field, var1);
      this.iZ = (Integer)SafeReflection.fetch(z_field, var1);
      this.x = (double)this.iX / 32.0D;
      this.y = (double)this.iY / 32.0D;
      this.z = (double)this.iZ / 32.0D;
      this.yaw = (Byte)SafeReflection.fetch(yaw_field, var1);
      this.pitch = (Byte)SafeReflection.fetch(pitch_field, var1);
   }

   public PacketPlayOutNamedEntitySpawn create() {
      PacketPlayOutNamedEntitySpawn var1 = new PacketPlayOutNamedEntitySpawn();
      SafeReflection.set(id_field, var1, this.id);
      SafeReflection.set(x_field, var1, MathHelper.floor(this.x * 32.0D));
      SafeReflection.set(y_field, var1, MathHelper.floor(this.y * 32.0D));
      SafeReflection.set(z_field, var1, MathHelper.floor(this.z * 32.0D));
      SafeReflection.set(yaw_field, var1, this.yaw);
      SafeReflection.set(pitch_field, var1, this.pitch);
      return var1;
   }
}
