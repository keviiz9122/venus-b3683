package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "D",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   logData = true
)
public class KillAuraD extends Check implements PacketHandler {
   private final BasicDeque<Double> pitchList = new CappedQueue(20);
   private Float lastPitch = null;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (var1.isLook()) {
         if (this.lastPitch != null && this.playerData.getLastAttackTicks() <= 3 && this.playerData.getLastAttacked() != null) {
            double var2 = (double)Math.abs(var1.getPitch() - this.lastPitch);
            this.pitchList.addFirst(var2);
            if (this.pitchList.size() >= 20) {
               double var4 = MathUtil.deviation(this.pitchList);
               double var6 = MathUtil.average((Iterable)this.pitchList);
               boolean var10000;
               if ((!(var6 > 17.5D) || !(var4 > 15.0D)) && (!(var6 > 22.5D) || !(var4 > 12.5D))) {
                  var10000 = false;
               } else {
                  var10000 = true;
               }

               boolean var8 = var10000;
               if (var8) {
                  this.handleViolation(String.format("AVG: %s DEV: %s", var6, var4));
               } else {
                  this.decreaseVL(0.025D);
               }

               this.pitchList.clear();
            }
         }

         this.lastPitch = var1.getPitch();
      }

   }
}
