package me.levansj01.verus.check.checks.killaura;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToDoubleFunction;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.java.AtomicCappedQueue;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "A",
   friendlyName = "Aimbot",
   version = CheckVersion.RELEASE,
   minViolations = -2.0D,
   maxViolations = 6
)
public class KillAuraA extends Check implements PacketHandler {

   public void handle(VPacketPlayInFlying<?> var1) {
      if (var1.isLook() && !this.playerData.isTeleporting(5) && this.playerData.isSpawned() && this.playerData.getLastAttackTicks() <= 20 && this.playerData.getLastAttacked() != null) {
         PlayerLocation var2 = this.playerData.getLocation();
         AtomicCappedQueue var3 = (AtomicCappedQueue)this.playerData.getRecentMoveMap().get(this.playerData.getLastAttackedId());
         if (var3 != null) {
            int var4 = this.playerData.getTransactionPing();
            List var5 = var3.toList();
            ArrayList var6 = new ArrayList();
            long var7 = this.playerData.getTimestamp() - 125L - (long)var4;
            Iterator var9 = var5.iterator();
            PacketLocation var10 = (PacketLocation)var9.next();

            PacketLocation var11;
            while(var9.hasNext()) {
               var11 = (PacketLocation)var9.next();
               long var12 = var11.getTimestamp() - var7;
               if (var12 > 0L) {
                  var6.add(var10);
                  if (var12 > 100L) {
                     var10 = var11;
                     break;
                  }
               }

               var10 = var11;
            }

            if (var6.isEmpty()) {
               var6.add(var10);
            }

            var11 = (PacketLocation)MathUtil.min((Iterable)var6, (ToDoubleFunction)(PacketLocation::getTimestamp));
            int var20 = var5.indexOf(var11);
            if (var20 > 0) {
               var6.add((PacketLocation)var5.get(var20 - 1));
            }

            Iterator var13 = var6.iterator();

            while(var13.hasNext()) {
               PacketLocation var14 = (PacketLocation)var13.next();
               float[] var15 = MathUtil.getRotationFromPosition(var2, var14);
               double var16 = MathUtil.getDistanceBetweenAngles(var2.getYaw(), var15[0]);
               double var18 = MathUtil.getDistanceBetweenAngles(var2.getPitch(), var15[1]);
               if (var16 == 0.0D || var18 == 0.0D) {
                  if (var16 == 0.0D) {
                     this.handleViolation("Yaw", 1.0D);
                  }

                  return;
               }
            }
         }

         this.decreaseVL(1.0E-4D);
      }

   }
}
