package me.levansj01.verus.check.checks.payload;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.data.client.ClientType;

@CheckInfo(
   type = CheckType.PAYLOAD,
   subType = "B",
   friendlyName = "Hacked Client",
   version = CheckVersion.RELEASE,
   logData = true,
   maxViolations = 1
)
public class CustomPayloadB extends Check {
   private static final Map<String, String> INVALID_BRANDS = (new Builder()).put("Vanilla", "Jigsaw").put("\u0007Vanilla", "Jigsaw").put("Synergy", "Synergy").put("\u0007Synergy", "Synergy").put("Created By ", "Vape").put("\u0007Created By ", "Vape").build();
   private static final Map<String, ClientType> CLIENT_TYPES;

   static {
      CLIENT_TYPES = (new Builder()).put("vanilla", ClientType.VANILLA).put("fml,forge", ClientType.FORGE).put("LiteLoader", ClientType.LITE_LOADER).put("fabric", ClientType.FABRIC).put("\u0007vanilla", ClientType.VANILLA).put("\u0007fml,forge", ClientType.FORGE).put("\u0007LiteLoader", ClientType.LITE_LOADER).put("\u0006fabric", ClientType.FABRIC).build();
   }

   public void handle(String var1) {
      String var2 = (String)INVALID_BRANDS.get(var1);
      if (var2 != null) {
         this.handleViolation(String.format("T: %s", var2));
         this.handleBan(true);
      } else {
         ClientType var3 = (ClientType)CLIENT_TYPES.get(var1);
         if (var3 != null) {
            this.playerData.getClientData().setType(var3);
         } else {
            this.debug(var1);
         }

         this.playerData.setBrand(var1);
      }
   }
}
