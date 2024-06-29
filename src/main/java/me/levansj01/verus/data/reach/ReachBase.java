package me.levansj01.verus.data.reach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.Transaction;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.Intersection;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.ChatColor;

public class ReachBase {
   private final int lastPing;
   private final PlayerLocation location;
   private final long timestamp;
   private final int ping;
   private final List<PacketLocation> recentMoves;
   private List<IntersectionData> intersecionList;
   private final int connection;
   private final boolean sneaking;
   private List<DistanceData> distanceList;
   private int i = 0;
   private double certainty;
   private DistanceData distanceData;
   private final PlayerLocation lastLocation;
   private static final double EXPAND = 0.03125D;
   private final PlayerData playerData;
   private final Transaction transaction;
   private final int nonMoveTicks;
   private PlayerLocation newLast;

   public int getI() {
      return this.i;
   }

   public double getCertainty() {
      return this.certainty;
   }

   private void raytrace(List<PacketLocation> var1) {
      this.newLast = this.lastLocation.clone();
      this.newLast.setPitch(this.location.getPitch());
      this.newLast.setYaw(this.location.getYaw());
      this.intersecionList = (List)var1.stream().map(this::getReachRayTrace).collect(Collectors.toList());
   }

   public Transaction getTransaction() {
      return this.transaction;
   }

   public int getLastPing() {
      return this.lastPing;
   }

   private IntersectionData getReachRayTrace(PacketLocation var1) {
      return this.playerData.getVersion() != ClientVersion.V1_8 && this.playerData.getVersion() != ClientVersion.VERSION_UNSUPPORTED ? this.getReachRayTrace(var1, 3.0D, this.newLast) : this.getReachRayTrace(var1, 3.0D, this.lastLocation, this.newLast);
   }

   public void execute() {
      short var10002;
      if (this.playerData.getVersion() == ClientVersion.V1_7) {
         var10002 = 160;
      } else {
         var10002 = 210;
      }

      this.raytraceInterpolate(this.calculateMoves(var10002, 60));
      this.calculateLegacy(this.calculateMoves(125, 50));
   }

   public int getConnection() {
      return this.connection;
   }

   private void calculateLegacy(List<PacketLocation> var1) {
      this.distanceList = (List)var1.stream().map((var1x) -> {
         return this.getReach(var1x, this.location, this.lastLocation);
      }).collect(Collectors.toList());
      this.distanceData = (DistanceData)this.distanceList.stream().min(Comparator.comparingDouble(DistanceData::getReach)).get();
   }

   public List<DistanceData> getDistanceList() {
      return this.distanceList;
   }

   public PlayerLocation getLastLocation() {
      return this.lastLocation;
   }

   public int getNonMoveTicks() {
      return this.nonMoveTicks;
   }

   public List<PacketLocation> getRecentMoves() {
      return this.recentMoves;
   }

   public int getPing() {
      return this.ping;
   }

   public void dev() {
   }

   public List<IntersectionData> getIntersecionList() {
      return this.intersecionList;
   }

