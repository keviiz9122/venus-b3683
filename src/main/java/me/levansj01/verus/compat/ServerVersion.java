package me.levansj01.verus.compat;

public enum ServerVersion {
   v1_18_R2,
   v1_16_R2,
   v1_8_R3,
   v1_16_R3,
   v1_11_R1,
   v1_17_R1,
   v1_13_R2,
   v1_14_R1,
   NONE,
   v1_16_R1,
   v1_19_R1,
   v1_7_R4,
   v1_12_R1,
   v1_18_R1,
   v1_15_R1;

   public boolean afterEq(ServerVersion var1) {
      return this.ordinal() >= var1.ordinal();
   }

   public boolean before(ServerVersion var1) {
      return var1.ordinal() > this.ordinal();
   }

   public boolean beforeEq(ServerVersion var1) {
      return var1.ordinal() >= this.ordinal();
   }

   public boolean after(ServerVersion var1) {
      return this.ordinal() > var1.ordinal();
   }
}
