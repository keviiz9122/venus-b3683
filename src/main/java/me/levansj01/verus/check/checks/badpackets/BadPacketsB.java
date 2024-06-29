package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "B",
   friendlyName = "Invalid Direction",
   version = CheckVersion.RELEASE,
   maxViolations = 1
)
public class BadPacketsB extends Check implements PacketHandler {
   public void handle(VPacketPlayInFlying<?> var1) {
      if (!this.playerData.isTeleportingV2() && !this.playerData.isTeleporting()) {
         float var2 = var1.getPitch();
         if (Math.abs(var2) > 90.0F) {
            this.handleViolation(() -> {
               return String.format("P: %.2f", var2);
            });
         }
      }

   }
}
