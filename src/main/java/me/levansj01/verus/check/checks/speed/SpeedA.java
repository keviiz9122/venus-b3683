package me.levansj01.verus.check.checks.speed;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.velocity.Velocity;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.DoubleWrapper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.inventory.PlayerInventory;

@CheckInfo(
   type = CheckType.SPEED,
   subType = "A",
   friendlyName = "Speed",
   version = CheckVersion.RELEASE,
   minViolations = -5.0D,
   maxViolations = 30,
   logData = true
)
public class SpeedA extends MovementCheck {
   public static final double STRAIGHT_SPRINT = 0.281D;
   public static final double FAST_AIR = 0.6125D;
   public static final double SPEED_POTION_AIR = 0.0175D;
   private int lastBypassTick = -50;
   private int lastGroundTick;
   public static final double SPEED_POTION_GROUND = 0.0573D;
   private int bypassFallbackTicks;
   private int lastBlockAboveTick = -20;
   public static final double SPEED_POTION_AIR_FAST = 0.0375D;
   public static final double AIR_SLOWDOWN_TICK = 0.125D;
   private int lastAirTick;
   public static final double NORMAL_AIR = 0.36D;
   public static final double STRAIGHT_NON_SPRINT = 0.217D;
   public static final double SPEED_CUBIC_AMOUNT = 0.0018D;
   private int lastSpeed;
   public static final double SPRINT = 0.2865D;
   private int lastFastAirTick;
   public static final double NON_SPRINT = 0.2325D;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if ((var2.getX() != var1.getX() || var2.getZ() != var1.getZ()) && !this.playerData.isTeleportingV2() && !this.playerData.isVehicle() && !this.playerData.isGliding() && !this.playerData.isFallFlying() && !this.playerData.isHooked() && !this.playerData.isRiptiding()) {
         if (this.playerData.canFly() || this.playerData.isFlying()) {
            if (this.playerData.isFlying()) {
               this.bypassFallbackTicks = 20;
            }

            return;
         }

         double var5 = MathUtil.hypot(var2.getX() - var1.getX(), var2.getZ() - var1.getZ());
         DoubleWrapper var7 = new DoubleWrapper(0.0D);
         int var8 = Math.max((Integer)this.playerData.getSpeedLevel().get(), 0);
         if (this.lastSpeed > var8) {
            var8 = this.lastSpeed - 1;
         }

         boolean var9;
         if (var2.getGround()) {
            if (this.bypassFallbackTicks > 0) {
               this.bypassFallbackTicks -= 10;
            }

            this.lastGroundTick = this.playerData.getTotalTicks();
            double var17 = Math.toDegrees(-Math.atan2(var2.getX() - var1.getX(), var2.getZ() - var1.getZ()));
            double var11 = MathUtil.getDistanceBetweenAngles360(var17, (double)var2.getYaw());
            boolean var10000;
            if (var11 < 5.0D && var11 < 90.0D) {
               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var13 = var10000;
            var7.addAndGet((double)var8 * 0.0573D);
            double var10001;
            if (var13) {
               var10001 = 0.281D;
            } else {
               var10001 = 0.2865D;
            }

            var7.addAndGet(var10001);
            if (this.playerData.getMovementSpeed() > 0.2D) {
               var7.set(var7.get() * this.playerData.getMovementSpeed() / 0.2D);
            }

            if (this.lastAirTick >= this.lastGroundTick - 10) {
               var7.addAndGet((double)(this.lastGroundTick - this.lastAirTick) * 0.125D);
            }
         } else {
            if (this.bypassFallbackTicks > 0) {
               var7.addAndGet(0.1D);
               --this.bypassFallbackTicks;
            }

            this.lastAirTick = this.playerData.getTotalTicks();
            var9 = false;
            if (var5 > 0.36D && this.lastFastAirTick < this.lastGroundTick) {
               this.lastFastAirTick = this.playerData.getTotalTicks();
               var7.addAndGet(0.6125D);
               var9 = true;
            } else {
               var7.addAndGet(0.36D);
            }

            if (this.playerData.isFallFlying()) {
               this.bypassFallbackTicks = 100;
               var7.set(var7.get() * 5.0D);
            }

            double var10 = (double)var8;
            if (this.lastAirTick - this.lastGroundTick < 1 + (var8 - 1) / 2) {
               var10 += (double)(var8 * var8 * var8) * 0.0018D;
            }

            double var10002;
            if (var9) {
               var10002 = 0.0375D;
            } else {
               var10002 = 0.0175D;
            }

            var7.addAndGet(var10 * var10002);
            if (this.playerData.getMovementSpeed() > 0.2D) {
               var7.addAndGet(var7.get() * (this.playerData.getMovementSpeed() - 0.2D) * 2.0D);
            }
         }

         this.lastSpeed = var8;
         if (!VerusTypeLoader.isDev() && this.playerData.getHorizontalSpeedTicks() < (this.playerData.getPingTicks() + 10) * 2) {
            return;
         }

         var9 = this.playerData.hasLag();
         Velocity var18 = (Velocity)MathUtil.max(this.playerData.getVelocityQueue(), Velocity::getHypotSquaredXZ);
         if (var18 != null) {
            var7.addAndGet(Math.sqrt(var18.getHypotSquaredXZ()));
         }

         if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2) && BukkitUtil.hasEffect((State)this.playerData.getEffects(), 30) || this.playerData.hadAttributes() || this.playerData.hadSpeedBoost()) {
            var7.set(var7.get() * 2.0D);
         }

