package me.levansj01.verus.util;

import me.levansj01.verus.compat.NMSManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public interface IBlockPosition {
   default Material getType(Player var1, World var2) {
      return NMSManager.getInstance().getTypeWithAPI(var1, var2, this);
   }

   int getY();

   int getZ();

   default boolean sameChunkY(IBlockPosition var1) {
      return this.getY() >> 4 == var1.getY() >> 4;
   }

   /** @deprecated */
   @Deprecated
   default Block getBlock(World var1) {
      return var1.getBlockAt(this.getX(), this.getY(), this.getZ());
   }

   default boolean sameChunkXZ(IBlockPosition var1) {
      return this.getX() >> 4 == var1.getX() >> 4 && this.getZ() >> 4 == var1.getZ() >> 4;
   }

   default boolean isWaterLogged(World var1) {
      return NMSManager.getInstance().isWaterLogged(var1, this);
   }

   default MaterialData getTypeAndData(Player var1, World var2) {
      return NMSManager.getInstance().getTypeAndDataWithAPI(var1, var2, this);
   }

   int getX();

   default float getFrictionFactor(Player var1, World var2) {
      return NMSManager.getInstance().getFrictionFactor(var1, var2, this);
   }
}
