package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "P",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9
)
public class BadPacketsP extends Check implements PacketHandler {
   private boolean interact;
   private boolean attack;
   private boolean interactAt;

   public void handle(VPacketPlayInFlying<?> var1) {
      this.attack = this.interactAt = this.interact = false;
   }

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction().isAttack()) {
         if (!this.attack && (this.interact || this.interactAt)) {
            StringBuilder var10001 = (new StringBuilder()).append("Attack [");
            String var10002;
            if (this.interactAt) {
               var10002 = "Interact At ";
            } else {
               var10002 = "";
            }

            var10001 = var10001.append(var10002);
            if (this.interact) {
               var10002 = "Interact";
            } else {
               var10002 = "";
            }

            this.handleViolation(var10001.append(var10002).append("]").toString(), 1.0D, true);
            this.interact = this.interactAt = false;
         }

         this.attack = true;
      } else if (var1.getAction().isInteract()) {
         this.interact = true;
      } else if (var1.getAction().isInteractAt()) {
         if (!this.interactAt && this.interact) {
            this.handleViolation("Interact", 1.0D, true);
            this.interact = false;
         }

         this.interactAt = true;
      }

   }
}
