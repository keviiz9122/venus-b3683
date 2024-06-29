package me.levansj01.verus.compat.packets;

import java.util.Arrays;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.SectionData;

public abstract class VPacketPlayOutMapChunkBulk extends VPacket {
   private static final int count = count();
   protected boolean groundUp;
   protected State<VPacketPlayOutMapChunkBulk.MappedChunk[]> chunks;

   public boolean isGroundUp() {
      return this.groundUp;
   }

   public State<VPacketPlayOutMapChunkBulk.MappedChunk[]> getChunks() {
      return this.chunks;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int ordinal() {
      return count;
   }

   public static class MappedChunk {
      private final int z;
      private final int x;
      private final boolean unload;
      private final SectionData[] chunkMap;

      protected boolean canEqual(Object var1) {
         return var1 instanceof VPacketPlayOutMapChunkBulk.MappedChunk;
      }

      public boolean isUnload() {
         return this.unload;
      }

      public MappedChunk(int var1, int var2, SectionData[] var3, boolean var4) {
         this.x = var1;
         this.z = var2;
         this.chunkMap = var3;
         this.unload = var4;
      }

      public String toString() {
         return "VPacketPlayOutMapChunkBulk.MappedChunk(x=" + this.getX() + ", z=" + this.getZ() + ", chunkMap=" + Arrays.deepToString(this.getChunkMap()) + ", unload=" + this.isUnload() + ")";
      }

      public int hashCode() {
         boolean var1 = true;
         byte var2 = 1;
         int var3 = var2 * 59 + this.getX();
         var3 = var3 * 59 + this.getZ();
         var3 = var3 * 59 + (this.isUnload() ? 79 : 97);
         var3 = var3 * 59 + Arrays.deepHashCode(this.getChunkMap());
         return var3;
      }

      public int getZ() {
         return this.z;
      }

      public int getX() {
         return this.x;
      }

      public boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof VPacketPlayOutMapChunkBulk.MappedChunk)) {
            return false;
         } else {
            VPacketPlayOutMapChunkBulk.MappedChunk var2 = (VPacketPlayOutMapChunkBulk.MappedChunk)var1;
            if (!var2.canEqual(this)) {
               return false;
            } else if (this.getX() != var2.getX()) {
               return false;
            } else if (this.getZ() != var2.getZ()) {
               return false;
            } else if (this.isUnload() != var2.isUnload()) {
               return false;
            } else {
               return Arrays.deepEquals(this.getChunkMap(), var2.getChunkMap());
            }
         }
      }

      public SectionData[] getChunkMap() {
         return this.chunkMap;
      }
   }
}
