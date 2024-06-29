package me.levansj01.verus.check.checks.reach;

import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.reach.DistanceData;
import me.levansj01.verus.data.reach.ReachBase;

@CheckInfo(
   type = CheckType.REACH,
   subType = "A",
   friendlyName = "Reach",
   version = CheckVersion.RELEASE,
   maxViolations = 30,
   minViolations = -1.5D,
   logData = true
)
public class ReachA extends ReachCheck {
   private long lastFlag;

   public ReachA() {
      if (NMSManager.getInstance().getServerVersion().after(ServerVersion.v1_8_R3)) {
         this.setMaxViolation(100);
      }

   }

   public void handle(ReachBase var1, long var2) {
      DistanceData var4 = var1.getDistanceData();
      double var5 = var4.getReach();
      double var7 = var4.getExtra();
      double var9 = var4.getVertical();
      double var11 = var4.getHorizontal();
      if (var5 > 3.0D && var11 < 6.0D && var7 < 6.0D) {
         if (var2 - this.lastFlag < 50L) {
            return;
         }

         this.lastFlag = var2;
         this.handleViolation(String.format("R: %.2f H: %.2f V: %.2f E: %.2f S: %s C: %.2f P: %s", var5, var11, var9, var7, var1.getDistanceList().size(), var1.getCertainty(), this.playerData.getTransactionPing()), Math.min(var5, 4.5D) - 3.0D + var1.getCertainty());
      } else {
         this.violations -= Math.min(this.violations + 1.5D, 0.005D);
      }

   }
}
