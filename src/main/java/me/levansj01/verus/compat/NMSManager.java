package me.levansj01.verus.compat;

import com.google.common.cache.CacheBuilder;
import com.lunarclient.bukkitapi.LunarClientAPI;
import com.viaversion.viaversion.api.Via;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.api.API;
import me.levansj01.verus.api.impl.API1_4;
import me.levansj01.verus.compat.api.wrapper.BlockPosition;
import me.levansj01.verus.compat.api.wrapper.PlayerAbilities;
import me.levansj01.verus.compat.netty.NettyHandler;
import me.levansj01.verus.compat.netty.UnshadedNettyHandler;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.data.transaction.world.LazyData;
import me.levansj01.verus.data.version.ClientVersion;
import me.levansj01.verus.util.Cuboid;
import me.levansj01.verus.util.IBlockPosition;
import me.levansj01.verus.util.location.ILocation;
import me.levansj01.verus.util.viaversion.V1_17Remapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;
import protocolsupport.api.ProtocolSupportAPI;
import protocolsupport.api.ProtocolVersion;

public abstract class NMSManager<D> {
   private static NMSManager<?> nmsManager = null;
   protected ServerVersion serverVersion;
   private final NettyHandler<?> nettyHandler = this.newNettyHandler();
   protected boolean protocolsupportplugin;
   protected boolean viaversionplugin;
   protected boolean lunarClientAPI;
   protected boolean floodGateAPI;
   protected boolean fastUtil;
   protected boolean newViaAPI;
   private ViaHandler viaHandler;

   public static NMSManager<?> getInstance() {
      return nmsManager == null ? (nmsManager = newInstance()) : nmsManager;
   }

   private static NMSManager<?> newInstance() {
      try {
         String version = Bukkit.getServer().getClass().getName().split("\\.")[3];
         NMSManager<?> nmsManager = (NMSManager)Class.forName(NMSManager.class.getName().replace(".NMSManager", "." + version + ".NMSManager")).newInstance();
         ServerVersion serverVersion = ServerVersion.valueOf(version);
         nmsManager.setServerVersion(serverVersion);
         if (serverVersion.afterEq(ServerVersion.v1_17_R1) && Bukkit.getPluginManager().isPluginEnabled("ViaBackwards")) {
            try {
               V1_17Remapper.remap();
            } catch (Throwable var4) {
               VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to inject protocol changes into ViaVersion, ", var4);
            }
         }

         return nmsManager;
      } catch (Throwable var5) {
         throw new IllegalArgumentException(var5);
      }
   }

   public NMSManager() {
      PluginManager pluginManager = Bukkit.getPluginManager();
      this.protocolsupportplugin = pluginManager.isPluginEnabled("ProtocolSupport");
      this.viaversionplugin = pluginManager.isPluginEnabled("ViaVersion");
      this.lunarClientAPI = pluginManager.isPluginEnabled("LunarClient-API");
      this.floodGateAPI = pluginManager.isPluginEnabled("floodgate");

      try {
         Class.forName("com.viaversion.viaversion.api.Via");
         this.newViaAPI = true;
      } catch (ClassNotFoundException var5) {
      }

      try {
         Class.forName("org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap");
         this.fastUtil = true;
      } catch (ClassNotFoundException var4) {
      }

      if (this.viaversionplugin) {
         try {
            this.viaHandler = new ViaHandler();
         } catch (Throwable var3) {
            VerusLauncher.getPlugin().getLogger().log(Level.SEVERE, "Failed to initialize ViaHandler, you may need to increase packet limits.");
         }
      }

   }

   public int getMinYLevel() {
      return 0;
   }

   public abstract boolean isRunning();

   public void syncCommands(List<Command> commands) {
   }

   public <C> NettyHandler<C> getNettyHandler() {
      return this.nettyHandler;
   }

   protected NettyHandler<?> newNettyHandler() {
      return new UnshadedNettyHandler();
   }

   public ExecutorService getDataExecutorService(String name) {
      return Executors.newCachedThreadPool(this.nettyHandler.newThreadFactory(name));
   }

   public boolean isRunningLunarClient(Player player) {
      return this.lunarClientAPI && LunarClientAPI.getInstance().isRunningLunarClient(player);
   }

   protected abstract ClientVersion defaultVersion();

   protected abstract int versionNumber();