   public PlayerLocation getNewLast() {
      return this.newLast;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   private String rayTraceDebug(List<PacketLocation> var1, PacketLocation var2) {
      PlayerLocation var3 = this.lastLocation.clone();
      var3.setPitch(this.location.getPitch());
      var3.setYaw(this.location.getYaw());
      IntersectionData var4;
      if (this.playerData.getVersion() != ClientVersion.V1_8 && this.playerData.getVersion() != ClientVersion.VERSION_UNSUPPORTED) {
         var4 = this.getReachRayTrace(var2, 3.0D, var3);
      } else {
         var4 = this.getReachRayTrace(var2, 3.0D, this.lastLocation, var3);
      }

      boolean var5 = var1.contains(var2);
      boolean var10000;
      if (var4.getMinReach() != null) {
         var10000 = true;
      } else {
         var10000 = false;
      }

      boolean var6 = var10000;
      ChatColor var8;
      if (var5) {
         if (var6) {
            var8 = ChatColor.DARK_GREEN;
         } else {
            var8 = ChatColor.DARK_RED;
         }
      } else if (var6) {
         var8 = ChatColor.GREEN;
      } else {
         var8 = ChatColor.RED;
      }

      ChatColor var7 = var8;
      return var7 + "[" + this.i++ + "]";
   }

   public PlayerData getPlayerData() {
      return this.playerData;
   }

   public DistanceData getDistanceData() {
      return this.distanceData;
   }

   public boolean isSneaking() {
      return this.sneaking;
   }

   private List<PacketLocation> calculateMoves(int var1, int var2) {
      ArrayList var3 = new ArrayList();
      int var4 = Math.max(this.lastPing, this.ping);
      int var5 = Math.abs(this.ping - this.lastPing);
      long var6 = this.timestamp - (long)var1 - (long)var4 - (long)this.connection;
      Iterator var8 = this.recentMoves.iterator();
      PacketLocation var9 = (PacketLocation)var8.next();

      while(var8.hasNext()) {
         PacketLocation var10 = (PacketLocation)var8.next();
         long var11 = var10.getTimestamp() - var6;
         if (var11 > 0L) {
            var3.add(var9);
            if (var11 - (long)var5 > (long)(var2 + this.connection)) {
               var9 = var10;
               break;
            }
         } else {
            this.certainty = (double)(-var11);
         }

         var9 = var10;
      }

      if (var3.isEmpty()) {
         var3.add(var9);
      }

      this.certainty /= 50.0D;
      return var3;
   }

   public ReachBase(PlayerData var1, PlayerLocation var2, PlayerLocation var3, long var4, int var6, int var7, int var8, int var9, List<PacketLocation> var10, boolean var11, Transaction var12) {
      this.playerData = var1;
      this.location = var2;
      this.lastLocation = var3;
      this.timestamp = var4;
      this.ping = var6;
      this.lastPing = var7;
      this.connection = var8;
      this.nonMoveTicks = var9;
      this.recentMoves = var10;
      this.sneaking = var11;
      this.transaction = var12;
   }

   public PlayerLocation getLocation() {
      return this.location;
   }

   private DistanceData getReach(PacketLocation var1, PlayerLocation var2, PlayerLocation var3) {
      Cuboid var4 = var1.hitbox().expand(0.03125D, 0.0D, 0.03125D);
      double var5 = MathUtil.positiveSmaller(var2.getX() - var4.cX(), var3.getX() - var4.cX());
      double var7 = MathUtil.positiveSmaller(var2.getZ() - var4.cZ(), var3.getZ() - var4.cZ());
      double var9 = Math.min(var4.distanceXZ(var3), var4.distanceXZ(var2));
      double var11 = MathUtil.getMinimumAngle(var1, var2, var3);
      double var13 = MathUtil.getMinimumAngle(var1, var3);
      double var15 = 0.0D;
      double var17 = 0.0D;

      do {
         if (!(var11 > 90.0D)) {
            var15 += Math.abs(Math.sin(Math.toRadians(var11))) * var9;
            var17 += Math.abs(Math.sin(Math.toRadians(var13))) * var9;
            boolean var10000;
            if (var2.getPitch() > 0.0F) {
               var10000 = true;
            } else {
               var10000 = false;
            }

            boolean var10001;
            if (var3.getPitch() > 0.0F) {
               var10001 = true;
            } else {
               var10001 = false;
            }

            double var25;
            if (var10000 != var10001) {
               var25 = 0.0D;
            } else {
               var25 = MathUtil.lowestAbs(var2.getPitch(), var3.getPitch());
            }

            double var19 = var25;
            double var26;
            if (var9 > 0.8D) {
               var26 = 0.98D;
            } else {
               var26 = 0.95D;
            }

            double var21 = Math.max(Math.abs(Math.tan(Math.toRadians(Math.abs(var19 * var26)))) * var9, Math.abs(Math.sin(Math.toRadians(Math.abs(var19)))) * MathUtil.average(3, var9));
            double var23 = MathUtil.hypot(var9, var21, var15) - 0.001D;
            return new DistanceData(var4, var5, var7, var9, var11, var15, var21, var23, var17);
         }

         var11 -= 90.0D;
         var15 += var9;
         var17 += var9;
      } while (true);
   }

   private void raytraceInterpolate(List<PacketLocation> var1) {
      if (var1.isEmpty()) {
         this.intersecionList = Collections.emptyList();
      } else {
         this.newLast = this.lastLocation.clone();
         this.newLast.setPitch(this.location.getPitch());
         this.newLast.setYaw(this.location.getYaw());
         this.intersecionList = new ArrayList(var1.size() * 3 - 2);
         Iterator var2 = var1.iterator();
         PacketLocation var3 = (PacketLocation)var2.next();
         this.intersecionList.add(this.getReachRayTrace(var3));

         do {
            if (!var2.hasNext()) {
               return;
            }

            PacketLocation var4 = var3;
            var3 = (PacketLocation)var2.next();
            int var5 = 0;

            while(var5 < 3) {
               this.intersecionList.add(this.getReachRayTrace(var3.interpolateFrom(var4, var5 + 1)));
               ++var5;
            }
         } while (true);
      }
   }

   private IntersectionData getReachRayTrace(PacketLocation var1, double var2, PlayerLocation... var4) {
      Cuboid var5 = var1.hitbox();
      Intersection[] var6 = (Intersection[])Arrays.stream(var4).map((var4x) -> {
         return var5.calculateIntercept(var4x.toEyeVector(this.sneaking), var4x.toEyeVector(this.sneaking).add(var4x.getDirection().multiply(var2)));
      }).toArray((var0) -> {
         return new Intersection[var0];
      });
      return new IntersectionData(var1, var5, var6);
   }

   public double getUncertaintyReachValue() {
      double var1 = this.distanceData.getReach();
      double var10000;
      if (var1 > 3.0D) {
         var10000 = 3.0D + (var1 - 3.0D) * this.certainty;
      } else {
         var10000 = var1;
      }

      return var10000;
   }
}
