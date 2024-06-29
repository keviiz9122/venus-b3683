package me.levansj01.verus.type.premium.checks.autoclicker;

import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;

@CheckInfo(
   type = CheckType.AUTO_CLICKER,
   subType = "C",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   minViolations = -5.0D,
   maxViolations = 30,
   logData = true,
   butterfly = true
)
public class AutoClickerC extends PacketCheck {
   private int normal = 0;
   private int flags = 0;
   private int stage = 0;

   public void handle(VPacket var1, long var2) {
      if (this.stage == 0) {
         if (var1 instanceof VPacketPlayInArmAnimation) {
            this.stage = 1;
         }
      } else if (this.stage == 1) {
         if (var1 instanceof VPacketPlayInBlockDig) {
            this.stage = 2;
         } else {
            this.stage = 0;
         }
      } else if (this.stage == 2) {
         if (var1 instanceof VPacketPlayInBlockDig && ((VPacketPlayInBlockDig)var1).getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
            ++this.flags;
            this.run();
            this.stage = 0;
         } else if (var1 instanceof VPacketPlayInArmAnimation) {
            this.stage = 3;
         } else {
            this.stage = this.flags = this.normal = 0;
         }
      } else if (this.stage == 3) {
         if (var1 instanceof VPacketPlayInFlying) {
            this.stage = 4;
         } else {
            this.stage = this.flags = this.normal = 0;
         }
      } else if (this.stage == 4) {
         if (var1 instanceof VPacketPlayInBlockDig && ((VPacketPlayInBlockDig)var1).getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
            ++this.normal;
            this.run();
            this.stage = 0;
         } else {
            this.stage = this.flags = this.normal = 0;
         }
      }

   }

   public void run() {
      int var1 = this.normal - this.flags;
      if (var1 != 1 && this.flags > 0 || this.flags > 5) {
         if (var1 <= 2 && var1 >= -2) {
            this.decreaseVL(0.5D);
         } else {
            TickerMap var2 = this.playerData.getTickerMap();
            int var3 = var2.get(TickerType.DOUBLE_SWING) - var2.get(TickerType.LEGIT_SWING);
            if (var3 > 20) {
               this.handleViolation(String.format("D: %s", var1), 0.75D, true);
            }
         }
      }

   }
}