   public ClientVersion getVersion(Player player) {
      if (this.viaversionplugin) {
         int version;
         if (this.newViaAPI) {
            version = Via.getAPI().getPlayerVersion(player.getUniqueId());
         } else {
            version = us.myles.ViaVersion.api.Via.getAPI().getPlayerVersion(player.getUniqueId());
         }

         if (version <= 0) {
            return this.defaultVersion();
         }

         if (version <= 5) {
            return ClientVersion.V1_7;
         }

         if (version <= 47) {
            return ClientVersion.V1_8;
         }

         if (!this.protocolsupportplugin || version != this.versionNumber()) {
            return this.getById(version);
         }
      }

      if (this.protocolsupportplugin) {
         ProtocolVersion version = ProtocolSupportAPI.getProtocolVersion(player);
         if (version == null) {
            return ClientVersion.VERSION_UNSUPPORTED;
         } else {
            return this.getByProtocolSupport(version.name(), version.getId());
         }
      } else {
         return this.defaultVersion();
      }
   }

   protected ClientVersion getByProtocolSupport(String versionName, int id) {
      byte var4 = -1;
      switch(versionName.hashCode()) {
      case -374160366:
         if (versionName.equals("MINECRAFT_1_11_1")) {
            var4 = 9;
         }
         break;
      case -374159405:
         if (versionName.equals("MINECRAFT_1_12_1")) {
            var4 = 11;
         }
         break;
      case -374159404:
         if (versionName.equals("MINECRAFT_1_12_2")) {
            var4 = 12;
         }
         break;
      case -374158444:
         if (versionName.equals("MINECRAFT_1_13_1")) {
            var4 = 14;
         }
         break;
      case -374158443:
         if (versionName.equals("MINECRAFT_1_13_2")) {
            var4 = 15;
         }
         break;
      case -374157483:
         if (versionName.equals("MINECRAFT_1_14_1")) {
            var4 = 17;
         }
         break;
      case -374157482:
         if (versionName.equals("MINECRAFT_1_14_2")) {
            var4 = 18;
         }
         break;
      case -374157481:
         if (versionName.equals("MINECRAFT_1_14_3")) {
            var4 = 19;
         }
         break;
      case -374157480:
         if (versionName.equals("MINECRAFT_1_14_4")) {
            var4 = 20;
         }
         break;
      case -374156522:
         if (versionName.equals("MINECRAFT_1_15_1")) {
            var4 = 22;
         }
         break;
      case -374156521:
         if (versionName.equals("MINECRAFT_1_15_2")) {
            var4 = 23;
         }
         break;
      case -374155561:
         if (versionName.equals("MINECRAFT_1_16_1")) {
            var4 = 25;
         }
         break;
      case -374155560:
         if (versionName.equals("MINECRAFT_1_16_2")) {
            var4 = 26;
         }
         break;
      case -374155559:
         if (versionName.equals("MINECRAFT_1_16_3")) {
            var4 = 27;
         }
         break;
      case -374155558:
         if (versionName.equals("MINECRAFT_1_16_4")) {
            var4 = 28;
         }
         break;
      case -374154600:
         if (versionName.equals("MINECRAFT_1_17_1")) {
            var4 = 30;
         }
         break;
      case -374153639:
         if (versionName.equals("MINECRAFT_1_18_1")) {
            var4 = 32;
         }
         break;
      case -374153638:
         if (versionName.equals("MINECRAFT_1_18_2")) {
            var4 = 33;
         }
         break;
      case -373938841:
         if (versionName.equals("MINECRAFT_1_7_10")) {
            var4 = 1;
         }
         break;
      case 218893493:
         if (versionName.equals("MINECRAFT_FUTURE")) {
            var4 = 34;
         }
         break;
      case 591949304:
         if (versionName.equals("MINECRAFT_1_8")) {
            var4 = 2;
         }
         break;
      case 591949305:
         if (versionName.equals("MINECRAFT_1_9")) {
            var4 = 3;
         }
         break;
      case 1170559071:
         if (versionName.equals("MINECRAFT_1_10")) {
            var4 = 7;
         }
         break;
      case 1170559072:
         if (versionName.equals("MINECRAFT_1_11")) {
            var4 = 8;
         }
         break;
      case 1170559073:
         if (versionName.equals("MINECRAFT_1_12")) {
            var4 = 10;
         }
         break;
      case 1170559074:
         if (versionName.equals("MINECRAFT_1_13")) {
            var4 = 13;
         }
         break;
      case 1170559075:
         if (versionName.equals("MINECRAFT_1_14")) {
            var4 = 16;
         }
         break;
      case 1170559076:
         if (versionName.equals("MINECRAFT_1_15")) {
            var4 = 21;
         }
         break;
      case 1170559077:
         if (versionName.equals("MINECRAFT_1_16")) {
            var4 = 24;
         }
         break;
      case 1170559078:
         if (versionName.equals("MINECRAFT_1_17")) {
            var4 = 29;
         }
         break;
      case 1170559079:
         if (versionName.equals("MINECRAFT_1_18")) {
            var4 = 31;
         }
         break;
      case 1927600109:
         if (versionName.equals("MINECRAFT_1_7_5")) {
            var4 = 0;
         }
         break;
      case 1927602027:
         if (versionName.equals("MINECRAFT_1_9_1")) {
            var4 = 4;
         }
         break;
      case 1927602028:
         if (versionName.equals("MINECRAFT_1_9_2")) {
            var4 = 5;
         }
         break;
      case 1927602030:
         if (versionName.equals("MINECRAFT_1_9_4")) {
            var4 = 6;
         }
      }

      switch(var4) {
      case 0:
      case 1:
         return ClientVersion.V1_7;
      case 2:
         return ClientVersion.V1_8;
      case 3:
      case 4:
      case 5:
      case 6:
         return ClientVersion.V1_9;
      case 7:
         return ClientVersion.V1_10;
      case 8:
      case 9:
         return ClientVersion.V1_11;
      case 10:
      case 11:
      case 12:
         return ClientVersion.V1_12;
      case 13:
      case 14:
      case 15:
         return ClientVersion.V1_13;
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
         return ClientVersion.V1_14;
      case 21:
      case 22:
      case 23:
         return ClientVersion.V1_15;
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
         return ClientVersion.V1_16;
      case 29:
      case 30:
         return ClientVersion.V1_17;
      case 31:
      case 32:
      case 33:
         return ClientVersion.V1_18;
      case 34:
         return this.defaultVersion().next();
      default:
         return id > 47 ? this.getById(id) : ClientVersion.VERSION_UNSUPPORTED;
      }
   }

