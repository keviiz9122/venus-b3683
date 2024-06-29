package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "G5",
   friendlyName = "Ping Spoof",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 50
)
public class BadPacketsG5 extends Check implements PacketHandler {
   private long lastKeepAlive;

   public void handle(VPacketPlayInKeepAlive<?> var1) {
      if (this.playerData.shouldHaveReceivedPing() && this.lastKeepAlive > var1.getId() && this.playerData.getTotalTicks() > 160) {
         long var2 = this.lastKeepAlive - var1.getId();
         this.handleViolation(() -> {
            return String.format("D: %s", var2);
         });
      }

      this.lastKeepAlive = var1.getId();
   }
}
