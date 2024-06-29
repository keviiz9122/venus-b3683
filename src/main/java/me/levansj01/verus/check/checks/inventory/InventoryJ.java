package me.levansj01.verus.check.checks.inventory;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.INVENTORY,
   subType = "J",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9
)
public class InventoryJ extends Check implements PacketHandler {
   private boolean swing = false;
   private boolean click = false;

   public void handle(VPacketPlayInWindowClick<?> var1) {
      this.click = true;
   }

   public void handle(VPacketPlayInArmAnimation<?> var1) {
      this.swing = true;
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.swing && this.click) {
         this.handleViolation("", 1.0D, true);
      }

      this.swing = false;
      this.click = false;
   }
}
