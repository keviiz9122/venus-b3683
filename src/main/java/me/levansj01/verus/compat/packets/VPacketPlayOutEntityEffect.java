package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.data.transaction.effects.Effect;
import me.levansj01.verus.data.transaction.effects.Effectable;

public abstract class VPacketPlayOutEntityEffect extends VPacket implements Effectable {
   protected byte effect;
   protected byte amplifier;
   protected byte flags;
   protected int id;
   protected int duration;
   private static final int count = count();

   public int getId() {
      return this.id;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public byte getEffect() {
      return this.effect;
   }

   public Effect toEffect() {
      return new Effect(this.duration, this.effect, this.amplifier);
   }

   public byte getAmplifier() {
      return this.amplifier;
   }

   public int getDuration() {
      return this.duration;
   }

   public byte getFlags() {
      return this.flags;
   }

   public int ordinal() {
      return count;
   }
}
