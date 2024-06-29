package me.levansj01.verus.data.reach;

import java.util.Arrays;
import java.util.Objects;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.Intersection;

public class IntersectionData {
   private final Cuboid hitbox;
   private final PacketLocation move;
   private boolean fetched;
   private final Intersection[] intersections;
   private Double minReach;

   public void setMinReach(Double var1) {
      this.minReach = var1;
   }

   public boolean isFetched() {
      return this.fetched;
   }

   public String toString() {
      return "IntersectionData(move=" + this.getMove() + ", hitbox=" + this.getHitbox() + ", intersections=" + Arrays.deepToString(this.getIntersections()) + ", fetched=" + this.isFetched() + ", minReach=" + this.getMinReach() + ")";
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof IntersectionData;
   }

   public Cuboid getHitbox() {
      return this.hitbox;
   }

   public PacketLocation getMove() {
      return this.move;
   }

   public IntersectionData(PacketLocation var1, Cuboid var2, Intersection[] var3) {
      this.move = var1;
      this.hitbox = var2;
      this.intersections = var3;
   }

   public Intersection[] getIntersections() {
      return this.intersections;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof IntersectionData)) {
         return false;
      } else {
         IntersectionData var2 = (IntersectionData)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else if (this.isFetched() != var2.isFetched()) {
            return false;
         } else {
            label61: {
               Double var3 = this.getMinReach();
               Double var4 = var2.getMinReach();
               if (var3 == null) {
                  if (var4 == null) {
                     break label61;
                  }
               } else if (var3.equals(var4)) {
                  break label61;
               }

               return false;
            }

            PacketLocation var5 = this.getMove();
            PacketLocation var6 = var2.getMove();
            if (var5 == null) {
               if (var6 != null) {

                  return false;
               }
            } else if (!var5.equals(var6)) {
               return false;
            }

            label46: {
               Cuboid var7 = this.getHitbox();
               Cuboid var8 = var2.getHitbox();
               if (var7 == null) {
                  if (var8 == null) {
                     break label46;
                  }
               } else if (var7.equals(var8)) {
                  break label46;
               }

               return false;
            }

            if (!Arrays.deepEquals(this.getIntersections(), var2.getIntersections())) {
               return false;
            } else {
               return true;
            }
         }
      }
   }

   public Double getMinReach() {
      if (!this.fetched) {
         Arrays.stream(this.intersections).filter(Objects::nonNull).mapToDouble(Intersection::getDistance).min().ifPresent((var1) -> {
            this.minReach = var1;
         });
         this.fetched = true;
      }

      return this.minReach;
   }

   public void setFetched(boolean var1) {
      this.fetched = var1;
   }

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      int var10000 = var2 * 59;
      byte var10001;
      if (this.isFetched()) {
         var10001 = 79;
      } else {
         var10001 = 97;
      }

      int var6 = var10000 + var10001;
      Double var3 = this.getMinReach();
      var10000 = var6 * 59;
      int var7;
      if (var3 == null) {
         var7 = 43;
      } else {
         var7 = var3.hashCode();
      }

      var6 = var10000 + var7;
      PacketLocation var4 = this.getMove();
      var10000 = var6 * 59;
      if (var4 == null) {
         var7 = 43;
      } else {
         var7 = var4.hashCode();
      }

      var6 = var10000 + var7;
      Cuboid var5 = this.getHitbox();
      var10000 = var6 * 59;
      if (var5 == null) {
         var7 = 43;
      } else {
         var7 = var5.hashCode();
      }

      var6 = var10000 + var7;
      var6 = var6 * 59 + Arrays.deepHashCode(this.getIntersections());
      return var6;
   }
}
