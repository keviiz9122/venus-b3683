package me.levansj01.verus.check.checks.badpackets;

import java.util.function.Supplier;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "D",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   maxViolations = 1
)
public class BadPacketsD extends Check implements PacketHandler {

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.RELEASE_USE_ITEM) {
         switch(var1.getFace()) {
         case UP:
         case NORTH:
         case EAST:
         case WEST:
            Supplier var10001 = () -> {
               return String.format("F: %s", var1.getFace().name());
            };
            boolean var10003;
            if (this.violations < 2.0D) {
               var10003 = true;
            } else {
               var10003 = false;
            }

            this.handleViolation(var10001, 1.0D, var10003);
         }
      }

   }
}
