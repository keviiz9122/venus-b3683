package me.levansj01.verus.check.checks.scaffold;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.inventory.ItemStack;

@CheckInfo(
   type = CheckType.SCAFFOLD,
   subType = "E",
   friendlyName = "Scaffold",
   version = CheckVersion.RELEASE,
   schem = true,
   maxViolations = 1
)
public class ScaffoldE extends Check implements PacketHandler {
   public void handle(VPacketPlayInBlockPlace<?> var1) {
      if (var1.getFace() != 255) {
         ItemStack var2 = var1.getItemStack();
         if (var2 != null && var2.getType() != MaterialList.AIR) {
            float var3 = var1.getBlockX();
            float var4 = var1.getBlockY();
            float var5 = var1.getBlockZ();
            if (var3 > 1.0F || var4 > 1.0F || var5 > 1.0F) {
               this.handleViolation(String.format("X: %.2f Y: %.2f Z: %.2f", var3, var4, var5));
            }
         }
      }

   }
}
