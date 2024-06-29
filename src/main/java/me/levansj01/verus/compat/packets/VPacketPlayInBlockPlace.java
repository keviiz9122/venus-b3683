package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import org.bukkit.inventory.ItemStack;

public abstract class VPacketPlayInBlockPlace extends VPacket {
   protected int hand = 0;
   protected long timestamp;
   protected ItemStack itemStack;
   protected boolean empty;
   private static final int count = count();
   protected int face;
   protected float blockX;
   protected float blockZ;
   protected float blockY;
   protected BlockPosition position;

   public boolean isEmpty() {
      return this.empty;
   }

   public int ordinal() {
      return count;
   }

   public float getBlockZ() {
      return this.blockZ;
   }

   public float getBlockX() {
      return this.blockX;
   }

   public float getBlockY() {
      return this.blockY;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public boolean isItem() {
      return this.itemStack != null && this.isUse();
   }

   public int getHand() {
      return this.hand;
   }

   public BlockPosition getPosition() {
      return this.position;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public boolean isUse() {
      return this.position != null && this.position.getX() == -1 && (this.position.getY() == -1 || this.position.getY() == 255) && this.position.getZ() == -1 && this.blockX == 0.0F && this.blockY == 0.0F && this.blockZ == 0.0F && this.face == 255;
   }

   public int getFace() {
      return this.face;
   }
}
