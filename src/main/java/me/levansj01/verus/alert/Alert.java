package me.levansj01.verus.alert;

import java.util.UUID;
import java.util.function.Supplier;
import me.levansj01.verus.check.Check;

public class Alert {
   private String name;
   private final Supplier<String> data;
   private final Check check;
   private int lag;
   private UUID uuid;
   private final int vl;
   private final long timestamp = System.currentTimeMillis();
   private int ping;

   public long getTimestamp() {
      return this.timestamp;
   }

   public int getLag() {
      return this.lag;
   }

   public Check getCheck() {
      return this.check;
   }

   public int getVl() {
      return this.vl;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public Supplier<String> getData() {
      return this.data;
   }

   public Alert(Check var1, Supplier<String> var2, int var3) {
      this.check = var1;
      this.data = var2;
      this.vl = var3;
   }

   public void setUuid(UUID var1) {
      this.uuid = var1;
   }

   public void setPing(int var1) {
      this.ping = var1;
   }

   public String getName() {
      return this.name;
   }

   public int getPing() {
      return this.ping;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setLag(int var1) {
      this.lag = var1;
   }
}
