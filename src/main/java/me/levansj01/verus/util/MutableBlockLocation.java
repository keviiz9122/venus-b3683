package me.levansj01.verus.util;

import me.levansj01.verus.util.location.Vector3d;
import org.bukkit.block.Block;

public class MutableBlockLocation implements IBlockPosition {
   private int z;
   private int y;
   private int x;

   public void setY(int var1) {
      this.y = var1;
   }

   public void setZ(int var1) {
      this.z = var1;
   }

   public int getX() {
      return this.x;
   }

   public MutableBlockLocation incrementY() {
      return this.add(0, 1, 0);
   }

   public MutableBlockLocation incrementX() {
      return this.add(1, 0, 0);
   }

   public String toString() {
      return "MutableBlockLocation(x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ")";
   }

   public static MutableBlockLocation from(Block var0) {
      return new MutableBlockLocation(var0.getX(), var0.getY(), var0.getZ());
   }

   public MutableBlockLocation andThen(Vector3d var1) {
      this.x = (int)Math.floor(var1.getX());
      this.y = (int)Math.floor(var1.getY());
      this.z = (int)Math.floor(var1.getZ());
      return this;
   }

   public static MutableBlockLocation from(Vector3d var0) {
      return new MutableBlockLocation((int)Math.floor(var0.getX()), (int)Math.floor(var0.getY()), (int)Math.floor(var0.getZ()));
   }

   public MutableBlockLocation add(int var1, int var2, int var3) {
      this.x += var1;
      this.y += var2;
      this.z += var3;
      return this;
   }

   public MutableBlockLocation(int var1, int var2, int var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public MutableBlockLocation incrementZ() {
      return this.add(0, 0, 1);
   }

   public int getY() {
      return this.y;
   }

   public void setX(int var1) {
      this.x = var1;
   }

   public int getZ() {
      return this.z;
   }

   public MutableBlockLocation andThen(int var1, int var2, int var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
      return this;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof MutableBlockLocation)) {
         return false;
      } else {
         MutableBlockLocation var2 = (MutableBlockLocation)var1;
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

   protected boolean canEqual(Object var1) {
      return var1 instanceof MutableBlockLocation;
   }
}
