package me.levansj01.verus.check;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.annotation.CheckInfo;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.check.version.CheckVersion;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import org.bukkit.entity.Player;

public class Check {
   private String friendlyName;
   private List<ServerVersion> unsupportedServers = Collections.emptyList();
   private String subType;
   private List<ClientVersion> unsupportedVersions;
   protected Player player;
   protected PlayerData playerData;
   private int maxViolation = Integer.MAX_VALUE;
   private boolean heavy;
   private CheckType type;
   private boolean pullback;
   private int priority = 1;
   private boolean logData;
   private int lastViolation;
   protected double minViolation;
   protected double violations;
   private boolean blocks;
   private CheckVersion checkVersion;
   private boolean schem;

   public boolean isLogData() {
      return this.logData;
   }

   public void setSubType(String var1) {
      this.subType = var1;
   }

   public void setType(CheckType var1) {
      this.type = var1;
   }

   public String getSubType() {
      return this.subType;
   }

   public boolean isBlocks() {
      return this.blocks;
   }

   public List<ServerVersion> getUnsupportedServers() {
      return this.unsupportedServers;
   }

   public void handleViolation(String var1, double var2) {
      this.handleViolation(var1, var2, false);
   }

   public CheckVersion getCheckVersion() {
      return this.checkVersion;
   }

   public double getMinViolation() {
      return this.minViolation;
   }

   public void setUnsupportedServers(ServerVersion... var1) {
      this.unsupportedServers = Arrays.asList(var1);
   }

   public void handleViolation(String var1) {
      this.handleViolation(var1, 1.0D);
   }

   public void handleBan() {
      this.handleBan(false);
   }

   public Player getPlayer() {
      return this.player;
   }

   public double getViolations() {
      return this.violations;
   }

   public void setPriority(int var1) {
      this.priority = var1;
   }

