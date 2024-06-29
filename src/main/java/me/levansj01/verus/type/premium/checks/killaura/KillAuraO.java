package me.levansj01.verus.type.premium.checks.killaura;

import java.util.Objects;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.KILL_AURA,
   subType = "O",
   friendlyName = "MultiAura",
   version = CheckVersion.RELEASE,
   maxViolations = 5,
   unsupportedAtleast = ClientVersion.V1_9
)
public class KillAuraO extends Check implements PacketHandler {
   private boolean ticked;
   private int entityId;

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction().equals(VPacketPlayInUseEntity.EntityUseAction.ATTACK) && this.playerData.isSurvival() && this.playerData.getTotalTicks() > 200) {
         if (!this.ticked && !Objects.equals(this.entityId, var1.getId())) {
            this.handleViolation();
         } else {
            this.decreaseVL(0.25D);
         }

         this.entityId = var1.getId();
         this.ticked = false;
      }

   }

   public void handle(VPacketPlayInFlying<?> var1) {
      this.ticked = true;
      this.decreaseVL(0.025D);
   }
}
