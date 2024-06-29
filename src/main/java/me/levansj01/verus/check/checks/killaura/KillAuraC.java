package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "C",
   friendlyName = "Aimbot",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   maxViolations = 5
)
public class KillAuraC extends Check implements PacketHandler {
   private Float yawSpeed = 360.0F;
   private Float lastYaw = null;

   public void handle(VPacketPlayInFlying<?> var1) {
      if (var1.isLook()) {
         if (this.lastYaw != null && !this.playerData.isTeleporting()) {
            float var2 = Math.abs(this.lastYaw - var1.getYaw());
            if (this.yawSpeed < 45.0F) {
               float var3;
               if (var2 > 345.0F && var2 < 375.0F) {
                  var3 = Math.abs(360.0F - (var2 + Math.abs(180.0F - Math.abs(var1.getYaw() % 180.0F - this.lastYaw % 180.0F))));
                  if ((double)Math.abs(var3) == 0.0D) {
                     this.handleViolation(String.format("D: %s", var2), 1.0D, true);
                  }
               } else if ((double)var2 > 172.5D && (double)var2 < 187.5D) {
                  var3 = Math.abs(180.0F - (var2 + Math.abs(90.0F - Math.abs(var1.getYaw() % 90.0F - this.lastYaw % 90.0F))));
                  if ((double)Math.abs(var3) == 0.0D) {
                     this.handleViolation(String.format("D: %s", var2), 0.75D, true);
                  }
               }

               this.decreaseVL(0.005D);
            }

            this.yawSpeed = this.yawSpeed * 3.0F;
            this.yawSpeed = this.yawSpeed + var2;
            this.yawSpeed = this.yawSpeed / 4.0F;
         }

         this.lastYaw = var1.getYaw();
      }

   }
}
