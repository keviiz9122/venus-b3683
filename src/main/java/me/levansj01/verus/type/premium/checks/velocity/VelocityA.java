package me.levansj01.verus.type.premium.checks.velocity;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(
   type = CheckType.VELOCITY,
   subType = "A",
   friendlyName = "vVelocity",
   version = CheckVersion.RELEASE,
   minViolations = -0.5D,
   maxViolations = 5
)
public class VelocityA extends Check implements PacketHandler {

   public void handle(VPacketPlayInFlying<?> var1) {
      PlayerLocation var2 = this.playerData.getLastLastLocation();
      PlayerLocation var3 = this.playerData.getLocation();
      if (this.playerData.getVerticalVelocityTicks() > this.playerData.getMoveTicks() - 1 && this.playerData.getLastVelY() > 0.0D) {
         double var4 = var3.getY() - var2.getY();
         if (var4 > 0.0D) {
            var4 = Math.ceil(var4 * 8000.0D) / 8000.0D;
            if (var4 < 0.41999998688697815D && var2.getGround() && !var3.getGround() && MathUtil.onGround(var2.getY()) && !MathUtil.onGround(var3.getY()) && this.playerData.getTickerMap().get(TickerType.EXPLOSION) > this.playerData.getMaxPingTicks() + 2) {
               double var6 = var4 / this.playerData.getLastVelY();
               if (var6 < 0.995D) {
                  this.handleViolation(String.format("P: %.1f.", var6 * 100.0D));
               }
            }

            this.playerData.setResetVelocity(true);
         } else {
            this.decreaseVL(0.01D);
         }
      }

   }
}
