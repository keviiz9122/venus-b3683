package me.levansj01.verus.type.premium.checks.killaura;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.java.AtomicCappedQueue;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "Q",
   friendlyName = "KillAura",
   version = CheckVersion.DEVELOPMENT,
   minViolations = -5.0D,
   maxViolations = 20
)
public class KillAuraQ extends AimCheck {
   private final Queue<Double> values = new LinkedList();

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (this.playerData.getLastAttackTicks() <= 1 && this.playerData.getLastAttacked() != null && (double)Math.min(Math.abs(var1.getYaw() - var2.getYaw()), Math.abs(var1.getPitch() - var2.getPitch())) > 0.5D) {
         AtomicCappedQueue var5 = (AtomicCappedQueue)this.playerData.getRecentMoveMap().get(this.playerData.getLastAttackedId());
         if (var5 != null && var5.size() > 5) {
            ArrayList var6 = new ArrayList();
            int var7 = this.playerData.getTransactionPing();
            long var8 = var3 - 125L - (long)var7;
            Iterator var10 = var5.iterator();
            PacketLocation var11 = (PacketLocation)var10.next();

            while(var10.hasNext()) {
               PacketLocation var12 = (PacketLocation)var10.next();
               long var13 = var12.getTimestamp() - var8;
               if (var13 > 0L) {
                  var6.add(var11);
                  if (var13 > 75L) {
                     var11 = var12;
                     break;
                  }
               }

               var11 = var12;
            }

            if (var6.isEmpty()) {
               var6.add(var11);
            }

            Stream var18 = var6.stream().map((var1x) -> {
               return MathUtil.getLuckyAura(var2, var1x);
            });
            if (this.values.size() > 10) {
               double var19 = MathUtil.average((Iterable)this.values);
               Double var15 = (Double)var18.min(Comparator.comparingDouble((var2x) -> {
                  return Math.abs(var2x - var19) + Math.abs(var2x / 2.0D);
               })).orElse((Object)null);
               if (var15 != null) {
                  this.values.add(var15);
                  double var16 = MathUtil.deviation(this.values);
                  if (var16 < 0.1D && var19 < 0.3D) {
                     this.handleViolation(String.format("AVG: %.2f DEV: %.3f", var19, var16), 1.25D - var16 * 10.0D);
                  } else {
                     this.decreaseVL(0.1D);
                  }

                  this.values.poll();
               }
            } else {
               this.values.add(MathUtil.average((Iterable)var18.collect(Collectors.toList())));
            }
         }
      }

   }
}
