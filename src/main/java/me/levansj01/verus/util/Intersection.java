package me.levansj01.verus.util;

import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.util.location.Vector3d;

public class Intersection {
   private final Direction direction;
   private Double distance;
   private final Vector3d intercept;
   private final Vector3d displacement;

   public Direction getDirection() {
      return this.direction;
   }

   public void setDistance(Double var1) {
      this.distance = var1;
   }

   public Intersection(Vector3d var1, Vector3d var2, Direction var3) {
      this.intercept = var1;
      this.displacement = var2;
      this.direction = var3;
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof Intersection;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Intersection)) {
         return false;
      } else {
         Intersection var2 = (Intersection)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else if (Double.compare(this.getDistance(), var2.getDistance()) != 0) {
            return false;
         } else {
            label49: {
               Vector3d var3 = this.getIntercept();
               Vector3d var4 = var2.getIntercept();
               if (var3 == null) {
                  if (var4 == null) {
                     break label49;
                  }
               } else if (var3.equals(var4)) {
                  break label49;
               }

               return false;
            }

            Vector3d var5 = this.getDisplacement();
            Vector3d var6 = var2.getDisplacement();
            if (var5 == null) {
               if (var6 != null) {
                  return false;
               }
            } else if (!var5.equals(var6)) {
               return false;
            }

            Direction var7 = this.getDirection();
            Direction var8 = var2.getDirection();
            if (var7 == null) {
               if (var8 != null) {
                  return false;
               }
            } else if (!var7.equals(var8)) {
               return false;
            }

            return true;
         }
      }
   }

   public double getDistance() {
      if (this.distance == null) {
         this.distance = this.displacement.length();
      }

      return this.distance;
   }

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      long var3 = Double.doubleToLongBits(this.getDistance());
      int var8 = var2 * 59 + (int)(var3 >>> 32 ^ var3);
      Vector3d var5 = this.getIntercept();
      var8 = var8 * 59 + (var5 == null ? 43 : var5.hashCode());
      Vector3d var6 = this.getDisplacement();
      var8 = var8 * 59 + (var6 == null ? 43 : var6.hashCode());
      Direction var7 = this.getDirection();
      var8 = var8 * 59 + (var7 == null ? 43 : var7.hashCode());
      return var8;
   }

   public String toString() {
      return "Intersection(intercept=" + this.getIntercept() + ", displacement=" + this.getDisplacement() + ")";
   }

   public Vector3d getDisplacement() {
      return this.displacement;
   }

   public Vector3d getIntercept() {
      return this.intercept;
   }
}