         if (var5 > var7.get()) {
            World var19 = this.player.getWorld();
            PlayerLocation var12 = this.playerData.getLocation();
            Cuboid var20 = (new Cuboid(var12)).move(0.0D, 2.0D, 0.0D).expand(0.5D, 0.5D, 0.5D);
            Cuboid var14 = (new Cuboid(var12)).add(-0.5D, 0.5D, -1.99D, 0.5D, -0.5D, 0.5D);
            int var15 = this.playerData.getTotalTicks();
            int var16 = this.lastFastAirTick;
            this.run(() -> {
               ServerVersion var12 = NMSManager.getInstance().getServerVersion();
               if (var12.after(ServerVersion.v1_7_R4)) {
                  PlayerInventory var13 = this.player.getInventory();
                  if (BukkitUtil.hasEnchantment(var13.getBoots(), "DEPTH_STRIDER") && !var14.checkBlocks(this.player, var19, (var0) -> {
                     boolean var10000;
                     if (!MaterialList.LIQUIDS.contains(var0)) {
                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     return var10000;
                  })) {
                     return;
                  }

                  if (var12.afterEq(ServerVersion.v1_16_R1)) {
                     int var14x = BukkitUtil.getEnchantment(var13.getBoots(), "SOUL_SPEED");
                     if (var14x > 0) {
                        var7.addAndGet(var7.get() * (0.9D + (double)var14x * 0.125D));
                     }
                  }
               }

               boolean var10000;
               if (var15 - 20 < this.lastBlockAboveTick) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var18x = var10000;
               if (!var18x && !var20.checkBlocks(this.player, var19, (var0) -> {
                  boolean var10000;
                  if (var0 == MaterialList.AIR) {
                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  return var10000;
               })) {
                  var18x = true;
                  this.lastBlockAboveTick = var15;
               }

               if (var15 - 60 < this.lastBypassTick) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var19x = var10000;
               if (!var19x && !var14.checkBlocks(this.player, var19, (var0) -> {
                  boolean var10000;
                  if (!MaterialList.ICE.contains(var0) && !MaterialList.SLABS.contains(var0) && !MaterialList.STAIRS.contains(var0) && !MaterialList.STICKY.contains(var0)) {
                     var10000 = true;
                  } else {
                     var10000 = false;
                  }

                  return var10000;
               })) {
                  var19x = true;
                  this.lastBypassTick = var15;
               }

               byte var20x;
               if (var9) {
                  var20x = 2;
               } else {
                  var20x = 1;
               }

               byte var15x = var20x;
               if (var18x) {
                  var7.addAndGet(0.3D * (double)var15x);
               }

               if (var19x) {
                  double var10001;
                  if (var2.getGround()) {
                     var10001 = 0.25D;
                  } else {
                     var10001 = 0.3D;
                  }

                  var7.addAndGet(var10001 * (double)var15x);
                  this.bypassFallbackTicks = 60;
               }

               if (var5 > var7.get()) {
                  double var21;
                  if (var9) {
                     var21 = (var5 - var7.get()) * 0.5D + 0.1D;
                  } else {
                     var21 = var5 - var7.get();
                  }

                  double var16x = var21 + 0.3D;
                  if (this.playerData.isTeleporting()) {
                     var16x = 0.15D;
                  }

                  this.handleViolation(() -> {
                     Object[] var10001 = new Object[]{var5, var7.get(), var2.getGround(), var9, null, null};
                     double var10004;
                     if (var18 == null) {
                        var10004 = 0.0D;
                     } else {
                        var10004 = Math.sqrt(var18.getHypotSquaredXZ());
                     }

                     var10001[4] = var10004;
                     var10001[5] = var15 - var16;
                     return String.format("DST: %.3f LM %.3f G: %s L: %s V: %s FA: %s", var10001);
                  }, var16x);
               } else {
                  this.decreaseVL(0.02D);
               }

            });
         } else {
            this.decreaseVL(0.02D);
         }
      }

   }
}
