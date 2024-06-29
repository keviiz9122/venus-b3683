package me.levansj01.verus.check.checks.fly;

import java.util.Iterator;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(
   type = CheckType.FLY,
   subType = "E",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -3.5D,
   maxViolations = 30,
   logData = true
)
public class FlyE extends MovementCheck {
   private int lastBypassTick = -10;
   private double threshold;

   public void setMaxViolation(int var1) {
      if (this.playerData != null && this.playerData.getVersion().afterEq(ClientVersion.V1_9)) {
         var1 *= 5;
      }

      super.setMaxViolation(var1);
   }

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.playerData.getTotalTicks() - 20 >= this.lastBypassTick) {
         if (var1.getGround() && var2.getY() > var1.getY() && this.playerData.getVelocityTicks() > (this.playerData.getMaxPingTicks() + 1) * 4 && !this.playerData.isTeleporting() && this.playerData.isSpawned() && !this.playerData.isFallFlying() && !this.playerData.isGliding() && !this.playerData.hasPlacedBucket() && !this.playerData.isLevitating() && !this.playerData.isTeleportingV2() && !this.playerData.hasPlacedBlock(true) && !this.playerData.canFly()) {
            double var5 = var2.getY() - var1.getY();
            double var10000;
            if (!this.playerData.getVersion().before(ClientVersion.V1_9)) {
               var10000 = 0.419999986886978D;
            } else {
               var10000 = 0.41999998688697815D;
            }

            double var7 = var10000;
            boolean var14;
            if (this.playerData.getVelocityTicks() <= (this.playerData.getMaxPingTicks() + 1) * 4) {
               var14 = true;
            } else {
               var14 = false;
            }

            boolean var9 = var14;
            if (this.playerData.getVelocityQueue().stream().anyMatch((var2x) -> {
               boolean var10000;
               if (!var2x.isExplosion() && !(Math.abs(var2x.getOriginalY() - var5) <= 1.25E-4D)) {
                  var10000 = false;
               } else {
                  var10000 = true;
               }

               return var10000;
            })) {
               return;
            }

            if (var5 < var7 && (var2.getY() - var7) % 1.0D > 1.0E-15D) {
               World var10 = this.player.getWorld();
               Cuboid var11 = Cuboid.withLimit(var1, var2, 16).move(0.0D, 2.0D, 0.0D).expand(0.5D, 0.5D, 0.5D);
               Cuboid var12 = Cuboid.withLimit(var1, var2, 16).move(0.0D, -0.25D, 0.0D).expand(0.5D, 0.75D, 0.5D);
               int var13 = this.playerData.getTotalTicks();
               if (this.playerData.hasJumpBoost()) {
                  this.lastBypassTick = var13;
               }

               this.run(() -> {
                  if (this.playerData.getTotalTicks() - 20 >= this.lastBypassTick) {
                     if (var11.checkBlocks(this.player, var10, (var0) -> {
                        boolean var10000;
                        if (var0 == MaterialList.AIR) {
                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        return var10000;
                     }) && var12.checkBlocks(this.player, var10, (var0) -> {
                        boolean var10000;
                        if (!MaterialList.INVALID_SHAPE.contains(var0) && !MaterialList.BAD_VELOCITY.contains(var0) && !MaterialList.BED.contains(var0)) {
                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        return var10000;
                     })) {
                        Iterator var9x = this.player.getNearbyEntities(2.5D, 2.5D, 2.5D).iterator();

                        while(true) {
                           if (!var9x.hasNext()) {
                              double var10001 = this.threshold;
                              double var10002;
                              if (var9) {
                                 var10002 = 0.4D;
                              } else {
                                 var10002 = 1.0D;
                              }

                              this.threshold = var10001 + var10002;
                              this.handleViolation(String.format("D: %s Y: %s", var5, var2.getY()), this.threshold);
                              break;
                           }

                           Entity var10x = (Entity)var9x.next();
                           if (var10x instanceof Boat || var10x instanceof Minecart) {
                              this.threshold = 0.0D;
                              this.decreaseVL(0.025D);
                              this.lastBypassTick = var13 - 100;
                              return;
                           }
                        }
                     } else {
                        this.threshold = 0.0D;
                        this.decreaseVL(0.025D);
                        this.lastBypassTick = var13;
                     }

                  }
               });
            } else {
               this.threshold = 0.0D;
               this.decreaseVL(0.025D);
            }
         }

      }
   }
}
