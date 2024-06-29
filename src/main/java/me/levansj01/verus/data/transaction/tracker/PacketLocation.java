package me.levansj01.verus.data.transaction.tracker;

public class PacketLocation extends BasicLocation {
   private long nextOffset;
   private long timestamp;

   public void setNextOffset(long var1) {
      this.nextOffset = var1;
   }

   public int hashCode() {
      boolean var1 = true;
      int var2 = super.hashCode();
      long var3 = this.getTimestamp();
      var2 = var2 * 59 + (int)(var3 >>> 32 ^ var3);
      long var5 = this.getNextOffset();
      var2 = var2 * 59 + (int)(var5 >>> 32 ^ var5);
      return var2;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long var1) {
      this.timestamp = var1;
   }

   public PacketLocation add(double var1, double var3, double var5) {
      return new PacketLocation(this.x + var1, this.y + var3, this.z + var5, this.timestamp, this.nextOffset);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof PacketLocation)) {
         return false;
      } else {
         PacketLocation var2 = (PacketLocation)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else if (!super.equals(var1)) {
            return false;
         } else if (this.getTimestamp() != var2.getTimestamp()) {
            return false;
         } else {
            return this.getNextOffset() == var2.getNextOffset();
         }
      }
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof PacketLocation;
   }

   public PacketLocation interpolateFrom(PacketLocation var1, int var2) {
      double var3 = (double)var2 / 3.0D;
      return new PacketLocation(var1.x + (this.x - var1.x) * var3, var1.y + (this.y - var1.y) * var3, var1.z + (this.z - var1.z) * var3, this.timestamp, this.nextOffset);
   }

   public long getNextOffset() {
      return this.nextOffset;
   }

   public long getTime() {
      return this.nextOffset - this.timestamp;
   }

   public PacketLocation(double var1, double var3, double var5, long var7, long var9) {
      super(var1, var3, var5);
      this.timestamp = var7;
      this.nextOffset = var9;
   }

   public PacketLocation clone() {
      return new PacketLocation(this.x, this.y, this.z, this.timestamp, this.nextOffset);
   }
}
