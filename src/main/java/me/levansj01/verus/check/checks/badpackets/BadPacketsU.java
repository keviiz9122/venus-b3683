package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "U",
   friendlyName = "Invalid Inventory",
   version = CheckVersion.RELEASE,
   minViolations = -1.5D,
   maxViolations = 5,
   schem = true
)
public class BadPacketsU extends Check implements PacketHandler {
   private Long swapped;
   private Long lastFlying;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.lastFlying != null && this.swapped != null) {
         long var2 = this.playerData.getTimestamp();
         if (this.playerData.getVersion().after(ClientVersion.V1_8) && var2 - this.lastFlying > 60L) {
            return;
         }

         if (var2 - this.playerData.getLastLastFlying() > 45L && var2 - this.swapped > 40L) {
            this.handleViolation(String.format("S: %s F: %s", var2 - this.swapped, var2 - this.lastFlying), 0.5D);
         } else {
            this.decreaseVL(0.05D);
         }
      }

      this.lastFlying = this.playerData.getLastFlying();
      this.swapped = null;
   }

   public void handle(VPacketPlayInHeldItemSlot<?> var1) {
      if (this.playerData.isSurvival()) {
         long var2 = this.playerData.getTimestamp();
         if (var2 - this.playerData.getLastFlying() < 5L) {
            this.swapped = var2;
         } else {
            this.decreaseVL(0.1D);
         }
      }

   }
}
