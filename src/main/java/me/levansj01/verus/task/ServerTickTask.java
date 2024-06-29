package me.levansj01.verus.task;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.storage.StorageEngine;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class ServerTickTask implements Runnable {
   private long lastTick;
   private final AtomicBoolean cancel = new AtomicBoolean();
   private BukkitTask bukkitTask;
   public static ServerTickTask instance;
   private long tick;
   private long lastTransaction;
   private Runnable endTask;

   public BukkitTask getBukkitTask() {
      return this.bukkitTask;
   }

   public AtomicBoolean getCancel() {
      return this.cancel;
   }

   public long getLastTransaction() {
      return this.lastTransaction;
   }

   public boolean isLagging(long var1) {
      boolean var10000;
      if (var1 - this.tick <= 750L && this.tick - this.lastTick <= 750L) {
         var10000 = false;
      } else {
         var10000 = true;
      }

      return var10000;
   }

   public static ServerTickTask getInstance() {
      ServerTickTask var10000;
      if (instance == null) {
         var10000 = instance = new ServerTickTask();
      } else {
         var10000 = instance;
      }

      return var10000;
   }

   public long getTick() {
      return this.tick;
   }

   public Runnable getEndTask() {
      return this.endTask;
   }

   public void cancel() {
      this.cancel.set(true);
      if (this.bukkitTask != null) {
         this.bukkitTask.cancel();
      }

      if (this.endTask != null) {
         NMSManager.getInstance().postToMainThread(this.endTask);
      }

   }

   public void run() {
      this.lastTick = this.tick;
      this.tick = System.currentTimeMillis();
      if (StorageEngine.getInstance().getVerusConfig().isMoreTransactions() || this.tick - this.lastTransaction >= 250L) {
         this.lastTransaction = this.tick;
         this.sendTransactions();
      }

   }

   public long getLastTick() {
      return this.lastTick;
   }

   public void sendTransactions() {
      if (!this.cancel.get()) {
         NMSManager var1 = NMSManager.getInstance();
         Iterator var2 = DataManager.getInstance().getPlayerList().iterator();

         do {
            if (!var2.hasNext()) {
               return;
            }

            PlayerData var3 = (PlayerData)var2.next();
            var1.sendTransaction(var3.getPlayer(), var3.incrementTransactionId());
         } while (true);
      }
   }

   public void schedule() {
      this.bukkitTask = Bukkit.getScheduler().runTaskTimer(VerusLauncher.getPlugin(), this, 1L, 1L);
      if (StorageEngine.getInstance().getVerusConfig().isMoreTransactions()) {
         NMSManager var1 = NMSManager.getInstance();
         var1.postToMainThread(() -> {
            if (!this.cancel.get()) {
               this.endTask = var1.scheduleEnd(this::sendTransactions);
            }
         });
      }

   }
}
