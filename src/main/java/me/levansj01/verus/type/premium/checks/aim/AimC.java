package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.AIM_ASSIST,
   subType = "C",
   friendlyName = "Aim",
   version = CheckVersion.RELEASE,
   minViolations = -2.5D
)
public class AimC extends AimCheck {

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.playerData.getLastAttackTicks() <= 100) {
         float var5 = Math.abs(var2.getYaw() - var1.getYaw());
         float var6 = Math.abs(var2.getPitch() - var1.getPitch());
         if (var5 > 0.0F && (double)var5 < 0.01D && (double)var6 > 0.2D) {
            this.handleViolation(String.format("Y: %s P: %s", var5, var6));
         } else {
            this.violations -= Math.min(this.violations + 2.5D, 0.05D);
         }

      }
   }
}
