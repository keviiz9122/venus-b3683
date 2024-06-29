package me.levansj01.verus.type.premium.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "K",
   friendlyName = "Auto Clicker",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9
)
public class KillAuraK extends Check implements PacketHandler {
   private boolean attack;

   public void handle(VPacketPlayInBlockDig<?> var1) {
      if ((var1.getType() == VPacketPlayInBlockDig.PlayerDigType.DROP_ITEM || var1.getType() == VPacketPlayInBlockDig.PlayerDigType.DROP_ALL_ITEMS) && this.attack) {
         this.handleViolation("", 1.0D, true);
      }

   }

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
         this.attack = true;
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.attack = false;
   }
}
