package me.levansj01.verus.data.state.lazy;

import java.util.function.Supplier;
import me.levansj01.verus.data.state.State;

public class Lazy implements State {
   private T reference;
   private Supplier<T> supplier;
   private boolean fetched;

   public T get() {
      if (!this.fetched) {
         this.reference = this.supplier.get();
         this.fetched = true;
         this.supplier = null;
      }

      return this.reference;
   }

   public void release() {
      this.supplier = null;
      this.reference = null;
   }

   public Lazy(Supplier<T> var1) {
      this.supplier = var1;
   }
}
