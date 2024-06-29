package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.data.transaction.tracker.IServerLocation;
import me.levansj01.verus.data.transaction.tracker.ServerLocation;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;

public class SPacketPlayOutEntityTeleport extends VPacketPlayOutEntityTeleport {
   protected int iY;
   private static final Field pitch_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "f");
   private static final Field yaw_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "e");
   protected int iZ;
   private static final Field ground_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "g");
   private static final Field y_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "c");
   private static final Field x_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "b");
   protected int iX;
   private static final Field z_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "d");
   private static final Field id_field = SafeReflection.access(PacketPlayOutEntityTeleport.class, "a");

   public PacketPlayOutEntityTeleport create() {
      PacketPlayOutEntityTeleport var1 = new PacketPlayOutEntityTeleport();
      SafeReflection.set(id_field, var1, this.id);
      SafeReflection.set(x_field, var1, MathHelper.floor(this.x * 32.0D));
      SafeReflection.set(y_field, var1, MathHelper.floor(this.y * 32.0D));
      SafeReflection.set(z_field, var1, MathHelper.floor(this.z * 32.0D));
      SafeReflection.set(yaw_field, var1, this.yaw);
      SafeReflection.set(pitch_field, var1, this.pitch);
      SafeReflection.set(ground_field, var1, this.ground);
      return var1;
   }

   public IServerLocation location() {
      return new ServerLocation(this.iX, this.iY, this.iZ);
   }

   public void accept(PacketPlayOutEntityTeleport var1) {
      this.id = (Integer)SafeReflection.fetch(id_field, var1);
      this.iX = (Integer)SafeReflection.fetch(x_field, var1);
      this.iY = (Integer)SafeReflection.fetch(y_field, var1);
      this.iZ = (Integer)SafeReflection.fetch(z_field, var1);
      this.x = (double)this.iX / 32.0D;
      this.y = (double)this.iY / 32.0D;
      this.z = (double)this.iZ / 32.0D;
      this.yaw = (Byte)SafeReflection.fetch(yaw_field, var1);
      this.pitch = (Byte)SafeReflection.fetch(pitch_field, var1);
      this.ground = (Boolean)SafeReflection.fetch(ground_field, var1);
   }
}
