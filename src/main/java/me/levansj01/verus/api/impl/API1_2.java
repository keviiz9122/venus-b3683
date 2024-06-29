package me.levansj01.verus.api.impl;

import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.api.VerusAPI;
import me.levansj01.verus.api.events.BanEvent;
import me.levansj01.verus.api.events.ViolationEvent;
import me.levansj01.verus.api.manager.APIManager1_2;
import me.levansj01.verus.api.manager.VerusManager;
import me.levansj01.verus.api.manager.impl.BlankManager;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;

public class API1_2 extends API {
   public VerusManager create(VerusPlugin var1) {
      return new APIManager1_2(var1);
   }

   public void enable(VerusPlugin var1) {
      VerusAPI var2 = (VerusAPI)fetchPlugin();
      var2.setVerusManager(this.create(var1));
   }

   public boolean fireBanEvent(PlayerData var1, Check var2) {
      BanEvent var3 = new BanEvent(var1.getPlayer(), this.toAPI(var2));
      Bukkit.getPluginManager().callEvent(var3);
      return var3.isCancelled();
   }

   public void disable() {
      VerusAPI var1 = (VerusAPI)fetchPlugin();
      var1.setVerusManager(new BlankManager());
   }

   public boolean fireViolationEvent(PlayerData var1, Check var2, int var3) {
      ViolationEvent var4 = new ViolationEvent(var1.getPlayer(), this.toAPI(var2), var3);
      Bukkit.getPluginManager().callEvent(var4);
      return var4.isCancelled();
   }

   protected me.levansj01.verus.api.check.Check toAPI(Check var1) {
      return new me.levansj01.verus.api.check.Check(var1.getType().getName(), var1.getSubType(), var1.getFriendlyName());
   }
}
