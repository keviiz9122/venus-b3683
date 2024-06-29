package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
   type = CheckType.AUTO_CLICKER,
   subType = "A",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   minViolations = -5.0D,
   maxViolations = 10,
   logData = true
)
public class AutoClickerA extends Check implements PacketHandler {
   private boolean place;
   private boolean swung;
   private int ticks = 0;
   private final BasicDeque<Integer> intervals = new CappedQueue(40);

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      this.place = true;
   }

   public void handle(VPacketPlayInArmAnimation<?> var1) {
      this.swung = true;
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.swung && !this.playerData.isSwingDigging() && !this.place && this.playerData.isSurvival() && this.playerData.getLastAttackTicks() < 1200) {
         if (this.ticks < 8) {
            this.intervals.addFirst(this.ticks);
            if (this.intervals.size() >= 40) {
               double var2 = MathUtil.deviation(this.intervals);
               double var4 = (0.325D - var2) * 2.0D + 0.675D;
               if (var4 > 0.0D) {
                  this.handleViolation(String.format("D: %s", var2), var4);
               } else {
                  this.decreaseVL(-var4);
               }

               this.intervals.clear();
            }
         }

         this.ticks = 0;
      }

      this.place = false;
      this.swung = false;
      ++this.ticks;
   }
}
