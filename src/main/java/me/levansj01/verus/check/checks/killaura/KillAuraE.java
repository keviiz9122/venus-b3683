package me.levansj01.verus.check.checks.killaura;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "E",
   friendlyName = "KillAura",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9,
   maxViolations = 10
)
public class KillAuraE extends Check implements PacketHandler {
   private boolean place = false;

   public void handle(VPacketPlayInFlying<?> var1) {
      this.place = false;
   }

   public void handle(VPacketPlayInBlockPlace<?> var1) {
      this.place = true;
   }

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (this.place && var1.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
         this.handleViolation("", 1.0D, true);
      }

   }
}
