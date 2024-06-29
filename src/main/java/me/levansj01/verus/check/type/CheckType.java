package me.levansj01.verus.check.type;

import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;

public enum CheckType {
   AUTO_CLICKER("AutoClicker", "2"),
   KILL_AURA("KillAura", "6"),
   VELOCITY("Velocity", "12"),
   SCAFFOLD("Scaffold", "14"),
   SPEED("Speed", "10"),
   BAD_PACKETS("BadPackets", "3"),
   FLY("Fly", "4"),
   SERVER_CRASHER("Server Crasher", "15"),
   MANUAL("Manual", "100"),
   PHASE("Phase", "8"),
   AIM_ASSIST("AimAssist", "1"),
   PAYLOAD("Payload", "7"),
   INVENTORY("Inventory", "5"),
   REACH("Reach", "9"),
   TIMER("Timer", "11"),
   MISC("Misc", "13");

   private final String name;
   private static final Map<String, CheckType> nameMap;
   private final String suffix;

   static {
      Builder var0 = new Builder();
      CheckType[] var1 = values();
      int var2 = var1.length;
      int var3 = 0;

      do {
         if (var3 >= var2) {
            nameMap = var0.build();
            return;
         }

         CheckType var4 = var1[var3];
         var0.put(var4.name.toLowerCase(), var4);
         ++var3;
      } while (true);
   }

   public CheckType previous() {
      int var1 = this.ordinal() - 1;
      if (var1 < 0) {
         var1 = 0;
      } else if (var1 == 7) {
         var1 = 6;
      }

      return values()[var1];
   }

   public boolean valid() {
      boolean var10000;
      if (!this.ignore()) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      return var10000;
   }

   public String getName() {
      return this.name;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public boolean ignore() {
      boolean var10000;
      if (!this.equals(PHASE) && !this.equals(MANUAL)) {
         var10000 = false;
      } else {
         var10000 = true;
      }

      return var10000;
   }

   public static CheckType getByString(String var0) {
      return (CheckType)nameMap.get(var0);
   }

   public CheckType next() {
      int var1 = this.ordinal() + 1;
      if (var1 > 14) {
         var1 = 14;
      } else if (var1 == 7) {
         var1 = 8;
      }

      return values()[var1];
   }

   private CheckType(String var3, String var4) {
      this.name = var3;
      this.suffix = var4;
   }
}
