package me.levansj01.verus.check.checks.scaffold;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.inventory.ItemStack;

@CheckInfo(
   friendlyName = "Scaffold",
   type = CheckType.SCAFFOLD,
   subType = "G",
   version = CheckVersion.RELEASE,
   schem = true,
   minViolations = -1.0D,
   maxViolations = 10
)
public class ScaffoldG extends Check implements PacketHandler {
   private static final Cuboid CUBOID = new Cuboid();

   public void handle(VPacketPlayInFlying<?> var1) {
      this.decreaseVL(0.05D);
   }

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      int var2 = var1.getFace();
      if (var2 != 255 && !this.playerData.isTeleportingV2()) {
         ItemStack var3 = var1.getItemStack();
         if (var3 != null && var3.getType() != MaterialList.AIR) {
            BlockPosition var4 = var1.getPosition();
            if (var4 != null) {
               PlayerLocation var5 = this.playerData.getLocation().clone();
               var5.setY(var5.getY() + this.player.getEyeHeight());
               CUBOID.setValues(var5);
               if (!CUBOID.containsBlock(this.player.getWorld(), var4)) {
                  Direction var6 = Direction.values()[var1.getFace()];
                  double var7;
                  switch(var6) {
                  case EAST:
                     var7 = var5.getX() - (double)var4.getX();
                     if (var7 < 0.3D) {
                        this.handleViolation(() -> {
                           return String.format("%s %.2f", var6.name(), var7);
                        });
                     }
                     break;
                  case SOUTH:
                     var7 = var5.getZ() - (double)var4.getZ();
                     if (var7 < 0.3D) {
                        this.handleViolation(() -> {
                           return String.format("%s %.2f", var6.name(), var7);
                        });
                     }
                     break;
                  case WEST:
                     var7 = var5.getX() - (double)var4.getX();
                     if (var7 > 0.7D) {
                        this.handleViolation(() -> {
                           return String.format("%s %.2f", var6.name(), var7);
                        });
                     }
                     break;
                  case NORTH:
                     var7 = var5.getZ() - (double)var4.getZ();
                     if (var7 > 0.7D) {
                        this.handleViolation(() -> {
                           return String.format("%s %.2f", var6.name(), var7);
                        });
                     }
                     break;
                  case DOWN:
                     var7 = var5.getY() - (double)var4.getY();
                     if (var7 > 2.0D) {
                        this.handleViolation(() -> {
                           return String.format("%s %.2f", var6.name(), var7);
                        });
                     }
                  }
               }
            }
         }
      }

   }
}
