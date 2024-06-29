package me.levansj01.verus.storage.database.check;

import java.util.Iterator;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;

public class DBCheckManager extends CheckManager {

   public void setEnabled(Check var1, boolean var2) {
      this.setEnabled(var1.identifier(), var2);
   }

   public void loadChecks() {
      StorageEngine var1 = StorageEngine.getInstance();
      Database var2 = var1.getDatabase();
      var2.loadCheckValues(this.checks, (var1x) -> {
         Iterator var2 = var1x.iterator();

         do {
            if (!var2.hasNext()) {
               return;
            }

            CheckValues var3 = (CheckValues)var2.next();
            this.values.put(var3.getCheckId(), var3);
         } while (true);
      });
   }

   public void setMaxViolation(String var1, int var2) {
      super.setMaxViolation(var1, var2);
      StorageEngine.getInstance().getDatabase().updateCheckViolation(var1, var2);
   }

   public void removeCommand(CheckValues var1, int var2) {
   }

   public void setAutoban(String var1, boolean var2) {
      super.setAutoban(var1, var2);
      StorageEngine.getInstance().getDatabase().updateCheckPunish(var1, var2);
   }

   private void updateCommands(CheckValues var1) {
   }

   public void addCommand(CheckValues var1, String var2) {
   }

   /** @deprecated */
   @Deprecated
   public void saveChecks() {
   }

   public void setEnabled(String var1, boolean var2) {
      super.setEnabled(var1, var2);
      StorageEngine.getInstance().getDatabase().updateCheckAlerts(var1, var2);
   }

   public void setAutoban(Check var1, boolean var2) {
      this.setAutoban(var1.identifier(), var2);
   }
}
