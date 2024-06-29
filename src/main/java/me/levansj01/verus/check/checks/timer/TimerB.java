package me.levansj01.verus.check.checks.timer;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
   type = CheckType.TIMER,
   subType = "B",
   friendlyName = "Timer",
   version = CheckVersion.RELEASE,
   logData = true,
   maxViolations = 40,
   minViolations = -3.0D
)
public class TimerB extends Check implements PacketHandler {
   private Long lastLook;
   private Long lastPacket;
   private final BasicDeque<Long> delays = new CappedQueue(40);

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      if ((var1.isUse() || var1.isEmpty()) && this.lastLook != null && this.playerData.getTimestamp() - this.lastLook < 5L && this.playerData.getVersion().afterEq(ClientVersion.V1_17)) {
         this.delays.addFirst(250L);
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      long var2 = this.playerData.getTimestamp();
      if (this.playerData.shouldHaveReceivedPing() && this.playerData.getTotalTicks() > 200 && !this.playerData.isTeleporting(3) && !this.playerData.isTeleportingV2() && this.playerData.isSpawned() && !this.playerData.isVehicle() && !this.playerData.hasLag() && var1.isPos() && (this.playerData.getVersion().after(ClientVersion.V1_8) && this.playerData.getVersion() != ClientVersion.VERSION_UNSUPPORTED || this.playerData.isMoved())) {
         if (this.lastPacket != null) {
            this.delays.addFirst(var2 - this.lastPacket);
            if (this.delays.size() == 40) {
               double var4 = MathUtil.average((Iterable)this.delays);
               double var6 = 50.0D / var4;
               if (var4 <= 48.0D) {
                  this.handleViolation(String.format("T: %.1f M: %.2f", var4, var6), Math.min(Math.floor(var6 / 0.6D), 2.0D));
               } else {
                  this.decreaseVL(0.25D);
               }

               this.delays.clear();
            }
         }

         this.lastPacket = var2;
      }

      if (var1.isLook()) {
         this.lastLook = var2;
      }

   }
}
