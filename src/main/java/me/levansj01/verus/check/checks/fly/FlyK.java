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
   subType = "K",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -2.0D,
   maxViolations = 10,
   logData = true
)
public class FlyK extends MovementCheck {

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      double var5 = var1.getY() - var2.getY();
      if (var1.getGround() && var5 > 0.079D && !this.playerData.isFlying() && this.playerData.isSurvival() && !this.playerData.isTeleporting(2) && this.playerData.getTickerMap().get(TickerType.TELEPORT) > 1 && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() / 2 && this.playerData.isSpawned() && !this.playerData.hasLag() && !this.playerData.isFallFlying() && !this.playerData.isGliding() && Math.abs(var1.getY() % 0.5D - 0.015555072702202466D) > 1.0E-12D && !this.playerData.hasPlacedBlock(true)) {
         World var7 = this.player.getWorld();
         Cuboid var8 = (new Cuboid(this.playerData.getLocation())).add(-0.5D, 0.5D, -1.0D, 1.5D, -0.5D, 0.5D);
         this.run(() -> {
            if (var8.checkBlocks(this.player, var7, (var0) -> {
               boolean var10000;
               if (!MaterialList.BAD_VELOCITY.contains(var0) && !MaterialList.INVALID_SHAPE.contains(var0)) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               return var10000;
            })) {
               this.handleViolation(String.format("D: %s G: %s", var5, var2.getGround()));
            }

         });
      } else {
         this.decreaseVL(0.005D);
      }

   }
}
