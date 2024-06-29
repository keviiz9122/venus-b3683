package me.levansj01.verus.compat.api;

public interface Transactionable {
   default boolean valid() {
      return true;
   }

   short id();
}
