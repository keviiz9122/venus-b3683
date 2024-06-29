package me.levansj01.verus.check.checks.speed;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.util.MutableBlockLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(
   type = CheckType.SPEED,
   subType = "B",
   friendlyName = "FastLadder",
   version = CheckVersion.RELEASE,
   maxViolations = 25,
   logData = true
)
public class SpeedB extends MovementCheck {
   private double lastDiff;
   private int lastLadderCheck;
   private boolean ladder = false;
   private int threshold;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (var2.getY() > var1.getY() && !var2.getGround() && !var1.getGround() && !this.playerData.isFlying() && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() * 2 && !this.playerData.isLevitating()) {
         if (this.ladder) {
            double var5 = var2.getY() - var1.getY();
            if (var5 > 0.118D) {
               if (this.threshold++ > 1 && var5 >= this.lastDiff * 0.95D) {
                  this.threshold = 0;
                  PlayerLocation var7 = var2.clone();
                  World var8 = this.player.getWorld();
                  this.run(() -> {
                     MutableBlockLocation var5x = new MutableBlockLocation((int)Math.floor(var7.getX()), (int)Math.floor(var7.getY() + 1.0D), (int)Math.floor(var7.getZ()));
                     Material var6 = NMSManager.getInstance().getTypeWithAPI(this.player, var8, var5x);
                     boolean var10000;
                     if (!var5x.isWaterLogged(var8) && !var5x.add(0, -1, 0).isWaterLogged(var8) && !var5x.add(0, 2, 0).isWaterLogged(var8)) {
                        var10000 = false;
                     } else {
                        var10000 = true;
                     }

                     boolean var7x = var10000;
                     if (!var7x && MaterialList.CLIMBABLE.contains(var6)) {
                        this.handleViolation(String.format("D: %s", var5));
                     } else {
                        this.ladder = false;
                     }

                  });
               }
            } else {
               this.threshold = 0;
            }

            this.lastDiff = var5;
         } else if (this.lastLadderCheck++ > 9) {
            this.lastLadderCheck = 0;
            PlayerLocation var9 = var2.clone();
            World var6 = this.player.getWorld();
            this.run(() -> {
               MutableBlockLocation var3 = new MutableBlockLocation((int)Math.floor(var9.getX()), (int)Math.floor(var9.getY() + 1.0D), (int)Math.floor(var9.getZ()));
               Material var4 = NMSManager.getInstance().getTypeWithAPI(this.player, var6, var3);
               if (MaterialList.CLIMBABLE.contains(var4)) {
                  this.ladder = true;
               }

            });
         }
      } else {
         this.ladder = false;
      }

   }
}
