package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.data.state.State;

public interface SectionData extends SectionUpdate {
   State<LazyData> get(int var1, int var2, int var3);

   void set(int var1, int var2, int var3, LazyData var4);

   default boolean contains(int var1, int var2, int var3) {
      return true;
   }
}
