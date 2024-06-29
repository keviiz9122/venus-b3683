package me.levansj01.verus.check.checks.fly;

import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathHelper;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.util.Vector;

@CheckInfo(
   type = CheckType.FLY,
   subType = "Z",
   friendlyName = "Elytra Fly",
   version = CheckVersion.RELEASE,
   minViolations = -7.5D,
   unsupportedVersions = {ClientVersion.V1_7, ClientVersion.V1_8},
   unsupportedServers = {ServerVersion.v1_7_R4, ServerVersion.v1_8_R3}
)
public class FlyZ extends MovementCheck {
   private int lastBypass;
   private static final float VALUE = 0.017453292F;
   private final Vector lastMovement = new Vector();
   private int ticks = 0;

   private static Vector getVectorForRotation(float var0, float var1) {
      float var2 = MathHelper.cos(-var1 * 0.017453292F - 3.1415927F);
      float var3 = MathHelper.sin(-var1 * 0.017453292F - 3.1415927F);
      float var4 = -MathHelper.cos(-var0 * 0.017453292F);
      float var5 = MathHelper.sin(-var0 * 0.017453292F);
      return new Vector(var3 * var4, var5, var2 * var4);
   }

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (!this.playerData.isFallFlying() || this.playerData.canFly() || this.playerData.isBoosting() || var2.getGround() || this.playerData.isLevitating() || this.playerData.getVelocityTicks() <= this.playerData.getMaxPingTicks() || var1.getGround() || this.playerData.getTotalTicks() - this.lastBypass <= 20 || !NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_13_R2) && BukkitUtil.hasEffect((State)this.playerData.getEffects(), 28)) {
         this.ticks = 0;
      } else {
         if (this.ticks++ > 9) {
            float var5 = var2.getPitch();
            float var6 = var2.getYaw();
            float var7 = var5 * 0.017453292F;
            double var8 = var2.getY() - var1.getY();
            double var10 = this.lastMovement.getY();
            double var12 = this.lastMovement.length();
            Vector var14 = getVectorForRotation(var5, var6);
            double var15 = var14.length();
            double var17 = Math.sqrt(Math.pow(var14.getX(), 2.0D) + Math.pow(var14.getZ(), 2.0D));
            float var19 = (float)(Math.pow((double)MathHelper.cos(var7), 2.0D) * Math.min(1.0D, var15 / 0.4D));
            double var20 = var8 / 0.9900000095367432D;
            double var22 = (var10 - 0.08D) * 0.9800000190734863D;
            if (var7 < 0.0F) {
               var20 -= var12 * (double)(-MathHelper.sin(var7)) * 0.04D * 3.2D;
            }

            if (var10 < 0.0D && var17 > 0.0D) {
               var20 -= var10 * -0.1D * (double)var19;
            }

            if (this.playerData.getVersion().before(ClientVersion.V1_13)) {
               var20 -= -0.08D + (double)var19 * 0.06D;
            } else {
               var20 -= 0.08D * (-1.0D + (double)var19 * 0.75D);
            }

            double var24 = MathUtil.lowestAbs(var10 - var20, var8 - var22);
            double var26 = MathUtil.lowestAbs((var20 - var10) / var20, (var8 - var22) / var22);
            if (Math.abs(var24) > 0.025D && Math.abs(var26) > 0.075D) {
               World var28 = this.player.getWorld();
               Cuboid var29 = (new Cuboid(var2)).expand(0.5D, 1.0D, 0.5D).move(0.0D, 1.0D, 0.0D);
               int var30 = this.playerData.getTotalTicks();
               this.run(() -> {
                  if (this.playerData.getTotalTicks() - this.lastBypass >= 20) {
                     if (var29.checkBlocks(this.player, var28, (var0) -> {
                        boolean var10000;
                        if (!MaterialList.BAD_VELOCITY.contains(var0)) {
                           var10000 = true;
                        } else {
                           var10000 = false;
                        }

                        return var10000;
                     })) {
                        this.handleViolation(() -> {
                           return String.format("%.2f %.2f", var8, var24);
                        });
                     } else {
                        this.lastBypass = var30;
                        this.ticks = 0;
                     }

                  }
               });
            } else {
               this.decreaseVL(0.1D);
            }
         }

         this.lastMovement.setX(var2.getX() - var1.getX());
         this.lastMovement.setY(var2.getY() - var1.getY());
         this.lastMovement.setZ(var2.getZ() - var1.getZ());
      }

   }
}
