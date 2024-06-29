package me.levansj01.verus.verus2.data.player;

public class TickerMap {
   private final int[] timers = new int[TickerType.values().length];

   public int increment(TickerType var1) {
      return ++this.timers[var1.ordinal()];
   }

   public void reset(TickerType var1) {
      this.set(var1, 0);
   }

   public void set(TickerType var1, int var2) {
      this.timers[var1.ordinal()] = var2;
   }

   public void incrementAll() {
      TickerType[] var1 = TickerType.getAutos();
      int var2 = var1.length;
      int var3 = 0;

      do {
         if (var3 >= var2) {
            return;
         }

         TickerType var4 = var1[var3];
         this.increment(var4);
         ++var3;
      } while (true);
   }

   public TickerMap() {
      int var1 = 0;
      TickerType[] var2 = TickerType.values();
      int var3 = var2.length;
      int var4 = 0;

      do {
         if (var4 >= var3) {
            return;
         }

         TickerType var5 = var2[var4];
         this.timers[var1] = var5.getStarting();
         ++var1;
         ++var4;
      } while (true);
   }

   public int get(TickerType var1) {
      return this.timers[var1.ordinal()];
   }

   public void set(TickerType var1, TickerType var2) {
      this.timers[var1.ordinal()] = this.get(var2);
   }
}
