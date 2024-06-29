package me.levansj01.verus.check.checks.timer;

import java.util.concurrent.TimeUnit;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.TIMER,
   subType = "A",
   friendlyName = "Timer",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 60,
   logData = true,
   unsupportedAtleast = ClientVersion.V1_9
)
public class TimerA extends Check implements PacketHandler {
   private long offset = -100L;
   private static final long CHECK_TIME_LINEAR = toNanos(45L);
   private long lastFlag;
   private Long lastPacket = null;
   private long lastBow;
   private boolean place;

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      if (var1.isItem()) {
         this.place = true;
      }

   }

   public static long fromNanos(long var0) {
      return TimeUnit.NANOSECONDS.toMillis(var0);
   }

   public static long toNanos(long var0) {
      return TimeUnit.MILLISECONDS.toNanos(var0);
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      long var2 = this.playerData.getNanos();
      if (this.place) {
         this.lastBow = var2;
         this.place = false;
      }

      if (this.lastPacket != null) {
         long var4 = var2 - this.lastPacket;
         long var6 = toNanos(50L) - var4;
         this.offset += var6;
         if (this.offset > CHECK_TIME_LINEAR) {
            boolean var10000;
            if (fromNanos(var2 - this.lastBow) < 50L) {
               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var8 = var10000;
            if ((fromNanos(var2 - this.lastFlag) > 1L || var8) && this.playerData.shouldHaveReceivedPing() && this.playerData.getTotalTicks() > 400 && !this.playerData.isTeleporting(3) && !this.playerData.isTeleportingV2() && this.playerData.isSpawned() && !this.playerData.isVehicle()) {
               long var9 = this.playerData.getLastTeleport();
               if (!this.playerData.isMoved() && !var8) {
                  var10000 = false;
               } else {
                  var10000 = true;
               }

               boolean var11 = var10000;
               double var16;
               if (var11) {
                  var16 = 1.0D;
               } else {
                  var16 = 0.3D;
               }

               double var12 = var16;
               long var14 = this.playerData.getTimestamp();
               this.handleViolation(() -> {
                  Object[] var10001 = new Object[]{fromNanos(this.offset), fromNanos(var2 - this.lastFlag), fromNanos(var2 - this.lastBow), null, null};
                  long var10004;
                  if (var9 == 0L) {
                     var10004 = -1L;
                  } else {
                     var10004 = var14 - var9;
                  }

                  var10001[3] = var10004;
                  var10001[4] = var11;
                  return String.format("O: %sms F: %sms B: %sms T: %sms M: %s", var10001);
               }, var12);
            }

            this.lastFlag = var2;
            this.offset = 0L;
         } else {
            this.decreaseVL(0.0025D);
         }
      }

      this.lastPacket = var2;
   }
}
