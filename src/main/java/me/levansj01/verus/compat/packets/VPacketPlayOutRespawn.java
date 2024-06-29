package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import org.bukkit.GameMode;

public abstract class VPacketPlayOutRespawn extends VPacket {
   protected GameMode gameMode;
   private static final int count = count();

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int ordinal() {
      return count;
   }

   public GameMode getGameMode() {
      return this.gameMode;
   }
}
