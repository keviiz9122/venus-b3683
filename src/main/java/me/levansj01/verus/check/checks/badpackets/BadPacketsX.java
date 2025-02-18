package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "X",
   friendlyName = "FastBreak",
   version = CheckVersion.RELEASE,
   minViolations = -0.5D,
   maxViolations = 10
)
public class BadPacketsX extends Check implements PacketHandler {
   private int ticks = 0;
   private int stage = 0;

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.STOP_DESTROY_BLOCK) {
         this.stage = 1;
         this.decreaseVL(1.0E-4D);
      } else if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
         if (this.stage == 2 && (this.ticks != 1 || this.playerData.getVersion().before(ClientVersion.V1_9))) {
            this.handleViolation(String.format("T: %s", this.ticks), 0.25D);
         }

         this.stage = 0;
         this.ticks = 0;
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.stage == 2) {
         this.decreaseVL(0.01D);
      }

      if (this.stage == 1) {
         ++this.ticks;
         this.stage = 2;
      } else {
         this.stage = 0;
      }

   }
}
