package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.MathUtil;

@CheckInfo(
   type = CheckType.AUTO_CLICKER,
   subType = "E",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   minViolations = -1.0D,
   logData = true
)
public class AutoClickerE extends Check implements PacketHandler {
   private int ticks = 0;
   private final BasicDeque<Integer> tickList = new CappedQueue(41);

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.playerData.isDigging() && !this.playerData.isAbortedDigging()) {
         ++this.ticks;
      }

   }

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
         this.ticks = 0;
      } else if (var1.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
         BlockPosition var2 = var1.getBlockPosition();
         Direction var3 = var1.getFace();
         if (var2.equals(this.playerData.getDiggingBlock()) && !var3.equals(this.playerData.getDiggingBlockFace())) {
            this.tickList.addFirst(this.ticks);
            if (this.tickList.size() > 40) {
               double var4 = MathUtil.deviation(this.tickList);
               if (var4 < 0.325D) {
                  this.handleViolation(String.format("D: %s", var4), 1.325D - var4, true);
               } else {
                  this.decreaseVL(0.25D);
               }

               this.tickList.clear();
            }
         }

         this.ticks = 0;
      }

   }
}
