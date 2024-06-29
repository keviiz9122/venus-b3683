package me.levansj01.verus.util.java;

import java.util.function.Supplier;

public class CachedSupplier implements Supplier {
   private T instance;
   private Supplier<T> supplier;

   public Supplier<T> getSupplier() {
      return this.supplier;
   }

   public void setSupplier(Supplier<T> var1) {
      this.supplier = var1;
   }

   public T get() {
      if (this.supplier != null) {
         this.instance = this.supplier.get();
         this.supplier = null;
      }

      return this.instance;
   }

   public T getInstance() {
      return this.instance;
   }

   public void setInstance(T var1) {
      this.instance = var1;
   }

   public static <T> CachedSupplier<T> of(Supplier<T> var0) {
      CachedSupplier var1 = new CachedSupplier();
      var1.setSupplier(var0);
      return var1;
   }
}
