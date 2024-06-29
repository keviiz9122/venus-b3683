package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
   type = CheckType.AUTO_CLICKER,
   subType = "D",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 10,
   logData = true
)
public class AutoClickerD extends Check implements PacketHandler {
   private int lastTicks = 0;
   private int ticks = 0;
   private int done = 0;
   private boolean digging;
   private boolean swung;
   private final BasicDeque<Integer> averageTicks = new CappedQueue(50);
   private boolean abortedDigging;

   public void handle(VPacketPlayInArmAnimation<?> var1) {
      this.swung = true;
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.swung && !this.digging && this.playerData.getLastAttackTicks() < 1200) {
         if (this.ticks < 7) {
            this.averageTicks.addFirst(this.ticks);
         }

         if (this.averageTicks.size() > 40) {
            double var2 = MathUtil.average((Iterable)this.averageTicks);
            if (var2 < 2.5D) {
               if (this.ticks > 3 && this.ticks < 20 && this.lastTicks < 20) {
                  this.violations -= Math.min(this.violations, 0.25D);
                  this.done = 0;
               } else if ((double)(this.done++) > 600.0D / (var2 * 1.5D)) {
                  this.handleViolation(String.format("A: %s", var2), 1.0D);
                  this.done = 0;
               }
            } else {
               this.done = 0;
            }
         }

         this.lastTicks = this.ticks;
         this.ticks = 0;
      }

      if (this.abortedDigging) {
         this.digging = false;
         this.abortedDigging = false;
      }

      this.swung = false;
      ++this.ticks;
   }

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
         this.digging = true;
         this.abortedDigging = false;
      } else if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
         this.abortedDigging = true;
      }

   }
}
