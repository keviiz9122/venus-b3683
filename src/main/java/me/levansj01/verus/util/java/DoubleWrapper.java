package me.levansj01.verus.util.java;

public class DoubleWrapper {
   private double value;

   public DoubleWrapper(double var1) {
      this.value = (double)Double.doubleToRawLongBits(var1);
   }

   public double addAndGet(double var1) {
      this.value += var1;
      return this.value;
   }

   public double get() {
      return this.value;
   }

   public void set(double var1) {
      this.value = var1;
   }
}
