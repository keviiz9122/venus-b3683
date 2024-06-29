package me.levansj01.verus.check.checks.badpackets;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(
   friendlyName = "Ping Spoof",
   type = CheckType.BAD_PACKETS,
   subType = "G7",
   version = CheckVersion.RELEASE,
   maxViolations = 5,
   minViolations = -2.0D,
   unsupportedAtleast = ClientVersion.V1_9,
   unsupportedServerAtleast = ServerVersion.v1_17_R1
)
public class BadPacketsG7 extends Check implements PacketHandler {
   private static final int TIME = 20 * StorageEngine.getInstance().getVerusConfig().getPingTimeout();

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (StorageEngine.getInstance().getVerusConfig().isPingKickCheck()) {
         TickerMap var2 = this.playerData.getTickerMap();
         int var3 = var2.get(TickerType.TOTAL);
         int var4 = var3 - var2.get(TickerType.LAST_RECEIVED_TRANSACTION);
         int var5 = var3 - var2.get(TickerType.LAST_SENT_TRANSACTION);
         if (var3 > TIME && var4 >= TIME && var5 < 10 && var1.isPlayer()) {
            this.handleViolation(() -> {
               return String.format("S: %s D: %s", var5, var4);
            });
         } else {
            this.decreaseVL(0.01D);
         }
      }

   }
}
