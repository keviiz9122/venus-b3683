package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "M",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   unsupportedServerAtleast = ServerVersion.v1_11_R1,
   maxViolations = 10
)
public class KillAuraM extends Check implements PacketHandler {
   private boolean attack;
   private boolean place;

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
         this.attack = true;
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      if (this.place && this.attack) {
         this.handleViolation("", 1.0D, true);
      }

      this.place = this.attack = false;
   }

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      BlockPosition var2 = var1.getPosition();
      if (var2.getX() != -1 && var2.getY() != -1 && var2.getZ() != -1) {
         this.place = true;
      }

   }
}
