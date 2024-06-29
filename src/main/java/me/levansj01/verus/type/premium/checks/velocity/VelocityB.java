package me.levansj01.verus.type.premium.checks.velocity;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.util.Vector;

@CheckInfo(
   type = CheckType.VELOCITY,
   subType = "B",
   friendlyName = "hVelocity",
   version = CheckVersion.RELEASE,
   minViolations = -2.5D
)
public class VelocityB extends MovementCheck {
   private int lastBypassTicks;
   private double total;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.playerData.getLastVelX() != 0.0D && this.playerData.getLastVelZ() != 0.0D && this.playerData.getHorizontalVelocityTicks() > this.playerData.getMoveTicks()) {
         double var5 = var2.getY() - var1.getY();
         if (var5 > 0.0D) {
            double var7 = MathUtil.hypot(this.playerData.getLastVelX(), this.playerData.getLastVelZ());
            if (this.playerData.getLastLastLocation().getGround() && var1.getGround() && MathUtil.onGround(var1.getY()) && !var2.getGround() && !MathUtil.onGround(var2.getY()) && this.playerData.getTotalTicks() - this.lastBypassTicks > 10 && var7 > 0.25D) {
               PlayerLocation var9;
               if (this.playerData.getLastNonMoveTicks() == 0) {
                  Vector var10 = new Vector(this.playerData.getLastLastLocation().getX(), this.playerData.getLastLastLocation().getY(), this.playerData.getLastLastLocation().getZ());
                  var10.subtract(new Vector(var1.getX(), var1.getY(), var1.getZ()));
                  var9 = var2.add(var10.getX(), var10.getY(), var10.getZ());
               } else {
                  var9 = var2.clone();
               }

               double var19 = MathUtil.hypot(var2.getX() - var1.getX(), var2.getZ() - var1.getZ());
               double var12 = MathUtil.hypot(var9.getX() - var1.getX(), var9.getZ() - var1.getZ());
               double var14 = Math.max(var19, var12) / var7;
               this.decreaseVL(0.0025D);
               if (var14 < 1.0D) {
                  World var16 = this.player.getWorld();
                  Cuboid var17 = (new Cuboid(var2)).add(-1.0D, 1.0D, 0.0D, 2.05D, -1.0D, 1.0D);
                  int var18 = this.playerData.getTotalTicks();
                  this.run(() -> {
                     if (var17.checkBlocks(this.player, var16, (var0) -> {
                        boolean var10000;
                        if (var0 == MaterialList.AIR) {
                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        return var10000;
                     })) {
                        if ((this.total += 1.05D - var14) > 2.0D) {
                           this.total = 0.0D;
                           this.handleViolation(String.format("P: %.2f D: %.3f", var14, var12));
                        }
                     } else {
                        this.total -= Math.min(this.total, 1.05D);
                        this.lastBypassTicks = var18;
                     }

                  });
               } else {
                  this.total -= Math.min(this.total, 1.05D);
               }
            }

            this.playerData.setLastVelX(0.0D);
            this.playerData.setLastVelZ(0.0D);
         }
      }

   }
}
