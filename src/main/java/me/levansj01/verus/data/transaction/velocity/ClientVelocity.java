package me.levansj01.verus.data.transaction.velocity;

public class ClientVelocity extends BasicVelocity {
   private final int ticks;
   private final long timestamp;

   public int getTicks() {
      return this.ticks;
   }

   public ClientVelocity(double var1, double var3, double var5, int var7, long var8, boolean var10) {
      super(var1, var3, var5, var10);
      this.ticks = var7;
      this.timestamp = var8;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public String toString() {
      return "ClientVelocity(ticks=" + this.getTicks() + ", timestamp=" + this.getTimestamp() + ")";
   }
}
