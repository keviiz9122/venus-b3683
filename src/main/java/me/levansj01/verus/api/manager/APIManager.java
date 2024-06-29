package me.levansj01.verus.api.manager;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.check.Check;
import me.levansj01.verus.api.check.InvalidCheckException;
import me.levansj01.verus.api.log.Ban;
import me.levansj01.verus.api.log.Log;
import me.levansj01.verus.api.log.StorageNotConnectedException;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.util.java.WrappingIterator;

public abstract class APIManager implements VerusManager {
   protected final HashBasedTable<CheckType, String, Check> checkTable;
   protected final CheckManager checkManager = CheckManager.getInstance();
   protected final DataManager dataManager = DataManager.getInstance();
   protected final Map<Check, me.levansj01.verus.check.Check> checkMap;
   protected final List<Check> checks;
   protected final StorageEngine storageEngine = StorageEngine.getInstance();

   private Check getUncheckedCheck(String var1, String var2) {
      Check var3 = this.getCheckOrNull(var1, var2);
      Check var10000;
      if (var3 == null) {
         var10000 = new Check(var1, var2);
      } else {
         var10000 = var3;
      }

      return var10000;
   }

   public boolean isEnabled() {
      return true;
   }

   private Ban toAPI(me.levansj01.verus.storage.database.Ban var1) {
      Check var2 = this.getUncheckedCheck(var1.getType(), var1.getSubType());
      return new Ban(var2, var1.getTimestamp());
   }

   public boolean isValid(Check var1) {
      if (var1.getType() != null && var1.getSubType() != null) {
         CheckType var2 = CheckType.getByString(var1.getType());
         return var2 == null ? false : this.checkMap.containsKey(var1);
      } else {
         return false;
      }
   }

   protected Check getCheckOrNull(String var1, String var2) {
      CheckType var3 = CheckType.getByString(var1);
      return var3 != null ? (Check)this.checkTable.get(var3, var2.toLowerCase()) : null;
   }

   public List<Check> getChecks() {
      return ImmutableList.copyOf(this.checks);
   }

   public boolean setAlert(Check var1, boolean var2) throws InvalidCheckException {
      me.levansj01.verus.check.Check var3 = this.getCheck(var1);
      boolean var4 = this.checkManager.isEnabled(var3);
      if (var4 != var2) {
         this.checkManager.setEnabled(var3, var2);
         return true;
      } else {
         return false;
      }
   }

   public boolean isAlert(Check var1) throws InvalidCheckException {
      return this.checkManager.isEnabled(this.getCheck(var1));
   }

   public boolean isPunish(Check var1) throws InvalidCheckException {
      return this.checkManager.isAutoban(this.getCheck(var1));
   }

   public APIManager(VerusPlugin var1) {
      me.levansj01.verus.check.Check[] var2 = var1.getTypeLoader().loadChecks();
      this.checks = (List)Arrays.stream(var2).map(this::toAPI).collect(Collectors.toList());
      this.checkMap = Maps.newHashMapWithExpectedSize(var2.length);
      this.checkTable = HashBasedTable.create(CheckType.values().length, var2.length / CheckType.values().length);
      me.levansj01.verus.check.Check[] var3 = var2;
      int var4 = var2.length;
      int var5 = 0;

      do {
         if (var5 >= var4) {
            return;
         }

         me.levansj01.verus.check.Check var6 = var3[var5];
         this.checkMap.put(this.toAPI(var6), var6);
         this.checkTable.put(var6.getType(), var6.getSubType().toLowerCase(), this.toAPI(var6));
         ++var5;
      } while (true);
   }

   protected me.levansj01.verus.check.Check getCheck(Check var1) throws InvalidCheckException {
      me.levansj01.verus.check.Check var2 = (me.levansj01.verus.check.Check)this.checkMap.get(var1);
      if (var2 == null) {
         throw new InvalidCheckException(var1);
      } else {
         return var2;
      }
   }

   public boolean isConnected() {
      return this.storageEngine.isConnected();
   }

   private Check toAPI(me.levansj01.verus.check.Check var1) {
      return new Check(var1.getType().getName(), var1.getSubType(), var1.getFriendlyName());
   }

   private Log toAPI(me.levansj01.verus.storage.database.Log var1) {
      Check var2 = this.getUncheckedCheck(var1.getType(), var1.getSubType());
      return new Log(var2, var1.getTimestamp(), var1.getViolations(), var1.getPing(), var1.getLag());
   }

   public boolean setPunish(Check var1, boolean var2) throws InvalidCheckException {
      me.levansj01.verus.check.Check var3 = this.getCheck(var1);
      boolean var4 = this.checkManager.isAutoban(var3);
      if (var4 != var2) {
         this.checkManager.setAutoban(var3, var2);
         return true;
      } else {
         return false;
      }
   }

   public void fetchLogs(UUID var1, int var2, Consumer<Iterable<Log>> var3) throws StorageNotConnectedException {
      if (!this.isConnected()) {
         throw new StorageNotConnectedException();
      } else {
         Database var4 = this.storageEngine.getDatabase();
         var4.getLogs(var1, var2, (var2x) -> {
            var3.accept(() -> {
               return new WrappingIterator(var2x.iterator(), this::toAPI);
            });
         });
      }
   }

   public void fetchBans(UUID var1, int var2, Consumer<Iterable<Ban>> var3) throws StorageNotConnectedException {
      if (!this.isConnected()) {
         throw new StorageNotConnectedException();
      } else {
         Database var4 = this.storageEngine.getDatabase();
         var4.getBans(var1, var2, (var2x) -> {
            var3.accept(() -> {
               return new WrappingIterator(var2x.iterator(), this::toAPI);
            });
         });
      }
   }
}
