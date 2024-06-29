package me.levansj01.verus.data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.VehicleCheck;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG1;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG2;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG4;
import me.levansj01.verus.check.checks.badpackets.BadPacketsG8;
import me.levansj01.verus.check.checks.manual.ManualA;
import me.levansj01.verus.check.checks.payload.CustomPayloadB;
import me.levansj01.verus.check.handler.TeleportCheck;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.api.PacketHandler;

public class CheckData {
   private ManualA manualBanCheck;
   private ReachCheck[] reachChecks;
   private MovementCheck2[] movementChecks2;
   private Check[] checks;
   private PingHandler[] pingHandlers;
   private PacketCheck[] packetChecks;
   private MovementCheck[] movementChecks;
   private BadPacketsG2 transactionCheck;
   private Check skipTransactionCheck;
   private BadPacketsG1 keepAliveCheck;
   private CustomPayloadB brandCheck;
   private final Check[] originalChecks;
   private AimCheck[] aimChecks;
   private final Map<String, Check> byIdentifier = new ConcurrentHashMap();
   private BadPacketsG4 pingSpoofCheck;
   private VehicleCheck[] vehicleChecks;
   private PacketHandler[] packetHandlers;
   private PacketHandler[][] packetHandlerByClass;
   private final PlayerData playerData;
   /** @deprecated */
   @Deprecated
   private TeleportCheck[] teleportChecks;

   public PacketHandler[][] getPacketHandlerByClass() {
      return this.packetHandlerByClass;
   }

   public BadPacketsG4 getPingSpoofCheck() {
      return this.pingSpoofCheck;
   }

   public BadPacketsG2 getTransactionCheck() {
      return this.transactionCheck;
   }

   public PlayerData getPlayerData() {
      return this.playerData;
   }

   public Check[] getOriginalChecks() {
      return this.originalChecks;
   }

   public BadPacketsG1 getKeepAliveCheck() {
      return this.keepAliveCheck;
   }

   private void initPacketHandles() {
      int var1 = this.packetHandlers.length;
      int[][] var2 = new int[var1][];
      int var3 = 0;

      while(var3 < var1) {
         var2[var3] = this.packetHandlers[var3].parse();
         ++var3;
      }

      var3 = VPacket.PACKET_COUNT.get();
      PacketHandler[][] var4 = new PacketHandler[var3][];
      List[] var5 = new List[var3];
      int var6 = 0;

      do {
         if (var6 >= var2.length) {
            var6 = 0;

            do {
               if (var6 >= var3) {
                  this.packetHandlerByClass = var4;
                  return;
               }

               List var12 = var5[var6];
               if (var12 == null) {
               } else {
                  var4[var6] = (PacketHandler[])var12.toArray(new PacketHandler[0]);
               }

               ++var6;
            } while (true);
         }

         int[] var7 = var2[var6];
         int var8 = var7.length;
         int var9 = 0;

         while(var9 < var8) {
            int var10 = var7[var9];
            List var11 = var5[var10];
            if (var11 == null) {
               var11 = var5[var10] = new LinkedList();
            }

            var11.add(this.packetHandlers[var6]);
            ++var9;
         }

         ++var6;
      } while (true);
   }

   public AimCheck[] getAimChecks() {
      return this.aimChecks;
   }

   CheckData(PlayerData var1, Check... var2) {
      this.playerData = var1;
      this.originalChecks = var2;
      Check[] var3 = this.originalChecks;
      int var4 = var3.length;
      int var5 = 0;

      do {
         if (var5 >= var4) {
            this.updateChecks();
            return;
         }

         Check var6 = var3[var5];
         var6.setPlayerData(var1);
         var6.setPlayer(var1.getPlayer());
         this.byIdentifier.put(var6.identifier(), var6);
         ++var5;
      } while (true);
   }

   public <T> T getSingle(Class<T> var1) {
      Stream var10000 = Arrays.stream(this.checks);
      Objects.requireNonNull(var1);
      return var10000.filter(var1::isInstance).findFirst().orElse((Object)null);
   }

   public Map<String, Check> getByIdentifier() {
      return this.byIdentifier;
   }

   public Check[] getChecks() {
      return this.checks;
   }

   private <T> List<T> getList(Class<T> var1) {
      Stream var10000 = Arrays.stream(this.checks);
      Objects.requireNonNull(var1);
      return (List)var10000.filter(var1::isInstance).map((var0) -> {
         return var0;
      }).collect(Collectors.toList());
   }

   public Check getSkipTransactionCheck() {
      return this.skipTransactionCheck;
   }

   public CustomPayloadB getBrandCheck() {
      return this.brandCheck;
   }

