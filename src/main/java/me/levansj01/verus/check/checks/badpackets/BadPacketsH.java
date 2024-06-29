package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "H",
   friendlyName = "Invalid Direction",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 15,
   unsupportedAtleast = ClientVersion.V1_9
)
public class BadPacketsH extends Check implements PacketHandler {
   private Float lastPitch = null;
   private Float lastYaw = null;
   private Float oldPitch = null;
   private Float oldYaw = null;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (var1.isLook()) {
         float var2 = var1.getYaw();
         float var3 = var1.getPitch();
         if (!this.playerData.isTeleportingV2() && !this.playerData.isTeleporting(5) && !this.playerData.hasRecentTeleport(10, this.playerData.getLastLocation(), this.playerData.getLocation()) && !this.playerData.isVehicle() && !this.playerData.hasFast(this.playerData.getTimestamp() - (long)this.playerData.getTransactionPing()) && this.playerData.isSpawned() && !this.playerData.isSuffocating()) {
            if (this.oldYaw == null || this.oldYaw != var2 || this.oldPitch == null || this.oldPitch != var3) {
               this.oldPitch = this.oldYaw = null;
               if (this.lastYaw != null && this.lastPitch != null && this.lastYaw == var2 && this.lastPitch == var3) {
                  this.handleViolation(() -> {
                     return String.format("%f %f", this.lastYaw, this.lastPitch);
                  });
               } else {
                  this.decreaseVL(0.05D);
               }
            }

            this.lastYaw = var2;
            this.lastPitch = var3;
         } else {
            this.oldYaw = var2;
            this.oldPitch = var3;
         }
      }

   }
}
