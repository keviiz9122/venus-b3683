package me.levansj01.verus.data.version;

public enum ClientVersion {
   V1_8("1.8"),
   V1_13("1.13"),
   V1_12("1.12"),
   V1_10("1.10"),
   NONE("Error"),
   V1_7("1.7"),
   V1_16("1.16"),
   V1_19("1.19"),
   V1_17("1.17"),
   V1_9("1.9"),
   VERSION_UNSUPPORTED("Unknown"),
   V1_11("1.11"),
   V1_14("1.14"),
   V1_18("1.18"),
   V1_15("1.15");

   private final String name;

   public String getName() {
      return this.name;
   }

   private ClientVersion(String var3) {
      this.name = var3;
   }

   public boolean afterEq(ClientVersion var1) {
      boolean var10000;
      if (this.ordinal() >= var1.ordinal()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public boolean before(ClientVersion var1) {
      boolean var10000;
      if (this.ordinal() < var1.ordinal()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public boolean after(ClientVersion var1) {
      boolean var10000;
      if (this.ordinal() > var1.ordinal()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public ClientVersion next() {
      ClientVersion[] var1 = values();
      return var1[Math.min(this.ordinal() + 1, var1.length - 1)];
   }

   public static String a() {
      return "nice watermark!";
   }
}
