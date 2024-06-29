package me.levansj01.verus.compat.api.wrapper;

import me.levansj01.verus.util.IBlockPosition;
import me.levansj01.verus.util.MutableBlockLocation;

public class BlockPosition implements IBlockPosition {
   private int y;
   private int z;
   private int x;

   public int getY() {
      return this.y;
   }

   public MutableBlockLocation toMutableBlock() {
      return new MutableBlockLocation(this.x, this.y, this.z);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof BlockPosition)) {
         return false;
      } else {
         BlockPosition var2 = (BlockPosition)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else if (this.getX() != var2.getX()) {
            return false;
         } else if (this.getY() != var2.getY()) {
            return false;
         } else {
            return this.getZ() == var2.getZ();
         }
      }
   }

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      int var3 = var2 * 59 + this.getX();
      var3 = var3 * 59 + this.getY();
      var3 = var3 * 59 + this.getZ();
      return var3;
   }

   public BlockPosition(int var1, int var2, int var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public BlockPosition move(Direction var1, int var2) {
      switch(var1) {
      case UP:
         this.y += var2;
         break;
      case DOWN:
         this.y -= var2;
         break;
      case SOUTH:
         this.z += var2;
         break;
      case NORTH:
         this.z -= var2;
         break;
      case EAST:
         this.x += var2;
         break;
      case WEST:
         this.x -= var2;
      }

      return this;
   }

   public void face(int var1) {
      switch(var1) {
      case 0:
         --this.y;
         break;
      case 1:
         ++this.y;
         break;
      case 2:
         --this.z;
         break;
      case 3:
         ++this.z;
         break;
      case 4:
         --this.x;
         break;
      case 5:
         ++this.x;
      }

   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof BlockPosition;
   }

   public int diff(BlockPosition var1) {
      return Math.abs(this.x - var1.x) + Math.abs(this.z - var1.z);
   }

   public void setZ(int var1) {
      this.z = var1;
   }

   public BlockPosition copy() {
      return new BlockPosition(this.x, this.y, this.z);
   }

   public String toString() {
      return "BlockPosition(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
   }

   public int getX() {
      return this.x;
   }

   public void setY(int var1) {
      this.y = var1;
   }

   public void setX(int var1) {
      this.x = var1;
   }

   public int getZ() {
      return this.z;
   }

   public boolean nearby(BlockPosition var1) {
      return this.diff(var1) == 1;
   }
}
