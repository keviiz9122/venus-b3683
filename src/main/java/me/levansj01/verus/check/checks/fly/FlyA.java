package me.levansj01.verus.check.checks.fly;

import java.util.Iterator;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.location.PacketLocation;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(
   type = CheckType.FLY,
   subType = "A",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -2.5D,
   maxViolations = 30,
   logData = true,
   heavy = true
)
public class FlyA extends Check implements PacketHandler {
   private int lastBypassTick;
   private Cuboid cuboid = null;
   private int threshold;
   private int delay;
   private boolean ground;

   public static boolean isFullyPassable(Material var0) {
      switch(var0) {
      case AIR:
      case WATER:
      case STATIONARY_WATER:
      case LAVA:
      case STATIONARY_LAVA:
         return true;
      default:
         return false;
      }
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      PacketLocation var2 = this.playerData.getCurrentLocation2();
      int var3 = this.playerData.getTotalTicks();
      if (this.ground && var2.isGround() && this.playerData.isSurvival() && var3 > this.lastBypassTick) {
         World var4 = this.player.getWorld();
         boolean var5 = var1.isPos();
         if (this.cuboid == null || this.delay++ > StorageEngine.getInstance().getVerusConfig().getHeavyTicks() && var5 && !this.cuboid.containsBlock(var4, var2)) {
            PlayerLocation var6 = this.playerData.getLastLocation().clone();
            PlayerLocation var7 = this.playerData.getLocation().clone();
            this.cuboid = Cuboid.withLimit(var6, var7, 16).move(0.0D, -0.5D, 0.0D).expand(0.4D, 0.5D, 0.4D);
            this.run(() -> {
               if (this.cuboid == null) {
                  this.cuboid = Cuboid.withLimit(var6, var7, 16).move(0.0D, -0.5D, 0.0D).expand(0.4D, 0.5D, 0.4D);
               }

               if (!this.playerData.isTeleporting(5) && this.cuboid.checkBlocks(this.player, var4, FlyA::isFullyPassable)) {
                  Iterator var6x = this.player.getNearbyEntities(1.0D + this.cuboid.getWidthX(), 1.0D + this.cuboid.getWidthY(), 1.0D + this.cuboid.getWidthZ()).iterator();

                  while(true) {
                     if (!var6x.hasNext()) {
                        this.cuboid = null;
                        if (this.threshold++ > this.playerData.getMaxPingTicks2()) {
                           String var10001 = String.format("%s > %s | l=%s p=%s", this.threshold, this.playerData.getMaxPingTicks(), this.playerData.hasLag(), var5);
                           double var10002;
                           if (!var5 && this.playerData.hasLag()) {
                              var10002 = 0.1D;
                           } else {
                              var10002 = 1.0D;
                           }

                           this.handleViolation(var10001, var10002);
                        }

                        this.delay = StorageEngine.getInstance().getVerusConfig().getHeavyTicks() + 1;
                        break;
                     }

                     Entity var7x = (Entity)var6x.next();
                     if (var7x instanceof Boat || var7x instanceof Minecart) {
                        this.lastBypassTick = var3 + 100;
                        return;
                     }
                  }
               } else {
                  this.threshold = 0;
                  this.decreaseVL(2.5D);
               }

            });
            this.delay = 0;
         }
      } else {
         this.threshold = 0;
         this.delay = StorageEngine.getInstance().getVerusConfig().getHeavyTicks() + 1;
      }

      this.ground = var2.isGround();
   }
}
