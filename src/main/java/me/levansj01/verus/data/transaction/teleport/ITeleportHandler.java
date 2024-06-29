package me.levansj01.verus.data.transaction.teleport;

import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.util.location.IPacketLocation;

public interface ITeleportHandler {
   boolean isTeleport(IPacketLocation var1);

   boolean isTeleporting();

   void handle(VPacketPlayOutPosition<?> var1);
}
