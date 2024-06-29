package me.levansj01.verus.type.premium.checks.aim;

import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.AIM_ASSIST,
   subType = "A",
   friendlyName = "Aimbot",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_17,
   maxViolations = 2,
   logData = true
)
public class AimA extends AimCheck {
   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      float var5 = Math.abs(var1.getYaw() - var2.getYaw());
      if (var5 >= 1.0F && var5 % 0.1F == 0.0F) {
         if (var5 % 1.0F == 0.0F) {
            ++this.violations;
         }

         if (var5 % 10.0F == 0.0F) {
            ++this.violations;
         }

         if (var5 % 30.0F == 0.0F) {
            ++this.violations;
         }

         this.handleViolation(String.format("Y: %s", var5));
      }

      float var6 = Math.abs(var1.getPitch() - var2.getPitch());
      if (var6 >= 1.0F && var6 % 0.1F == 0.0F) {
         if (var6 % 1.0F == 0.0F) {
            ++this.violations;
         }

         if (var6 % 10.0F == 0.0F) {
            ++this.violations;
         }

         if (var6 % 30.0F == 0.0F) {
            ++this.violations;
         }

         this.handleViolation(String.format("P: %s", var6));
      }

   }
}
