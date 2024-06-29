package me.levansj01.verus.compat.api;

import java.util.function.Consumer;
import java.util.logging.Level;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.data.PlayerData;

public abstract class VPacketListener implements Consumer {
   protected final PlayerData data;

   public void accept(Object var1) {
      if (this.data.isEnabled()) {
         try {
            this.process(var1);
         } catch (Throwable var3) {
            VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to handle outgoing " + var1.getClass().getSimpleName() + ": ", var3);
         }

      }
   }

   protected <T extends O> void handleOut(T var1, VPacket<T> var2) {
      var2.accept(var1);
      var2.handle(this.data);
   }

   public VPacketListener(PlayerData var1) {
      this.data = var1;
   }

   protected <T extends I> void handleIn(T var1, VPacket<T> var2) {
      var2.accept(var1);
      this.data.preProcess(var2);
      var2.handle(this.data);
      this.data.handlePacketListeners(var2);
      this.data.postProcess(var2);
   }

   protected abstract void process(Object var1);
}
