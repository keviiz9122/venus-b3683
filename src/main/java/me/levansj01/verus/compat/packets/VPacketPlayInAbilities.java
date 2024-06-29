package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;

public abstract class VPacketPlayInAbilities extends VPacket {
   protected PlayerAbilities abilities;
   private static final int count = count();

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public PlayerAbilities getAbilities() {
      return this.abilities;
   }

   public int ordinal() {
      return count;
   }
}
