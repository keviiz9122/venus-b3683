package me.levansj01.verus.data.transaction.tracker;

import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityLiving;

public interface ITracker {
   void updateVersion();

   void handle(VPacketPlayOutEntityDestroy<?> var1);

   void handle(VPacketPlayOutEntity<?> var1);

   void handle(VPacketPlayOutSpawnEntityLiving<?> var1);

   ITrackedPlayer get(int var1);

   void handle(VPacketPlayOutEntityTeleport<?> var1);

   void handle(VPacketPlayOutNamedEntitySpawn<?> var1);

   void handle(VPacketPlayOutSpawnEntity<?> var1);

   void handle(VPacketPlayOutAttachEntity<?> var1);
}
