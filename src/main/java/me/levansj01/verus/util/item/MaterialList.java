package me.levansj01.verus.util.item;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.compat.ServerVersion;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;

public class MaterialList {
   public static final Material _FIREWORK = findNew("FIREWORK", "FIREWORK_ROCKET");
   public static final Material SHIELD = find("SHIELD");
   public static final Material PAPER = find("PAPER");
   public static final Set<Material> BOOKS;
   public static final Material TRIDENT = find("TRIDENT");
   public static final Material AIR = find("AIR");
   public static final Set<Material> STICKY;
   public static final Material STAINED_GLASS_PANE = find("STAINED_GLASS_PANE");
   public static final Set<Material> BED;
   public static final Set<Material> BAD_VELOCITY;
   public static final Material DIAMOND_SWORD = find("DIAMOND_SWORD");
   public static final Set<Material> INPASSABLE;
   public static final Set<Material> INVALID_JUMP;
   public static final Set<Material> FULLY_PASSABLE;
   public static final Material WEB = find("WEB");
   public static Set<Material> INVALID_VELOCITY;
   public static final Material BARRIER = find("BARRIER");
   public static final Set<Material> SLABS;
   public static final Material ELYTRA = findNew("ELYTRA");
   public static final Material BOW = find("BOW");
   public static final Set<Material> INVALID_SHAPE;
   public static final MaterialData AIR_DATA;
   public static final Set<Material> SHULKER_BOX;
   public static final Set<Material> PLACEABLE;
   public static final Set<Material> LIQUIDS;
   public static final Set<Material> STAIRS;
   public static final Set<Material> GATES;
   public static final Set<Material> ON_WALL;
   public static final Material WATER_BUCKET = findNew("WATER_BUCKET");
   public static final Set<Material> FENCES;
   public static final Material VINE = find("VINE");
   public static Set<Material> BOBBLE;
   public static final Material REDSTONE = find("REDSTONE");
   public static final Material BUBBLE_COLUMN = find("BUBBLE_COLUMN");
   public static final Set<Material> BOUND_UP;
   public static final Set<Material> FLAT;
   public static final Set<Material> ICE;
   public static final Set<Material> SOUL_SAND;
   public static final Material ARROW = find("ARROW");
   public static final Material BOOK = find("BOOK");
   public static final Material POTION = find("POTION");
   public static final Material SLIME_BLOCK = find("SLIME_BLOCK");
   public static final Material SNOW = find("SNOW_BLOCK");
   public static final Set<Material> CLIMBABLE;
   public static final Material PURPLE_FUCKING_SHULKER = findNew("PURPLE_SHULKER_BOX");

   public static boolean canPlace(ItemStack var0) {
      Material var1 = var0.getType();
      if (var1 == POTION && var0.getDurability() > 0) {
         Potion var2;
         try {
            var2 = Potion.fromItemStack(var0);
         } catch (IllegalArgumentException var4) {
            return false;
         }

         return !var2.isSplash();
      } else {
         return PLACEABLE.contains(var1);
      }
   }

   private static Set<Material> union(Collection<Material> var0, Collection<Material>... var1) {
      EnumSet var2 = EnumSet.copyOf(var0);
      Collection[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Collection var6 = var3[var5];
         var2.addAll(var6);
      }

      return var2;
   }

   private static Material fromName(String var0) {
      if (NMSManager.getInstance().getServerVersion().afterEq(ServerVersion.v1_13_R2)) {
         Material var1 = Material.getMaterial("LEGACY_" + var0);
         if (var1 != null) {
            return var1;
         }
      }

      return Material.getMaterial(var0);
   }

   public static Set<Material> process(String... var0) {
      return (Set)Arrays.stream(var0).map(MaterialList::fromName).filter(Objects::nonNull).collect(Collectors.toCollection(() -> {
         return EnumSet.noneOf(Material.class);
      }));
   }

