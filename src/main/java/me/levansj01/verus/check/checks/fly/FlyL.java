package me.levansj01.verus.check.checks.fly;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.location.PacketLocation;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

@CheckInfo(
   type = CheckType.FLY,
   subType = "L",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   maxViolations = 100,
   minViolations = -1.0D
)
public class FlyL extends MovementCheck2 {

   public void handle(PacketLocation var1, PacketLocation var2, long var3) {
      if (var1.isGround() && var1.getY() % 0.5D * 16.0D % 1.0D != 0.0D && var1.getY() % 0.5D * 64.0D != 1.0D && var1.getY() % 0.5D != 0.0D && var2.getY() % 1.0D != 0.0D && var1.getY() % 1.0D != 0.09375D && Math.abs(var1.getY() % 0.5D - 0.015555072702202466D) > 1.0E-12D && !var2.isTeleport() && (VerusTypeLoader.isEnterprise() || !this.playerData.isTeleporting(2)) && !this.playerData.canFly() && this.playerData.getVelocityTicks() > this.playerData.getPingTicks() && !this.playerData.isVehicle() && this.playerData.getTotalTicks() > 200) {
         World var5 = this.player.getWorld();
         Cuboid var6 = Cuboid.withLimit(var1, var2, 16).add(-0.5D, 0.5D, -0.42D, 1.8D, -0.5D, 0.5D);
         Cuboid var7 = Cuboid.withLimit(var1, var2, 16).add(-0.5D, 0.5D, -2.0D, 0.0D, -0.5D, 0.5D);
         this.run(() -> {
            Iterator var6x = this.player.getNearbyEntities(2.5D, 2.5D, 2.5D).iterator();

            do {
               if (!var6x.hasNext()) {
                  if (var7.checkBlocks(this.player, var5, (var0) -> {
                     boolean var10000;
                     if (!MaterialList.BOUND_UP.contains(var0)) {
                        var10000 = true;
                     } else {
                        var10000 = false;
                     }

                     return var10000;
                  })) {
                     Player var10001 = this.player;
                     Set var10003 = MaterialList.FULLY_PASSABLE;
                     Objects.requireNonNull(var10003);
                     if (var6.checkBlocks(var10001, var5, var10003::contains)) {
                        this.handleViolation(String.format("F: %s T: %s M: %s", var1.getY() % 1.0D, var2.getY() % 1.0D, this.playerData.getNonMoveTicks()));
                     }
                  }

                  return;
               }

               Entity var7x = (Entity)var6x.next();
               if (var7x instanceof Boat || var7x instanceof Minecart) {
                  return;
               }
            } while (true);
         });
      } else {
         this.decreaseVL(0.001D);
      }

   }
}
