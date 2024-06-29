package me.levansj01.verus.type.premium.checks.speed;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;

@CheckInfo(
   type = CheckType.SPEED,
   subType = "E",
   friendlyName = "OmniSprint",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 20
)
public class SpeedE extends MovementCheck {
   private Double lastAngle = null;
   private int threshold = 0;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.playerData.isSprinting(true) && var2.getGround() && var1.getGround() && !this.playerData.hasLag() && !this.playerData.isTeleporting(3) && this.playerData.getVelocityTicks() > this.playerData.getPingTicks()) {
         double var5 = var2.getX() - var1.getX();
         double var7 = var2.getZ() - var1.getZ();
         double var9 = Math.toDegrees(-Math.atan2(var5, var7));
         double var11 = Math.min(MathUtil.getDistanceBetweenAngles360(var9, (double)var2.getYaw()), MathUtil.getDistanceBetweenAngles360(var9, (double)var1.getYaw()));
         if (this.lastAngle != null && var5 != 0.0D && var7 != 0.0D) {
            double var13 = MathUtil.getDistanceBetweenAngles360(this.lastAngle, var11);
            double var15 = 50.0D;
            if (var11 > var15) {
               if (var13 < 5.0D && this.threshold++ > 4) {
                  World var17 = this.player.getWorld();
                  Cuboid var18 = (new Cuboid(this.playerData.getLocation())).expand(0.5D, 0.5D, 0.5D);
                  this.run(() -> {
                     if (var18.checkBlocks(this.player, var17, (var0) -> {
                        boolean var10000;
                        if (!MaterialList.BAD_VELOCITY.contains(var0)) {
                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        return var10000;
                     })) {
                        this.handleViolation(String.format("D: %s LD: %s", var11, var13));
                     } else {
                        this.threshold = -20;
                     }

                  });
                  this.threshold = 0;
               }
            } else {
               this.threshold = 0;
            }
         }

         this.lastAngle = var11;
      } else {
         this.lastAngle = null;
         this.threshold = 0;
      }

   }
}
