package me.levansj01.verus.check.checks.crasher;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.inventory.ItemStack;

@CheckInfo(
   type = CheckType.SERVER_CRASHER,
   subType = "F",
   friendlyName = "Server Crasher",
   version = CheckVersion.DEVELOPMENT,
   maxViolations = 1
)
public class ServerCrasherF extends Check implements PacketHandler {
   public void handle(VPacketPlayInCustomPayload<?> var1) {
      String var2 = var1.getChannel();
      if (var2.equals("MC|BOpen") || var2.equals("MC|BEdit") || var2.equals("MC|BSign")) {
         ItemStack var3 = this.player.getItemInHand();
         if (var3 != null && !MaterialList.BOOKS.contains(var3.getType())) {
            this.handleViolation();
            this.playerData.fuckOff();
         }
      }

   }
}
