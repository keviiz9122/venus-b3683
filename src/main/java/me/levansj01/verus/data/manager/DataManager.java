package me.levansj01.verus.data.manager;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.client.ClientType;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.util.java.JavaV;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.floodgate.api.FloodgateApi;

public class DataManager {
   private final Map<Integer, WeakReference<PlayerData>> playersById = new ConcurrentHashMap();
   private final Map<UUID, PlayerData> players = new ConcurrentHashMap();
   private static DataManager instance;
   private final ExecutorService executorService = NMSManager.getInstance().getDataExecutorService("Data Manager Thread %s");
   private final List<PlayerData> playerList = new CopyOnWriteArrayList();
   public final VerusPlugin plugin;

   public static DataManager getInstance() {
      return instance;
   }

   public List<PlayerData> getPlayerList() {
      return this.playerList;
   }

   public static void enable(VerusPlugin var0) {
      instance = new DataManager(var0);
      NMSManager.getInstance().postToMainThread(() -> {
         Iterator var0 = Bukkit.getOnlinePlayers().iterator();

         do {
            if (!var0.hasNext()) {
               return;
            }

            Player var1 = (Player)var0.next();
            NMSManager.getInstance().setAsyncPotionEffects(var1);
         } while (true);
      });
      instance.getExecutorService().execute(() -> {
         Iterator var0 = Bukkit.getOnlinePlayers().iterator();

         while(var0.hasNext()) {
            Player var1 = (Player)var0.next();

            label23: {
               try {
                  instance.inject(var1);
               } catch (Throwable var3) {
                  var3.printStackTrace();
                  break label23;
               }
            }
         }

      });
   }

   public static void disable() {
      if (instance != null) {
         JavaV.shutdownAndAwaitTermination(instance.executorService, 5L, TimeUnit.SECONDS);
         Thread var0 = new Thread(() -> {
            Collection var10000 = Bukkit.getOnlinePlayers();
            DataManager var10001 = instance;
            Objects.requireNonNull(var10001);
            var10000.forEach(var10001::uninject);
         });
         var0.start();

         label26: {
            try {
               var0.join(TimeUnit.SECONDS.toMillis(5L));
            } catch (InterruptedException var3) {
               Thread.currentThread().interrupt();
               break label26;
            }
         }

         Iterator var1 = instance.getPlayers().iterator();

         while(var1.hasNext()) {
            PlayerData var2 = (PlayerData)var1.next();
            var2.release();
         }
      }

      instance = null;
   }

   public ExecutorService getExecutorService() {
      return this.executorService;
   }

   public boolean isPlayer(int var1) {
      return this.playersById.containsKey(var1);
   }

   public PlayerData getPlayer(UUID var1) {
      return (PlayerData)this.players.get(var1);
   }

   public VerusPlugin getPlugin() {
      return this.plugin;
   }

   public Map<Integer, WeakReference<PlayerData>> getPlayersById() {
      return this.playersById;
   }

   public void inject(Player var1) {
      if (var1 != null && !var1.hasMetadata("fake")) {
         PlayerData var2 = new PlayerData(var1, this.plugin.getTypeLoader().loadChecks());
         StorageEngine var3 = StorageEngine.getInstance();
         VerusConfiguration var4 = var3.getVerusConfig();
         NMSManager var5 = NMSManager.getInstance();
         API var6 = API.getAPI();
         if (var6 != null && var6.fireInitEvent(var2) || var4.isGeyser() && var1.getName().startsWith(var4.getGeyserPrefix()) || var5.isFloodGateAPI() && FloodgateApi.getInstance().isFloodgatePlayer(var1.getUniqueId())) {
            var2.setEnabled(false);
            var2.getClientData().setType(ClientType.BEDROCK);
         }

         PlayerData var7 = (PlayerData)this.players.put(var1.getUniqueId(), var2);
         if (var7 != null) {
            this.playerList.remove(var7);
         }

         this.playersById.put(var1.getEntityId(), new WeakReference(var2));
         this.playerList.add(var2);
         if (var2.isEnabled()) {
            var5.inject(var2);
         }

         if (var3.isConnected()) {
            var2.loadData();
         }
      }

   }

   public Collection<PlayerData> getPlayers() {
      return this.players.values();
   }

   public PlayerData getPlayer(int var1) {
      WeakReference var2 = (WeakReference)this.playersById.get(var1);
      return var2 == null ? null : (PlayerData)var2.get();
   }

   public DataManager(VerusPlugin var1) {
      this.plugin = var1;
   }

   public PlayerData getPlayer(Player var1) {
      return this.getPlayer(var1.getUniqueId());
   }

   public void uninject(Player var1) {
      PlayerData var2 = (PlayerData)this.players.remove(var1.getUniqueId());

      try {
         WeakReference var3 = (WeakReference)this.playersById.remove(var1.getEntityId());
         if (var3 != null) {
            var3.clear();
         }

         if (var2 != null) {
            var2.saveData();
            var2.setEnabled(false);
            NMSManager.getInstance().uninject(var2);
         }
      } finally {
         if (var2 != null) {
            var2.release();
         }

      }

      if (var2 != null) {
         this.playerList.remove(var2);
      }

   }

   public List<PlayerData> getMostViolations() {
      return (List)this.players.values().stream().sorted(Collections.reverseOrder(Comparator.comparingDouble(PlayerData::getTotalViolations))).collect(Collectors.toList());
   }
}