   protected ClientVersion getById(int id) {
      if (id >= 759) {
         return ClientVersion.V1_19;
      } else if (id >= 757) {
         return ClientVersion.V1_18;
      } else if (id >= 755) {
         return ClientVersion.V1_17;
      } else if (id >= 735) {
         return ClientVersion.V1_16;
      } else if (id >= 573) {
         return ClientVersion.V1_15;
      } else if (id >= 477) {
         return ClientVersion.V1_14;
      } else if (id >= 393) {
         return ClientVersion.V1_13;
      } else if (id >= 335) {
         return ClientVersion.V1_12;
      } else if (id >= 315) {
         return ClientVersion.V1_11;
      } else if (id >= 210) {
         return ClientVersion.V1_10;
      } else if (id >= 107) {
         return ClientVersion.V1_9;
      } else {
         return id >= 47 ? ClientVersion.V1_8 : ClientVersion.V1_7;
      }
   }

   public abstract boolean isLoaded(World var1, int var2, int var3);

   public Material getTypeWithAPI(Player player, World world, IBlockPosition block) {
      API api;
      if ((api = API.getAPI()) instanceof API1_4) {
         MaterialData data = api.getFakeBlock(player, world, block.getX(), block.getY(), block.getZ());
         if (data != null) {
            return data.getItemType();
         }
      }

      return this.getType(player, world, block);
   }

   protected abstract Material getType(Player var1, World var2, IBlockPosition var3);

   public abstract MaterialData toData(D var1);

   public D fromID(int id) {
      throw new UnsupportedOperationException();
   }

   public LazyData toLazy(D data, State<Integer> id) {
      throw new UnsupportedOperationException();
   }

   public LazyData toLazy(D data) {
      throw new UnsupportedOperationException();
   }

   public LazyData fromIDToLazy(int id) {
      return this.toLazy(this.fromID(id), State.of(id));
   }

   public D place(ItemStack stack, Player player, BlockPosition pos, int face, float blockX, float blockY, float blockZ) {
      throw new UnsupportedOperationException();
   }

