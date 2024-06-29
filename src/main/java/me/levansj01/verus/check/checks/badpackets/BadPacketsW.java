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
   subType = "W",
   friendlyName = "MultiUse",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9,
   unsupportedVersions = {ClientVersion.V1_7},
   minViolations = -2.0D
)
public class BadPacketsW extends Check implements PacketHandler {
   private int placed = 0;

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      if (var1.isItem() && this.placed++ > this.playerData.getMaxPingTicks()) {
         this.handleViolation(String.format("V: %s | %s", this.placed, this.playerData.getMaxPingTicks()), (double)(this.placed - 1));
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.placed = 0;
      this.decreaseVL(2.0D);
   }
}
