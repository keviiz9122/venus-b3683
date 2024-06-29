package me.levansj01.verus.api.impl;

import me.levansj01.verus.api.events.PlayerInitEvent;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;

public class API1_2_1 extends API1_2 {
   public boolean fireInitEvent(PlayerData var1) {
      PlayerInitEvent var2 = new PlayerInitEvent(var1.getPlayer());
      Bukkit.getPluginManager().callEvent(var2);
      return var2.isCancelled();
   }
}
