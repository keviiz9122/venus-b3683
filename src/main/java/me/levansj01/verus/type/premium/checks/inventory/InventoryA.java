package me.levansj01.verus.type.premium.checks.inventory;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.INVENTORY,
   subType = "A",
   friendlyName = "Inventory",
   version = CheckVersion.RELEASE,
   minViolations = -5.0D,
   maxViolations = 5,
   unsupportedAtleast = ClientVersion.V1_9,
   unsupportedServerAtleast = ServerVersion.v1_11_R1,
   schem = true
)
public class InventoryA extends Check implements PacketHandler {
   private boolean clicked = false;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.clicked) {
         if (this.playerData.getTimestamp() - this.playerData.getLastLastFlying() > 40L) {
            this.handleViolation();
         } else {
            this.decreaseVL(0.05D);
         }

         this.clicked = false;
      }

   }

   public void handle(VPacketPlayInWindowClick<?> var1) {
      if (this.playerData.getTimestamp() - this.playerData.getLastFlying() < 5L) {
         this.clicked = var1.isShiftClick();
      } else {
         this.decreaseVL(0.1D);
      }

   }
}
