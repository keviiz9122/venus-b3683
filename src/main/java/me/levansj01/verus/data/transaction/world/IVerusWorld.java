package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayOutBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunkBulk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutUnloadChunk;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.util.IBlockPosition;
import org.bukkit.util.NumberConversions;

public interface IVerusWorld extends Releasable {
   void handle(VPacketPlayInBlockDig<?> var1);

   void handle(VPacketPlayOutMapChunkBulk<?> var1);

   void handle(VPacketPlayOutMultiBlockChange<?> var1);

   void handle(VPacketPlayOutUnloadChunk<?> var1);

   IVerusChunk getChunk(int var1, int var2);

   void handle(VPacketPlayInBlockPlace<?> var1);

   void handle(VPacketPlayOutMapChunk<?> var1);

   void handle(VPacketPlayOutBlockChange<?> var1);

   void handle(VPacketPlayOutRespawn<?> var1);

   boolean isLoaded(int var1, int var2);

   boolean isChangingDimension();

   IVerusBlock get(int var1, int var2, int var3);

   default IVerusBlock get(IBlockPosition var1) {
      return this.get(var1.getX(), var1.getY(), var1.getZ());
   }

   default boolean isLoaded(double var1, double var3) {
      return this.isLoaded(NumberConversions.floor(var1), NumberConversions.floor(var3));
   }

   default IVerusChunk getChunk(IBlockPosition var1) {
      return this.getChunk(var1.getX() >> 4, var1.getZ() >> 4);
   }
}
