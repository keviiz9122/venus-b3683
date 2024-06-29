package me.levansj01.verus.type.premium.checks.scaffold;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.World;

@CheckInfo(
   friendlyName = "Scaffold",
   type = CheckType.SCAFFOLD,
   subType = "A",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 5,
   schem = true
)
public class ScaffoldA extends Check implements PacketHandler {

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      if (var1.getFace() == 1 && (double)var1.getBlockY() == 0.0D) {
         World var2 = this.player.getWorld();
         BlockPosition var3 = var1.getPosition();
         this.run(() -> {
            Material var3x = NMSManager.getInstance().getTypeWithAPI(this.player, var2, var3);
            if (!MaterialList.FLAT.contains(var3x)) {
               this.handleViolation(String.valueOf(var3x));
            }

         });
      } else {
         this.decreaseVL(0.05D);
      }

   }
}
