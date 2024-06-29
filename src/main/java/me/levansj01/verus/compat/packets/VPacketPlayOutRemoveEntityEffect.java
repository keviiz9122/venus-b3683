package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.effects.Effectable;
import me.levansj01.verus.data.transaction.effects.RemovedEffect;

public abstract class VPacketPlayOutRemoveEntityEffect extends VPacket implements Effectable {
   protected int id;
   protected int effect;
   private static final int count = count();

   public int ordinal() {
      return count;
   }

   public RemovedEffect toEffect() {
      return new RemovedEffect(this.effect);
   }

   public int getId() {
      return this.id;
   }

   public int getEffect() {
      return this.effect;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }
}
