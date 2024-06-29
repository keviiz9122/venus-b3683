package me.levansj01.verus.check.checks.killaura;

import java.util.Comparator;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.reach.DistanceData;
import me.levansj01.verus.data.reach.ReachBase;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "G",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   minViolations = -3.0D,
   maxViolations = 20,
   logData = true
)
public class KillAuraG extends ReachCheck {
   private int threshold = 0;

   public void handle(ReachBase var1, long var2) {
      DistanceData var4 = var1.getDistanceData();
      double var5 = ((DistanceData)var1.getDistanceList().stream().min(Comparator.comparingDouble(DistanceData::getExtra)).get()).getExtra() / var4.getHorizontal();
      double var7 = var4.getX() * var5;
      double var9 = var4.getZ() * var5;
      if (Math.max(Math.abs(var7), Math.abs(var9)) > 0.5D) {
         if (this.threshold++ > 2 + this.playerData.getPingTicks() / 3) {
            this.handleViolation(String.format("X: %.2f Z: %.2f E: %.2f", var7, var9, var4.getExtra()), 1.0D, true);
         }
      } else {
         this.decreaseVL(0.2D);
         this.threshold = 0;
      }

   }
}
