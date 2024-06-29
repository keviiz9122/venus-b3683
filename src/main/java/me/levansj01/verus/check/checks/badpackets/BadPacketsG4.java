package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.transaction.teleport.Teleport;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "G4",
   friendlyName = "Ping Spoof",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D
)
public class BadPacketsG4 extends Check {
   private int threshold = 0;

   public void accept(long var1, Teleport var3) {
      long var4 = var1 - var3.getTime();
      int var6 = this.playerData.getTransactionPing();
      if (var4 * 10L + 100L < (long)var6 && this.playerData.getTotalTicks() > 100) {
         if (this.threshold++ > 3) {
            this.handleViolation(() -> {
               return String.format("Tr: %s K: %s Te: %s", var6, this.playerData.getPing(), var4);
            }, (double)this.threshold / 4.0D);
            this.playerData.setTransactionPing((int)var4);
         }
      } else {
         this.threshold = 0;
      }

   }
}
