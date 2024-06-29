package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutTransaction;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutTransaction;

public class SPacketPlayOutTransaction extends VPacketPlayOutTransaction {
   private static final Field id_field = SafeReflection.access(PacketPlayOutTransaction.class, "b");
   private static final Field accepted_field = SafeReflection.access(PacketPlayOutTransaction.class, "c");
   private static final Field window_field = SafeReflection.access(PacketPlayOutTransaction.class, "a");

   public void accept(PacketPlayOutTransaction var1) {
      this.window = (Integer)SafeReflection.fetch(window_field, var1);
      this.id = (Short)SafeReflection.fetch(id_field, var1);
      this.accepted = (Boolean)SafeReflection.fetch(accepted_field, var1);
   }
}
