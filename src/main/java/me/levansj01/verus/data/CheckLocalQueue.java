package me.levansj01.verus.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;
import me.levansj01.verus.check.Check;

public class CheckLocalQueue {
   private final Supplier<Queue<T>> queueSupplier;
   private final Map<Check, Queue<T>> checkData;

   public void add(T var1) {
      this.apply((var1x) -> {
         var1x.add(var1);
      });
   }

   public Queue<T> get(Check var1) {
      return (Queue)this.checkData.computeIfAbsent(var1, (var1x) -> {
         return (Queue)this.queueSupplier.get();
      });
   }

   public static <T> CheckLocalQueue<T> async() {
      return new CheckLocalQueue(new ConcurrentHashMap(), ConcurrentLinkedQueue::new);
   }

   public CheckLocalQueue(Map<Check, Queue<T>> var1, Supplier<Queue<T>> var2) {
      this.checkData = var1;
      this.queueSupplier = var2;
   }

   public static <T> CheckLocalQueue<T> sync() {
      return new CheckLocalQueue(new HashMap(), LinkedList::new);
   }

   public void clear() {
      this.apply(Collection::clear);
   }

   public void apply(Consumer<Queue<T>> var1) {
      this.checkData.values().forEach(var1);
   }
}
