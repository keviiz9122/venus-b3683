package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutBlockChange;
import me.levansj01.verus.compat.v1_8_R3.NMSManager;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;

public class SPacketPlayOutBlockChange extends VPacketPlayOutBlockChange {
   private static final Field block_field = SafeReflection.access(PacketPlayOutBlockChange.class, "block");
   private static final Field a_field = SafeReflection.access(PacketPlayOutBlockChange.class, "a");

   public void accept(PacketPlayOutBlockChange var1) {
      BlockPosition var2 = (BlockPosition)SafeReflection.fetch(a_field, var1);
      this.x = var2.getX();
      this.y = var2.getY();
      this.z = var2.getZ();
      IBlockData var3 = (IBlockData)SafeReflection.fetch(block_field, var1);
      this.data = NMSManager.getInstance().toLazy(var3);
   }
}
