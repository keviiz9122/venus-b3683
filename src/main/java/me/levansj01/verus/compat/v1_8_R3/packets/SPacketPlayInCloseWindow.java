package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayInCloseWindow;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayInCloseWindow;

public class SPacketPlayInCloseWindow extends VPacketPlayInCloseWindow {
   private static final Field window_field = SafeReflection.access(PacketPlayInCloseWindow.class, "id");

   public void accept(PacketPlayInCloseWindow var1) {
      this.window = (Integer)SafeReflection.fetch(window_field, var1);
   }
}
