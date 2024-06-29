package me.levansj01.verus.type.premium.checks.inventory;

import java.util.Objects;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.INVENTORY,
   subType = "B",
   friendlyName = "Inventory",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9,
   maxViolations = 5
)
public class InventoryB extends Check implements PacketHandler {
   private int stage = 0;
   private int slot = -999;

   public void handle(VPacketPlayInWindowClick<?> var1) {
      int var2 = var1.getSlot();
      if (var1.isShiftClick() && var1.getButton() == 0 && !this.playerData.hasLag()) {
         if (this.stage == 0 && var1.isItem()) {
            this.stage = 1;
         } else if (this.stage == 1 && !var1.isItem() && Objects.equals(this.slot, var2)) {
            this.handleViolation("", 1.0D, true);
            this.stage = 0;
         } else {
            this.stage = 0;
         }
      } else {
         this.stage = 0;
      }

      this.slot = var2;
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.stage = 0;
      this.decreaseVL(0.025D);
   }
}
