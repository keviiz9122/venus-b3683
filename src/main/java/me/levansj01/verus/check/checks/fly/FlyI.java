package me.levansj01.verus.check.checks.fly;

import java.util.Iterator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.potion.PotionEffectType;

@CheckInfo(
   type = CheckType.FLY,
   subType = "I",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -2.5D,
   maxViolations = 50,
   logData = true
)
public class FlyI extends MovementCheck {
   private int lastTPTick;
   private int lastBypassTick;
   private double threshold;
   private Double lastYDiff = null;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.playerData.getTickerMap().get(TickerType.TELEPORT) <= 1) {
         this.lastYDiff = null;
         this.threshold = 0.0D;
      } else {
         if (!var1.getGround() || !var2.getGround()) {
            double var5 = var2.getY() - var1.getY();
            ClientVersion var7 = this.playerData.getVersion();
            double var8 = var5 - 0.41999998688697815D;
            if (this.lastYDiff != null && this.playerData.getTotalTicks() - 40 > this.lastBypassTick && !this.playerData.hasPlacedBucket() && !this.playerData.hasPlacedBlock(true) && !this.playerData.isFallFlying() && !this.playerData.isGliding() && !this.playerData.canFly() && this.playerData.isSpawned() && !this.playerData.isVehicle() && this.playerData.getVelocityTicks() > (2 + this.playerData.getMaxPingTicks()) * 2 && this.playerData.getTickerMap().get(TickerType.EXPLOSION) > this.playerData.getMaxPingTicks() + 2 && !this.playerData.isLevitating() && !this.playerData.isRiptiding() && !this.playerData.hadJumpBoost() && (NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_13_R2) || var2.getY() < var1.getY() && !BukkitUtil.hasEffect((State)this.playerData.getEffects(), 28)) && Math.abs(var5 + 0.9800000190734863D) > 1.0E-11D && Math.abs(var5 + 0.09800000190735147D) > 1.0E-11D && Math.abs(var5 - 0.0030162615090425504D) > 1.0E-9D && (!StorageEngine.getInstance().getVerusConfig().isUnloadedChunkFix() || Math.abs(var5 / 0.9800000190734863D + 0.08D) > 1.0E-11D) && Math.abs(var8) > 9.999999960041972E-13D && Math.abs(var8 - (double)(Integer)this.playerData.getJumpLevel().get() * 0.1D) > 1.0000000116860974E-7D && (var7.before(ClientVersion.V1_9) || Math.abs(var5 + 0.15233518685055714D) > 1.0E-11D && Math.abs(var5 + 0.07242780368044421D) > 1.0E-11D) && (var7.before(ClientVersion.V1_13) || Math.max(var2.getY(), var1.getY()) < 255.0D)) {
               boolean var10000;
               if (var1.getX() != var2.getX() && var1.getZ() != var2.getZ()) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               boolean var10 = var10000;
               double var11 = (this.lastYDiff - 0.08D) * 0.9800000190734863D;
               if (var2.getGround() && var5 < 0.0D && var11 < var5 && MathUtil.onGround(var2.getY()) || var1.distanceXZSquared(var2) < 0.0025D && BukkitUtil.hasEffect(this.playerData.getEffects(), PotionEffectType.JUMP)) {
                  var11 = var5;
               } else if ((VerusTypeLoader.isDev() || this.playerData.getVersion() != ClientVersion.V1_9 && !BukkitUtil.hasEffect(this.playerData.getEffects(), PotionEffectType.JUMP)) && Math.abs(var11) < 0.005D) {
                  var11 = 0.0D;
               }

               double var13 = Math.abs(var11 - var5);
               double var15 = (var11 - var5) / var11;
               int var17 = this.playerData.getTotalTicks();
               if (var13 > 2.0D && Math.abs(var15) > 300.0D) {
                  String var10001 = String.format("%s %s %.3f, %.3f%s", this.playerData.getTeleportTicks(), var1.getGround(), var13, var15 * 100.0D, "%");
                  double var10002;
                  if (var17 - this.lastTPTick <= 20 && var17 - this.lastTPTick > 1) {
                     var10002 = 1.0D;
                  } else {
                     var10002 = 0.5D;
                  }

                  this.handleViolation(var10001, var10002);
                  this.lastTPTick = var17;
               }

               if (!this.playerData.isTeleporting() && !var1.getGround()) {
                  if (var13 > 1.0E-7D) {
                     World var18 = this.player.getWorld();
                     Cuboid var19 = (new Cuboid(var2)).add(-0.5D, 0.5D, -1.0D, 1.5D, -0.5D, 0.5D);
                     double var25;
                     if (VerusTypeLoader.isDev()) {
                        var25 = 0.29999D;
                     } else {
                        var25 = 0.5D;
                     }

                     double var20 = var25;
                     double var22 = this.playerData.getLocation().getY();
                     Cuboid var24 = Cuboid.withLimit(var1, var2, 16).move(0.0D, 2.0D, 0.0D).add(-var20, var20, -0.5D, 0.5D, -var20, var20);
                     this.run(() -> {
                        boolean var12 = var1.toBlock().isWaterLogged(var18);
                        Predicate var13x = (var0) -> {
                           boolean var10000;
                           if (!MaterialList.BAD_VELOCITY.contains(var0) && !MaterialList.INVALID_SHAPE.contains(var0)) {
                              var10000 = true;
                           } else {
                              var10000 = false;
                           }

                           return var10000;
                        };
                        if (var19.checkBlocks(this.player, var18, var13x) && var24.checkBlocks(this.player, var18, (var0) -> {
                           boolean var10000;
                           if (var0 == MaterialList.AIR) {
                              var10000 = true;
                           } else {
                              var10000 = false;
                           }

                           return var10000;
                        }) && !var12) {
                           Iterator var14 = this.player.getNearbyEntities(2.5D, 2.5D, 2.5D).iterator();

                           while(true) {
                              if (!var14.hasNext()) {
                                 ++this.threshold;
                                 boolean var10000;
                                 if (!this.playerData.hasLag() && !this.playerData.hasFast() && var10) {
                                    var10000 = false;
                                 } else {
                                    var10000 = true;
                                 }

                                 boolean var16 = var10000;
                                 Supplier var10001 = () -> {
                                    return String.format("D: %s D2: %s P: %s V: %s", var13, var5, var2.getY() % 1.0D, var10);
                                 };
                                 double var10002;
                                 if (var16) {
                                    var10002 = 0.1D;
                                 } else {
                                    var10002 = 1.0D;
                                 }

                                 this.handleViolation(var10001, var10002 * this.threshold);
                                 break;
                              }

                              Entity var15 = (Entity)var14.next();
                              if (var15 instanceof Boat || var15 instanceof Minecart) {
                                 this.threshold = 0.0D;
                                 this.lastBypassTick = var17 - 100;
                                 return;
                              }
                           }
                        } else {
                           this.decreaseVL(0.1D);
                           this.lastBypassTick = var17;
                           this.threshold = 0.0D;
                        }

                     });
                  } else {
                     this.decreaseVL(0.025D);
                     this.threshold = 0.0D;
                  }
               }
            }
         }

         if (var2.getGround() && var1.getGround()) {
            this.lastYDiff = null;
         } else {
            this.lastYDiff = var2.getY() - var1.getY();
         }

      }
   }
}
