package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "O",
   friendlyName = "Invalid Interact",
   version = CheckVersion.RELEASE,
   minViolations = -1.5D,
   maxViolations = 5,
   schem = true,
   unsupportedAtleast = ClientVersion.V1_17
)
public class BadPacketsO extends Check implements PacketHandler {
   private boolean placed;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.placed) {
         if (this.playerData.getTimestamp() - this.playerData.getLastLastFlying() > 40L) {
            this.handleViolation("", 0.5D);
         } else {
            this.decreaseVL(0.05D);
         }

         this.placed = false;
      }

   }

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      if (this.playerData.isSurvival()) {
         if (this.playerData.getTimestamp() - this.playerData.getLastFlying() < 5L) {
            this.placed = true;
         } else {
            this.decreaseVL(0.1D);
         }
      }

   }
}
