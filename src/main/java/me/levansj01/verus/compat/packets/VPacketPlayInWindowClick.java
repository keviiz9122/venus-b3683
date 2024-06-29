package me.levansj01.verus.compat.packets;

import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;
import org.bukkit.inventory.ItemStack;

public abstract class VPacketPlayInWindowClick extends VPacket {
   protected ItemStack itemStack;
   protected Integer button;
   private static final int count = count();
   protected Integer mode;
   protected Integer slot;
   protected Integer window;

   public Integer getButton() {
      return this.button;
   }

   public abstract boolean isItem();

   public Integer getWindow() {
      return this.window;
   }

   public abstract boolean isShiftClick();

   public int ordinal() {
      return count;
   }

   public abstract boolean isChest();

   public Integer getSlot() {
      return this.slot;
   }

   public Integer getMode() {
      return this.mode;
   }

   public void handle(PacketHandler var1) {
      var1.handle(this);
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }
}
