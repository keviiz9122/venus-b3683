package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInSetCreativeSlot;

@CheckInfo(
   type = CheckType.SERVER_CRASHER,
   subType = "E",
   friendlyName = "Server Crasher",
   version = CheckVersion.DEVELOPMENT,
   maxViolations = 1,
   minViolations = -2.0D,
   schem = true
)
public class ServerCrasherE extends Check implements PacketHandler {
   public void handle(VPacketPlayInSetCreativeSlot<?> var1) {
      switch(this.player.getGameMode()) {
      case SURVIVAL:
      case ADVENTURE:
         this.handleViolation();
      default:
      }
   }
}
