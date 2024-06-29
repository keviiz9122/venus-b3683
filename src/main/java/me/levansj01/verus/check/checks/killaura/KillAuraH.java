package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "H",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 5
)
public class KillAuraH extends Check implements PacketHandler {
   private boolean attack;

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction().isAttack()) {
         long var2 = this.playerData.getLastFlying();
         if (this.playerData.getTimestamp() - var2 < 5L) {
            this.attack = true;
         } else {
            this.decreaseVL(0.1D);
         }
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.attack) {
         if (this.playerData.getTimestamp() - this.playerData.getLastLastFlying() > 40L) {
            this.handleViolation("", 0.5D);
         } else {
            this.decreaseVL(0.05D);
         }

         this.attack = false;
      }

   }
}
