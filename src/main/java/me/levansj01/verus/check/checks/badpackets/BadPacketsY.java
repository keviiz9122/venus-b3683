package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.BAD_PACKETS,
   subType = "Y",
   friendlyName = "Blink",
   version = CheckVersion.RELEASE,
   logData = true,
   minViolations = -2.5D,
   maxViolations = 15
)
public class BadPacketsY extends Check implements PacketHandler {
   private Long lastTickDelay;
   private Long lastKeepAlive = 0L;

   public void handle(VPacketPlayInKeepAlive<?> var1) {
      this.lastKeepAlive = this.playerData.getTimestamp();
   }

   public void handleIn(Transactionable var1) {
      if (this.playerData.shouldHaveReceivedPing() && !this.player.isDead() && !this.playerData.isVehicle() && this.playerData.isSurvival() && this.playerData.getTotalTicks() > 800) {
         long var2 = (long)Math.max(50, Math.max(this.playerData.getTransactionPing(), this.playerData.getPing()));
         long var4 = this.playerData.getTimestamp() - this.playerData.getLastFlying();
         if (this.lastTickDelay != null) {
            long var6 = var4 - this.lastTickDelay;
            long var8 = this.playerData.getTimestamp() - this.lastKeepAlive;
            short var10000;
            if (this.playerData.getVersion().after(ClientVersion.V1_8)) {
               var10000 = 2000;
            } else {
               var10000 = 500;
            }

            long var10 = (long)var10000 + var2 * 2L;
            if (var4 > var10 && var6 > Math.max(250L, var2) && var6 < 500L && var8 < 1000L + var2 * 2L) {
               this.handleViolation(() -> {
                  return String.format("T: %s D: %s L: %s O: %s K: %s", var4, var6, var10, Math.abs(var4 - var10), var8);
               }, 0.5D);
            } else {
               this.decreaseVL(0.05D);
            }
         }

         this.lastTickDelay = var4;
      }

   }
}
