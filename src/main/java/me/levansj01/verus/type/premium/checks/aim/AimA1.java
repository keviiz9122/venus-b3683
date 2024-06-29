package me.levansj01.verus.type.premium.checks.aim;

import java.util.Objects;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.AIM_ASSIST,
   subType = "A1",
   friendlyName = "Aimbot",
   version = CheckVersion.RELEASE,
   maxViolations = 10
)
public class AimA1 extends AimCheck {
   private float lastYawChange;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.playerData.getLastAttackTicks() < 3) {
         float var5 = Math.abs(var1.getYaw() - var2.getYaw());
         int var6;
         if (var5 > 1.0F && (float)(var6 = Math.round(var5)) == var5) {
            if (Objects.equals(var5, this.lastYawChange)) {
               this.handleViolation(String.format("Y: %s", var5));
            }

            this.lastYawChange = (float)var6;
         } else {
            this.lastYawChange = 0.0F;
         }
      }

   }
}
