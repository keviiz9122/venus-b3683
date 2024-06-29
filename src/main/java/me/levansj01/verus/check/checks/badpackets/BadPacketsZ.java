package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "Z",
   friendlyName = "Nuker",
   version = CheckVersion.RELEASE,
   unsupportedVersions = {ClientVersion.V1_7},
   minViolations = -1.0D,
   maxViolations = 50
)
public class BadPacketsZ extends Check implements PacketHandler {
   private int invalid = 0;

   public void handle(VPacketPlayInBlockDig<?> var1) {
      switch(var1.getType()) {
      case START_DESTROY_BLOCK:
      case STOP_DESTROY_BLOCK:
         if (this.playerData.shouldHaveReceivedPing() && !this.playerData.hasLag() && !this.playerData.hasFast() && this.invalid++ > 2) {
            this.handleViolation();
         }
         break;
      default:
         this.invalid = 0;
         this.decreaseVL(1.0D);
      }

   }

   public void handle(VPacketPlayInArmAnimation<?> var1) {
      this.invalid = 0;
   }
}
