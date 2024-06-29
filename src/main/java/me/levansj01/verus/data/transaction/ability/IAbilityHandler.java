package me.levansj01.verus.data.transaction.ability;

import java.util.Deque;
import java.util.function.Predicate;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.util.java.JavaV;
import org.bukkit.GameMode;

public interface IAbilityHandler {
   default boolean all(Predicate<PlayerAbilities> var1) {
      boolean var10000;
      PlayerAbilities var2;
      Deque var3;
      if ((var2 = this.getAbilities()) != null && var1.test(var2) && ((var3 = this.getAbilitiesQueue()).isEmpty() || JavaV.allMatch(var1, var3))) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   void handle(VPacketPlayOutRespawn<?> var1);

   default boolean any(Predicate<PlayerAbilities> var1) {
      boolean var10000;
      PlayerAbilities var2;
      Deque var3;
      if ((var2 = this.getAbilities()) == null || var1.test(var2) || !(var3 = this.getAbilitiesQueue()).isEmpty() && JavaV.anyMatch(var1, (Iterable)var3)) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   boolean isSurvivalOrAdventure();

   PlayerAbilities getAbilities();

   boolean canFly();

   boolean isSurvival();

   void handle(VPacketPlayOutAbilities<?> var1);

   void handle(VPacketPlayOutGameStateChange<?> var1);

   Deque<PlayerAbilities> getAbilitiesQueue();

   boolean possible(GameMode var1);
}
