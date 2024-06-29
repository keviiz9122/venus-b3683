package me.levansj01.verus.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.alert.Alert;
import me.levansj01.verus.alert.manager.AlertManager;
import me.levansj01.verus.check.AimCheck;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.MovementCheck;
import me.levansj01.verus.check.MovementCheck2;
import me.levansj01.verus.check.PacketCheck;
import me.levansj01.verus.check.ReachCheck;
import me.levansj01.verus.check.VehicleCheck;
import me.levansj01.verus.check.handler.TeleportCheck;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.compat.VPacket;
import me.levansj01.verus.compat.ViaHandler;
import me.levansj01.verus.compat.api.PacketHandler;
import me.levansj01.verus.compat.api.Transactionable;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.Direction;
import me.levansj01.verus.compat.api.wrapper.EnumHand;
import me.levansj01.verus.compat.packets.VPacketPlayInArmAnimation;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockDig;
import me.levansj01.verus.compat.packets.VPacketPlayInBlockPlace;
import me.levansj01.verus.compat.packets.VPacketPlayInClientCommand;
import me.levansj01.verus.compat.packets.VPacketPlayInCloseWindow;
import me.levansj01.verus.compat.packets.VPacketPlayInCustomPayload;
import me.levansj01.verus.compat.packets.VPacketPlayInEntityAction;
import me.levansj01.verus.compat.packets.VPacketPlayInFlying;
import me.levansj01.verus.compat.packets.VPacketPlayInHeldItemSlot;
import me.levansj01.verus.compat.packets.VPacketPlayInKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayInSteerVehicle;
import me.levansj01.verus.compat.packets.VPacketPlayInUseEntity;
import me.levansj01.verus.compat.packets.VPacketPlayInUseItem;
import me.levansj01.verus.compat.packets.VPacketPlayInVehicleMove;
import me.levansj01.verus.compat.packets.VPacketPlayInWindowClick;
import me.levansj01.verus.compat.packets.VPacketPlayOutAbilities;
import me.levansj01.verus.compat.packets.VPacketPlayOutAttachEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityDestroy;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityEffect;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityMetadata;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityTeleport;
import me.levansj01.verus.compat.packets.VPacketPlayOutEntityVelocity;
import me.levansj01.verus.compat.packets.VPacketPlayOutExplosion;
import me.levansj01.verus.compat.packets.VPacketPlayOutGameStateChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutKeepAlive;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMapChunkBulk;
import me.levansj01.verus.compat.packets.VPacketPlayOutMultiBlockChange;
import me.levansj01.verus.compat.packets.VPacketPlayOutNamedEntitySpawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutOpenWindow;
import me.levansj01.verus.compat.packets.VPacketPlayOutPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutRemoveEntityEffect;
import me.levansj01.verus.compat.packets.VPacketPlayOutRespawn;
import me.levansj01.verus.compat.packets.VPacketPlayOutSetSlot;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntity;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnEntityLiving;
import me.levansj01.verus.compat.packets.VPacketPlayOutSpawnPosition;
import me.levansj01.verus.compat.packets.VPacketPlayOutUnloadChunk;
import me.levansj01.verus.compat.packets.VPacketPlayOutUpdateAttributes;
import me.levansj01.verus.data.client.ClientData;
import me.levansj01.verus.data.client.ClientType;
import me.levansj01.verus.data.reach.ReachBase;
import me.levansj01.verus.data.state.Releasable;
import me.levansj01.verus.data.state.ResetState;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.ability.IAbilityHandler;
import me.levansj01.verus.data.transaction.attribute.IAttributeHandler;
import me.levansj01.verus.data.transaction.effects.IEffectHandler;
import me.levansj01.verus.data.transaction.metadata.IMetadataHandler;
import me.levansj01.verus.data.transaction.teleport.ITeleportHandler;
import me.levansj01.verus.data.transaction.teleport.Teleport;
import me.levansj01.verus.data.transaction.tracker.ITracker;
import me.levansj01.verus.data.transaction.tracker.PacketLocation;
import me.levansj01.verus.data.transaction.velocity.ClientVelocity;
import me.levansj01.verus.data.transaction.velocity.IVelocityHandler;
import me.levansj01.verus.data.transaction.velocity.Velocity;
import me.levansj01.verus.data.transaction.world.IVerusWorld;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.messaging.MessagingHandler;
import me.levansj01.verus.messaging.listener.BrandListener;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.config.VerusConfiguration;
import me.levansj01.verus.storage.database.Database;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.type.dev.transaction.MetadataHandler;
import me.levansj01.verus.type.enterprise.transaction.ability.AbilityHandler;
import me.levansj01.verus.type.enterprise.transaction.attribute.AttributeHandler;
import me.levansj01.verus.type.enterprise.transaction.effects.EffectHandler;
import me.levansj01.verus.type.enterprise.transaction.teleport.TeleportHandler;
import me.levansj01.verus.type.enterprise.transaction.tracker.Tracker;
import me.levansj01.verus.type.enterprise.transaction.velocity.VelocityHandler;
import me.levansj01.verus.type.enterprise.transaction.world.VerusWorld;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.AtomicCappedQueue;
import me.levansj01.verus.util.java.BasicDeque;
import me.levansj01.verus.util.java.CachedSupplier;
import me.levansj01.verus.util.java.CappedQueue;
import me.levansj01.verus.util.java.JavaV;
import me.levansj01.verus.util.java.MathUtil;
import me.levansj01.verus.util.java.StringUtil;
import me.levansj01.verus.util.location.ILocationGround;
import me.levansj01.verus.util.location.PlayerLocation;
import me.levansj01.verus.verus2.data.player.TickerMap;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Warning;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.ServerOperator;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerData implements PacketHandler, Releasable {
   private static final int TRIM_SIZE = 80;
   private final Player player;
   private final UUID uuid;
   private final String name;
   private final CheckData checkData;
   private final TickerMap tickerMap = new TickerMap();
   private final ClientData clientData;
   private ClientVersion version;
   /** @deprecated */
   @Deprecated
   private final Queue<PlayerLocation> teleportList = new ConcurrentLinkedQueue();
   /** @deprecated */
   @Deprecated
   private final BasicDeque<Teleport> recentTeleports = new CappedQueue(200);
   /** @deprecated */
   @Deprecated
   private final Queue<Velocity> velocityQueue = new ConcurrentLinkedQueue();
   /** @deprecated */
   @Deprecated
   private final Queue<BiConsumer<Integer, Double>> pingQueue = new LinkedList();
   /** @deprecated */
   @Deprecated
   private final BasicDeque<Integer> connectionFrequency = new AtomicCappedQueue(5);
   /** @deprecated */
   @Deprecated
   private Teleport lastTeleport2;
   /** @deprecated */
   @Deprecated
   private final Queue<Teleport> teleports = new ConcurrentLinkedQueue();
   private final Queue<Alert> spoofedAlerts = new ConcurrentLinkedQueue();
   private final Map<Integer, AtomicCappedQueue<PacketLocation>> recentMoveMap;
   private final CheckLocalQueue<ClientVelocity> velocityData;
   private final Map<Long, Long> keepAliveMap;
   private final Map<Short, Transaction> transactionMap;
   private final Queue<ReachBase> reachData;
   private final PlayerLocation location;
   private PlayerLocation lastLocation;
   private PlayerLocation lastLastLocation;
   private me.levansj01.verus.util.location.PacketLocation currentLocation2;
   private me.levansj01.verus.util.location.PacketLocation lastLocation2;
   private me.levansj01.verus.util.location.PacketLocation lastLastLocation2;
   private BlockPosition spawnLocation;
   private PlayerData lastAttacked;
   private long lastFlying;
   private long lastLastFlying;
   private long lastDelayed;
   private long lastFast;
   private long lastTeleport;
   private long lastClientTransaction;
   private long lastRespawn;
   private long lastKeepAliveTimestamp;
   private int lastTeleportTicks;
   private int flyingTicks;
   private int allowFlightTicks;
   private int velocityTicks;
   private int verticalVelocityTicks;
   private int horizontalVelocityTicks;
   private int horizontalSpeedTicks;
   private int ticks;
   private int totalTicks;
   private int lastSentTransaction;
   private int lastKeepAlive;
   private int survivalTicks;
   private int lastAttackTicks;
   private int nonMoveTicks;
   private int lastNonMoveTicks;
   private int lastInventoryTick;
   private int lastInventoryOutTick;
   private int lastSetSlot;
   private int lastTransactionPing;
   private int averageTransactionPing;
   private int ping;
   private int lastPing;
   private int averagePing;
   private int lastAttackedId;
   private int blockTicks;
   private int lastFace;
   private int receivedTransactions;
   private boolean fallFlying;
   private boolean receivedTransaction;
   private boolean vehicle;
   private boolean wasVehicle;
   private boolean sentTransaction;
   private boolean moved;
   private boolean aimed;
   private boolean blocking;
   private boolean placing;
   private boolean swingDigging;
   private boolean abortedDigging;
   private boolean stoppedDigging;
   private boolean ready;
   private BlockPosition diggingBlock;
   private Direction diggingBlockFace;
   private Boolean sprinting;
   private Boolean sneaking;
   private final ResetState<PotionEffect[]> effects;
   private final ResetState<Boolean> shouldHaveReceivedPing;
   private final ResetState<Integer> speedLevel;
   private final ResetState<Integer> slowLevel;
   private final ResetState<Integer> jumpLevel;
   private final ResetState<Integer> pingTicks;
   private final ResetState<Integer> maxPingTicks;
   private final ResetState<Integer> maxPingTick2;
   private BlockPosition lastBlockPosition;
   private boolean banned;
   private boolean enabled;
   private boolean checkSpoofing;
   private boolean spoofBan;
   private boolean resetVelocity;
   private boolean digging;
   private boolean alerts;
   private boolean debug;
   private boolean inventoryOpen;
   private short lastTransactionID;
   private int teleportTicks;
   private int lastFakeEntityDamageTicks;
   private int suffocationTicks;
   private int transactionPing;
   private int spawned;
   private int oldVehicleId;
   private int fallDamage;
   private long timestamp;
   private long nanos;
   private double lastVelY;
   private double lastVelX;
   private double lastVelZ;
   private double velY;
   private String brand;
   private Check spoofBanCheck;
   private CheckType focus;
   private String focusSubType;
   private Transaction transaction;
   private Transaction lastTransaction;
   private Transaction nextTransaction;
   private final Queue<Runnable> start;
   private final Queue<Runnable> end;
   private final Queue<Runnable> nextTrans;
   private IVerusWorld world;
   private ITeleportHandler teleportHandler;
   private IVelocityHandler velocityHandler;
   private IAbilityHandler abilityHandler;
   private IEffectHandler effectHandler;
   private ITracker tracker;
   private IAttributeHandler attributeHandler;
   private IMetadataHandler metadataHandler;
   private final Queue<Integer> vehicleIds;
   private Integer vehicleId;
   private ViaHandler.PlayerHandler viaPlayerHandler;
   private final PlayerLocation vehicleLocation;
   private PlayerLocation lastVehicleLocation;

   public PlayerData(Player player, Check[] checks) {
      this.recentMoveMap = NMSManager.getInstance().createCache(TimeUnit.MINUTES.toMillis(30L), (Long)null);
      this.velocityData = CheckLocalQueue.async();
      this.keepAliveMap = NMSManager.getInstance().createCache((Long)null, TimeUnit.MINUTES.toMillis(10L));
      this.transactionMap = (Map)(StorageEngine.getInstance().getVerusConfig().isMoreTransactions() ? new ConcurrentHashMap() : NMSManager.getInstance().createCache((Long)null, TimeUnit.MINUTES.toMillis(3L)));
      this.reachData = new LinkedList();
      this.flyingTicks = 1200;
      this.allowFlightTicks = 1200;
      this.verticalVelocityTicks = -20;
      this.survivalTicks = 1200;
      this.lastAttackTicks = 600;
      this.diggingBlock = null;
      this.diggingBlockFace = null;
      this.sprinting = null;
      this.sneaking = null;
      this.enabled = true;
      this.lastTransactionID = (short)ThreadLocalRandom.current().nextInt();
      this.suffocationTicks = 100;
      this.spawned = Integer.MAX_VALUE;
      this.spoofBanCheck = null;
      this.nextTransaction = new Transaction((Transaction)null);
      this.start = new ConcurrentLinkedQueue();
      this.end = new ConcurrentLinkedQueue();
      this.nextTrans = new ConcurrentLinkedQueue();
      this.vehicleIds = new LinkedList();
      this.player = player;
      this.uuid = player.getUniqueId();
      this.name = player.getName();
      this.updateVersion();
      this.checkData = new CheckData(this, checks);
      this.location = this.vehicleLocation = BukkitUtil.fromPlayer(player);
      this.currentLocation2 = this.lastLocation2 = this.lastLastLocation2 = BukkitUtil.fromPlayer2(player);
      this.lastLocation = this.lastLastLocation = this.location.clone();
      this.alerts = BukkitUtil.hasPermissionMeta(player, "verus.alerts");
      this.debug = BukkitUtil.hasPermissionMeta(player, "verus.admin");
      this.clientData = new ClientData("Unknown", ClientType.UNKNOWN);
      GameMode gameMode = player.getGameMode();
      if (gameMode != GameMode.SURVIVAL && gameMode != GameMode.ADVENTURE) {
         this.survivalTicks = 0;
      }

      if (player.getAllowFlight()) {
         this.allowFlightTicks = 0;
      }

      if (player.isFlying()) {
         this.flyingTicks = 0;
      }

      this.effects = State.reset(() -> {
         return (PotionEffect[])player.getActivePotionEffects().toArray(new PotionEffect[0]);
      });
      this.speedLevel = State.reset(() -> {
         return BukkitUtil.getPotionLevel((State)this.effects, PotionEffectType.SPEED);
      });
      this.slowLevel = State.reset(() -> {
         return BukkitUtil.getPotionLevel((State)this.effects, PotionEffectType.SLOW);
      });
      this.jumpLevel = State.reset(() -> {
         return BukkitUtil.getPotionLevel((State)this.effects, PotionEffectType.JUMP);
      });
      this.shouldHaveReceivedPing = State.reset(() -> {
         return this.receivedTransaction || this.totalTicks < this.lastKeepAlive + 4 || this.lastTeleport2 != null && this.tickerMap.get(TickerType.LAST_SENT_TRANSACTION) > this.lastTeleport2.getTicks() + 5 || this.totalTicks > 1200;
      });
      this.pingTicks = State.reset(() -> {
         return Math.min(100, (Boolean)this.shouldHaveReceivedPing.get() ? (int)Math.ceil((double)this.getTransactionPing() / 50.0D) : (int)MathUtil.highest(40, this.totalTicks)) + 1;
      });
      this.maxPingTicks = State.reset(() -> {
         return Math.min(100, (Boolean)this.shouldHaveReceivedPing.get() ? (int)Math.ceil(MathUtil.highest((double)this.transactionPing, (double)this.lastTransactionPing, (double)this.averageTransactionPing) / 50.0D) : Math.max(40, this.totalTicks)) + 1;
      });
      this.maxPingTick2 = State.reset(() -> {
         return Math.min(600, (Boolean)this.shouldHaveReceivedPing.get() ? (int)Math.ceil(MathUtil.highest((double)this.transactionPing, (double)this.lastTransactionPing, (double)this.averageTransactionPing) / 50.0D) : Math.max(40, this.totalTicks)) + 1;
      });
      NMSManager<?> nmsManager = NMSManager.getInstance();
      PotionEffect[] effects = (PotionEffect[])this.effects.get();
      if (BukkitUtil.hasEffect(effects, PotionEffectType.JUMP.getId())) {
         this.tickerMap.reset(TickerType.JUMP_BOOST);
      }

      if (BukkitUtil.hasEffect(effects, PotionEffectType.SPEED.getId())) {
         this.tickerMap.reset(TickerType.SPEED_BOOST);
      }

      if (BukkitUtil.hasEffect((PotionEffect[])effects, 25)) {
         this.tickerMap.reset(TickerType.LEVITATION);
      }

      if (nmsManager.isGliding(player)) {
         this.tickerMap.reset(TickerType.GLIDING);
      }

      nmsManager.postToMainThread(() -> {
         this.after(() -> {
            this.end(() -> {
               this.ready = true;
            });
         });
      });
      nmsManager.postToMainThread(() -> {
         nmsManager.sendTransaction(player, this.incrementTransactionId());
      });
      ViaHandler handler = NMSManager.getInstance().getViaHandler();
      if (handler != null) {
         this.viaPlayerHandler = handler.get(this.uuid);
      }

      if (VerusTypeLoader.isEnterprise()) {
         this.teleportHandler = new TeleportHandler(this);
         this.velocityHandler = new VelocityHandler(this);
         this.abilityHandler = new AbilityHandler(this);
         this.effectHandler = new EffectHandler(this);
         this.world = new VerusWorld(this);
         if (nmsManager.getServerVersion().beforeEq(ServerVersion.v1_8_R3)) {
            this.tracker = new Tracker(this);
         }

         this.attributeHandler = new AttributeHandler(this);
      }

      if (VerusTypeLoader.isDev()) {
         this.metadataHandler = new MetadataHandler(this);
      }

   }

   public void updateVersion(int ordinal) {
      this.updateVersion(ClientVersion.values()[ordinal]);
   }

   public void updateVersion(ClientVersion version) {
      if (!Objects.equals(this.version, version)) {
         this.version = version;
         if (this.checkData != null) {
            this.checkData.updateChecks();
         }
      }

      if (this.tracker != null) {
         this.tracker.updateVersion();
      }

   }

   public void updateVersion() {
      ClientVersion version;
      try {
         version = NMSManager.getInstance().getVersion(this.player);
      } catch (Throwable var3) {
         VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to obtain version for " + this.player.getName() + ": ", var3);
         version = ClientVersion.VERSION_UNSUPPORTED;
      }

      this.updateVersion(version);
   }

   public void before(Runnable runnable) {
      if (this.lastTransaction == null) {
         runnable.run();
      } else {
         this.lastTransaction.queue(runnable);
      }

   }

   public void after(Runnable runnable) {
      this.nextTransaction.queue(runnable);
   }

   public void start(Runnable runnable) {
      this.start.add(runnable);
   }

   public void end(Runnable runnable) {
      this.end.add(runnable);
   }

   public void nextTrans(Runnable runnable) {
      this.nextTrans.add(runnable);
   }

   public short incrementTransactionId() {
      short var10002 = this.lastTransactionID;
      this.lastTransactionID = (short)(var10002 + 1);
      return var10002;
   }

   public void handleIn(Transactionable transactionable) {
      short id = transactionable.id();
      if (this.viaPlayerHandler != null) {
         this.viaPlayerHandler.decrease();
      }

      this.tickerMap.set(TickerType.LAST_RECEIVED_TRANSACTION, TickerType.TOTAL);
      Transaction transaction = (Transaction)this.transactionMap.remove(id);
      if (transaction != null && transaction.isSent()) {
         this.transaction = transaction;
         this.receivedTransaction = true;
         transaction.receive(this.timestamp, this);
         ++this.receivedTransactions;
         JavaV.executeSafely(this.nextTrans, () -> {
            return " in next transaction " + transaction.getId();
         });
         State<Double> deviation = State.fast(() -> {
            return MathUtil.variance(0, this.connectionFrequency);
         });
         this.lastTransactionPing = this.transactionPing;
         this.transactionPing = transaction.ping().intValue();
         VerusConfiguration configuration = StorageEngine.getInstance().getVerusConfig();
         if (configuration.isPingKick() && (long)this.transactionPing > TimeUnit.SECONDS.toMillis((long)configuration.getPingTimeout())) {
            Check check = this.checkData.getTransactionCheck();
            if (check != null) {
               check.setViolations(1.0D);
               check.handleViolation();
            }

            VerusLauncher.getPlugin().getLogger().info(this.getName() + " was timed out after " + configuration.getPingTimeout() + " seconds.");
            this.fuckOff();
            return;
         }

         this.averageTransactionPing = (this.averageTransactionPing * 4 + this.transactionPing) / 5;
         this.resetPingTicks();

         BiConsumer pingConsumer;
         while((pingConsumer = (BiConsumer)this.pingQueue.poll()) != null) {
            pingConsumer.accept(this.transactionPing, (Double)deviation.get());
         }

         List<ReachBase> reachDataList = new ArrayList(this.reachData);
         ReachCheck[] var8 = this.checkData.getReachChecks();
         int var9 = var8.length;

         int var10;
         for(var10 = 0; var10 < var9; ++var10) {
            ReachCheck reachCheck = var8[var10];
            reachCheck.handle((List)reachDataList, this.timestamp);
         }

         this.reachData.clear();
         PingHandler[] var13 = this.checkData.getPingHandlers();
         var9 = var13.length;

         for(var10 = 0; var10 < var9; ++var10) {
            PingHandler pingHandler = var13[var10];
            pingHandler.handleTransaction(transaction.getSent(), this.timestamp);
         }
      } else if (this.checkData.getTransactionCheck() != null && this.totalTicks > 400 && !this.inventoryOpen) {
         NMSManager.getInstance().postToMainThread(() -> {
            if (this.transactionMap.remove(id) == null) {
            }

         });
      }

      this.lastClientTransaction = this.timestamp;
   }

   public void handle(VPacketPlayInArmAnimation<?> packet) {
      this.blocking = false;
      if (!this.swingDigging && !this.placing) {
         this.tickerMap.increment(TickerType.POSSIBLE_SWINGS);
      }

   }

   public void handle(VPacketPlayInBlockDig<?> packet) {
      GameMode gameMode = this.player.getGameMode();
      if (gameMode == GameMode.CREATIVE) {
         this.digging = false;
         this.swingDigging = false;
      } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.START_DESTROY_BLOCK) {
         if (gameMode == GameMode.SURVIVAL) {
            this.digging = true;
            this.diggingBlock = packet.getBlockPosition();
            this.diggingBlockFace = packet.getFace();
            this.swingDigging = true;
         }

         this.abortedDigging = false;
         this.stoppedDigging = false;
      } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.ABORT_DESTROY_BLOCK) {
         this.abortedDigging = true;
      } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.STOP_DESTROY_BLOCK) {
         this.stoppedDigging = true;
      } else if (packet.getType() == VPacketPlayInBlockDig.PlayerDigType.RELEASE_USE_ITEM) {
         this.blocking = false;
         if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2)) {
            ItemStack[] var3 = new ItemStack[]{this.player.getItemInHand(), NMSManager.getInstance().getOffHand(this.player)};
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               ItemStack stack = var3[var5];
               if (stack != null && stack.getType() == MaterialList.TRIDENT) {
                  int riptide = BukkitUtil.getEnchantment(stack, "RIPTIDE");
                  if (riptide > 0) {
                     this.tickerMap.set(TickerType.RIPTIDE, -10 * (riptide + 1));
                  }
               }
            }
         }
      }

      if (this.world != null) {
         this.world.handle(packet);
      }

   }

   public void handle(VPacketPlayInBlockPlace<?> packet) {
      ItemStack itemStack = packet.getItemStack();
      if (!packet.isEmpty()) {
         this.lastBlockPosition = packet.getPosition();
         this.lastFace = packet.getFace();
         if (itemStack != null) {
            this.handleItem(itemStack);
            this.blocking = MaterialList.canPlace(itemStack);
         }

         this.placing = true;
         if (this.world != null) {
            this.world.handle(packet);
         }

      } else {
         EnumHand hand = EnumHand.values()[packet.getHand()];
         switch(hand) {
         case MAIN_HAND:
            itemStack = this.player.getItemInHand();
            break;
         case OFF_HAND:
            itemStack = NMSManager.getInstance().getOffHand(this.player);
         }

         if (itemStack != null) {
            this.handleInteraction(itemStack.getType());
            this.blocking = MaterialList.canPlace(itemStack);
         }

      }
   }

   public void handle(VPacketPlayInUseItem<?> packet) {
      this.lastBlockPosition = packet.getPosition();
      this.lastFace = packet.getFace();
      EnumHand hand = EnumHand.values()[packet.getHand()];
      switch(hand) {
      case MAIN_HAND:
         this.handleItem(this.player.getItemInHand());
         break;
      case OFF_HAND:
         this.handleItem(NMSManager.getInstance().getOffHand(this.player));
      }

      this.placing = true;
   }

   public void handle(VPacketPlayInClientCommand<?> packet) {
      this.blocking = false;
      if (packet.getCommand() == VPacketPlayInClientCommand.ClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
         this.inventoryOpen = true;
         this.lastInventoryTick = this.totalTicks;
      }

   }

   public void handle(VPacketPlayInCloseWindow<?> packet) {
      this.inventoryOpen = false;
      this.blocking = false;
   }

   public void handle(VPacketPlayInCustomPayload<?> packet) {
      String channel = packet.getChannel();
      if (channel != null && !channel.equals("MC|Brand") && channel.equals("minecraft:brand")) {
      }

   }

   public void handle(VPacketPlayInEntityAction<?> packet) {
      VPacketPlayInEntityAction.PlayerAction playerAction = packet.getAction();
      switch(playerAction.getType()) {
      case SPRINT:
         this.sprinting = playerAction.isValue();
         this.tickerMap.reset(TickerType.SPRINT);
         break;
      case SNEAK:
         this.sneaking = playerAction.isValue();
         break;
      case OTHER:
         switch(packet.getAction()) {
         case START_FALL_FLYING:
            if (!this.fallFlying) {
               this.tickerMap.set(TickerType.FALL_FLYING, -20);
               this.fallFlying = true;
            }
         }
      }

   }

   public void handle(VPacketPlayInFlying<?> packet) {
      long timestamp = this.timestamp;
      this.nanos = System.nanoTime();
      this.tickerMap.incrementAll();
      this.tickerMap.set(TickerType.ATTACKS_IN_LAST, this.tickerMap.get(TickerType.ATTACKS));
      this.tickerMap.reset(TickerType.ATTACKS);
      if (packet.isPos() && !packet.sameXZ(this.currentLocation2)) {
         this.tickerMap.increment(TickerType.MOVES_SINCE_TELEPORT);
      }

      if (this.teleportHandler != null) {
         packet.setTeleport(this.teleportHandler.isTeleport(packet));
      }

      if (!packet.isTeleport()) {
         ++this.ticks;
      }

      Iterator var4 = this.teleports.iterator();

      while(var4.hasNext()) {
         Teleport teleport = (Teleport)var4.next();
         if (teleport.matches(packet)) {
            this.teleports.remove(teleport);
            this.tickerMap.reset(TickerType.TELEPORT);
            this.tickerMap.reset(TickerType.MOVES_SINCE_TELEPORT);
            this.lastTeleport2 = teleport;
            if (this.checkData.getPingSpoofCheck() != null) {
               this.checkData.getPingSpoofCheck().accept(timestamp, teleport);
            }
            break;
         }

         if (timestamp - teleport.getTime() > (long)((this.getAverageTransactionPing() + 1000) * 2) && this.tickerMap.get(TickerType.TOTAL) - teleport.getTicks() > 2 * (this.getMaxPingTicks() + 20)) {
            try {
               this.teleports.remove(teleport);
            } catch (Throwable var20) {
               var20.printStackTrace();
            }
         }
      }

      if (!packet.isTeleport()) {
         JavaV.executeSafely(this.start, () -> {
            return " start of next tick";
         });
      }

      NMSManager<?> nmsManager = NMSManager.getInstance();
      if (this.totalTicks == 5) {
         nmsManager.postToMainThread(() -> {
            this.updateVersion();

            try {
               if (nmsManager.isRunningLunarClient(this.player)) {
                  this.clientData.setType(ClientType.LUNAR);
               }
            } catch (Throwable var5) {
               VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to update lunar status: ", var5);
            }

            try {
               BrandListener listener = MessagingHandler.getInstance().getBrandListener();
               if (listener != null) {
                  String brand = listener.getBrand(this.uuid);
                  if (brand != null) {
                     this.checkData.getBrandCheck().handle(brand);
                  }
               }
            } catch (Throwable var4) {
               VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to update brand status: ", var4);
            }

         });
      }

      this.effects.reset();
      this.speedLevel.reset();
      this.slowLevel.reset();
      this.jumpLevel.reset();
      this.resetPingTicks();
      this.velocityQueue.removeIf((velocity) -> {
         return this.tickerMap.get(TickerType.TOTAL) - velocity.getTicks() > this.getMaxPingTicks() + 2 && timestamp - velocity.getTimestamp() > (long)(this.getTransactionPing() + 100) && velocity.attenuate(packet.isGround());
      });
      this.lastLastLocation2 = this.lastLocation2;
      this.lastLocation2 = this.currentLocation2;
      this.currentLocation2 = packet.from(this.lastLocation2);
      if (packet.isPos() && this.currentLocation2.distanceSquared(this.lastLocation2) != 0.0D) {
         this.tickerMap.reset(TickerType.NOT_MOVING);
      }

      MovementCheck2[] var22 = this.checkData.getMovementChecks2();
      int var6 = var22.length;

      int possibleSwings;
      for(possibleSwings = 0; possibleSwings < var6; ++possibleSwings) {
         MovementCheck2 check = var22[possibleSwings];
         check.handle(this.lastLocation2, this.currentLocation2, timestamp);
      }

      this.location.setTimestamp(timestamp);
      this.location.setTeleport(packet.isTeleport());
      this.location.setGround(packet.isGround());
      if (packet.isPos()) {
         this.location.setX(packet.getX());
         this.location.setY(packet.getY());
         this.location.setZ(packet.getZ());
      }

      if (packet.isLook()) {
         this.location.setYaw(packet.getYaw());
         this.location.setPitch(packet.getPitch());
      }

      this.lastNonMoveTicks = this.nonMoveTicks;
      if (packet.isPos()) {
         this.nonMoveTicks = 0;
      } else {
         ++this.nonMoveTicks;
      }

      long diff = timestamp - this.lastFlying;
      if (diff > 110L) {
         this.lastDelayed = timestamp;
      }

      if (diff < 15L) {
         this.lastFast = timestamp;
      }

      this.lastLastFlying = this.lastFlying;
      this.lastFlying = timestamp;
      if (this.teleportList.isEmpty()) {
         ++this.teleportTicks;
      } else {
         this.teleportTicks = 0;
      }

      if (this.version == ClientVersion.V1_9) {
         diff = Math.min(diff, 110L);
      }

      this.connectionFrequency.addFirst(50 - (int)diff);
      possibleSwings = this.tickerMap.get(TickerType.POSSIBLE_SWINGS);
      if (possibleSwings > 0 && !this.swingDigging) {
         this.tickerMap.reset(TickerType.LEGIT_SWING);
         if (possibleSwings > 1) {
            this.tickerMap.reset(TickerType.DOUBLE_SWING);
         }
      }

      this.tickerMap.reset(TickerType.POSSIBLE_SWINGS);
      if (this.placing) {
         this.placing = false;
      }

      if (this.abortedDigging) {
         this.abortedDigging = false;
         this.swingDigging = false;
         this.digging = false;
      }

      if (this.stoppedDigging) {
         this.stoppedDigging = false;
         this.digging = false;
      }

      if (this.resetVelocity) {
         this.lastVelY = 0.0D;
         this.resetVelocity = false;
      }

      PlayerLocation teleport2 = (PlayerLocation)this.teleportList.peek();
      if (teleport2 != null) {
         int tickDifference = this.totalTicks - teleport2.getTickTime();
         if (packet.isPos() && tickDifference >= this.getMoveTicks() && teleport2.sameXYZ(this.location)) {
            this.teleportList.poll();
            if (this.lastLocation.distanceXZSquared(this.location) > 4.0D) {
               this.spawned = 0;
            }

            this.lastVelX = this.lastVelY = this.lastVelZ = this.velY;
            this.lastLocation = this.location.clone();
            this.lastLastLocation = this.location.clone();
            this.lastTeleportTicks = this.totalTicks;
         } else if (tickDifference > (packet.isPos() ? this.getMaxPingTicks() * 2 : this.getMaxPingTicks() * 4)) {
            this.teleportList.poll();
         }
      }

      if (this.lastVelY == 0.0D && this.velY != 0.0D) {
         if (packet.isGround()) {
            this.velY = 0.0D;
         } else {
            this.velY -= 0.08D;
            this.velY *= 0.98D;
         }
      }

      if (packet.isGround()) {
         if (this.fallFlying) {
            this.fallFlying = false;
            this.tickerMap.set(TickerType.ELYTRA_EXIT, -100);
         }

         if (!this.isTeleportingV2()) {
            this.spawned = Integer.MAX_VALUE;
         }
      }

      if (this.spawned <= 600) {
         ++this.spawned;
         if (packet.isPos()) {
            if (this.lastLocation.getY() < this.location.getY()) {
               this.spawned += 40;
            } else if (this.lastLocation.getY() == this.location.getY() && !this.lastLocation.sameXYZ(this.location)) {
               this.spawned += 10;
            }
         }
      }

      if (this.player.isFlying()) {
         this.flyingTicks = 0;
      } else {
         ++this.flyingTicks;
      }

      if (this.player.getAllowFlight()) {
         this.allowFlightTicks = 0;
      } else {
         ++this.allowFlightTicks;
      }

      if (this.wasVehicle) {
         this.tickerMap.reset(TickerType.VEHICLE);
      } else {
         this.tickerMap.increment(TickerType.VEHICLE);
      }

      if (!this.vehicle && this.player.getVehicle() == null) {
         if (!this.lastLocation.sameXYZ(this.location)) {
            this.wasVehicle = false;
         }
      } else {
         this.wasVehicle = true;
         this.tickerMap.reset(TickerType.VEHICLE);
      }

      GameMode gameMode = this.player.getGameMode();
      if (gameMode != GameMode.SURVIVAL && gameMode != GameMode.ADVENTURE) {
         this.survivalTicks = 0;
      } else {
         ++this.survivalTicks;
      }

      if (gameMode.getValue() == 3) {
         this.sprinting = false;
      }

      ItemStack itemInhand = this.player.getItemInHand();
      if (itemInhand != null && (!MaterialList.canPlace(itemInhand) || itemInhand.getType().equals(MaterialList.BOW) && !this.player.getInventory().contains(MaterialList.ARROW))) {
         this.blocking = false;
      }

      if (this.blocking) {
         ++this.blockTicks;
      } else {
         this.blockTicks = 0;
      }

      PotionEffect[] effects = (PotionEffect[])this.effects.get();
      if (BukkitUtil.hasEffect(effects, PotionEffectType.JUMP.getId())) {
         this.tickerMap.reset(TickerType.JUMP_BOOST);
      }

      if (BukkitUtil.hasEffect(effects, PotionEffectType.SPEED.getId())) {
         this.tickerMap.reset(TickerType.SPEED_BOOST);
      }

      if (BukkitUtil.hasEffect((PotionEffect[])effects, 25)) {
         this.tickerMap.reset(TickerType.LEVITATION);
      }

      if (nmsManager.isGliding(this.player)) {
         this.tickerMap.reset(TickerType.GLIDING);
      }

      ++this.totalTicks;
      ++this.lastAttackTicks;
      ++this.velocityTicks;
      ++this.horizontalSpeedTicks;
      ++this.verticalVelocityTicks;
      ++this.horizontalVelocityTicks;
      int var13;
      int var14;
      if (this.moved = !this.lastLocation.sameXYZ(this.location)) {
         MovementCheck[] var12 = this.checkData.getMovementChecks();
         var13 = var12.length;

         for(var14 = 0; var14 < var13; ++var14) {
            MovementCheck movementCheck = var12[var14];
            movementCheck.handle(this.lastLocation, this.location, timestamp);
         }
      }

      if (this.aimed = !this.lastLocation.sameYawPitch(this.location)) {
         AimCheck[] var26 = this.checkData.getAimChecks();
         var13 = var26.length;

         for(var14 = 0; var14 < var13; ++var14) {
            AimCheck aimCheck = var26[var14];
            aimCheck.handle(this.lastLocation, this.location, timestamp);
         }
      }

      this.lastLastLocation = this.lastLocation.clone();
      this.lastLocation = this.location.clone();
      if (this.lastAttackTicks <= 1 && this.lastAttacked != null && !this.isTeleporting(5) && !this.isVehicle() && this.isSurvival() && !this.isTeleportingV2() && this.lastAttacked.getTeleportTicks() > this.lastAttacked.getMaxPingTicks() + this.getMaxPingTicks() && this.lastAttacked.getTickerMap().get(TickerType.VEHICLE) > this.lastAttacked.getMaxPingTicks() + this.getMaxPingTicks()) {
         boolean possibleLag = StorageEngine.getInstance().getVerusConfig().isIgnoreLag() && this.hasFast() || this.hasLag();
         boolean sneaking = this.sneaking != null && this.sneaking;
         PlayerLocation location = this.location.clone();
         PlayerLocation lastLocation = this.lastLastLocation.clone();
         AtomicCappedQueue<PacketLocation> recentMovesQueue = (AtomicCappedQueue)this.recentMoveMap.get(this.lastAttackedId);
         if (recentMovesQueue != null && recentMovesQueue.size() >= Math.min(40, 10 + this.getMaxPingTicks())) {
            List<PacketLocation> recentMoves = (List)recentMovesQueue.stream().map(PacketLocation::clone).collect(Collectors.toList());
            int lastPing = this.transactionPing;
            int nonMoveTicks = this.nonMoveTicks;
            this.pingQueue.add((ping, connection) -> {
               ReachBase reachBase = new ReachBase(this, location, lastLocation, timestamp, ping, lastPing, connection.intValue(), nonMoveTicks, recentMoves, sneaking, this.transaction);
               reachBase.execute();
               if (!possibleLag) {
                  ReachCheck[] var13 = this.checkData.getReachChecks();
                  int var14 = var13.length;

                  for(int var15 = 0; var15 < var14; ++var15) {
                     ReachCheck reachCheck = var13[var15];
                     reachCheck.handle(reachBase, timestamp);
                  }

                  this.reachData.add(reachBase);
               }

            });
         }
      }

   }

   public void handle(VPacketPlayInHeldItemSlot<?> packet) {
      this.blocking = false;
   }

   public void handle(VPacketPlayInKeepAlive<?> packet) {
      long id = packet.getId();
      this.lastKeepAlive = this.tickerMap.get(TickerType.TOTAL);
      this.lastKeepAliveTimestamp = this.timestamp;
      Long sent = (Long)this.keepAliveMap.remove(id);
      if (sent != null) {
         this.lastPing = this.ping;
         this.ping = (int)(this.timestamp - sent);
         this.averagePing = (this.averagePing * 4 + this.ping) / 5;
         NMSManager.getInstance().updatePing(this);
         PingHandler[] var5 = this.checkData.getPingHandlers();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PingHandler pingHandler = var5[var7];
            pingHandler.handleKeepAlive(sent, this.timestamp);
         }
      } else if (this.checkData.getKeepAliveCheck() != null) {
         NMSManager.getInstance().postToMainThread(() -> {
            if (this.keepAliveMap.remove(id) == null && this.totalTicks > 600) {
               this.checkData.getKeepAliveCheck().handleViolation(() -> {
                  return String.format("ID: %s", id);
               }, 1.0D, true);
            }

         });
      }

      this.resetPingTicks();
   }

   public void handle(VPacketPlayInVehicleMove<?> packet) {
      if (this.isVehicle()) {
         this.vehicleLocation.setX(packet.getX());
         this.vehicleLocation.setY(packet.getY());
         this.vehicleLocation.setZ(packet.getZ());
         if (this.lastVehicleLocation != null && !this.vehicleLocation.sameXYZ(this.lastVehicleLocation)) {
            VehicleCheck[] var2 = this.checkData.getVehicleChecks();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               VehicleCheck vehicleCheck = var2[var4];
               vehicleCheck.handle(this.lastVehicleLocation, this.vehicleLocation);
            }
         }

         this.lastVehicleLocation = this.vehicleLocation.clone();
      } else {
         this.lastVehicleLocation = null;
      }

   }

   public void handle(VPacketPlayInSteerVehicle<?> packet) {
      if ((this.vehicle || this.player.getVehicle() != null) && Math.abs(packet.getForward()) <= 0.9800000190734863D && Math.abs(packet.getStrafe()) <= 0.9800000190734863D) {
         this.wasVehicle = true;
         this.tickerMap.reset(TickerType.VEHICLE);
      }

   }

   public void handle(VPacketPlayInUseEntity<?> packet) {
      if (packet.getAction() == VPacketPlayInUseEntity.EntityUseAction.ATTACK) {
         this.tickerMap.increment(TickerType.ATTACKS);
         this.lastAttacked = null;
         this.lastAttackedId = packet.getId();
         if (packet.isPlayer()) {
            this.lastAttacked = packet.getPlayerData();
            Random random = ThreadLocalRandom.current();
            AlertManager alertManager = AlertManager.getInstance();
            if (this.spoofBan && random.nextDouble() < 0.1D) {
               alertManager.handleBan(this, this.spoofBanCheck, false);
            } else if (this.checkSpoofing && random.nextDouble() < 0.25D) {
               while(this.checkSpoofing) {
                  Alert alert = (Alert)this.spoofedAlerts.poll();
                  if (alert == null) {
                     this.checkSpoofing = false;
                  } else if (this.timestamp - alert.getTimestamp() < TimeUnit.SECONDS.toMillis(15L)) {
                     alertManager.getExecutorService().submit(() -> {
                        return alertManager.handleAlert(this, alert.getCheck(), alert.getData(), alert.getVl());
                     });
                     break;
                  }
               }
            }
         }

         this.lastAttackTicks = 0;
      }

   }

   public void handle(VPacketPlayInWindowClick<?> packet) {
      if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_11_R1) && packet.getWindow() == 0 && packet.getSlot() == 6 && packet.getItemStack() != null && packet.getItemStack().getType() == MaterialList.ELYTRA) {
         this.tickerMap.set(TickerType.ELYTRA_EXIT, -100);
      }

      this.tickerMap.reset(TickerType.WINDOW_CLICK);
   }

   public void handleOut(Transactionable transactionable) {
      if (transactionable.valid()) {
         long timestamp = System.currentTimeMillis();
         short id = transactionable.id();
         this.transactionMap.put(id, this.nextTransaction);
         this.nextTransaction.send(id, timestamp);
         this.tickerMap.set(TickerType.LAST_SENT_TRANSACTION, TickerType.TOTAL);
         this.sentTransaction = true;
         this.lastTransaction = this.nextTransaction;
         this.nextTransaction = new Transaction(this.lastTransaction);
      }

   }

   public void handle(VPacketPlayOutAbilities<?> packet) {
      if (this.abilityHandler != null) {
         this.abilityHandler.handle(packet);
      }

   }

   public void handle(VPacketPlayOutAttachEntity<?> packet) {
      if (packet.getLeash() == 0) {
         if (this.tracker != null) {
            this.tracker.handle(packet);
         }

         if (packet.getEntityId() == this.player.getEntityId()) {
            int vehicle = packet.getAttachId();
            if (vehicle == -1) {
               this.after(() -> {
                  this.vehicleId = null;
               });
            } else {
               this.before(() -> {
                  this.vehicleIds.add(vehicle);
               });
               this.after(() -> {
                  this.vehicleId = vehicle;
               });
            }

            this.vehicle = packet.getAttachId() > 0;
            this.oldVehicleId = packet.getAttachId();
            this.tickerMap.reset(TickerType.VEHICLE);
            this.wasVehicle = true;
         }

      }
   }

   public void handle(VPacketPlayOutBlockChange<?> packet) {
      if (this.world != null) {
         this.world.handle(packet);
      }

   }

   public void handle(VPacketPlayOutEntity<?> packet) {
      if (this.tracker != null) {
         this.tracker.handle(packet);
      }

      if (packet.isPos()) {
         int entityId = packet.getId();
         AtomicCappedQueue<PacketLocation> recentMoveQueue = (AtomicCappedQueue)this.recentMoveMap.get(entityId);
         if (recentMoveQueue != null) {
            PacketLocation last = (PacketLocation)recentMoveQueue.peekLast();
            if (last != null) {
               long now = System.currentTimeMillis();
               last.setNextOffset(now);
               PacketLocation location = packet.move(last);
               location.setNextOffset(Long.MAX_VALUE);
               location.setTimestamp(now);
               recentMoveQueue.addLast(location);
            }
         }
      }

   }

   public void handle(VPacketPlayOutEntityDestroy<?> packet) {
      if (this.tracker != null) {
         this.tracker.handle(packet);
      }

      int[] ids = packet.getIds();
      if (ids.length == 1 && this.vehicle && ids[0] == this.oldVehicleId) {
         this.vehicle = false;
      }

      this.after(() -> {
         if (this.vehicleId != null) {
            int[] var2 = ids;
            int var3 = ids.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               int id = var2[var4];
               if (this.vehicleId == id) {
                  this.vehicleId = null;
                  break;
               }
            }

         }
      });
      int[] var3 = ids;
      int var4 = ids.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int id = var3[var5];
         this.recentMoveMap.remove(id);
      }

   }

   public void handle(VPacketPlayOutEntityEffect<?> packet) {
      if (this.effectHandler != null) {
         this.effectHandler.handle(packet);
      }

   }

   public void handle(VPacketPlayOutEntityTeleport<?> packet) {
      if (this.tracker != null) {
         this.tracker.handle(packet);
      }

      AtomicCappedQueue<PacketLocation> recentMoveQueue = (AtomicCappedQueue)this.recentMoveMap.get(packet.getId());
      if (recentMoveQueue != null) {
         PacketLocation last = (PacketLocation)recentMoveQueue.peekLast();
         long now = System.currentTimeMillis();
         if (last != null) {
            last.setNextOffset(now);
         }

         PacketLocation location = packet.toLocation(now);
         recentMoveQueue.addLast(location);
      }

   }

   public void handle(VPacketPlayOutEntityVelocity<?> packet) {
      if (this.velocityHandler != null) {
         this.velocityHandler.handle(packet);
      }

      if (packet.getId() == this.player.getEntityId()) {
         double dx = (double)packet.getMotX() / 8000.0D;
         double dy = (double)packet.getMotY() / 8000.0D;
         double dz = (double)packet.getMotZ() / 8000.0D;
         this.handleVelocity(dx, dy, dz, false);
      }

   }

   public void handle(VPacketPlayOutExplosion<?> packet) {
      this.handleVelocity((double)packet.getMotX(), (double)packet.getMotY(), (double)packet.getMotZ(), true);
      this.tickerMap.reset(TickerType.EXPLOSION);
      if (this.velocityHandler != null) {
         this.velocityHandler.handle(packet);
      }

   }

   public void handle(VPacketPlayOutGameStateChange<?> packet) {
      if (this.abilityHandler != null) {
         this.abilityHandler.handle(packet);
      }

   }

   public void handle(VPacketPlayOutKeepAlive<?> packet) {
      long timestamp = System.currentTimeMillis();
      this.keepAliveMap.put(packet.getId(), timestamp);
   }

   public void handle(VPacketPlayOutMapChunk<?> packet) {
      if (this.world != null) {
         this.world.handle(packet);
      }

   }

   public void handle(VPacketPlayOutMapChunkBulk<?> packet) {
      if (this.world != null) {
         this.world.handle(packet);
      }

   }

   public void handle(VPacketPlayOutMultiBlockChange<?> packet) {
      if (this.world != null) {
         this.world.handle(packet);
      }

   }

   public void handle(VPacketPlayOutNamedEntitySpawn<?> packet) {
      if (this.tracker != null) {
         this.tracker.handle(packet);
      }

      AtomicCappedQueue<PacketLocation> recentMoveQueue = (AtomicCappedQueue)this.recentMoveMap.computeIfAbsent(packet.getId(), (id) -> {
         return new AtomicCappedQueue(80);
      });
      PacketLocation location = packet.toLocation(System.currentTimeMillis());
      recentMoveQueue.addLast(location);
   }

   public void handle(VPacketPlayOutOpenWindow<?> packet) {
      this.digging = false;
      this.lastInventoryOutTick = this.totalTicks;
   }

   public void handle(VPacketPlayOutPosition<?> packet) {
      long now = System.currentTimeMillis();
      JavaV.trim(this.teleportList, 1000);
      JavaV.trim(this.teleports, 1000);
      if (this.teleportHandler != null) {
         this.teleportHandler.handle(packet);
      }

      Teleport teleport = packet.toTeleport(this.tickerMap.get(TickerType.TOTAL), now);
      this.teleports.add(teleport);
      this.recentTeleports.addFirst(teleport);
      PlayerLocation playerLocation = packet.toLocation(this);
      this.teleportList.add(playerLocation);
      this.teleportTicks = 0;
      this.lastTeleport = now;
      this.lastVelX = this.lastVelY = this.lastVelZ = this.velY = 0.0D;
      TeleportCheck[] var6 = this.checkData.getTeleportChecks();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         TeleportCheck teleportCheck = var6[var8];
         teleportCheck.handle(packet, now);
      }

   }

   public void handle(VPacketPlayOutRemoveEntityEffect<?> packet) {
      if (this.effectHandler != null) {
         this.effectHandler.handle(packet);
      }

   }

   public void handle(VPacketPlayOutRespawn<?> packet) {
      if (this.world != null) {
         this.world.handle(packet);
      }

      if (this.abilityHandler != null) {
         this.abilityHandler.handle(packet);
      }

      this.inventoryOpen = false;
      this.blocking = false;
      this.lastRespawn = System.currentTimeMillis();
   }

   public void handle(VPacketPlayOutSetSlot<?> packet) {
      this.lastSetSlot = this.totalTicks;
   }

   public void handle(VPacketPlayOutSpawnPosition<?> packet) {
      long timestamp = System.currentTimeMillis();
      BlockPosition blockPosition = packet.getPosition();
      PlayerLocation playerLocation = new PlayerLocation(timestamp, this.totalTicks, (double)blockPosition.getX(), (double)blockPosition.getY(), (double)blockPosition.getZ(), 0.0F, 0.0F, (Boolean)null, false);
      this.teleportList.add(playerLocation);
      this.teleportTicks = 0;
      this.lastTeleport = System.currentTimeMillis();
      this.lastVelX = this.lastVelY = this.lastVelZ = this.velY = 0.0D;
      this.spawnLocation = packet.getPosition();
   }

   public void handle(VPacketPlayOutUnloadChunk<?> packet) {
      if (this.world != null) {
         this.world.handle(packet);
      }

   }

   public void handle(VPacketPlayOutUpdateAttributes<?> packet) {
      if (this.attributeHandler != null) {
         this.attributeHandler.handle(packet);
      }

   }

   public void handle(VPacketPlayOutEntityMetadata<?> packet) {
      if (this.metadataHandler != null) {
         this.metadataHandler.handle(packet);
      }

   }

   public void preProcess(VPacket<?> packet) {
      this.timestamp = System.currentTimeMillis();
   }

   public void postProcess(VPacket<?> packet) {
      PacketCheck[] var2 = this.checkData.getPacketChecks();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PacketCheck check = var2[var4];

         try {
            check.handle(packet, this.timestamp);
         } catch (Throwable var7) {
            Bukkit.getLogger().log(Level.WARNING, check.getType().getName() + " " + check.getSubType() + " (" + check.getFriendlyName() + ") failed to handle " + packet.getClass().getName() + " ", var7);
         }
      }

      if (packet instanceof VPacketPlayInFlying) {
         VPacketPlayInFlying<?> flying = (VPacketPlayInFlying)packet;
         if (!flying.isTeleport()) {
            JavaV.executeSafely(this.end, () -> {
               return " end of next tick";
            });
         }
      }

   }

   public void handle(VPacketPlayOutSpawnEntity<?> packet) {
      if (this.tracker != null) {
         this.tracker.handle(packet);
      }

   }

   public void handle(VPacketPlayOutSpawnEntityLiving<?> packet) {
      if (this.tracker != null) {
         this.tracker.handle(packet);
      }

   }

   public void handlePacketListeners(VPacket<?> packet) {
      PacketHandler[] handlers = this.checkData.getHandle(packet);
      if (handlers != null) {
         PacketHandler[] var3 = handlers;
         int var4 = handlers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PacketHandler listener = var3[var5];

            try {
               packet.handle(listener);
            } catch (Throwable var9) {
               Check check = (Check)listener;
               Bukkit.getLogger().log(Level.WARNING, check.getType().getName() + " " + check.getSubType() + " (" + check.getFriendlyName() + ") failed to handle " + packet.getClass().getName() + " ", var9);
            }
         }

      }
   }

   private void handleVelocity(double dx, double dy, double dz, boolean explosion) {
      if (!this.isVehicle()) {
         this.velY = dy;
         this.lastVelX = dx;
         this.lastVelZ = dz;
         this.horizontalVelocityTicks = 0;
         int currentTicks = this.totalTicks;
         Cuboid cuboid = Cuboid.withLimit(this.lastLocation, this.location, 16).move(0.0D, 1.5D, 0.0D).expand(1.25D, 1.0D, 1.25D);
         World world = this.player.getWorld();
         Runnable runnable = () -> {
            int ticksOffset = currentTicks - this.totalTicks;
            boolean blockAbove = this.velY > 0.0D && !cuboid.checkBlocks(this.player, world, (material) -> {
               return material == MaterialList.AIR;
            });
            if (!blockAbove && this.lastVelY == 0.0D) {
               this.lastVelY = this.velY;
               this.verticalVelocityTicks = ticksOffset;
            } else {
               this.lastVelY = 0.0D;
            }

         };
         if (Bukkit.isPrimaryThread()) {
            runnable.run();
         } else {
            NMSManager.getInstance().postToMainThread(runnable);
         }
      }

      this.tickerMap.reset(TickerType.VELOCITY);
      Velocity velocity = new Velocity(this.tickerMap.get(TickerType.TOTAL), System.currentTimeMillis(), dx, dy, dz, explosion);
      this.velocityQueue.add(velocity);
      this.velocityData.add(velocity.getClient());
      if (MathUtil.hypotSquared(dx, dy, dz) > 9.0E-4D) {
         this.velocityTicks = Math.min(this.velocityTicks, 0) - (int)Math.ceil(Math.pow(MathUtil.hypotSquared(dx, dy, dz) * 2.0D, 1.75D) * 4.0D);
         if (MathUtil.hypotSquared(dx, dz) > 9.0E-4D) {
            this.horizontalSpeedTicks = Math.min(this.horizontalSpeedTicks, 0) - (int)Math.ceil(Math.pow(MathUtil.hypotSquared(dx, dz) * 2.0D, 2.0D) * 8.0D);
         }
      }

   }

   public void handleItem(ItemStack itemStack) {
      Material type = itemStack.getType();
      if (this.lastFace >= 0 && this.lastFace <= Direction.values().length) {
         Direction direction = Direction.values()[this.lastFace];
         if (type != null && type != MaterialList.AIR && (type.isBlock() || type == MaterialList.WEB)) {
            BlockPosition placedBlock = this.lastBlockPosition.copy().move(direction, 1);
            this.reset(TickerType.PLACED_BLOCK);
            Cuboid cuboid = new Cuboid(this.location);
            if (type == MaterialList.WEB) {
               cuboid.add(-1.0D, 1.0D, -1.0D, 2.0D, -1.0D, 1.0D);
            } else {
               cuboid.add(-0.5D, 0.5D, -1.0D, 1.0D, -0.5D, 0.5D);
            }

            World world = this.player.getWorld();
            if (cuboid.containsBlock(world, placedBlock)) {
               this.reset(TickerType.PLACED_BLOCK_UNDER);
            }
         }
      }

      this.handleInteraction(type);
   }

   public void handleInteraction(Material type) {
      if (type == MaterialList.WATER_BUCKET) {
         this.reset(TickerType.WATER_BUCKET);
      } else if (type == MaterialList._FIREWORK) {
         this.tickerMap.set(TickerType.ELYTRA_BOOST, -10);
      }

   }

   public void setBrand(String brand) {
      this.brand = brand;
      this.clientData.setBrand(brand);
   }

   public void checkBrand(String brand) {
      this.checkData.getBrandCheck().handle(brand);
   }

   public boolean isFocused(Check check) {
      return this.focus == null || this.focus == check.getType() && check.getSubType().equals(this.focusSubType);
   }

   public boolean isTeleportingV2() {
      return this.tickerMap.get(TickerType.TELEPORT) == 0;
   }

   public boolean isTeleportingMST() {
      return this.tickerMap.get(TickerType.MOVES_SINCE_TELEPORT) == 0;
   }

   public void reset() {
      this.sprinting = false;
      this.tickerMap.reset(TickerType.SPRINT);
      this.sneaking = false;
   }

   public void reset(TickerType type) {
      this.tickerMap.reset(type);
   }

   public void loadData() {
      StorageEngine storageEngine = StorageEngine.getInstance();
      if (storageEngine.isConnected()) {
         Database database = storageEngine.getDatabase();
         if (BukkitUtil.hasPermission(this.player, "verus.alerts")) {
            database.loadAlerts(this);
         }

         if (storageEngine.getVerusConfig().isPersistence()) {
            database.loadData(this);
         }
      }

   }

   public void saveData() {
      StorageEngine storageEngine = StorageEngine.getInstance();
      if (storageEngine.isConnected() && storageEngine.getVerusConfig().isPersistence()) {
         Database database = storageEngine.getDatabase();
         if (this.banned) {
            database.removeData(this);
         } else {
            database.updateData(this);
         }
      }

   }

   public void release() {
      if (this.world != null) {
         this.world.release();
      }

   }

   public void fuckOff() {
      NMSManager<?> nmsManager = NMSManager.getInstance();
      nmsManager.close(this);
      this.enabled = false;
   }

   public void closeInventory() {
      this.player.closeInventory();
      this.inventoryOpen = false;
   }

   public int getMoveTicks() {
      return (int)Math.floor((double)Math.min(this.transactionPing, this.averageTransactionPing) / 125.0D);
   }

   /** @deprecated */
   @Deprecated
   public Integer getLag() {
      return (int)Math.floor(MathUtil.variance(0, this.connectionFrequency));
   }

   public void resetPingTicks() {
      this.shouldHaveReceivedPing.reset();
      this.pingTicks.reset();
      this.maxPingTicks.reset();
      this.maxPingTick2.reset();
   }

   public int getPingTicks() {
      return (Integer)this.pingTicks.get();
   }

   public int getMaxPingTicks() {
      return (Integer)this.maxPingTicks.get();
   }

   public int getMaxPingTicks2() {
      return (Integer)this.maxPingTick2.get();
   }

   public boolean shouldHaveReceivedPing() {
      return (Boolean)this.shouldHaveReceivedPing.get();
   }

   /** @deprecated */
   @Deprecated
   public boolean hasLag(long timestamp) {
      return this.lastFlying != 0L && this.lastDelayed != 0L && timestamp - this.lastDelayed < 110L;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasLag() {
      return this.hasLag(this.lastFlying);
   }

   /** @deprecated */
   @Deprecated
   public boolean hasFast(long timestamp) {
      return this.lastFlying != 0L && this.lastFast != 0L && timestamp - this.lastFast < 110L;
   }

   /** @deprecated */
   @Deprecated
   public boolean hasFast() {
      return this.hasFast(this.lastFlying);
   }

   /** @deprecated */
   @Deprecated
   @Warning
   public boolean isTeleporting() {
      return this.isTeleporting(1);
   }

   /** @deprecated */
   @Deprecated
   public boolean hasRecentTeleport(int multiplier, ILocationGround test) {
      if (test.isGround()) {
         return false;
      } else {
         int until = this.getTotalTicks() - this.getMaxPingTicks2() * multiplier;
         Iterator var4 = this.recentTeleports.iterator();

         while(var4.hasNext()) {
            Teleport teleport = (Teleport)var4.next();
            if (teleport.getTicks() < until) {
               break;
            }

            if (test.matches(teleport)) {
               return true;
            }
         }

         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean hasRecentTeleport(int multiplier, ILocationGround from, ILocationGround to) {
      if (from.isGround()) {
         return this.hasRecentTeleport(multiplier, to);
      } else {
         int until = this.getTotalTicks() - this.getMaxPingTicks2() * multiplier;
         Iterator var5 = this.recentTeleports.iterator();

         while(true) {
            if (var5.hasNext()) {
               Teleport teleport = (Teleport)var5.next();
               if (teleport.getTicks() >= until) {
                  if (!from.matches(teleport) && !to.matches(teleport)) {
                     continue;
                  }

                  return true;
               }
            }

            return false;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   @Warning
   public boolean isTeleporting(int multiplier) {
      return this.teleportTicks <= this.getMaxPingTicks() * multiplier;
   }

   public boolean isBlocking() {
      return this.blockTicks >= (this.getMaxPingTicks() + 2) * 2;
   }

   public boolean isLevitating() {
      return this.tickerMap.get(TickerType.LEVITATION) <= (this.getMaxPingTicks() + 2) * 2;
   }

   public boolean hadLevitation() {
      int value = this.tickerMap.get(TickerType.LEVITATION);
      return value > 0 && value <= (this.getMaxPingTicks() + 2) * 2;
   }

   public boolean hasJumpBoost() {
      return this.tickerMap.get(TickerType.JUMP_BOOST) <= this.getMaxPingTicks() * 2;
   }

   public boolean hadJumpBoost() {
      int value = this.tickerMap.get(TickerType.JUMP_BOOST);
      return value > 0 && value <= (this.getMaxPingTicks() + 2) * 2;
   }

   public boolean hadSpeedBoost() {
      int value = this.tickerMap.get(TickerType.SPEED_BOOST);
      return value > 0 && value <= (this.getMaxPingTicks() + 2) * 2;
   }

   public boolean hadAttributes() {
      int value = this.tickerMap.get(TickerType.ATTRIBUTES);
      return value > 0 && value <= (this.getMaxPingTicks() + 2) * 2;
   }

   public boolean isSlimePush() {
      return this.tickerMap.get(TickerType.SLIME_PUSH) < (this.getMaxPingTicks() + 2) * 2;
   }

   public boolean isSuffocating() {
      return this.tickerMap.get(TickerType.SUFFOCATING) <= this.getMaxPingTicks() * 2;
   }

   public boolean isRiptiding() {
      return this.tickerMap.get(TickerType.RIPTIDE) <= (this.getMaxPingTicks() + 2) * 3;
   }

   public boolean isVehicle() {
      return this.tickerMap.get(TickerType.VEHICLE) <= (this.getMaxPingTicks() + 2) * 2;
   }

   /** @deprecated */
   @Deprecated
   public boolean isSurvival(int multiplier) {
      return this.survivalTicks > this.getMaxPingTicks() * multiplier;
   }

   /** @deprecated */
   @Deprecated
   public boolean isSurvival() {
      return this.isSurvival(1);
   }

   /** @deprecated */
   @Deprecated
   public boolean isFlying() {
      return this.flyingTicks <= (this.getMaxPingTicks() + 2) * 3;
   }

   /** @deprecated */
   @Deprecated
   public boolean canFly() {
      return this.allowFlightTicks <= (this.getMaxPingTicks() + 2) * 3;
   }

   public boolean isGliding() {
      return this.tickerMap.get(TickerType.GLIDING) <= (this.getMaxPingTicks() + 2) * 5 || this.metadataHandler != null && (this.metadataHandler.isElytraFlying() || this.metadataHandler.isToggledElytra());
   }

   public boolean isFallFlying() {
      int ticks = (this.getMaxPingTicks() + 2) * 5;
      return this.fallFlying || this.tickerMap.get(TickerType.FALL_FLYING) <= ticks || this.tickerMap.get(TickerType.ELYTRA_EXIT) <= ticks;
   }

   public boolean isBoosting() {
      return this.tickerMap.get(TickerType.ELYTRA_BOOST) <= (this.getMaxPingTicks() + 2) * 5;
   }

   public boolean isHooked() {
      return this.tickerMap.get(TickerType.HOOKED) <= (this.getMaxPingTicks() + 2) * 3;
   }

   public boolean hasPlacedBlock(boolean withPing) {
      return this.tickerMap.get(TickerType.PLACED_BLOCK_UNDER) < (this.getPingTicks() + 1) * 2;
   }

   public boolean hasPlacedBucket() {
      return this.tickerMap.get(TickerType.WATER_BUCKET) <= this.getMaxPingTicks() * 2;
   }

   public boolean isSpawned() {
      return this.spawned > 600;
   }

   public boolean isSprinting(boolean definite) {
      return this.sprinting == null ? !definite : this.sprinting && (!definite || this.tickerMap.get(TickerType.SPRINT) + 2 < this.tickerMap.get(TickerType.TELEPORT)) || !definite && this.player.isSprinting();
   }

   public double getTotalViolations() {
      double total = 0.0D;
      Check[] var3 = this.checkData.getChecks();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Check check = var3[var5];
         if (CheckManager.getInstance().isEnabled(check)) {
            total += Math.max(check.getViolations(), 0.0D);
         }
      }

      return total;
   }

   public double getMovementSpeed() {
      double walkSpeed = (double)this.player.getWalkSpeed();
      double movementSpeed = NMSManager.getInstance().getMovementSpeed(this.player) * 2.0D;
      if (movementSpeed > walkSpeed) {
         this.tickerMap.reset(TickerType.ATTRIBUTES);
      }

      return movementSpeed;
   }

   public boolean isSneaking() {
      return this.sneaking != null && this.sneaking;
   }

   public boolean isVehicleNew() {
      return this.vehicleId != null || !this.vehicleIds.isEmpty();
   }

   public String getInfo() {
      String toReturn = VerusPlugin.COLOR + this.name + "'s Information: \n";
      if (this.version != null) {
         toReturn = toReturn + ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Version: " + ChatColor.WHITE + this.version.getName() + "\n";
      }

      ClientType clientType = this.clientData.getType();
      if (!clientType.isUnknown()) {
         toReturn = toReturn + ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Client Data: " + ChatColor.WHITE + clientType.getDisplay() + "\n";
      }

      String brand = this.clientData.getBrand();
      if (brand != null && !brand.isEmpty() && !brand.equals("Unknown")) {
         toReturn = toReturn + ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Brand: " + ChatColor.WHITE + brand + "\n";
      }

      if (this.enabled) {
         toReturn = toReturn + ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Average Ping: " + ChatColor.WHITE + this.getAverageTransactionPing() + "\n";
         toReturn = toReturn + ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Lag: " + ChatColor.WHITE + this.getLag() + ChatColor.GRAY + " (" + this.getConnectionFrequency().size() + ")\n";
      }

      PotionEffect[] effects = (PotionEffect[])this.effects.get();
      if (effects.length > 0) {
         toReturn = toReturn + ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Effects: " + ChatColor.WHITE + (String)Arrays.stream(effects).map(StringUtil::formatEffect).collect(Collectors.joining(ChatColor.GRAY + ", " + ChatColor.WHITE));
      } else {
         toReturn = toReturn + ChatColor.GRAY + "- " + VerusPlugin.COLOR + "Effects: " + ChatColor.WHITE + "None";
      }

      return toReturn;
   }

   public void debug(Supplier<String> data) {
      CachedSupplier<String> cachedSupplier = CachedSupplier.of(data);
      if (VerusTypeLoader.isDev()) {
         Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp).forEach((player) -> {
            player.sendMessage(ChatColor.GRAY + "(DEBUG) " + ChatColor.RESET + this.getName() + ": " + (String)cachedSupplier.get());
         });
      }

   }

   public Player getPlayer() {
      return this.player;
   }

   public UUID getUuid() {
      return this.uuid;
   }

   public String getName() {
      return this.name;
   }

   public CheckData getCheckData() {
      return this.checkData;
   }

   public TickerMap getTickerMap() {
      return this.tickerMap;
   }

   public ClientData getClientData() {
      return this.clientData;
   }

   public ClientVersion getVersion() {
      return this.version;
   }

   /** @deprecated */
   @Deprecated
   public Queue<PlayerLocation> getTeleportList() {
      return this.teleportList;
   }

   /** @deprecated */
   @Deprecated
   public BasicDeque<Teleport> getRecentTeleports() {
      return this.recentTeleports;
   }

   /** @deprecated */
   @Deprecated
   public Queue<Velocity> getVelocityQueue() {
      return this.velocityQueue;
   }

   /** @deprecated */
   @Deprecated
   public Queue<BiConsumer<Integer, Double>> getPingQueue() {
      return this.pingQueue;
   }

   /** @deprecated */
   @Deprecated
   public BasicDeque<Integer> getConnectionFrequency() {
      return this.connectionFrequency;
   }

   /** @deprecated */
   @Deprecated
   public Teleport getLastTeleport2() {
      return this.lastTeleport2;
   }

   /** @deprecated */
   @Deprecated
   public Queue<Teleport> getTeleports() {
      return this.teleports;
   }

   public Queue<Alert> getSpoofedAlerts() {
      return this.spoofedAlerts;
   }

   public Map<Integer, AtomicCappedQueue<PacketLocation>> getRecentMoveMap() {
      return this.recentMoveMap;
   }

   public CheckLocalQueue<ClientVelocity> getVelocityData() {
      return this.velocityData;
   }

   public Map<Long, Long> getKeepAliveMap() {
      return this.keepAliveMap;
   }

   public Map<Short, Transaction> getTransactionMap() {
      return this.transactionMap;
   }

   public Queue<ReachBase> getReachData() {
      return this.reachData;
   }

   public PlayerLocation getLocation() {
      return this.location;
   }

   public PlayerLocation getLastLocation() {
      return this.lastLocation;
   }

   public PlayerLocation getLastLastLocation() {
      return this.lastLastLocation;
   }

   public me.levansj01.verus.util.location.PacketLocation getCurrentLocation2() {
      return this.currentLocation2;
   }

   public me.levansj01.verus.util.location.PacketLocation getLastLocation2() {
      return this.lastLocation2;
   }

   public me.levansj01.verus.util.location.PacketLocation getLastLastLocation2() {
      return this.lastLastLocation2;
   }

   public BlockPosition getSpawnLocation() {
      return this.spawnLocation;
   }

   public PlayerData getLastAttacked() {
      return this.lastAttacked;
   }

   public long getLastFlying() {
      return this.lastFlying;
   }

   public long getLastLastFlying() {
      return this.lastLastFlying;
   }

   public long getLastDelayed() {
      return this.lastDelayed;
   }

   public long getLastFast() {
      return this.lastFast;
   }

   public long getLastTeleport() {
      return this.lastTeleport;
   }

   public long getLastClientTransaction() {
      return this.lastClientTransaction;
   }

   public long getLastRespawn() {
      return this.lastRespawn;
   }

   public long getLastKeepAliveTimestamp() {
      return this.lastKeepAliveTimestamp;
   }

   public int getLastTeleportTicks() {
      return this.lastTeleportTicks;
   }

   public int getFlyingTicks() {
      return this.flyingTicks;
   }

   public int getAllowFlightTicks() {
      return this.allowFlightTicks;
   }

   public int getVelocityTicks() {
      return this.velocityTicks;
   }

   public int getVerticalVelocityTicks() {
      return this.verticalVelocityTicks;
   }

   public int getHorizontalVelocityTicks() {
      return this.horizontalVelocityTicks;
   }

   public int getHorizontalSpeedTicks() {
      return this.horizontalSpeedTicks;
   }

   public int getTicks() {
      return this.ticks;
   }

   public int getTotalTicks() {
      return this.totalTicks;
   }

   public int getLastSentTransaction() {
      return this.lastSentTransaction;
   }

   public int getLastKeepAlive() {
      return this.lastKeepAlive;
   }

   public int getSurvivalTicks() {
      return this.survivalTicks;
   }

   public int getLastAttackTicks() {
      return this.lastAttackTicks;
   }

   public int getNonMoveTicks() {
      return this.nonMoveTicks;
   }

   public int getLastNonMoveTicks() {
      return this.lastNonMoveTicks;
   }

   public int getLastInventoryTick() {
      return this.lastInventoryTick;
   }

   public int getLastInventoryOutTick() {
      return this.lastInventoryOutTick;
   }

   public int getLastSetSlot() {
      return this.lastSetSlot;
   }

   public int getLastTransactionPing() {
      return this.lastTransactionPing;
   }

   public int getAverageTransactionPing() {
      return this.averageTransactionPing;
   }

   public int getPing() {
      return this.ping;
   }

   public int getLastPing() {
      return this.lastPing;
   }

   public int getAveragePing() {
      return this.averagePing;
   }

   public int getLastAttackedId() {
      return this.lastAttackedId;
   }

   public int getBlockTicks() {
      return this.blockTicks;
   }

   public int getLastFace() {
      return this.lastFace;
   }

   public int getReceivedTransactions() {
      return this.receivedTransactions;
   }

   public boolean isReceivedTransaction() {
      return this.receivedTransaction;
   }

   public boolean isWasVehicle() {
      return this.wasVehicle;
   }

   public boolean isSentTransaction() {
      return this.sentTransaction;
   }

   public boolean isMoved() {
      return this.moved;
   }

   public boolean isAimed() {
      return this.aimed;
   }

   public boolean isPlacing() {
      return this.placing;
   }

   public boolean isSwingDigging() {
      return this.swingDigging;
   }

   public boolean isAbortedDigging() {
      return this.abortedDigging;
   }

   public boolean isStoppedDigging() {
      return this.stoppedDigging;
   }

   public boolean isReady() {
      return this.ready;
   }

   public BlockPosition getDiggingBlock() {
      return this.diggingBlock;
   }

   public Direction getDiggingBlockFace() {
      return this.diggingBlockFace;
   }

   public Boolean getSprinting() {
      return this.sprinting;
   }

   public Boolean getSneaking() {
      return this.sneaking;
   }

   public ResetState<PotionEffect[]> getEffects() {
      return this.effects;
   }

   public ResetState<Boolean> getShouldHaveReceivedPing() {
      return this.shouldHaveReceivedPing;
   }

   public ResetState<Integer> getSpeedLevel() {
      return this.speedLevel;
   }

   public ResetState<Integer> getSlowLevel() {
      return this.slowLevel;
   }

   public ResetState<Integer> getJumpLevel() {
      return this.jumpLevel;
   }

   public ResetState<Integer> getMaxPingTick2() {
      return this.maxPingTick2;
   }

   public BlockPosition getLastBlockPosition() {
      return this.lastBlockPosition;
   }

   public boolean isBanned() {
      return this.banned;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean isCheckSpoofing() {
      return this.checkSpoofing;
   }

   public boolean isSpoofBan() {
      return this.spoofBan;
   }

   public boolean isResetVelocity() {
      return this.resetVelocity;
   }

   public boolean isDigging() {
      return this.digging;
   }

   public boolean isAlerts() {
      return this.alerts;
   }

   public boolean isDebug() {
      return this.debug;
   }

   public boolean isInventoryOpen() {
      return this.inventoryOpen;
   }

   public short getLastTransactionID() {
      return this.lastTransactionID;
   }

   public int getTeleportTicks() {
      return this.teleportTicks;
   }

   public int getLastFakeEntityDamageTicks() {
      return this.lastFakeEntityDamageTicks;
   }

   public int getSuffocationTicks() {
      return this.suffocationTicks;
   }

   public int getTransactionPing() {
      return this.transactionPing;
   }

   public int getSpawned() {
      return this.spawned;
   }

   public int getOldVehicleId() {
      return this.oldVehicleId;
   }

   public int getFallDamage() {
      return this.fallDamage;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public long getNanos() {
      return this.nanos;
   }

   public double getLastVelY() {
      return this.lastVelY;
   }

   public double getLastVelX() {
      return this.lastVelX;
   }

   public double getLastVelZ() {
      return this.lastVelZ;
   }

   public double getVelY() {
      return this.velY;
   }

   public String getBrand() {
      return this.brand;
   }

   public Check getSpoofBanCheck() {
      return this.spoofBanCheck;
   }

   public CheckType getFocus() {
      return this.focus;
   }

   public String getFocusSubType() {
      return this.focusSubType;
   }

   public Transaction getTransaction() {
      return this.transaction;
   }

   public Transaction getLastTransaction() {
      return this.lastTransaction;
   }

   public Transaction getNextTransaction() {
      return this.nextTransaction;
   }

   public Queue<Runnable> getStart() {
      return this.start;
   }

   public Queue<Runnable> getEnd() {
      return this.end;
   }

   public Queue<Runnable> getNextTrans() {
      return this.nextTrans;
   }

   public IVerusWorld getWorld() {
      return this.world;
   }

   public ITeleportHandler getTeleportHandler() {
      return this.teleportHandler;
   }

   public IVelocityHandler getVelocityHandler() {
      return this.velocityHandler;
   }

   public IAbilityHandler getAbilityHandler() {
      return this.abilityHandler;
   }

   public IEffectHandler getEffectHandler() {
      return this.effectHandler;
   }

   public ITracker getTracker() {
      return this.tracker;
   }

   public IAttributeHandler getAttributeHandler() {
      return this.attributeHandler;
   }

   public IMetadataHandler getMetadataHandler() {
      return this.metadataHandler;
   }

   public Queue<Integer> getVehicleIds() {
      return this.vehicleIds;
   }

   public Integer getVehicleId() {
      return this.vehicleId;
   }

   public ViaHandler.PlayerHandler getViaPlayerHandler() {
      return this.viaPlayerHandler;
   }

   public PlayerLocation getVehicleLocation() {
      return this.vehicleLocation;
   }

   public PlayerLocation getLastVehicleLocation() {
      return this.lastVehicleLocation;
   }

   public void setBanned(boolean banned) {
      this.banned = banned;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public void setCheckSpoofing(boolean checkSpoofing) {
      this.checkSpoofing = checkSpoofing;
   }

   public void setSpoofBan(boolean spoofBan) {
      this.spoofBan = spoofBan;
   }

   public void setResetVelocity(boolean resetVelocity) {
      this.resetVelocity = resetVelocity;
   }

   public void setDigging(boolean digging) {
      this.digging = digging;
   }

   public void setAlerts(boolean alerts) {
      this.alerts = alerts;
   }

   public void setDebug(boolean debug) {
      this.debug = debug;
   }

   public void setInventoryOpen(boolean inventoryOpen) {
      this.inventoryOpen = inventoryOpen;
   }

   public void setLastTransactionID(short lastTransactionID) {
      this.lastTransactionID = lastTransactionID;
   }

   public void setTeleportTicks(int teleportTicks) {
      this.teleportTicks = teleportTicks;
   }

   public void setLastFakeEntityDamageTicks(int lastFakeEntityDamageTicks) {
      this.lastFakeEntityDamageTicks = lastFakeEntityDamageTicks;
   }

   public void setSuffocationTicks(int suffocationTicks) {
      this.suffocationTicks = suffocationTicks;
   }

   public void setTransactionPing(int transactionPing) {
      this.transactionPing = transactionPing;
   }

   public void setSpawned(int spawned) {
      this.spawned = spawned;
   }

   public void setOldVehicleId(int oldVehicleId) {
      this.oldVehicleId = oldVehicleId;
   }

   public void setFallDamage(int fallDamage) {
      this.fallDamage = fallDamage;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public void setNanos(long nanos) {
      this.nanos = nanos;
   }

   public void setLastVelY(double lastVelY) {
      this.lastVelY = lastVelY;
   }

   public void setLastVelX(double lastVelX) {
      this.lastVelX = lastVelX;
   }

   public void setLastVelZ(double lastVelZ) {
      this.lastVelZ = lastVelZ;
   }

   public void setVelY(double velY) {
      this.velY = velY;
   }

   public void setSpoofBanCheck(Check spoofBanCheck) {
      this.spoofBanCheck = spoofBanCheck;
   }

   public void setFocus(CheckType focus) {
      this.focus = focus;
   }

   public void setFocusSubType(String focusSubType) {
      this.focusSubType = focusSubType;
   }
}