   public ManualA getManualBanCheck() {
      return this.manualBanCheck;
   }

   public VehicleCheck[] getVehicleChecks() {
      return this.vehicleChecks;
   }

   public PacketHandler[] getHandle(VPacket<?> var1) {
      int var2 = var1.ordinal();
      return var2 >= this.packetHandlerByClass.length ? null : this.packetHandlerByClass[var2];
   }

   /** @deprecated */
   @Deprecated
   public TeleportCheck[] getTeleportChecks() {
      return this.teleportChecks;
   }

   public ReachCheck[] getReachChecks() {
      return this.reachChecks;
   }

   private <T> T getSingle(String var1) {
      return Arrays.stream(this.checks).filter((var1x) -> {
         return var1x.getClass().getSimpleName().equals(var1);
      }).findFirst().orElse((Object)null);
   }

   public PingHandler[] getPingHandlers() {
      return this.pingHandlers;
   }

   public MovementCheck[] getMovementChecks() {
      return this.movementChecks;
   }

   public MovementCheck2[] getMovementChecks2() {
      return this.movementChecks2;
   }

   public void load(Check var1, double var2) {
      var1.setViolations(var2);
      var1.setLastViolation((int)Math.floor(var2));
   }

   public void updateChecks() {
      this.checks = (Check[])Arrays.stream(this.originalChecks).filter(Check::supported).toArray((var0) -> {
         return new Check[var0];
      });
      CheckManager var1 = CheckManager.getInstance();
      Check[] var2 = this.checks;
      int var3 = var2.length;
      int var4 = 0;

      do {
         if (var4 >= var3) {
            this.packetChecks = (PacketCheck[])this.getArray(PacketCheck.class, (var0) -> {
               return new PacketCheck[var0];
            });
            this.packetHandlers = (PacketHandler[])this.getArray(PacketHandler.class, (var0) -> {
               return new PacketHandler[var0];
            });
            this.movementChecks = (MovementCheck[])this.getArray(MovementCheck.class, (var0) -> {
               return new MovementCheck[var0];
            });
            this.movementChecks2 = (MovementCheck2[])this.getArray(MovementCheck2.class, (var0) -> {
               return new MovementCheck2[var0];
            });
            this.vehicleChecks = (VehicleCheck[])this.getArray(VehicleCheck.class, (var0) -> {
               return new VehicleCheck[var0];
            });
            this.teleportChecks = (TeleportCheck[])this.getArray(TeleportCheck.class, (var0) -> {
               return new TeleportCheck[var0];
            });
            this.aimChecks = (AimCheck[])this.getArray(AimCheck.class, (var0) -> {
               return new AimCheck[var0];
            });
            this.reachChecks = (ReachCheck[])this.getArray(ReachCheck.class, (var0) -> {
               return new ReachCheck[var0];
            });
            this.pingHandlers = (PingHandler[])this.getArray(PingHandler.class, (var0) -> {
               return new PingHandler[var0];
            });
            this.keepAliveCheck = (BadPacketsG1)this.getSingle(BadPacketsG1.class);
            this.transactionCheck = (BadPacketsG2)this.getSingle(BadPacketsG2.class);
            this.pingSpoofCheck = (BadPacketsG4)this.getSingle(BadPacketsG4.class);
            this.brandCheck = (CustomPayloadB)this.getSingle(CustomPayloadB.class);
            this.skipTransactionCheck = (Check)this.getSingle(BadPacketsG8.class);
            this.manualBanCheck = (ManualA)this.getSingle(ManualA.class);
            this.initPacketHandles();
            return;
         }

         Check var5 = var2[var4];
         var5.setPlayerData(this.playerData);
         var5.setPlayer(this.playerData.getPlayer());
         var5.setMaxViolation(var1.getMaxViolation(var5));
         ++var4;
      } while (true);
   }

   private <T> T[] getArray(Class<T> var1, IntFunction<T[]> var2) {
      Stream var10000 = Arrays.stream(this.checks);
      Objects.requireNonNull(var1);
      return var10000.filter(var1::isInstance).map((var0) -> {
         return var0;
      }).toArray(var2);
   }

   public Stream<Check> update() {
      return Arrays.stream(this.originalChecks).filter((var0) -> {
         boolean var10000;
         if (var0.getViolations() > var0.getMinViolation()) {
            var10000 = true;
         } else {
            var10000 = false;
         }

         return var10000;
      });
   }

   public PacketHandler[] getPacketHandlers() {
      return this.packetHandlers;
   }

   public PacketCheck[] getPacketChecks() {
      return this.packetChecks;
   }
}
