package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayOutAttachEntity extends VPacket {
   protected int attachId;
   protected int entityId;
   protected byte leash;
   private static final int count = count();

   public int getAttachId() {
      return this.attachId;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public int ordinal() {
      return count;
   }

   public byte getLeash() {
      return this.leash;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }
}
