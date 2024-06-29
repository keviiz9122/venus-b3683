package me.levansj01.verus.compat.v1_8_R3.packets;

import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

public class SPacketPlayInBlockPlace extends VPacketPlayInBlockPlace {
   public void accept(PacketPlayInBlockPlace var1) {
      this.position = new BlockPosition(var1.a().getX(), var1.a().getY(), var1.a().getZ());
      this.face = var1.getFace();
      ItemStack var2 = var1.getItemStack();
      this.itemStack = CraftItemStack.asBukkitCopy(var2);
      this.blockX = var1.d();
      this.blockY = var1.e();
      this.blockZ = var1.f();
      this.timestamp = var1.timestamp;
   }
}
