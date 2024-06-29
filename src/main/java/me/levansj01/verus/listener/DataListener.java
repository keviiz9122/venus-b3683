package me.levansj01.verus.listener;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.client.ClientType;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.type.Loader;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.JavaV;
import me.levansj01.verus.verus2.data.player.TickerType;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class DataListener implements Listener {
   private static final String DEVELOPER_MESSAGE;
   private static final DataManager MANAGER;
   private static final List<String> FORGE_CHANNELS;

   public void sendDeveloperMessage(Player player) {
      player.sendMessage(DEVELOPER_MESSAGE);
   }

   @EventHandler(
      priority = EventPriority.LOWEST
   )
   public void onJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      NMSManager.getInstance().setAsyncPotionEffects(player);
      MANAGER.getExecutorService().submit(() -> {
         MANAGER.inject(player);
      });
      if (BukkitUtil.isDev(player)) {
         this.sendDeveloperMessage(player);
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onRegister(PlayerRegisterChannelEvent event) {
      Player player = event.getPlayer();
      String channel = event.getChannel();
      if (channel != null && FORGE_CHANNELS.contains(channel)) {
         PlayerData playerData = DataManager.getInstance().getPlayer(player.getPlayer());
         if (playerData != null) {
            playerData.getClientData().setType(ClientType.FORGE);
         }
      }

   }

   @EventHandler
   public void onExtend(BlockPistonExtendEvent event) {
      if (StorageEngine.getInstance().getVerusConfig().isSlimePushFix()) {
         this.slimePush(event.getBlock(), event.getBlocks());
      }

   }

   @EventHandler
   public void onRetract(BlockPistonRetractEvent event) {
      if (StorageEngine.getInstance().getVerusConfig().isSlimePushFix() && NMSManager.getInstance().getServerVersion().after(ServerVersion.v1_7_R4)) {
         this.slimePush(event.getBlock(), event.getBlocks());
      }

   }

   private void slimePush(Block piston, @NotNull List<Block> blocks) {
      if (!blocks.isEmpty()) {
         World world = piston.getWorld();
         Location pistonLocation = piston.getLocation();
         piston.getWorld().getPlayers().forEach((player) -> {
            Location location = player.getLocation();
            if (location.getWorld().equals(world) && location.distanceSquared(pistonLocation) < 100.0D) {
               Set<Material> stickyBlocks = MaterialList.STICKY;
               Iterator var6 = blocks.iterator();

               while(var6.hasNext()) {
                  Block block = (Block)var6.next();
                  if (stickyBlocks.contains(block.getType()) && location.distanceSquared(block.getLocation()) < 6.25D) {
                     PlayerData playerData = DataManager.getInstance().getPlayer(player);
                     if (playerData == null) {
                        break;
                     }

                     playerData.reset(TickerType.SLIME_PUSH);
                  }
               }
            }

         });
      }
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      MANAGER.getExecutorService().submit(() -> {
         MANAGER.uninject(player);
      });
   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.MONITOR
   )
   public void onDamage(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         DamageCause cause = event.getCause();
         PlayerData playerData;
         switch(cause) {
         case SUFFOCATION:
            playerData = MANAGER.getPlayer(player);
            if (playerData != null) {
               playerData.reset(TickerType.SUFFOCATING);
            }
            break;
         case FALL:
            playerData = MANAGER.getPlayer(player);
            if (playerData != null) {
               playerData.setFallDamage(NMSManager.getInstance().getCurrentTick());
            }
         }
      }

   }

   @EventHandler(
      ignoreCancelled = true,
      priority = EventPriority.MONITOR
   )
   public void onVelocityEvent(PlayerVelocityEvent event) {
      PlayerData playerData = MANAGER.getPlayer(event.getPlayer());
      if (playerData != null && playerData.getFallDamage() + 1 == NMSManager.getInstance().getCurrentTick()) {
         playerData.setFallDamage(0);
         if (event.getVelocity().getY() <= 0.08D && !StorageEngine.getInstance().getVerusConfig().isFixSlimeBlocks()) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onDeath(PlayerDeathEvent event) {
      PlayerData playerData = MANAGER.getPlayer(event.getEntity());
      if (playerData != null) {
         playerData.reset();
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR,
      ignoreCancelled = true
   )
   public void onTeleport(PlayerTeleportEvent event) {
      if (VerusTypeLoader.isDev()) {
         Location to = event.getTo();
         if (to.getPitch() == 0.0F) {
            to.setPitch((float)(Math.random() - 0.5D) * 0.01F);
         }

         if (to.getYaw() == 0.0F) {
            to.setYaw((float)(Math.random() - 0.5D) * 0.01F);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onFish(PlayerFishEvent event) {
      if (event.getCaught() instanceof Player) {
         Player player = (Player)event.getCaught();
         PlayerData playerData = MANAGER.getPlayer(player);
         if (playerData != null) {
            playerData.reset(TickerType.HOOKED);
         }
      }

   }

   @EventHandler
   public void onClick(InventoryClickEvent event) {
      Inventory clicked = event.getClickedInventory() != null ? event.getClickedInventory() : event.getInventory();
      if (clicked != null && clicked.getHolder() instanceof GUI) {
         GUI gui = (GUI)clicked.getHolder();
         gui.onClick(event);
         event.setCancelled(true);
      } else {
         InventoryView view = event.getView();
         if (view != null) {
            Inventory top = view.getTopInventory();
            if (top != null && top.getHolder() instanceof GUI) {
               event.setCancelled(true);
            }
         }
      }

   }

   static {
      DEVELOPER_MESSAGE = String.format((String)JavaV.stream(ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------", ChatColor.WHITE + "This server is running %s (%s)", ChatColor.GRAY + ChatColor.STRIKETHROUGH.toString() + "------------------------------------------").collect(Collectors.joining("\n")), VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Anticheat" + ChatColor.GRAY, Loader.getUsername());
      MANAGER = DataManager.getInstance();
      FORGE_CHANNELS = Arrays.asList("FML|HS", "FML", "FML|MP", "FORGE", "legacy:fml", "legacy:fml|hs", "legacy:forge");
   }
}
