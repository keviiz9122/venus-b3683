package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.bytes.BasicByteData;
import me.levansj01.verus.data.bytes.ByteData;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.SectionData;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;

public abstract class VPacketPlayOutMapChunk<P> extends VPacket<P> {
   protected int chunkX;
   protected int chunkZ;
   protected State<SectionData[]> chunkMap;
   protected boolean groundUp;
   protected boolean unload;
   private static final int count = count();

   public static ByteData wrap(byte[] data) {
      VerusConfiguration config = StorageEngine.getInstance().getVerusConfig();
      return (ByteData)(config.isDirectMemory() ? NMSManager.getInstance().getNettyHandler().wrap(data, 0, data.length) : new BasicByteData(data));
   }

   public void handle(PacketHandler listener) {
      listener.handle(this);
   }

   public int ordinal() {
      return count;
   }

   public int getChunkX() {
      return this.chunkX;
   }

   public int getChunkZ() {
      return this.chunkZ;
   }

   public State<SectionData[]> getChunkMap() {
      return this.chunkMap;
   }

   public boolean isGroundUp() {
      return this.groundUp;
   }

   public boolean isUnload() {
      return this.unload;
   }
}
