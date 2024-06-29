package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;

@CheckInfo(
   type = CheckType.AUTO_CLICKER,
   subType = "X2",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   unsupportedAtleast = ClientVersion.V1_9,
   logData = true
)
public class AutoClickerX2 extends Check implements PacketHandler {
   private long lastFlying;
   private int swings;
   private int done;
   private boolean place;

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      this.place = true;
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.done++ >= 19) {
         int var2 = StorageEngine.getInstance().getVerusConfig().getMaxClicksPerSecond();
         if (this.swings > var2) {
            this.handleViolation(String.format("S: %s", this.swings), (double)(this.swings - var2) / 2.0D);
         } else {
            this.decreaseVL(0.1D);
         }

         this.done = this.swings = 0;
      }

      if (this.place) {
         this.place = false;
      }

      this.lastFlying = this.playerData.getTimestamp();
   }

   public void handle(VPacketPlayInArmAnimation<?> var1) {
      if (!this.playerData.isSwingDigging() && !this.place) {
         if (this.playerData.getVersion().before(ClientVersion.V1_8) && this.playerData.getTimestamp() - this.lastFlying > 110L) {
            return;
         }

         ++this.swings;
      }

   }
}
