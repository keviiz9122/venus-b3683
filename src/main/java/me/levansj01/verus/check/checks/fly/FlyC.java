package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.task.ServerTickTask;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.MutableBlockLocation;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;

@CheckInfo(
   type = CheckType.FLY,
   subType = "C",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 50
)
public class FlyC extends Check implements PacketHandler {
   private Double lastY = null;
   private int lastBypassTick = -10;
   private int threshold;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.lastY != null) {
         double var10000;
         if (var1.isPos()) {
            var10000 = var1.getY();
         } else {
            var10000 = this.lastY;
         }

         double var2 = var10000;
         if (this.lastY == var2 && var2 > 0.0D && !this.playerData.isVehicle() && !var1.isGround() && !this.playerData.canFly() && this.playerData.isSurvival() && !this.playerData.isLevitating() && !this.playerData.isFallFlying() && !this.playerData.isGliding() && !this.playerData.isTeleporting() && !this.playerData.isTeleportingV2() && !this.playerData.hasPlacedBucket() && !this.playerData.hasPlacedBlock(true) && this.playerData.getTotalTicks() - 20 > this.lastBypassTick && this.playerData.getVelocityTicks() > this.playerData.getMaxPingTicks() && this.playerData.isSpawned() && !ServerTickTask.getInstance().isLagging(this.playerData.getTimestamp())) {
            PlayerLocation var4 = this.playerData.getLocation();
            World var5 = this.player.getWorld();
            Cuboid var6 = (new Cuboid(var4)).add(-0.5D, 0.5D, -0.5D, 2.0D, -0.5D, 0.5D);
            int var7 = this.playerData.getTotalTicks();
            boolean var9;
            if (!this.playerData.hasLag() && !this.playerData.hasFast()) {
               var9 = false;
            } else {
               var9 = true;
            }

            boolean var8 = var9;
            this.run(() -> {
               MutableBlockLocation var8x = var4.toBlock();
               boolean var10000;
               if (!var8x.isWaterLogged(var5) && !var8x.add(0, -1, 0).isWaterLogged(var5) && !var8x.add(0, 2, 0).isWaterLogged(var5)) {
                  var10000 = false;
               } else {
                  var10000 = true;
               }

               boolean var9 = var10000;
               if (var6.checkBlocks(this.player, var5, (var0) -> {
                  boolean var10000;
                  if (!MaterialList.BAD_VELOCITY.contains(var0) && !MaterialList.SHULKER_BOX.contains(var0) && var0 != MaterialList.PURPLE_FUCKING_SHULKER) {
                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  return var10000;
               }) && !var9) {
                  int var10002 = this.threshold++;
                  byte var10001;
                  if (var8) {
                     var10001 = 4;
                  } else {
                     var10001 = 1;
                  }

                  if (var10002 > var10001) {
                     if (var8) {
                        this.threshold = 0;
                     }

                     this.handleViolation(String.format("Y: %.2f", var2), (double)(this.threshold - 1));
                  }
               } else {
                  this.threshold = 0;
                  this.decreaseVL(0.01D);
                  this.lastBypassTick = var7;
               }

            });
         } else {
            this.run(() -> {
               this.threshold = 0;
               this.decreaseVL(0.01D);
            });
         }
      }

      if (var1.isPos()) {
         this.lastY = var1.getY();
      }

   }
}
