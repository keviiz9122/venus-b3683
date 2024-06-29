package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "V",
   friendlyName = "Invalid Interact",
   version = CheckVersion.RELEASE,
   maxViolations = 5,
   logData = true
)
public class BadPacketsV extends Check implements PacketHandler {
   public void handle(VPacketPlayInUseEntity<?> var1) {
      int var2 = var1.getId();
      if (var2 < 0) {
         this.handleViolation(() -> {
            return String.format("E: %s", var2);
         });
      }

   }
}
