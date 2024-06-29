package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "R",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9
)
public class BadPacketsR extends Check implements PacketHandler {
   private boolean interact;
   private boolean sent;

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      this.sent = true;
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.sent = this.interact = false;
   }

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction().isInteract()) {
         if (this.sent && !this.interact) {
            this.handleViolation("", 1.0D, true);
            this.sent = false;
         }

         this.interact = true;
      }

   }
}
