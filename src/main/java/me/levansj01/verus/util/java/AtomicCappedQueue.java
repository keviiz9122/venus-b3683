package me.levansj01.verus.util.java;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import org.jetbrains.annotations.NotNull;

public class AtomicCappedQueue implements BasicDeque {
   private final AtomicInteger lastIndex = new AtomicInteger(0);
   private final AtomicInteger startIndex = new AtomicInteger(0);
   private final int length;
   private final AtomicReferenceArray<T> internal;

   public T peekLast() {
      return this.internal.get(modulo(this.lastIndex.get() - 1, this.length));
   }

   private static int modulo(int var0, int var1) {
      int var2 = var0 % var1;
      if (var2 < 0) {
         var2 += var1;
      }

      return var2;
   }

   public void addLast(T var1) {
      Object var2 = this.internal.getAndSet(modulo(this.lastIndex.getAndIncrement(), this.length), var1);
      if (var2 != null) {
         this.startIndex.incrementAndGet();
      }

   }

   public T pollLast() {
      return this.internal.getAndSet(modulo(this.lastIndex.decrementAndGet(), this.length), (Object)null);
   }

   public void addFirst(T var1) {
      Object var2 = this.internal.getAndSet(modulo(this.startIndex.decrementAndGet(), this.length), var1);
      if (var2 != null) {
         this.lastIndex.decrementAndGet();
      }

   }

   @NotNull
   public Iterator<T> iterator() {
      return new Iterator() {
         private int current;

         public boolean hasNext() {
            return this.current < AtomicCappedQueue.this.lastIndex.get();
         }

         {
            this.current = AtomicCappedQueue.this.startIndex.get();
         }

         public T next() {
            return AtomicCappedQueue.this.internal.get(AtomicCappedQueue.modulo(this.current++, AtomicCappedQueue.this.length));
         }
      };
   }

   public T pollFirst() {
      return this.internal.getAndSet(modulo(this.startIndex.getAndIncrement(), this.length), (Object)null);
   }

   public AtomicCappedQueue(int var1) {
      if (var1 <= 1) {
         throw new IllegalArgumentException("Cannot have length of " + var1);
      } else {
         this.length = var1;
         this.internal = new AtomicReferenceArray(var1);
      }
   }

   public int size() {
      return this.lastIndex.get() - this.startIndex.get();
   }

   public void clear() {
      for(int var1 = 0; var1 < this.length; ++var1) {
         this.internal.set(var1, (Object)null);
      }

      this.lastIndex.set(this.startIndex.get());
   }

   public T peekFirst() {
      return this.internal.get(modulo(this.startIndex.get(), this.length));
   }
}
