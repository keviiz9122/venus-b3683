package me.levansj01.verus.api.impl;

import java.lang.reflect.Field;
import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.events.PunishEvent;
import me.levansj01.verus.api.events.VerusShutdownEvent;
import me.levansj01.verus.api.events.VerusStartEvent;
import me.levansj01.verus.api.manager.APIManager1_3;
import me.levansj01.verus.api.manager.VerusManager;
import me.levansj01.verus.api.wrapper.BanResult;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.util.java.SafeReflection;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class API1_3 extends API1_2_1 {
   private static final Field Event_async = SafeReflection.access(Event.class, "async");

   public void enable(VerusPlugin var1) {
      super.enable(var1);
      Bukkit.getPluginManager().callEvent(new VerusStartEvent());
   }

   public void disable() {
      Bukkit.getPluginManager().callEvent(new VerusShutdownEvent());
      super.disable();
   }

   public VerusManager create(VerusPlugin var1) {
      return new APIManager1_3(var1);
   }

   public BanResult fireBanEvent(PlayerData var1, Check var2, boolean var3, List<String> var4) {
      PunishEvent var5 = new PunishEvent(var1.getPlayer(), this.toAPI(var2), var3, var4);
      SafeReflection.set(Event_async, var5, true);
      Bukkit.getPluginManager().callEvent(var5);
      BanResult var10000;
      if (var5.isCancelled()) {
         var10000 = BanResult.CANCEL;
      } else if (var5.isAnnounce()) {
         var10000 = BanResult.ANNOUNCE;
      } else {
         var10000 = BanResult.SILENT;
      }

      return var10000;
   }
}
