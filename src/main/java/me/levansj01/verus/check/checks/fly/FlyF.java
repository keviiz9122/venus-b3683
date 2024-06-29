package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.World;

@CheckInfo(
   type = CheckType.FLY,
   subType = "F",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -15.0D,
   maxViolations = 100
)
public class FlyF extends MovementCheck {
   private Double lastYChange = null;
   private int threshold;
   private int lastBypassTick = -10;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (!this.playerData.canFly() && this.playerData.isSurvival() && !var2.getGround() && !var1.getGround() && this.playerData.getTotalTicks() - 10 > this.lastBypassTick && !this.playerData.isTeleporting(3) && this.playerData.getTickerMap().get(TickerType.TELEPORT) > 2 && (this.playerData.getVelocityHandler() == null || !this.playerData.getVelocityHandler().possibleVelocity()) && !this.playerData.isVehicle() && this.playerData.isSpawned() && !this.playerData.isLevitating() && !this.playerData.isFallFlying() && !this.playerData.isGliding()) {
         double var5 = Math.abs(var1.getY() - var2.getY());
         if (this.lastYChange != null && var5 > 0.0D && var1.getY() > 0.0D && var2.getY() > 0.0D && !this.playerData.hasFast()) {
            if (var5 == this.lastYChange && (var5 < 0.098D || var5 > 0.09800001D) && Math.abs(var5 % 0.5D) - 0.15523200451660557D > 1.0E-12D && Math.abs(var5 % 0.5D) - 0.23052736891296366D > 1.0E-12D) {
               World var7 = this.player.getWorld();
               Cuboid var8 = (new Cuboid(var2)).add(-0.5D, 0.5D, -0.5D, 1.5D, -0.5D, 0.5D);
               Cuboid var9 = Cuboid.withLimit(var1, var2, 16).move(0.0D, 2.0D, 0.0D).expand(0.29999D, 0.5D, 0.29999D);
               int var10 = this.playerData.getTotalTicks();
               if (var5 == 1.0999999999999943D) {
                  this.lastBypassTick = var10;
                  return;
               }

               this.run(() -> {
                  if (var8.checkBlocks(this.player, var7, (var0) -> {
                     boolean var10000;
                     if (!MaterialList.BAD_VELOCITY.contains(var0)) {
                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     return var10000;
                  }) && var9.checkBlocks(this.player, var7, (var0) -> {
                     boolean var10000;
                     if (var0 == MaterialList.AIR) {
                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     return var10000;
                  })) {
                     if (var5 % 0.01D == 0.0D) {
                        ++this.threshold;
                     }

                     if (this.threshold++ > 1) {
                        this.handleViolation(String.format("D: %s", var5), (double)this.threshold / 2.0D);
                     }
                  } else {
                     this.threshold = 0;
                     this.violations -= Math.min(this.violations + 5.0D, 0.01D);
                     this.lastBypassTick = var10;
                  }

               });
            } else {
               this.violations -= Math.min(this.violations + 5.0D, 0.01D);
               this.threshold = 0;
            }
         }

         this.lastYChange = var5;
      }

   }
}
