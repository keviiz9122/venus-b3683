package me.levansj01.verus.check.checks.velocity;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.VELOCITY,
   subType = "C",
   friendlyName = "NoVelocity",
   version = CheckVersion.RELEASE,
   minViolations = -2.0D
)
public class VelocityC extends Check implements PacketHandler {

   public void handle(VPacketPlayInFlying<?> var1) {
      PlayerLocation var2 = this.playerData.getLastLastLocation();
      PlayerLocation var3 = this.playerData.getLocation();
      if (this.playerData.getVerticalVelocityTicks() > this.playerData.getMoveTicks() - 1 && this.playerData.getLastVelY() > 0.0D && this.playerData.getVerticalVelocityTicks() > this.playerData.getMaxPingTicks()) {
         double var4 = var3.getY() - var2.getY();
         if (var4 > 0.0D) {
            this.decreaseVL(1.0D);
         } else {
            this.handleViolation(String.format("T: %s | %s", this.playerData.getVerticalVelocityTicks(), this.playerData.getMaxPingTicks()));
         }

         this.playerData.setResetVelocity(true);
      }

   }
}
