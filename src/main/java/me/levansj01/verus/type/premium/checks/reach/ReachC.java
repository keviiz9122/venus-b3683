package me.levansj01.verus.type.premium.checks.reach;

import java.util.List;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.reach.DistanceData;
import me.levansj01.verus.data.reach.ReachBase;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
   type = CheckType.REACH,
   subType = "C",
   friendlyName = "Reach",
   version = CheckVersion.RELEASE,
   minViolations = -1.5D,
   maxViolations = 15
)
public class ReachC extends ReachCheck {

   public void handle(List<ReachBase> var1, long var2) {
      ReachBase var4 = (ReachBase)MathUtil.max(var1, ReachBase::getUncertaintyReachValue);
      if (var4 != null) {
         DistanceData var5 = var4.getDistanceData();
         double var6 = var4.getUncertaintyReachValue();
         double var8 = var5.getHorizontal();
         double var10 = var5.getExtra();
         if (var6 > 3.0D && var8 < 6.0D && var10 < 5.0D) {
            this.handleViolation(String.format("R: %.2f H: %.2f E: %.2f", var5.getReach(), var8, var10), Math.max(Math.ceil(var8 - 3.0D), 1.0D));
         } else {
            this.decreaseVL(0.035D);
         }
      }

   }
}
