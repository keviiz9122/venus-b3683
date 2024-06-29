package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.data.transaction.world.SectionUpdate;
import me.levansj01.verus.type.enterprise.transaction.world.Section;
import me.levansj01.verus.type.enterprise.transaction.world.VerusChunk;
import me.levansj01.verus.util.IBlockPosition;

public abstract class VPacketPlayOutMultiBlockChange extends VPacket {
   protected VPacketPlayOutMultiBlockChange.BlockChange[] changes;
   private static final int count = count();
   protected int chunkZ;
   protected int chunkX;

   public int ordinal() {
      return count;
   }

   public VPacketPlayOutMultiBlockChange.BlockChange[] getChanges() {
      return this.changes;
   }

   public int getChunkX() {
      return this.chunkX;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int getChunkZ() {
      return this.chunkZ;
   }

   public static class BlockChange implements IBlockPosition, SectionUpdate {
      private final int y;
      private final LazyData data;
      private final int x;
      private final int z;

      public BlockChange(int var1, int var2, int var3, LazyData var4) {
         this.x = var1;
         this.y = var2;
         this.z = var3;
         this.data = var4;
      }

      public void before(VerusChunk var1) {
         var1.getSection(this.y >> 4).getUpdates().add(this);
      }

      public State<LazyData> get(int var1, int var2, int var3) {
         return this.contains(var1, var2, var3) ? State.of(this.data) : null;
      }

      public int getY() {
         return this.y;
      }

      public boolean equals(Object var1) {
         if (var1 == this) {
            return true;
         } else if (!(var1 instanceof VPacketPlayOutMultiBlockChange.BlockChange)) {
            return false;
         } else {
            VPacketPlayOutMultiBlockChange.BlockChange var2 = (VPacketPlayOutMultiBlockChange.BlockChange)var1;
            if (!var2.canEqual(this)) {
               return false;
            } else if (this.getX() != var2.getX()) {
               return false;
            } else if (this.getY() != var2.getY()) {
               return false;
            } else if (this.getZ() != var2.getZ()) {
               return false;
            } else {
               LazyData var3 = this.getData();
               LazyData var4 = var2.getData();
               if (var3 == null) {
                  if (var4 != null) {
                     return false;
                  }
               } else if (!var3.equals(var4)) {
                  return false;
               }

               return true;
            }
         }
      }

      public int getZ() {
         return this.z;
      }

      public boolean contains(int var1, int var2, int var3) {
         return var1 == this.x && var2 == (this.y & 15) && var3 == this.z;
      }

      protected boolean canEqual(Object var1) {
         return var1 instanceof VPacketPlayOutMultiBlockChange.BlockChange;
      }

      public int getX() {
         return this.x;
      }

      public LazyData getData() {
         return this.data;
      }

      public String toString() {
         return "VPacketPlayOutMultiBlockChange.BlockChange(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", data=" + this.getData() + ")";
      }

      public void after(VerusChunk var1) {
         Section var2 = var1.getSection(this.y >> 4);
         var2.getUpdates().remove(this);
         SectionData var3 = var2.getData();
         if (var3 != null) {
            var3.set(this.x, this.y & 15, this.z, this.data);
         }
      }

      public int hashCode() {
         boolean var1 = true;
         byte var2 = 1;
         int var4 = var2 * 59 + this.getX();
         var4 = var4 * 59 + this.getY();
         var4 = var4 * 59 + this.getZ();
         LazyData var3 = this.getData();
         var4 = var4 * 59 + (var3 == null ? 43 : var3.hashCode());
         return var4;
      }
   }
}
