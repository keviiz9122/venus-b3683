package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(
   type = CheckType.SERVER_CRASHER,
   subType = "A",
   friendlyName = "Server Crasher",
   version = CheckVersion.RELEASE,
   maxViolations = 5,
   logData = true
)
public class ServerCrasherA extends Check implements PacketHandler {
   private int threshold = 0;

   public void handle(VPacketPlayInCustomPayload<?> var1) {
      String var2 = var1.getChannel();
      if ((var2.equals("MC|BOpen") || var2.equals("MC|BEdit")) && (this.threshold += 2) > 4) {
         this.handleViolation(String.format("C: %s", var2), (double)this.threshold / 4.0D);
         if (this.violations > (double)this.getMaxViolation()) {
            this.playerData.fuckOff();
         }
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.threshold -= Math.min(this.threshold, 1);
   }
}
