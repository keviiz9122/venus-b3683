package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;

public abstract class VPacketPlayInBlockDig extends VPacket {
   protected Direction face;
   private static final int count = count();
   protected VPacketPlayInBlockDig.PlayerDigType type;
   protected BlockPosition blockPosition;

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public int ordinal() {
      return count;
   }

   public Direction getFace() {
      return this.face;
   }

   public VPacketPlayInBlockDig.PlayerDigType getType() {
      return this.type;
   }

   public BlockPosition getBlockPosition() {
      return this.blockPosition;
   }

   public static enum PlayerDigType {
      SWAP_HELD_ITEMS,
      ABORT_DESTROY_BLOCK,
      STOP_DESTROY_BLOCK,
      RELEASE_USE_ITEM,
      START_DESTROY_BLOCK,
      DROP_ITEM,
      DROP_ALL_ITEMS;
   }
}
