package me.levansj01.verus.type;

public enum VerusType {
   CUSTOM,
   REGULAR,
   ENTERPRISE,
   DEV,
   PREMIUM;

   public boolean afterOrEq(VerusType var1) {
      boolean var10000;
      if (this.ordinal() >= var1.ordinal()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public boolean before(VerusType var1) {
      boolean var10000;
      if (this.ordinal() < var1.ordinal()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public boolean after(VerusType var1) {
      boolean var10000;
      if (this.ordinal() > var1.ordinal()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }
}