   public void setUnsupportedVersions(List<ClientVersion> var1) {
      this.unsupportedVersions = var1;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Check)) {
         return false;
      } else {
         Check var2 = (Check)var1;
         if (!var2.canEqual(this)) {
            return false;
         } else {
            label40: {
               CheckType var3 = this.getType();
               CheckType var4 = var2.getType();
               if (var3 == null) {
                  if (var4 == null) {
                     break label40;
                  }
               } else if (var3.equals(var4)) {
                  break label40;
               }

               return false;
            }

            String var5 = this.getSubType();
            String var6 = var2.getSubType();
            if (var5 == null) {
               if (var6 != null) {

                  return false;
               }
            } else if (!var5.equals(var6)) {
               return false;
            }

            return true;
         }
      }
   }

   public Check() {
      if (this.getClass().isAnnotationPresent(CheckInfo.class)) {
         CheckInfo var1 = (CheckInfo)this.getClass().getAnnotation(CheckInfo.class);
         this.setType(var1.type());
         this.setSubType(var1.subType());
         this.setFriendlyName(var1.friendlyName());
         if (this.friendlyName.isEmpty()) {
            this.setFriendlyName(this.type.getName());
         }

         this.setCheckVersion(var1.version());
         ClientVersion var2 = var1.unsupportedAtleast();
         ClientVersion[] var3 = var1.unsupportedVersions();
         this.setUnsupportedVersions(Arrays.asList((ClientVersion[])maxOf(var2, ClientVersion.NONE, var3, ClientVersion.class)));
         ServerVersion var4 = var1.unsupportedServerAtleast();
         ServerVersion[] var5 = var1.unsupportedServers();
         this.setUnsupportedServers((ServerVersion[])maxOf(var4, ServerVersion.NONE, var5, ServerVersion.class));
         this.setMinViolation(var1.minViolations());
         this.setPriority(var1.priority());
         this.setHeavy(var1.heavy());
         this.setSchem(var1.schem());
         this.setBlocks(var1.blocks());
         if (!var1.schem() && !var1.butterfly()) {
            this.setMaxViolation(var1.maxViolations());
         } else {
            VerusConfiguration var6 = StorageEngine.getInstance().getVerusConfig();
            if (var1.schem() && var6.isSchemBans()) {
               this.setMaxViolation(var1.maxViolations());
            }

            if (var1.butterfly() && var6.isButterflyBans()) {
               this.setMaxViolation(var1.maxViolations());
            }
         }

         this.setLogData(var1.logData());
      }

   }

   public void setPlayerData(PlayerData var1) {
      this.playerData = var1;
   }

   public void setBlocks(boolean var1) {
      this.blocks = var1;
   }

   public boolean isSchem() {
      return this.schem;
   }

   public void handleViolation() {
      this.handleViolation("");
   }

   public boolean isHeavy() {
      return this.heavy;
   }

   public int hashCode() {
      boolean var1 = true;
      byte var2 = 1;
      CheckType var3 = this.getType();
      int var10000 = var2 * 59;
      int var10001;
      if (var3 == null) {
         var10001 = 43;
      } else {
         var10001 = var3.hashCode();
      }

      int var5 = var10000 + var10001;
      String var4 = this.getSubType();
      var10000 = var5 * 59;
      if (var4 == null) {
         var10001 = 43;
      } else {
         var10001 = var4.hashCode();
      }

      var5 = var10000 + var10001;
      return var5;
   }

   protected boolean canEqual(Object var1) {
      return var1 instanceof Check;
   }

   /** @deprecated */
   @Deprecated
   public boolean valid() {
      boolean var10000;
      if (this.type != null && this.subType != null && this.friendlyName != null && this.checkVersion != null && this.unsupportedVersions != null) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public List<ClientVersion> getUnsupportedVersions() {
      return this.unsupportedVersions;
   }

   private static <T extends Enum<T>> T[] maxOf(T var0, T var1, T[] var2, Class<T> var3) {
      if (var0 == var1) {
         return var2;
      } else {
         Enum[] var4 = (Enum[])var3.getEnumConstants();
         Enum[] var5 = (Enum[])Array.newInstance(var3, var4.length - var0.ordinal() + var2.length);
         System.arraycopy(var4, var0.ordinal(), var5, 0, var5.length - var2.length);
         if (var2.length > 0) {
            System.arraycopy(var2, 0, var5, var5.length - var2.length, var2.length);
         }

         return var5;
      }
   }

   public boolean supported() {
      VerusConfiguration var1 = StorageEngine.getInstance().getVerusConfig();
      boolean var10000;
      if (this.unsupportedVersions.contains(this.playerData.getVersion()) || this.playerData.getVersion() == ClientVersion.VERSION_UNSUPPORTED && !this.unsupportedVersions.isEmpty() || this.heavy && !var1.isHeavyChecks() || this.schem && !var1.isSchem() || this.blocks && !var1.isBlockLog() || var1.isHardDisable() && CheckManager.getInstance().isDisabled(this)) {
         var10000 = false;
      } else {
         var10000 = true;
      }

      return var10000;
   }

   public String name() {
      return this.type.getName() + " " + this.subType;
   }

   public void debug(String var1) {
      this.debug(() -> {
         return var1;
      });
   }

   public String identifier() {
      return this.type.ordinal() + "" + this.subType;
   }

   public void decreaseVL(double var1) {
      this.violations -= Math.min(this.violations - this.minViolation, var1);
   }

   public void setMinViolation(double var1) {
      this.minViolation = this.violations = var1;
   }

   public void setLogData(boolean var1) {
      this.logData = var1;
   }

   public void setCheckVersion(CheckVersion var1) {
      this.checkVersion = var1;
   }

   public void setHeavy(boolean var1) {
      this.heavy = var1;
   }

   public void handleViolation(Supplier<String> var1, double var2) {
      this.handleViolation(var1, var2, false);
   }

   public int getMaxViolation() {
      return this.maxViolation;
   }

   public void setPullback(boolean var1) {
      this.pullback = var1;
   }

   public void setMaxViolation(int var1) {
      this.maxViolation = var1;
   }

   public void setPlayer(Player var1) {
      this.player = var1;
   }

   public void setLastViolation(int var1) {
      this.lastViolation = var1;
   }

   public void setFriendlyName(String var1) {
      this.friendlyName = var1;
   }

   /** @deprecated */
   @Deprecated
   public Check(CheckType var1, String var2, String var3, CheckVersion var4, ClientVersion... var5) {
      this.type = var1;
      this.subType = var2;
      this.friendlyName = var3;
      this.checkVersion = var4;
      this.unsupportedVersions = Arrays.asList(var5);
   }

   public void run(Runnable var1) {
      NMSManager.getInstance().postToMainThread(() -> {
         if (this.playerData.isEnabled()) {
            var1.run();
         }

      });
   }

   public int getPriority() {
      return this.priority;
   }

   public String getFriendlyName() {
      return this.friendlyName;
   }

   public boolean isPullback() {
      return this.pullback;
   }

   public void setViolations(double var1) {
      this.violations = var1;
   }

   public CheckType getType() {
      return this.type;
   }

   public void handleViolation(String var1, double var2, boolean var4) {
      this.handleViolation(() -> {
         return var1;
      }, var2, var4);
   }

   public void debug(Supplier<String> var1) {
      this.playerData.debug(var1);
   }

   public void handleBan(boolean var1) {
      AlertManager.getInstance().handleBan(this.playerData, this, var1);
   }

   public int getLastViolation() {
      return this.lastViolation;
   }

   public void handleViolation(Supplier<String> var1) {
      this.handleViolation(var1, 1.0D);
   }

   public void handleViolation(Supplier<String> var1, double var2, boolean var4) {
      AlertManager.getInstance().handleViolation(this.playerData, this, var1, var2, var4);
   }

   public PlayerData getPlayerData() {
      return this.playerData;
   }

   public void setSchem(boolean var1) {
      this.schem = var1;
   }
}
