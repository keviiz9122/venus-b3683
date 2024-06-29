package me.levansj01.verus.check.checks.badpackets;

import java.util.function.Supplier;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "G3",
   friendlyName = "Ping Spoof",
   minViolations = -3.0D,
   maxViolations = 30,
   version = CheckVersion.RELEASE,
   logData = true
)
public class BadPacketsG3 extends Check implements PacketHandler {
   private boolean receivedKeepAlive;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.receivedKeepAlive) {
         double var2 = MathUtil.highest((double)this.playerData.getLastTransactionPing(), (double)this.playerData.getTransactionPing(), (double)this.playerData.getAverageTransactionPing());
         double var4 = MathUtil.lowest(this.playerData.getLastPing(), this.playerData.getPing(), this.playerData.getAveragePing());
         if (var4 - var2 > 50.0D + var2 / 4.0D) {
            Supplier var10001 = () -> {
               return String.format("K(%s) T(%s) %s", var4, var2, var1.isPos());
            };
            double var10002;
            if (StorageEngine.getInstance().getVerusConfig().isMoreTransactions()) {
               var10002 = 0.25D;
            } else {
               var10002 = 1.0D;
            }

            this.handleViolation(var10001, var10002);
         } else {
            double var6;
            if (StorageEngine.getInstance().getVerusConfig().isMoreTransactions()) {
               var6 = 0.3D;
            } else {
               var6 = 0.1D;
            }

            this.decreaseVL(var6);
         }

         this.receivedKeepAlive = false;
      }

   }

   public void handle(VPacketPlayInKeepAlive<?> var1) {
      this.receivedKeepAlive = true;
   }
}
