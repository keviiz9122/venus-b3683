package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunkBulk;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunkBulk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk.ChunkMap;

public class SPacketPlayOutMapChunkBulk extends VPacketPlayOutMapChunkBulk {
   private static final Field b_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "b");
   private static final Field c_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "c");
   private static final Field d_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "d");
   private static final Field a_field = SafeReflection.access(PacketPlayOutMapChunkBulk.class, "a");

   public void accept(PacketPlayOutMapChunkBulk var1) {
      this.groundUp = (Boolean)SafeReflection.fetch(d_field, var1);
      int[] var2 = (int[])SafeReflection.fetch(a_field, var1);
      int[] var3 = (int[])SafeReflection.fetch(b_field, var1);
      ChunkMap[] var4 = (ChunkMap[])SafeReflection.fetch(c_field, var1);
      this.chunks = State.fast(() -> {
         VPacketPlayOutMapChunkBulk.MappedChunk[] var3x = new VPacketPlayOutMapChunkBulk.MappedChunk[var4.length];

         for(int var4x = 0; var4x < var3x.length; ++var4x) {
            int var5 = var2[var4x];
            int var6 = var3[var4x];
            ChunkMap var7 = var4[var4x];
            boolean var8 = var7 == null || var7.a.length == 0;
            var3x[var4x] = new VPacketPlayOutMapChunkBulk.MappedChunk(var5, var6, var8 ? null : SPacketPlayOutMapChunk.fromNMS(var7, true), var8);
         }

         return var3x;
      });
   }
}
