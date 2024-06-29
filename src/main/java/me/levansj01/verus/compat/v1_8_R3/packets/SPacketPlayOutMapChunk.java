package me.levansj01.verus.compat.v1_8_R3.packets;

import java.lang.reflect.Field;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.data.bytes.ByteData;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.BlankSection;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.util.java.SafeReflection;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk.ChunkMap;

public class SPacketPlayOutMapChunk extends VPacketPlayOutMapChunk {
   private static final Field b_field = SafeReflection.access(PacketPlayOutMapChunk.class, "b");
   private static final Field c_field = SafeReflection.access(PacketPlayOutMapChunk.class, "c");
   private static final Field d_field = SafeReflection.access(PacketPlayOutMapChunk.class, "d");
   private static final Field a_field = SafeReflection.access(PacketPlayOutMapChunk.class, "a");

   public void accept(PacketPlayOutMapChunk var1) {
      this.chunkX = (Integer)SafeReflection.fetch(a_field, var1);
      this.chunkZ = (Integer)SafeReflection.fetch(b_field, var1);
      this.groundUp = (Boolean)SafeReflection.fetch(d_field, var1);
      ChunkMap var2 = (ChunkMap)SafeReflection.fetch(c_field, var1);
      this.unload = var2 == null || var2.a.length == 0 && this.groundUp;
      this.chunkMap = this.unload ? State.of((Object)null) : State.fast(() -> {
         return fromNMS(var2, this.groundUp);
      });
   }

   public static SectionData[] fromNMS(ChunkMap var0, boolean var1) {
      SectionData[] var2 = new SectionData[16];
      if (var0 == null) {
         return var2;
      } else {
         ByteData var3 = wrap(var0.a);

         try {
            int var4 = var0.b;
            int var5 = 0;

            for(int var6 = 0; var6 < 16; ++var6) {
               if ((var4 >> var6 & 1) != 0) {
                  var2[var6] = me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk.SectionDataImpl.of(var3, var5);
                  ++var5;
               } else if (var1) {
                  var2[var6] = new BlankSection();
               }
            }

            SectionData[] var10 = var2;
            return var10;
         } finally {
            var3.release();
         }
      }
   }
}
