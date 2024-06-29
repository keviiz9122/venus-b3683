package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;

@CheckInfo(
   type = CheckType.FLY,
   subType = "G",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -2.5D,
   maxViolations = 20,
   logData = true
)
public class FlyG extends MovementCheck {
   private boolean ignoring = false;
   private int jump = 0;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.ignoring) {
         if (var2.getGround()) {
            this.ignoring = false;
         }
      } else if (var2.getY() > var1.getY() && this.playerData.getVelocityTicks() > (this.playerData.getPingTicks() + 1) * 2 && !this.playerData.isVehicle() && !this.playerData.canFly() && !this.playerData.isFallFlying() && !this.playerData.isGliding() && !this.playerData.isRiptiding() && !this.playerData.isHooked() && !this.playerData.isTeleportingV2() && this.playerData.isSurvival() && this.playerData.isSpawned() && !this.playerData.isLevitating() && !this.playerData.hasPlacedBlock(true) && !this.playerData.hadJumpBoost() && !this.playerData.hadLevitation()) {
         double var5 = var2.getY() - Math.max(0.0D, var1.getY());
         int var7 = (Integer)this.playerData.getJumpLevel().get();
         double var8 = 0.41999998688699D;
         double var10000;
         if (!var2.getGround()) {
            var10000 = var8;
         } else {
            var10000 = 0.5D;
         }

         double var10 = Math.max(var10000, var8 + (double)Math.max(this.jump, var7) * 0.2D);
         double var12 = var5 - var10;
         if (var1.getGround()) {
            this.jump = var7;
         }

         if (this.playerData.getVersion() != ClientVersion.V1_7 && var2.getGround() && var1.getGround() && (var12 == 0.0625D || var12 == 0.10000002384185791D)) {
            return;
         }

         if (var5 > var10 && Math.abs(var5 - 0.5D) > 1.0E-12D) {
            if (var5 > 100000.0D || this.violations > 300.0D) {
               AlertManager.getInstance().handleBan(this.playerData, this, false);
               this.playerData.fuckOff();
            }

            World var14 = this.player.getWorld();
            Cuboid var15 = (new Cuboid(var1)).move(0.0D, -1.5D, 0.0D).expand(0.5D, 2.0D, 0.5D);
            this.run(() -> {
               if (var15.checkBlocks(this.player, var14, (var0) -> {
                  boolean var10000;
                  if (!MaterialList.INVALID_JUMP.contains(var0) && !MaterialList.SHULKER_BOX.contains(var0) && var0 != MaterialList.PURPLE_FUCKING_SHULKER) {
                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  return var10000;
               })) {
                  String var10001 = String.format("D: %s", var12);
                  double var10002;
                  if (this.playerData.isTeleporting()) {
                     var10002 = 0.25D;
                  } else {
                     var10002 = Math.min(10.0D, 0.5D + var12);
                  }

                  this.handleViolation(var10001, var10002);
               } else {
                  this.ignoring = true;
               }

            });
         } else {
            this.violations -= Math.min(this.violations + 2.5D, 0.025D);
         }
      }

   }
}
