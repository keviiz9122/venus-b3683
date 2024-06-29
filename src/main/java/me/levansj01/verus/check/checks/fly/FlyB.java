package me.levansj01.verus.check.checks.fly;

import java.util.Iterator;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.World;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;

@CheckInfo(
   type = CheckType.FLY,
   subType = "B",
   friendlyName = "Fly",
   version = CheckVersion.RELEASE,
   minViolations = -5.0D,
   maxViolations = 25
)
public class FlyB extends MovementCheck {
   private int threshold;
   private int lastBypassTick = -10;

   public void handle(PlayerLocation var1, PlayerLocation var2, long var3) {
      if (var1.getGround() && var2.getGround() && var1.getY() != var2.getY() && !MathUtil.onGround(var1.getY()) && this.playerData.getTotalTicks() > 200 && !MathUtil.onGround(var2.getY()) && this.playerData.isSpawned() && this.playerData.getTotalTicks() - 10 > this.lastBypassTick && !this.playerData.canFly() && !this.playerData.isFallFlying() && !this.playerData.isGliding() && !this.playerData.isLevitating() && !this.playerData.isFlying() && !this.playerData.hadJumpBoost()) {
         World var5 = this.player.getWorld();
         Cuboid var6 = (new Cuboid(this.playerData.getLocation())).expand(0.5D, 0.5D, 0.5D);
         double var7 = var2.getY() - var1.getY();
         int var9 = this.playerData.getTotalTicks();
         this.run(() -> {
            if (!var6.checkBlocks(this.player, var5, (var0) -> {
               boolean var10000;
               if ((NMSManager.getInstance().getServerVersion().before(ServerVersion.v1_11_R1) || var0 != MaterialList.PURPLE_FUCKING_SHULKER) && !MaterialList.INVALID_SHAPE.contains(var0)) {
                  var10000 = true;
               } else {
                  var10000 = false;
               }

               return var10000;
            })) {
               this.threshold = 0;
               this.violations -= Math.min(this.violations + 4.0D, 0.05D);
               this.lastBypassTick = var9;
            } else {
               Iterator var6x = this.player.getNearbyEntities(2.5D, 2.5D, 2.5D).iterator();

               while(true) {
                  if (!var6x.hasNext()) {
                     ++this.threshold;
                     this.handleViolation(String.format("D: %s", var7), (double)this.threshold);
                     break;
                  }

                  Entity var7x = (Entity)var6x.next();
                  if (var7x instanceof Boat || var7x instanceof Minecart || var7x.getType().name().equalsIgnoreCase("SHULKER")) {
                     this.threshold = 0;
                     this.lastBypassTick = var9 - 100;
                     this.violations -= Math.min(this.violations + 4.0D, 0.05D);
                     return;
                  }
               }
            }

         });
      } else {
         this.threshold = 0;
         this.violations -= Math.min(this.violations + 4.0D, 0.05D);
      }

   }
}
