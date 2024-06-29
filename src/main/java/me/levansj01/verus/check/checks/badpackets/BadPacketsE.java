package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "E",
   friendlyName = "Invalid Move",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9,
   schem = true
)
public class BadPacketsE extends Check implements PacketHandler {
   private boolean sentSneak;
   private boolean sentSprint;

   public void handle(VPacketPlayInEntityAction<?> var1) {
      if (!this.playerData.isVehicle() && this.playerData.isSurvival() && !this.playerData.isTeleportingV2() && !this.playerData.hasLag()) {
         VPacketPlayInEntityAction.PlayerAction var2 = var1.getAction();
         if (var2.isSprint()) {
            if (this.sentSprint) {
               this.handleViolation("Sprint");
            }

            this.sentSprint = true;
         } else if (var2.isSneak()) {
            if (this.sentSneak) {
               this.handleViolation("Sneak");
            }

            this.sentSneak = true;
         }
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.sentSprint = this.sentSneak = false;
   }
}
