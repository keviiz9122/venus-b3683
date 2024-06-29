package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(
   type = CheckType.AUTO_CLICKER,
   subType = "B",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9,
   minViolations = -5.0D,
   maxViolations = 20,
   logData = true,
   butterfly = true
)
public class AutoClickerB extends Check implements PacketHandler {
   private boolean swing;
   private boolean first;
   private int stage;
   private int ticks;

   public void handle(VPacketPlayInArmAnimation<?> var1) {
      if (this.stage == 1) {
         this.stage = 2;
      } else if (this.stage == 3 || this.stage == 0) {
         this.swing = true;
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.stage == 2) {
         this.stage = 3;
      }

      this.swing = false;
      ++this.ticks;
   }

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      this.ticks = 0;
   }

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
         if (this.stage == 0 && this.ticks > 1) {
            this.stage = 1;
            this.first = this.swing;
         } else {
            this.stage = 0;
         }
      } else if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
         if (this.stage == 3) {
            if (this.swing) {
               TickerMap var2 = this.playerData.getTickerMap();
               int var3 = var2.get(TickerType.DOUBLE_SWING) - var2.get(TickerType.LEGIT_SWING);
               if (this.ticks > 1 && var3 > 20) {
                  this.handleViolation(String.valueOf(var3), 1.0D, true);
               }
            } else if (this.first) {
               this.decreaseVL(0.3D);
            }
         }

         this.stage = 0;
      }

   }
}
