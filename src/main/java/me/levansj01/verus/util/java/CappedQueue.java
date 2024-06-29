package me.levansj01.verus.util.java;

import java.util.Arrays;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;

public class CappedQueue implements BasicDeque {
   private int startIndex;
   private final Object[] internal;
   private int lastIndex;

   public void clear() {
      Arrays.fill(this.internal, (Object)null);
      this.lastIndex = this.startIndex;
   }

   @NotNull
   public Iterator<T> iterator() {
      return new Iterator() {
         private int current;

         public T next() {
            return CappedQueue.this.internal[CappedQueue.modulo(this.current++, CappedQueue.this.internal.length)];
         }

         {
            this.current = CappedQueue.this.startIndex;
         }

         public boolean hasNext() {
            return this.current < CappedQueue.this.lastIndex;
         }
      };
   }

   public CappedQueue(int var1) {
      if (var1 <= 1) {
         throw new IllegalArgumentException("Cannot have length of " + var1);
      } else {
         this.internal = new Object[var1];
      }
   }

   private static int modulo(int var0, int var1) {
      int var2 = var0 % var1;
      if (var2 < 0) {
         var2 += var1;
      }

      return var2;
   }

   public T peekFirst() {
      return this.internal[modulo(this.startIndex, this.internal.length)];
   }

   private T getAndSetInternal(int var1, T var2) {
      Object var3 = this.internal[var1];
      this.internal[var1] = var2;
      return var3;
   }

   public T pollFirst() {
      return this.getAndSetInternal(modulo(this.startIndex++, this.internal.length), (Object)null);
   }

   public int size() {
      return this.lastIndex - this.startIndex;
   }

   public void addFirst(T var1) {
      Object var2 = this.getAndSetInternal(modulo(--this.startIndex, this.internal.length), var1);
      if (var2 != null) {
         --this.lastIndex;
      }

   }

   public void addLast(T var1) {
      Object var2 = this.getAndSetInternal(modulo(this.lastIndex++, this.internal.length), var1);
      if (var2 != null) {
         ++this.startIndex;
      }

   }

   public T pollLast() {
      return this.getAndSetInternal(modulo(--this.lastIndex, this.internal.length), (Object)null);
   }

   public T peekLast() {
      return this.internal[modulo(this.lastIndex - 1, this.internal.length)];
   }
}
