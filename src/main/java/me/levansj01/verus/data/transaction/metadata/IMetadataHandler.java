package me.levansj01.verus.data.transaction.metadata;

import me.levansj01.verus.compat.packets.VPacketPlayOutEntityMetadata;

public interface IMetadataHandler {
   boolean isElytraFlying();

   void handle(VPacketPlayOutEntityMetadata<?> var1);

   boolean isToggledElytra();

   boolean getFlag(int var1);
}
