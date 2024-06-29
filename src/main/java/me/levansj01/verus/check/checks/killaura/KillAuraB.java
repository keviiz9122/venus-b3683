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
   subType = "B",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 5
)
public class KillAuraB extends Check implements PacketHandler {
   private int ticks;
   private int invalidTicks;
   private int totalTicks;
   private int lastTicks;

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction().isAttack()) {
         if (this.ticks <= 8) {
            if (this.lastTicks == this.ticks) {
               ++this.invalidTicks;
            }

            if (this.totalTicks++ >= 25) {
               if (this.invalidTicks > 22) {
                  this.handleViolation(String.format("%s/%s", this.invalidTicks, this.totalTicks), 1.0D + (double)(this.invalidTicks - 22) / 6.0D);
               } else {
                  this.violations -= Math.min(this.violations + 1.0D, 1.0D);
               }

               this.invalidTicks = this.totalTicks = 0;
            }

            this.lastTicks = this.ticks;
         }

         this.ticks = 0;
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      ++this.ticks;
   }
}
