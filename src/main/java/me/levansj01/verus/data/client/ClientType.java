package me.levansj01.verus.data.client;

public enum ClientType {
   VANILLA("Vanilla"),
   LUNAR("Lunar"),
   BEDROCK("Bedrock"),
   LITE_LOADER("LiteLoader"),
   UNKNOWN("Unknown"),
   FABRIC("Fabric"),
   BADLION("Badlion"),
   FORGE("Forge");

   private final String display;

   private ClientType(String var3) {
      this.display = var3;
   }

   public boolean isUnknown() {
      boolean var10000;
      if (this == UNKNOWN) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public String getDisplay() {
      return this.display;
   }
}
