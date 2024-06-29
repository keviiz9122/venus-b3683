package me.levansj01.verus.util.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface BasicDeque extends Iterable {
   void addFirst(T var1);

   void clear();

   T pollLast();

   void addLast(T var1);

   T pollFirst();

   T peekLast();

   T peekFirst();

   default List<T> toList() {
      ArrayList var1 = new ArrayList(this.size());
      Iterator var2 = this.iterator();

      while(var2.hasNext()) {
         Object var3 = var2.next();
         var1.add(var3);
      }

      return var1;
   }

   default Stream<T> stream() {
      return StreamSupport.stream(Spliterators.spliterator(this.iterator(), (long)this.size(), 16), false);
   }

   int size();
}
