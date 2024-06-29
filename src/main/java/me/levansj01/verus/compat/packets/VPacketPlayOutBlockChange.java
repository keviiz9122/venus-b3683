package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.data.transaction.world.SectionUpdate;
import me.levansj01.verus.type.enterprise.transaction.world.Section;
import me.levansj01.verus.type.enterprise.transaction.world.VerusChunk;

public abstract class VPacketPlayOutBlockChange extends VPacket implements SectionUpdate {
   protected int z;
   private static final int count = count();
   protected int x;
   protected int y;
   protected LazyData data;

   public boolean contains(int var1, int var2, int var3) {
      return var1 == (this.x & 15) && var2 == (this.y & 15) && var3 == (this.z & 15);
   }

   public int getY() {
      return this.y;
   }

   public void before(VerusChunk var1) {
      var1.getSection(this.y >> 4).getUpdates().add(this);
   }

   public void after(VerusChunk var1) {
      Section var2 = var1.getSection(this.y >> 4);
      var2.getUpdates().remove(this);
      SectionData var3 = var2.getData();
      if (var3 != null) {
         var3.set(this.x & 15, this.y & 15, this.z & 15, this.data);
      }
   }

   public int ordinal() {
      return count;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int getX() {
      return this.x;
   }

   public int getZ() {
      return this.z;
   }

   public LazyData getData() {
      return this.data;
   }

   public State<LazyData> get(int var1, int var2, int var3) {
      return this.contains(var1, var2, var3) ? State.of(this.data) : null;
   }
}
