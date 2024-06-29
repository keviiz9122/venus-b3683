package me.levansj01.verus.type.premium.checks.reach;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.data.version.ClientVersion;

@CheckInfo(
   type = CheckType.REACH,
   subType = "B",
   friendlyName = "Reach",
   version = CheckVersion.RELEASE,
   unsupportedAtleast = ClientVersion.V1_9,
   maxViolations = 4
)
public class ReachB extends Check implements PacketHandler {
   public static final double MAX_X = 0.4004004004004004D;
   public static final double MAX_YL = -0.1001001001001001D;
   public static final double V = 0.999D;
   public static final double MAX_YH = 1.901901901901902D;

   public void handle(VPacketPlayInUseEntity<?> var1) {
      if (var1.getAction() == VPacketPlayInUseEntity.EntityUseAction.INTERACT_AT) {
         double var2 = var1.getBodyX();
         double var4 = var1.getBodyY();
         double var6 = var1.getBodyZ();
         if ((Math.abs(var2) > 0.4004004004004004D || Math.abs(var6) > 0.4004004004004004D || var4 > 1.901901901901902D || var4 < -0.1001001001001001D) && var1.isPlayer() && (this.playerData.getTracker() == null || this.playerData.getTracker().get(var1.getId()) != null)) {
            this.handleViolation(String.format("X: %s Y: %s Z: %s", var2, var4, var6), 1.0D, true);
         }
      }

   }
}
