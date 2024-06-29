package me.levansj01.verus.type.premium.checks.inventory;

import java.util.Objects;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.PlayerData;

@CheckInfo(
   type = CheckType.INVENTORY,
   subType = "C",
   friendlyName = "Inventory",
   version = CheckVersion.RELEASE,
   maxViolations = 5,
   schem = true
)
public class InventoryC extends Check implements PacketHandler {
   private int ticks = 0;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.playerData.isSprinting(true) && this.playerData.isInventoryOpen()) {
         ++this.ticks;
      } else {
         this.ticks = 0;
         this.decreaseVL(0.001D);
      }

   }

   public void handle(VPacketPlayInWindowClick<?> var1) {
      if (var1.getSlot() != -999 && !this.playerData.isTeleportingV2() && this.playerData.isSpawned() && this.playerData.isMoved() && this.ticks > 3) {
         this.handleViolation(String.format("T: %s", this.ticks));
         PlayerData var10001 = this.playerData;
         Objects.requireNonNull(var10001);
         this.run(var10001::closeInventory);
      }

   }
}
