package me.levansj01.verus.util;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.stream.Stream;
import me.levansj01.launcher.VerusLauncher;
import me.levansj01.verus.data.state.State;
import me.levansj01.verus.gui.impl.MainGUI;
import me.levansj01.verus.util.location.PacketLocation;
import me.levansj01.verus.util.location.PlayerLocation;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BukkitUtil {
   public static boolean hasEffect(State<PotionEffect[]> var0, PotionEffectType var1) {
      return hasEffect((PotionEffect[])var0.get(), var1.getId());
   }

   public static int getPotionLevel(Collection<PotionEffect> var0, int var1) {
      return (Integer)var0.stream().filter((var1x) -> {
         return var1x.getType().getId() == var1;
      }).map(PotionEffect::getAmplifier).findAny().orElse(-1) + 1;
   }

   public static boolean hasPermission(Player var0, String var1) {
      return isDev(var0) || var0.hasPermission(var1);
   }

   public static int getPotionLevel(PotionEffect[] var0, PotionEffectType var1) {
      return getPotionLevel(var0, var1.getId());
   }

   public static boolean hasEffect(Collection<PotionEffect> var0, int var1) {
      return var0.stream().map(PotionEffect::getType).map(PotionEffectType::getId).anyMatch((var1x) -> {
         return var1 == var1x;
      });
   }

   public static int getPotionLevel(State<PotionEffect[]> var0, int var1) {
      return getPotionLevel((PotionEffect[])var0.get(), var1);
   }

   public static PacketLocation fromPlayer2(Player var0) {
      Location var1 = var0.getLocation();
      return new PacketLocation(var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch(), var0.isOnGround(), true, true, false);
   }

   public static boolean hasEnchantment(Player var0, int var1) {
      HashSet var2 = Sets.newHashSet();
      ItemStack[] var3 = var0.getInventory().getArmorContents();
      ItemStack[] var4 = var3;
      int var5 = var3.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemStack var7 = var4[var6];
         if (var7 != null) {
            var2.addAll(var7.getEnchantments().keySet());
         }
      }

      return var2.stream().map(Enchantment::getId).anyMatch((var1x) -> {
         return var1 == var1x;
      });
   }

   public static boolean hasPermission(CommandSender var0, String var1) {
      return !(var0 instanceof Player) || hasPermission((Player)var0, var1);
   }

   public static void setMeta(Metadatable var0, String var1, boolean var2) {
      if (var2) {
         var0.setMetadata(var1, new FixedMetadataValue(VerusLauncher.getPlugin(), true));
      } else {
         var0.removeMetadata(var1, VerusLauncher.getPlugin());
      }

   }

   public static int getPotionLevel(PotionEffect[] var0, int var1) {
      PotionEffect[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PotionEffect var5 = var2[var4];
         if (var5.getType().getId() == var1) {
            return var5.getAmplifier() + 1;
         }
      }

      return 0;
   }

   public static boolean hasEffect(PotionEffect[] var0, int var1) {
      PotionEffect[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PotionEffect var5 = var2[var4];
         if (var5.getType().getId() == var1) {
            return true;
         }
      }

      return false;
   }

   public static int getPotionLevel(State<PotionEffect[]> var0, PotionEffectType var1) {
      return getPotionLevel((PotionEffect[])var0.get(), var1.getId());
   }

   /** @deprecated */
   @Deprecated
   public static boolean hasEnchantment(ItemStack var0, int var1) {
      return var0 == null ? false : var0.getEnchantments().keySet().stream().map(Enchantment::getId).anyMatch((var1x) -> {
         return var1 == var1x;
      });
   }

   public static boolean hasEnchantment(ItemStack var0, String var1) {
      if (var0 == null) {
         return false;
      } else {
         Map var2 = var0.getEnchantments();
         if (var2.isEmpty()) {
            return false;
         } else {
            Enchantment var3 = Enchantment.getByName(var1.toUpperCase());
            if (var3 == null) {
               return false;
            } else {
               Stream var10000 = var2.keySet().stream();
               Objects.requireNonNull(var3);
               return var10000.anyMatch(var3::equals);
            }
         }
      }
   }

   public static int getEnchantment(ItemStack var0, String var1) {
      if (var0 == null) {
         return 0;
      } else {
         Map var2 = var0.getEnchantments();
         if (var2.isEmpty()) {
            return 0;
         } else {
            Enchantment var3 = Enchantment.getByName(var1.toUpperCase());
            return var3 == null ? 0 : (Integer)var2.entrySet().stream().filter((var1x) -> {
               return var3.equals(var1x.getKey());
            }).map(Entry::getValue).findAny().orElse(0);
         }
      }
   }

   public static boolean isDev(Player var0) {
      return MainGUI.ALLOWED_UUIDS.contains(var0.getUniqueId());
   }

   public static PlayerLocation fromPlayer(Player var0) {
      Location var1 = var0.getLocation();
      return new PlayerLocation(System.currentTimeMillis(), 0, var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch(), var0.isOnGround(), false);
   }

   public static boolean hasPermissionMeta(Player var0, String var1) {
      return var0.hasMetadata(var1) && var0.hasPermission(var1);
   }

   public static int getPotionLevel(Collection<PotionEffect> var0, PotionEffectType var1) {
      return getPotionLevel(var0, var1.getId());
   }

   public static boolean hasEffect(State<PotionEffect[]> var0, int var1) {
      return hasEffect((PotionEffect[])var0.get(), var1);
   }
}
