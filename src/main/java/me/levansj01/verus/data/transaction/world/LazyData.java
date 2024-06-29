package me.levansj01.verus.data.transaction.world;

import me.levansj01.verus.data.state.State;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class LazyData {
   private final State<Integer> value;
   private final State<MaterialData> data;
   public static final LazyData AIR;
   public static final State<LazyData> STATE_AIR;
   private final State<Material> type;
   private final State<Float> frictionFactor;

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      State var3 = this.getValue();
      int var10000 = var2 * 59;
      int var10001;
      if (var3 == null) {
         var10001 = 43;
      } else {
         var10001 = var3.hashCode();
      }

      int var7 = var10000 + var10001;
      State var4 = this.getData();
      var10000 = var7 * 59;
      if (var4 == null) {
         var10001 = 43;
      } else {
         var10001 = var4.hashCode();
      }

      var7 = var10000 + var10001;
      State var5 = this.getType();
      var10000 = var7 * 59;
      if (var5 == null) {
         var10001 = 43;
      } else {
         var10001 = var5.hashCode();
      }

      var7 = var10000 + var10001;
      State var6 = this.getFrictionFactor();
      var10000 = var7 * 59;
      if (var6 == null) {
         var10001 = 43;
      } else {
         var10001 = var6.hashCode();
      }

      var7 = var10000 + var10001;
      return var7;
   }

   public LazyData(State<Integer> var1, State<MaterialData> var2, State<Material> var3, State<Float> var4) {
      this.value = var1;
      this.data = var2;
      this.type = var3;
      this.frictionFactor = var4;
   }

   public State<Material> getType() {
      return this.type;
   }

   public String toString() {
      return "LazyData(value=" + this.getValue() + ", data=" + this.getData() + ", type=" + this.getType() + ", frictionFactor=" + this.getFrictionFactor() + ")";
   }

   public State<Float> getFrictionFactor() {
      return this.frictionFactor;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof LazyData)) {
         return false;
      } else {
         LazyData var2 = (LazyData)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else {
            State var3 = this.getValue();
            State var4 = var2.getValue();
            if (var3 == null) {
               if (var4 != null) {

                  return false;
               }
            } else if (!var3.equals(var4)) {
               return false;
            }

            State var5 = this.getData();
            State var6 = var2.getData();
            if (var5 == null) {
               if (var6 != null) {

                  return false;
               }
            } else if (!var5.equals(var6)) {
               return false;
            }

            State var7 = this.getType();
            State var8 = var2.getType();
            if (var7 == null) {
               if (var8 != null) {

                  return false;
               }
            } else if (!var7.equals(var8)) {
               return false;
            }

            State var9 = this.getFrictionFactor();
            State var10 = var2.getFrictionFactor();
            if (var9 == null) {
               if (var10 != null) {

                  return false;
               }
            } else if (!var9.equals(var10)) {
               return false;
            }

            return true;
         }
      }
   }

   public State<MaterialData> getData() {
      return this.data;
   }

   public State<Integer> getValue() {
      return this.value;
   }

   static {
      AIR = new LazyData(State.of(0), State.of(MaterialList.AIR_DATA), State.of(MaterialList.AIR), State.of(0.6F));
      STATE_AIR = State.of(AIR);
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof LazyData;
   }
}
