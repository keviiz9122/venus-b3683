package me.levansj01.verus.data.state.statics;

import me.levansj01.verus.data.state.State;

public class Static implements State {
   private final T data;

   public Static(T var1) {
      this.data = var1;
   }

   public T get() {
      return this.data;
   }
}
