package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public abstract class VPacketPlayInClientCommand extends VPacket {
   protected VPacketPlayInClientCommand.ClientCommand command;
   private static final int count = count();

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public VPacketPlayInClientCommand.ClientCommand getCommand() {
      return this.command;
   }

   public int ordinal() {
      return count;
   }

   public static enum ClientCommand {
      PERFORM_RESPAWN,
      OPEN_INVENTORY_ACHIEVEMENT,
      REQUEST_STATS;
   }
}
