package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;

@CheckInfo(
   type = CheckType.AUTO_CLICKER,
   subType = "J",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D
)
public class AutoClickerJ extends Check implements PacketHandler {
   private int ticks = 0;
   private double value = 0.0D;
   private int placeTicks = 0;
   private int abortTicks = 0;
   private int threshold = 0;
   private int lastticks = -1;

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if (this.placeTicks > 1) {
         if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
            this.ticks = 0;
         } else if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
            BlockPosition var2 = var1.getBlockPosition();
            Direction var3 = var1.getFace();
            if (var2.equals(this.playerData.getDiggingBlock()) && !var3.equals(this.playerData.getDiggingBlockFace()) && !this.playerData.isPlacing()) {
               this.decreaseVL(0.001D);
               if (this.ticks == this.lastticks && this.abortTicks < 4) {
                  this.value += 0.3D + (double)this.threshold * 0.2D;
                  if (this.value > 20.0D) {
                     this.handleViolation();
                     this.value = 0.0D;
                  }

                  ++this.threshold;
               } else {
                  this.value -= Math.min(this.value, 1.0D);
                  this.threshold = 0;
               }

               this.lastticks = this.ticks;
            }

            this.abortTicks = 0;
            this.ticks = 0;
         }
      }

   }

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      this.placeTicks = 0;
   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.playerData.isDigging() && !this.playerData.isAbortedDigging()) {
         ++this.ticks;
      }

      ++this.abortTicks;
   }
}
