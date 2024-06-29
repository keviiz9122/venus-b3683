package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.type.enterprise.transaction.world.VerusChunk;

public interface SectionUpdate extends Releasable {
   default void after(VerusChunk var1) {
   }

   default boolean contains(BlockPosition var1) {
      return this.contains(var1.getX() & 15, var1.getY() & 15, var1.getZ() & 15);
   }

   boolean contains(int var1, int var2, int var3);

   State<LazyData> get(int var1, int var2, int var3);

   default void before(VerusChunk var1) {
   }
}
