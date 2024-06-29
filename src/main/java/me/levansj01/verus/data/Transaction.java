package me.levansj01.verus.data;

import java.lang.ref.WeakReference;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.util.java.JavaV;

public class Transaction {
   private short id;
   private final WeakReference<Transaction> last;
   private final Queue<Runnable> runnableQueue;
   private Long sent;
   private final AtomicReference<Long> received;

   public Queue<Runnable> getRunnableQueue() {
      return this.runnableQueue;
   }

   public boolean isReceived() {
      boolean var10000;
      if (this.received.get() != null) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public short getId() {
      return this.id;
   }

   public WeakReference<Transaction> getLast() {
      return this.last;
   }

   public Long ping() {
      Long var10000;
      if (this.isSent() && this.isReceived()) {
         var10000 = (Long)this.received.get() - this.sent;
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public void queue(Runnable var1) {
      if (this.received.get() != null) {
         var1.run();
      } else {
         this.runnableQueue.add(var1);
      }

   }

   public AtomicReference<Long> getReceived() {
      return this.received;
   }

   public Transaction(Transaction var1) {
      this(new WeakReference(var1));
   }

   public Long getSent() {
      return this.sent;
   }

   public void receive(long var1, PlayerData var3) {
      this.received.set(var1);
      Transaction var4 = (Transaction)this.last.get();
      if (var4 != null && !var4.isReceived()) {
         int var5 = var3.getTotalTicks();
         Check var6 = var3.getCheckData().getSkipTransactionCheck();
         int var7 = var3.getReceivedTransactions();
         if (var6 != null) {
            var6.handleViolation(() -> {
               return String.format("ID: %s T: %s R: %s", var4.getId(), var5, var7);
            });
         }
      }

      this.last.clear();
      JavaV.executeSafely(this.runnableQueue, () -> {
         return " in transaction ID " + this.id;
      });
   }

   public void send(short var1, long var2) {
      this.id = var1;
      this.sent = var2;
   }

   private Transaction(WeakReference<Transaction> var1) {
      this.received = new AtomicReference((Object)null);
      this.runnableQueue = new ConcurrentLinkedQueue();
      this.last = var1;
   }

   public boolean isSent() {
      boolean var10000;
      if (this.sent != null) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }
}
