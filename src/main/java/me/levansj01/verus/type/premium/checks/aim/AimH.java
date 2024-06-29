package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.AIM_ASSIST,
   subType = "H",
   friendlyName = "Aim",
   version = CheckVersion.EXPERIMENTAL,
   minViolations = -1.0D
)
public class AimH extends AimCheck {

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      float var5 = Math.abs(var1.getYaw() - var2.getYaw());
      float var6 = Math.abs(var1.getPitch() - var2.getPitch());
      if (this.playerData.getLastAttackTicks() < 3 && var5 > 0.0F && (double)var5 < 0.8D && (double)var6 > 0.279D && (double)var6 < 0.28090858D) {
         this.handleViolation(String.format("Y: %s P: %s", var5, var6));
      } else {
         this.decreaseVL(1.0E-4D);
      }

   }
}
