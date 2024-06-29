package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.WorldSettings.EnumGamemode;
import org.bukkit.GameMode;

public class SPacketPlayOutRespawn extends VPacketPlayOutRespawn {
   private static final Field c_field = SafeReflection.access(PacketPlayOutRespawn.class, "c");

   public void accept(PacketPlayOutRespawn var1) {
      EnumGamemode var2 = (EnumGamemode)SafeReflection.fetch(c_field, var1);
      this.gameMode = GameMode.getByValue(var2.getId());
   }
}
