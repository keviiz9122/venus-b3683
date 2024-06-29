package me.levansj01.verus.type.premium.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "F",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   maxViolations = 10,
   unsupportedAtleast = ClientVersion.V1_9
)
public class KillAuraF extends Check implements PacketHandler {
   private boolean place;
   private boolean dig;

   public void handle(VPacketPlayInFlying<?> var1) {
      this.dig = this.place = false;
   }

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      this.place = true;
   }

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (!this.place && !this.playerData.isVehicle() && this.dig && var1.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
         this.handleViolation("", 1.0D);
      }

   }

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if (var1.getType() != VPacketPlayInBlockDig.PlayerDigType.DROP_ITEM && var1.getType() != VPacketPlayInBlockDig.PlayerDigType.DROP_ALL_ITEMS) {
         this.dig = true;
      }

   }
}