   static {
      AIR_DATA = new MaterialData(AIR);
      STICKY = process("SLIME_BLOCK", "HONEY_BLOCK");
      BED = process("BED", "WHITE_BED", "ORANGE_BED", "MAGENTA_BED", "LIGHT_BLUE_BED", "YELLOW_BED", "LIME_BED", "PINK_BED", "GRAY_BED", "LIGHT_GRAY_BED", "CYAN_BED", "PURPLE_BED", "BLUE_BED", "BROWN_BED", "GREEN_BED", "RED_BED", "BLACK_BED");
      ON_WALL = process("LEVER", "TRIPWIRE_HOOK", "LADDER", "TORCH", "WALL_TORCH", "SOUL_WALL_TORCH", "REDSTONE_WALL_TORCH", "REDSTONE_TORCH_ON", "REDSTONE_TORCH_OFF", "WOODEN_BUTTON", "STONE_BUTTON", "SPRUCE_BUTTON", "BIRCH_BUTTON", "JUNGLE_BUTTON", "ACACIA_BUTTON", "DARK_OAK_BUTTON", "CRIMSON_BUTTON", "WARPED_BUTTON", "BANNER", "WHITE_BANNER", "ORANGE_BANNER", "MAGENTA_BANNER", "LIGHT_BLUE_BANNER", "LIME_BANNER", "PINK_BANNER", "GRAY_BANNER", "LIGHT_GRAY_BANNER", "CYAN_BANNER", "PURPLE_BANNER", "BLUE_BANNER", "BROWN_BANNER", "GREEN_BANNER", "RED_BANNER", "BLACK_BANNER", "SIGN", "OAK_SIGN", "SPRUCE_SIGN", "BIRCH_SIGN", "JUNGLE_SIGN", "ACACIA_SIGN", "DARK_OAK_SIGN", "CRIMSON_SIGN", "WARPED_SIGN", "TRAPDOOR", "OAK_TRAPDOOR", "IRON_TRAPDOOR", "SPURCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OUT_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", "VINE", "TWISTING_VINES", "WEEPING_VINES");
      INVALID_SHAPE = process("HONEY_BLOCK", "HONEYCOMB_BLOCK", "FARMLAND", "SOIL", "COCOA", "ACACIA_STAIRS", "BIRCH_WOOD_STAIRS", "BRICK_STAIRS", "COBBLESTONE_STAIRS", "DARK_OAK_STAIRS", "JUNGLE_WOOD_STAIRS", "NETHER_BRICK_STAIRS", "QUARTZ_STAIRS", "SANDSTONE_STAIRS", "RED_SANDSTONE_STAIRS", "SMOOTH_STAIRS", "SPRUCE_WOOD_STAIRS", "WOOD_STAIRS", "SMOOTH_STAIRS", "PURPUR_STAIRS", "SMOOTH_QUARTZ_STAIRS", "SMOOTH_RED_SANDSTONE_STAIRS", "SMOOTH_SANDSTONE_STAIRS", "WARPED_STAIRS", "CRIMSON_STAIRS", "PRISMARINE_STAIRS", "PRISMARINE_BRICK_STAIRS", "DARK_PRISMARINE_STAIRS", "POLISHED_GRANITE_STAIRS", "MOSSY_STONE_BRICK_STAIRS", "POLISHED_DIORITE_STAIRS", "END_STONE_BRICK_STAIRS", "GRANITE_STAIRS", "ANDESITE_STAIRS", "RED_NETHER_BRICK_STAIRS", "POLISHED_ANDESITE_STAIRS", "DIORITE_STAIRS", "BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_BRICK_STAIRS", "CUT_COPPER_STAIRS", "EXPOSED_CUT_COPPER_STAIRS", "WEATHERED_CUT_COPPER_STAIRS", "OXIDIZED_CUT_COPPER_STAIRS", "WAXED_CUT_COPPER_STAIRS", "WAXED_EXPOSED_CUT_COPPER_STAIRS", "WAXED_WEATHERED_CUT_COPPER_STAIRS", "WAXED_OXIDIZED_CUT_COPPER_STAIRS", "COBBLED_DEEPSLATE_STAIRS", "POLISHED_DEEPSLATE_STAIRS", "DEEPSLATE_BRICK_STAIRS", "DEEPSLATE_TILE_STAIRS", "SMOOTH_STONE_SLAB", "SMOOTH_SANDSTONE_SLAB", "STONECUTTER", "BELL", "LANTERN", "CONDUIT", "LECTERN", "CAMPFIRE", "SOUL_CAMPFIRE", "IRON_TRAPDOOR", "OAK_TRAPDOOR", "SPRUCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OAK_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", "MANGROVE_TRAPDOOR", "DAYLIGHT_DETECTOR", "PISTON_EXTENSION", "PISTON_BASE", "PISTON_MOVING_PIECE", "PISTON_STICKY_BASE", "GRASS_PATH", "SNOW", "STEP", "WOOD_STEP", "CARPET", "CHEST", "ENDER_CHEST", "TRAPPED_CHEST", "ENDER_PORTAL_FRAME", "TRAP_DOOR", "SLIME_BLOCK", "WATER_LILY", "REDSTONE_COMPARATOR", "CAULDRON", "STATIONARY_WATER", "FENCE", "HOPPER", "BREWING_STAND", "BREWING_STAND_ITEM", "ACACIA_FENCE", "BIRCH_FENCE", "COBBLE_WALL", "DARK_OAK_FENCE", "IRON_FENCE", "JUNGLE_FENCE", "NETHER_FENCE", "SPRUCE_FENCE", "UNPOWERED_REPEATER", "POWERED_REPEATER", "DIODE_BLOCK_ON", "DIODE_BLOCK_OFF", "REDSTONE_COMPARATOR_OFF", "REDSTONE_COMPARATOR_ON", "SHULKER_BOX", "WHITE_SHULKER_BOX", "ORANGE_SHULKER_BOX", "MAGENTA_SHULKER_BOX", "LIGHT_BLUE_SHULKER_BOX", "YELLOW_SHULKER_BOX", "LIME_SHULKER_BOX", "PINK_SHULKER_BOX", "GRAY_SHULKER_BOX", "SILVER_SHULKER_BOX", "CYAN_SHULKER_BOX", "PURPLE_SHULKER_BOX", "BLUE_SHULKER_BOX", "BROWN_SHULKER_BOX", "GREEN_SHULKER_BOX", "RED_SHULKER_BOX", "BLACK_SHULKER_BOX", "CACTUS", "CAKE_BLOCK", "FLOWER_POT", "ENCHANTMENT_TABLE", "SKULL", "SKULL_ITEM", "STAINED_GLASS_PANE", "ACACIA_FENCE_GATE", "BIRCH_FENCE_GATE", "DARK_OAK_FENCE_GATE", "FENCE_GATE", "JUNGLE_FENCE_GATE", "SPRUCE_FENCE_GATE", "SEA_PICKLE", "CANDLE", "WHITE_CANDLE", "ORANGE_CANDLE", "MAGENTA_CANDLE", "LIGHT_BLUE_CANDLE", "YELLOW_CANDLE", "LIME_CANDLE", "PINK_CANDLE", "GRAY_CANDLE", "LIGHT_GRAY_CANDLE", "CYAN_CANDLE", "PURPLE_CANDLE", "BLUE_CANDLE", "BROWN_CANDLE", "GREEN_CANDLE", "RED_CANDLE", "BLACK_CANDLE", "MOSS_CARPET", "AMETHYST_CLUSTER", "LARGE_AMETHYST_BUD", "MEDIUM_AMETHYST_BUD", "SMALL_AMETHYST_BUD");
      INVALID_JUMP = process("SLIME_BLOCK", "BUBBLE_COLUMN", "PISTON_EXTENSION", "PISTON_BASE", "PISTON_MOVING_PIECE", "PISTON_STICKY_BASE", "POWDER_SNOW");
      SHULKER_BOX = process("WHITE_SHULKER_BOX", "ORANGE_SHULKER_BOX", "MAGENTA_SHULKER_BOX", "LIGHT_BLUE_SHULKER_BOX", "YELLOW_SHULKER_BOX", "LIME_SHULKER_BOX", "PINK_SHULKER_BOX", "GRAY_SHULKER_BOX", "SILVER_SHULKER_BOX", "CYAN_SHULKER_BOX", "PURPLE_SHULKER_BOX", "BLUE_SHULKER_BOX", "BROWN_SHULKER_BOX", "GREEN_SHULKER_BOX", "RED_SHULKER_BOX", "BLACK_SHULKER_BOX", "SHULKER_BOX");
      FENCES = process("ACACIA_FENCE", "BIRCH_FENCE", "COBBLE_WALL", "DARK_OAK_FENCE", "FENCE", "IRON_FENCE", "JUNGLE_FENCE", "NETHER_FENCE", "SPRUCE_FENCE", "STAINED_GLASS_PANE", "THIN_GLASS", "OAK_FENCE", "CRIMSON_FENCE", "WARPED_FENCE", "NETHER_BRICK_FENCE", "COBBLESTONE_WALL", "MOSSY_COBBLESTONE_WALL", "BRICK_WALL", "PRISMARINE_WALL", "RED_SANDSTONE_WALL", "MOSSY_STONE_BRICK_WALL", "GRANITE_WALL", "STONE_BRICK_WALL", "NETHER_BRICK_WALL", "ANDESITE_WALL", "RED_NETHER_BRICK_WALL", "SANDSTONE_WALL", "END_STONE_BRICK_WALL", "DIORITE_WALL", "BLACKSTONE_WALL", "POLISHED_BLACKSTONE_WALL", "POLISHED_BLACKSTONE_BRICK_WALL", "COBBLED_DEEPSLATE_WALL", "POLISHED_DEEPSLATE_WALL", "DEEPSLATE_BRICK_WALL", "DEEPSLATE_TILE_WALL");
      GATES = process("ACACIA_FENCE_GATE", "BIRCH_FENCE_GATE", "DARK_OAK_FENCE_GATE", "FENCE_GATE", "OAK_FENCE_GATE", "JUNGLE_FENCE_GATE", "SPRUCE_FENCE_GATE", "CRIMSON_FENCE_GATE", "WARPED_FENCE_GATE");
      BAD_VELOCITY = process("WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA", "WEB", "SLIME_BLOCK", "LADDER", "TRAP_DOOR", "IRON_TRAPDOOR", "VINE", "PISTON_EXTENSION", "PISTON_MOVING_PIECE", "SNOW", "FENCE", "STONE_SLAB2", "SOUL_SAND", "CHEST", "BOAT", "ICE", "PACKED_ICE", "BLUE_ICE", "FROSTED_ICE", "HOPPER", "FLOWER_POT", "SKULL", "SKULL_ITEM", "SCAFFOLDING", "BUBBLE_COLUMN", "SEAGRASS", "SWEET_BERRY_BUSH", "KELP", "OAK_TRAPDOOR", "SPURCE_TRAPDOOR", "BIRCH_TRAPDOOR", "JUNGLE_TRAPDOOR", "ACACIA_TRAPDOOR", "DARK_OUT_TRAPDOOR", "CRIMSON_TRAPDOOR", "WARPED_TRAPDOOR", "TWISTING_VINES", "WEEPING_VINES", "WEEPING_VINES_PLANT", "TWISTING_VINES_PLANT", "CAVE_VINES", "VINE", "CAVE_VINES_PLANT", "SCAFFOLDING", "POWDER_SNOW");
      INVALID_VELOCITY = process("WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA", "WEB", "SEAGRASS", "KELP");
      BOBBLE = process("BUBBLE_COLUMN", "WATER", "STATIONARY_WATER", "TWISTING_VINES", "WEEPING_VINES", "WEEPING_VINES_PLANT", "TWISTING_VINES_PLANT", "CAVE_VINES", "CAVE_VINES_PLANT");
      ICE = process("PACKED_ICE", "ICE", "FROSTED_ICE", "BLUE_ICE", "FROSTED_ICE");
      SLABS = process("STONE_SLAB", "STONE_SLAB2", "STEP", "WOOD_STEP", "OAK_SLAB", "SPRUCE_SLAB", "BIRCH_SLAB", "JUNGLE_SLAB", "ACACIA_SLAB", "DARK_OAK_SLAB", "CRIMSON_SLAB", "WARPED_SLAB", "STONE_SLAB", "SMOOTH_STONE_SLAB", "SANDSTONE_SLAB", "CUT_SANDSTONE_SLAB", "PETRIFIED_OAK_SLAB", "COBBLESTONE_SLAB", "BRICK_SLAB", "STONE_BRICK_SLAB", "NETHER_BRICK_SLAB", "QUARTZ_SLAB", "RED_SANDSTONE_SLAB", "CUT_RED_SANDSTONE_SLAB", "PURPUR_SLAB", "PRISMARINE_SLAB", "PRISMARINE_BRICK_SLAB", "DARK_PRISMARINE_SLAB", "POLISHED_GRANITE_SLAB", "SMOOTH_RED_SANDSTONE_SLAB", "MOSSY_STONE_BRICK_SLAB", "POLISHED_DIORITE_SLAB", "MOSSY_COBBLESTONE_SLAB", "END_STONE_BRICK_SLAB", "SMOOTH_SANDSTONE_SLAB", "SMOOTH_QUARTZ_SLAB", "GRANITE_SLAB", "ANDESITE_SLAB", "RED_NETHER_BRICK_SLAB", "POLISHED_ANDESITE_SLAB", "DIORITE_SLAB", "BLACKSTONE_SLAB", "POLISHED_BLACKSTONE_SLAB", "POLISHED_BLACKSTONE_BRICK_SLAB", "SNOW", "CUT_COPPER_SLAB", "EXPOSED_CUT_COPPER_SLAB", "WEATHERED_CUT_COPPER_SLAB", "OXIDIZED_CUT_COPPER_SLAB", "WAXED_CUT_COPPER_SLAB", "WAXED_EXPOSED_CUT_COPPER_SLAB", "WAXED_WEATHERED_CUT_COPPER_SLAB", "WAXED_OXIDIZED_CUT_COPPER_SLAB", "COBBLED_DEEPSLATE_SLAB", "POLISHED_DEEPSLATE_SLAB", "DEEPSLATE_BRICK_SLAB", "DEEPSLATE_TILE_SLAB");
      STAIRS = process("ACACIA_STAIRS", "BIRCH_WOOD_STAIRS", "BRICK_STAIRS", "COBBLESTONE_STAIRS", "DARK_OAK_STAIRS", "JUNGLE_WOOD_STAIRS", "NETHER_BRICK_STAIRS", "QUARTZ_STAIRS", "SANDSTONE_STAIRS", "RED_SANDSTONE_STAIRS", "SMOOTH_STAIRS", "SPRUCE_WOOD_STAIRS", "WOOD_STAIRS", "SMOOTH_STAIRS", "PURPUR_STAIRS", "SMOOTH_QUARTZ_STAIRS", "SMOOTH_RED_SANDSTONE_STAIRS", "SMOOTH_SANDSTONE_STAIRS", "WARPED_STAIRS", "CRIMSON_STAIRS", "PRISMARINE_STAIRS", "PRISMARINE_BRICK_STAIRS", "DARK_PRISMARINE_STAIRS", "POLISHED_GRANITE_STAIRS", "MOSSY_STONE_BRICK_STAIRS", "POLISHED_DIORITE_STAIRS", "END_STONE_BRICK_STAIRS", "GRANITE_STAIRS", "ANDESITE_STAIRS", "RED_NETHER_BRICK_STAIRS", "POLISHED_ANDESITE_STAIRS", "DIORITE_STAIRS", "BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_STAIRS", "POLISHED_BLACKSTONE_BRICK_STAIRS", "CUT_COPPER_STAIRS", "EXPOSED_CUT_COPPER_STAIRS", "WEATHERED_CUT_COPPER_STAIRS", "OXIDIZED_CUT_COPPER_STAIRS", "WAXED_CUT_COPPER_STAIRS", "WAXED_EXPOSED_CUT_COPPER_STAIRS", "WAXED_WEATHERED_CUT_COPPER_STAIRS", "WAXED_OXIDIZED_CUT_COPPER_STAIRS", "COBBLED_DEEPSLATE_STAIRS", "POLISHED_DEEPSLATE_STAIRS", "DEEPSLATE_BRICK_STAIRS", "DEEPSLATE_TILE_STAIRS");
      INPASSABLE = process("STONE", "GRASS", "DIRT", "COBBLESTONE", "MOSSY_COBBLESTONE", "WOOD", "BEDROCK", "WOOL", "LOG", "LOG_2", "CLAY_BRICK", "NETHER_BRICK", "SMOOTH_BRICK", "COAL_BLOCK", "IRON_BLOCK", "GOLD_BLOCK", "DIAMOND_BLOCK", "LAPIS_BLOCK", "GLASS", "STAINED_GLASS", "ENDER_STONE", "GLOWSTONE", "SANDSTONE", "RED_SANDSTONE", "BOOKSHELF", "NETHERRACK", "CLAY", "SNOW_BLOCK", "MELON_BLOCK", "EMERALD_BLOCK", "QUARTZ_BLOCK", "QUARTZ_ORE", "COAL_ORE", "DIAMOND_ORE", "REDSTONE_ORE", "EMERALD_ORE", "GLOWING_REDSTONE_ORE", "GOLD_ORE", "IRON_ORE", "LAPIS_ORE");
      PLACEABLE = process("DIAMOND_SWORD", "GOLD_SWORD", "IRON_SWORD", "STONE_SWORD", "WOOD_SWORD", "BOW", "SHIELD");
      FLAT = process("WATER_LILY", "GOLD_PLATE", "IRON_PLATE", "WOOD_PLATE", "STONE_PLATE", "STONE_PRESSURE_PLATE", "OAK_PRESSURE_PLATE", "SPRUCE_PRESSURE_PLATE", "BIRCH_PRESSURE_PLATE", "JUNGLE_PRESSURE_PLATE", "ACACIA_PRESSURE_PLATE", "DARK_OAK_PRESSURE_PLATE", "CRIMSON_PRESSURE_PLATE", "WARPED_PRESSURE_PLATE", "POLISHED_BLACKSTONE_PRESSURE_PLATE", "LIGHT_WEIGHTED_PRESSURE_PLATE", "HEAVY_WEIGHTED_PRESSURE_PLATE");
      LIQUIDS = process("WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA", "BUBBLE_COLUMN");
      BOOKS = process("WRITTEN_BOOK", "BOOK_AND_QUILL", "ENCHANTED_BOOK", "BOOK");
      CLIMBABLE = process("VINE", "LADDER", "TWISTING_VINES", "WEEPING_VINES", "WEEPING_VINES_PLANT", "TWISTING_VINES_PLANT", "CAVE_VINES", "CAVE_VINES_PLANT");
      SOUL_SAND = process("SOUL_SAND", "SOUL_SOIL");
      FULLY_PASSABLE = process("AIR", "WATER", "STATIONARY_WATER", "LAVA", "STATIONARY_LAVA");
      BOUND_UP = union(FENCES, GATES, SHULKER_BOX);
   }

   public static Material findNew(String... var0) {
      return (Material)Arrays.stream(var0).map(Material::getMaterial).filter(Objects::nonNull).findFirst().orElse((Object)null);
   }

   public static Material find(String... var0) {
      return (Material)Arrays.stream(var0).map(MaterialList::fromName).filter(Objects::nonNull).findFirst().orElse((Object)null);
   }
}