   public LazyData getPlace(ItemStack stack, Player player, BlockPosition pos, int face, float blockX, float blockY, float blockZ) {
      D data = this.place(stack, player, pos, face, blockX, blockY, blockZ);
      return data == null ? null : this.toLazy(data);
   }

   public MaterialData getTypeAndDataWithAPI(Player player, World world, IBlockPosition block) {
      API api;
      if ((api = API.getAPI()) instanceof API1_4) {
         MaterialData data = api.getFakeBlock(player, world, block.getX(), block.getY(), block.getZ());
         if (data != null) {
            return data;
         }
      }

      return this.getTypeAndData(player, world, block);
   }

   protected abstract MaterialData getTypeAndData(Player var1, World var2, IBlockPosition var3);

   public abstract float getDamage(Player var1, World var2, IBlockPosition var3);

   public abstract double getMovementSpeed(Player var1);

   public abstract double getKnockbackResistance(Player var1);

   public abstract boolean setOnGround(Player var1, boolean var2);

   public abstract boolean setInWater(Player var1, boolean var2);

   public abstract float getFrictionFactor(Block var1);

   public abstract float getFrictionFactor(Player var1, World var2, IBlockPosition var3);

   public abstract void sendTransaction(Player var1, short var2);

   public void sendTeleport(Player player, ILocation loc) {
      throw new UnsupportedOperationException();
   }

   public abstract void sendBlockUpdate(Player var1, World var2, int var3, int var4, int var5);

   public abstract void sendBlockUpdate(Player var1, Location var2);

   public abstract Cuboid getBoundingBox(Player var1, World var2, IBlockPosition var3);

   public abstract PlayerAbilities getAbilities(Player var1);

   public boolean isGliding(Player player) {
      return false;
   }

   public boolean isRiptiding(Player player) {
      return false;
   }

   public float getJumpFactor(Player player, World world, IBlockPosition block) {
      return 1.0F;
   }

   public <K, V> Map<K, V> createCache(Long access, Long write) {
      CacheBuilder<K, V> builder = CacheBuilder.newBuilder();
      if (access != null) {
         builder.expireAfterAccess(access, TimeUnit.MILLISECONDS);
      }

      if (write != null) {
         builder.expireAfterWrite(write, TimeUnit.MILLISECONDS);
      }

      return builder.build().asMap();
   }

   public <V> Map<Long, V> long2ObjectHashMap(int initialCapacity, float loadFactor) {
      return (Map)(this.fastUtil ? new Long2ObjectOpenHashMap(initialCapacity, loadFactor) : new HashMap());
   }

   public void setAsyncPotionEffects(Player player) {
   }

   public abstract void inject(PlayerData var1);

   public abstract void uninject(PlayerData var1);

   public abstract void postToMainThread(Runnable var1);

   public abstract Runnable scheduleEnd(Runnable var1);

   public abstract void close(Player var1);

   public void close(PlayerData playerData) {
      this.close(playerData.getPlayer());
   }

   public abstract void updatePing(Player var1, int var2);

   public void updatePing(PlayerData data) {
      this.updatePing(data.getPlayer(), data.getPing());
   }

   public abstract int getCurrentTick();

   public ItemStack getOffHand(Player player) {
      return null;
   }

   public boolean rayTrace(World world, double x, double y, double z, double dX, double dY, double dZ, boolean b1, boolean b2, boolean b3) {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty(World world, Entity entity, Cuboid boundingBox, IBlockPosition block) {
      throw new UnsupportedOperationException();
   }

   public boolean isWaterLogged(World world, IBlockPosition blockPosition) {
      return false;
   }

   public ServerVersion getServerVersion() {
      return this.serverVersion;
   }

   public boolean isProtocolsupportplugin() {
      return this.protocolsupportplugin;
   }

   public boolean isViaversionplugin() {
      return this.viaversionplugin;
   }

   public boolean isLunarClientAPI() {
      return this.lunarClientAPI;
   }

   public boolean isFloodGateAPI() {
      return this.floodGateAPI;
   }

   public boolean isFastUtil() {
      return this.fastUtil;
   }

   public boolean isNewViaAPI() {
      return this.newViaAPI;
   }

   public ViaHandler getViaHandler() {
      return this.viaHandler;
   }

   public void setServerVersion(ServerVersion serverVersion) {
      this.serverVersion = serverVersion;
   }
}
