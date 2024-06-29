package me.levansj01.verus.compat.api.wrapper;

public enum Direction {
   EAST,
   UP,
   SOUTH,
   WEST,
   NORTH,
   DOWN;

   public static Direction byFace(int var0) {
      Direction[] var1;
      return var0 >= 0 && var0 < (var1 = values()).length ? var1[var0] : null;
   }
}
