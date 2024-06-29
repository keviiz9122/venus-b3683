package me.levansj01.verus.storage.database.check;

import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.type.VerusTypeLoader;
import org.bukkit.configuration.file.YamlConfiguration;

public class FlatCheckManager extends CheckManager {

   public void setAutoban(Check var1, boolean var2) {
      this.setAutoban(var1.identifier(), var2);
   }

   public void loadChecks() {
      YamlConfiguration var1 = StorageEngine.getInstance().getVerusConfig().getCheckConfiguration();
      Check[] var2 = this.checks;
      int var3 = var2.length;
      int var4 = 0;

      do {
         if (var4 >= var3) {
            return;
         }

         Check var5 = var2[var4];
         this.values.compute(var5.identifier(), (var2x, var3x) -> {
            if (var3x == null) {
               var3x = new CheckValues(var5);
            }

            String var4 = var5.getType().getName() + "." + var5.getSubType();
            var3x.setAlert(var1.getBoolean(var4 + ".enabled", true));
            var3x.setPunish(var1.getBoolean(var4 + ".autoban", true));
            if (VerusTypeLoader.isCustom()) {
               var3x.setMaxViolation(var1.getInt(var4 + ".maxViolation", var5.getMaxViolation()));
               var3x.setCommands(var1.getStringList(var4 + ".commands"));
            }

            return var3x;
         });
         ++var4;
      } while (true);
   }

   public void addCommand(String var1, String var2) {
   }

   public void setEnabled(Check var1, boolean var2) {
      this.setEnabled(var1.identifier(), var2);
   }

   public void saveChecks() {
      VerusConfiguration var1 = StorageEngine.getInstance().getVerusConfig();
      YamlConfiguration var2 = var1.getCheckConfiguration();
      Check[] var3 = this.checks;
      int var4 = var3.length;
      int var5 = 0;

      do {
         if (var5 >= var4) {
            var1.saveConfig(var2, var1.getCheckFile());
            return;
         }

         Check var6 = var3[var5];
         String var7 = var6.getType().getName() + "." + var6.getSubType();
         var2.set(var7 + ".enabled", this.isEnabled(var6));
         var2.set(var7 + ".autoban", this.isAutoban(var6));
         if (VerusTypeLoader.isCustom()) {
            var2.set(var7 + ".maxViolation", this.getMaxViolation(var6));
            var2.set(var7 + ".commands", this.getCommands(var6));
         }

         ++var5;
      } while (true);
   }

   public void setEnabled(String var1, boolean var2) {
      super.setEnabled(var1, var2);
      this.saveChecks();
   }

   public void removeCommand(String var1, int var2) {
   }

   public void removeCommand(CheckValues var1, int var2) {
   }

   public void setMaxViolation(String var1, int var2) {
      super.setMaxViolation(var1, var2);
      this.saveChecks();
   }

   public void addCommand(CheckValues var1, String var2) {
   }

   public void setAutoban(String var1, boolean var2) {
      super.setAutoban(var1, var2);
      this.saveChecks();
   }
}
