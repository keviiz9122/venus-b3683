package me.levansj01.verus.data.transaction.effects;

import java.util.Collection;
import org.bukkit.potion.PotionEffectType;

public interface IEffectHandler {
   int SLOW_FALLING = 28;
   int DOLPHINS_GRACE = 30;
   int LEVITATION = 25;

   void handle(Effectable var1);

   default int max(PotionEffectType var1) {
      return this.max(var1.getId());
   }

   default Collection<Integer> all(PotionEffectType var1) {
      return this.all(var1.getId());
   }

   int max(int var1);

   Collection<Integer> all(int var1);

   default boolean possible(PotionEffectType var1) {
      return this.possible(var1.getId());
   }

   boolean possible(int var1);
}
