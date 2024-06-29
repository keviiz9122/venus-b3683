package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "N",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   minViolations = -2.5D,
   maxViolations = 5,
   unsupportedAtleast = ClientVersion.V1_9
)
public class KillAuraN extends MovementCheck {
   private static final double NON_SPRINT = 0.2325D;
   private boolean lastOnGround = false;
   private static final double SPEED = 0.02D;
   private int threshold = 0;
   private Double lastValue = null;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (var1.getGround() && var2.getGround() && this.lastOnGround && this.playerData.isSprinting(true) && this.playerData.isSurvival()) {
         double var5 = MathUtil.hypot(var2.getX() - var1.getX(), var2.getZ() - var1.getZ());
         if (this.lastValue != null && this.playerData.getLastAttackTicks() <= 1 && this.playerData.getLastAttacked() != null) {
            double var7 = 0.2325D;
            var7 += (double)(Integer)this.playerData.getSpeedLevel().get() * 0.02D;
            if (this.playerData.getMovementSpeed() > 0.2D) {
               var7 *= this.playerData.getMovementSpeed() / 0.2D;
            }

            if (var5 >= this.lastValue * 0.99D && var5 > var7) {
               if (this.threshold++ > 3) {
                  this.handleViolation(String.format("D: %s", var5 - var7), 1.0D, true);
                  this.threshold = 0;
               }
            } else {
               this.threshold = 0;
               this.decreaseVL(0.05D);
            }
         }

         this.lastValue = var5;
      } else {
         this.lastValue = null;
      }

      this.lastOnGround = var1.getGround();
   }
}
