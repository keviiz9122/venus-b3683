package me.levansj01.verus.check.checks.badpackets;

import java.util.function.Supplier;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "A",
   friendlyName = "Timer",
   version = CheckVersion.RELEASE,
   maxViolations = 5
)
public class BadPacketsA extends Check implements PacketHandler {
   private int packets;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (!this.playerData.isVehicle() && !var1.isPos()) {
         if (++this.packets > 20) {
            int var2 = this.packets;
            Supplier var10001 = () -> {
               return String.format("P: %s | %s", var2, this.playerData.getMaxPingTicks());
            };
            double var10002;
            if (var2 > 22) {
               var10002 = 2.0D;
            } else {
               var10002 = 0.2D;
            }

            this.handleViolation(var10001, var10002);
         } else {
            this.decreaseVL(1.0E-5D);
         }
      } else {
         this.packets = 0;
      }

   }
}
