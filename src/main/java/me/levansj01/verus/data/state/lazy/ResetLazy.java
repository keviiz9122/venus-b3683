package me.levansj01.verus.data.state.lazy;

import java.util.function.Supplier;
import me.levansj01.verus.data.state.ResetState;

public class ResetLazy implements ResetState {
   private Supplier<T> supplier;
   private T data;
   private boolean fetched;

   public T get() {
      if (!this.fetched) {
         this.data = this.supplier.get();
         this.fetched = true;
      }

      return this.data;
   }

   public void release() {
      this.supplier = null;
      this.data = null;
   }

   public ResetLazy(Supplier<T> var1) {
      this.supplier = var1;
   }

   public void reset() {
      this.fetched = false;
   }
}
