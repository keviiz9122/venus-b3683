package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;

@CheckInfo(
   friendlyName = "InvalidSteer",
   type = CheckType.BAD_PACKETS,
   subType = "B1",
   version = CheckVersion.RELEASE,
   maxViolations = 5,
   minViolations = -2.0D
)
public class BadPacketsB1 extends Check implements PacketHandler {
   public void handle(VPacketPlayInSteerVehicle<?> var1) {
      if (Math.abs(var1.getForward()) > 0.9800000190734863D || Math.abs(var1.getStrafe()) > 0.9800000190734863D) {
         this.handleViolation();
      }

   }
}
