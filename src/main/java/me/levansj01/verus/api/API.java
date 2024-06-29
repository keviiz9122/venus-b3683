package me.levansj01.verus.api;

import java.util.List;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.wrapper.BanResult;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

public abstract class API {
   private static API api = null;
   private static Boolean enabled = null;
   private String version = "Unknown";

   public static Plugin fetchPlugin() {
      return Bukkit.getPluginManager().getPlugin("VerusAPI");
   }

   public boolean fireInitEvent(PlayerData var1) {
      return false;
   }

   public void disable() {
   }

   public BanResult fireBanEvent(PlayerData var1, Check var2, boolean var3, List<String> var4) {
      BanResult var10000;
      if (this.fireBanEvent(var1, var2)) {
         var10000 = BanResult.CANCEL;
      } else if (var3) {
         var10000 = BanResult.ANNOUNCE;
      } else {
         var10000 = BanResult.SILENT;
      }

      return var10000;
   }

   public static API getAPI() {
      if (!isEnabled()) {
         return null;
      } else {
         if (api == null) {
            Plugin var0 = fetchPlugin();
            String var1 = var0.getDescription().getVersion();

            try {
               api = (API)Class.forName("me.levansj01.verus.api.impl.API" + var1.replace(".", "_")).asSubclass(API.class).newInstance();
               api.setVersion(var1);
            } catch (Throwable var3) {
               var3.printStackTrace();
               enabled = false;
               return api;
            }
         }

         return api;
      }
   }

   public static void check() {
      enabled = null;
   }

   public void enable(VerusPlugin var1) {
   }

   public abstract boolean fireViolationEvent(PlayerData var1, Check var2, int var3);

   public MaterialData getFakeBlock(Player var1, World var2, int var3, int var4, int var5) {
      return null;
   }

   public String getVersion() {
      return this.version;
   }

   /** @deprecated */
   @Deprecated
   protected boolean fireBanEvent(PlayerData var1, Check var2) {
      return false;
   }

   protected static boolean isEnabled() {
      if (enabled == null) {
         enabled = Bukkit.getPluginManager().isPluginEnabled("VerusAPI");
      }

      return enabled;
   }

   public void setVersion(String var1) {
      this.version = var1;
   }
}
