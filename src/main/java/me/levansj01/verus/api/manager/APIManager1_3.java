package me.levansj01.verus.api.manager;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.TimeUnit;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.check.Check;
import me.levansj01.verus.api.check.InvalidCheckException;
import me.levansj01.verus.data.CheckData;
import me.levansj01.verus.data.PlayerData;
import org.bukkit.entity.Player;

public class APIManager1_3 extends APIManager {

   public APIManager1_3(VerusPlugin var1) {
      super(var1);
   }

   public int getTransactionPing(Player var1) throws DataNotFoundException {
      return this.getData(var1).getTransactionPing();
   }

   public int getKeepAlivePing(Player var1) throws DataNotFoundException {
      return this.getData(var1).getPing();
   }

   public OptionalInt getPunishViolations(Check var1) throws InvalidCheckException {
      int var2 = this.checkManager.getMaxViolation(this.getCheck(var1));
      return var2 > 0 ? OptionalInt.of(var2) : OptionalInt.empty();
   }

   public int getTotalViolations(Player var1) throws DataNotFoundException {
      return (int)Math.floor(this.getData(var1).getTotalViolations());
   }

   public Optional<String> getBrand(Player var1) throws DataNotFoundException {
      return Optional.ofNullable(this.getData(var1).getBrand());
   }

   public boolean isAlerts(Player var1) throws DataNotFoundException {
      return this.getData(var1).isAlerts();
   }

   public boolean isGameFrozen(Player var1, TimeUnit var2, long var3) throws DataNotFoundException {
      PlayerData var5 = this.getData(var1);
      boolean var10000;
      if (var5.getLastKeepAliveTimestamp() - var5.getLastFlying() > var2.toMillis(var3)) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public Optional<Check> getCheck(String var1, String var2) {
      return Optional.ofNullable(this.getCheckOrNull(var1, var2));
   }

   protected PlayerData getData(Player var1) throws DataNotFoundException {
      if (var1 == null) {
         throw new IllegalArgumentException("Player cannot be null");
      } else {
         PlayerData var2 = this.dataManager.getPlayer(var1);
         if (var2 == null) {
            throw new DataNotFoundException(var1.getUniqueId());
         } else {
            return var2;
         }
      }
   }

   public void resetViolations(Player var1, Check var2, boolean var3) throws InvalidCheckException, DataNotFoundException {
      me.levansj01.verus.check.Check var4 = this.getCheck(var1, var2);
      double var10001;
      if (var3) {
         var10001 = var4.getMinViolation();
      } else {
         var10001 = 0.0D;
      }

      var4.setViolations(Math.min(var10001, var4.getViolations()));
   }

   protected me.levansj01.verus.check.Check getCheck(Player var1, Check var2) throws InvalidCheckException, DataNotFoundException {
      me.levansj01.verus.check.Check[] var3 = this.getData(var1).getCheckData().getOriginalChecks();
      return (me.levansj01.verus.check.Check)Arrays.stream(var3).filter((var1x) -> {
         return var1x.getType().getName().equalsIgnoreCase(var2.getType());
      }).filter((var1x) -> {
         return var1x.getSubType().equalsIgnoreCase(var2.getSubType());
      }).findAny().orElseThrow(() -> {
         return new InvalidCheckException(var2);
      });
   }

   public void resetViolations(Player var1, boolean var2) throws DataNotFoundException {
      CheckData var3 = this.getData(var1).getCheckData();
      me.levansj01.verus.check.Check[] var4 = var3.getChecks();
      int var5 = var4.length;
      int var6 = 0;

      do {
         if (var6 >= var5) {
            return;
         }

         me.levansj01.verus.check.Check var7 = var4[var6];
         double var10001;
         if (var2) {
            var10001 = var7.getMinViolation();
         } else {
            var10001 = 0.0D;
         }

         var7.setViolations(Math.min(var10001, var7.getViolations()));
         ++var6;
      } while (true);
   }

   public int getViolations(Player var1, Check var2) throws InvalidCheckException, DataNotFoundException {
      return (int)Math.floor(Math.max(this.getCheck(var1, var2).getViolations(), 0.0D));
   }
}
