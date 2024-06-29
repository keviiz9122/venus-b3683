package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class SPacketPlayInWindowClick extends VPacketPlayInWindowClick {
   public boolean isItem() {
      return this.itemStack != null;
   }

   public boolean isChest() {
      return this.window != 0;
   }

   public boolean isShiftClick() {
      return this.isChest() && this.mode == 1 && this.button < 2;
   }

   public void accept(PacketPlayInWindowClick var1) {
      this.slot = var1.b();
      this.window = var1.a();
      this.button = var1.c();
      this.mode = var1.f();
      this.itemStack = CraftItemStack.asBukkitCopy(var1.e());
   }
}
