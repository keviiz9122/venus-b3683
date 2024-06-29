package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "I",
   friendlyName = "Invalid Inventory",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9,
   minViolations = -1.5D,
   maxViolations = 5,
   schem = true
)
public class BadPacketsI extends Check implements PacketHandler {
   private int stage = 0;

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      if (this.stage == 1) {
         this.stage = 2;
      }

      if (var1.isItem()) {
         this.stage = 0;
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.stage = 0;
   }

   public void handle(VPacketPlayInHeldItemSlot<?> var1) {
      if (this.stage == 2) {
         this.handleViolation();
      }

      this.stage = 1;
   }
}
