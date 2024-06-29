package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.v1_8_R3.NMSManager;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange.MultiBlockChangeInfo;

public class SPacketPlayOutMultiBlockChange extends VPacketPlayOutMultiBlockChange {
   private static final Field a_field = SafeReflection.access(PacketPlayOutMultiBlockChange.class, "a", "chunkCoordIntPair");
   private static final Field b_field = SafeReflection.access(PacketPlayOutMultiBlockChange.class, "b", "multiBlockChangeInfoArray");

   public void accept(PacketPlayOutMultiBlockChange var1) {
      ChunkCoordIntPair var2 = (ChunkCoordIntPair)SafeReflection.fetch(a_field, var1);
      this.chunkX = var2.x;
      this.chunkZ = var2.z;
      MultiBlockChangeInfo[] var3 = (MultiBlockChangeInfo[])SafeReflection.fetch(b_field, var1);
      this.changes = new VPacketPlayOutMultiBlockChange.BlockChange[var3.length];

      for(int var4 = 0; var4 < var3.length; ++var4) {
         MultiBlockChangeInfo var5 = var3[var4];
         BlockPosition var6 = var5.a();
         LazyData var7 = NMSManager.getInstance().toLazy(var5.c());
         this.changes[var4] = new VPacketPlayOutMultiBlockChange.BlockChange(var6.getX() & 15, var6.getY(), var6.getZ() & 15, var7);
      }

   }
}
