package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;

@CheckInfo(
   type = CheckType.SERVER_CRASHER,
   subType = "D",
   friendlyName = "Server Crasher",
   version = CheckVersion.DEVELOPMENT,
   maxViolations = 1
)
public class ServerCrasherD extends Check implements PacketHandler {
   public void handle(VPacketPlayInCustomPayload<?> var1) {
      if (var1.getSize() > 15000) {
         this.handleViolation();
         this.playerData.fuckOff();
      }

   }
}
