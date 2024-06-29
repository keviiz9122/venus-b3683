package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import org.bukkit.World;

@CheckInfo(
   type = CheckType.FLY,
   subType = "D",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -5.0D,
   maxViolations = 25
)
public class FlyD extends Check implements PacketHandler {
   private boolean jumping = false;
   private int lastBypassTick = -10;
   private int jumped;
   private Double lastY = null;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (!var1.isGround() && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() * 2 && this.playerData.getTeleportTicks() > this.playerData.getPingTicks() && !this.playerData.isTeleportingV2() && !this.playerData.isFlying() && this.playerData.isSurvival() && this.playerData.getTotalTicks() - 40 > this.lastBypassTick && !this.playerData.hasPlacedBucket() && !this.playerData.isFallFlying() && !this.playerData.isGliding() && !this.playerData.isRiptiding() && !this.playerData.isLevitating() && !this.playerData.isVehicle()) {
         if (this.lastY != null) {
            if (this.jumping && var1.getY() < this.lastY) {
               if (this.jumped++ > 1 && !this.playerData.hadJumpBoost()) {
                  World var2 = this.player.getWorld();
                  Cuboid var3 = Cuboid.withLimit(this.playerData.getLastLocation(), this.playerData.getLocation(), 16).add(-0.5D, 0.5D, -0.5D, 2.0D, -0.5D, 0.5D);
                  Cuboid var4 = Cuboid.withLimit(this.playerData.getLastLocation(), this.playerData.getLocation(), 16).add(-0.5D, 0.5D, -2.0D, 0.0D, -0.5D, 0.5D);
                  int var5 = this.playerData.getTotalTicks();
                  this.run(() -> {
                     boolean var5x = this.playerData.getLocation().toBlock().isWaterLogged(var2);
                     ClientVersion var6 = this.playerData.getVersion();
                     ServerVersion var7 = NMSManager.getInstance().getServerVersion();
                     if (!var5x && var3.checkBlocks(this.player, var2, (var0) -> {
                        boolean var10000;
                        if (!MaterialList.BAD_VELOCITY.contains(var0)) {
                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        return var10000;
                     }) && (var6.before(ClientVersion.V1_13) || var7.before(ServerVersion.v1_13_R2) || var4.checkBlocks(this.player, var2, (var0) -> {
                        boolean var10000;
                        if (!MaterialList.BOBBLE.contains(var0)) {
                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        return var10000;
                     }))) {
                        this.handleViolation("", (double)(this.jumped - 1));
                     } else {
                        this.jumped = 0;
                        this.violations -= Math.min(this.violations + 1.0D, 0.05D);
                        this.lastBypassTick = var5;
                     }

                  });
               }

               this.jumping = false;
            } else if (var1.getY() > this.lastY) {
               this.jumping = true;
            }
         }
      } else {
         label76: {
            if (!MathUtil.onGround(this.playerData.getLocation().getY())) {
               double var10000;
               if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_18_R1)) {
                  var10000 = Math.abs(this.playerData.getLocation().getY());
               } else {
                  var10000 = this.playerData.getLocation().getY();
               }

               if (!((var10000 - 0.41999998688697815D) % 1.0D > 1.0E-15D)) {
                  break label76;
               }
            }

            this.jumped = 0;
            this.jumping = false;
         }
      }

      this.violations -= Math.min(this.violations + 5.0D, 0.025D);
      if (var1.isPos()) {
         this.lastY = var1.getY();
      }

   }
}
