package me.levansj01.verus.type.premium.checks.scaffold;

import java.util.Objects;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;

@CheckInfo(
   type = CheckType.SCAFFOLD,
   subType = "D",
   friendlyName = "Scaffold",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 1,
   schem = true
)
public class ScaffoldD extends Check implements PacketHandler {
   private Integer lastSlot = -999;

   public void handle(VPacketPlayInHeldItemSlot<?> var1) {
      if (Objects.equals(var1.getSlot(), this.lastSlot) && this.playerData.getTotalTicks() > 200) {
         this.handleViolation();
      } else {
         this.decreaseVL(0.025D);
      }

      this.lastSlot = var1.getSlot();
   }
}
