package me.levansj01.verus.gui.impl;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.api.API;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.data.PlayerData;
import me.levansj01.verus.data.manager.DataManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.storage.database.DatabaseType;
import me.levansj01.verus.type.VerusTypeLoader;
import me.levansj01.verus.util.BukkitUtil;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import me.levansj01.verus.util.java.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainGUI extends GUI {
   private static final String verusType;
   private static final String format;
   private static final String build;
   public static final Set<UUID> ALLOWED_UUIDS = ImmutableSet.of(UUID.fromString("564b7f5e-9efc-4b18-9ff3-9ea62117b113"), UUID.fromString("34272ea9-008d-4cb0-9990-4085d7048ddb"), UUID.fromString("79295695-5fa7-4fa1-bc68-a16b09de873d"), UUID.fromString("954b39b2-2161-45ae-8b7d-b61c81109621"));
   private static final ItemStack infoStack;
   private static final ItemStack blank;
   private static final String implementation;

   public void onClick(InventoryClickEvent var1) {
      int var2 = var1.getSlot();
      HumanEntity var3 = var1.getWhoClicked();
      if (var3 instanceof Player) {
         Player var4 = (Player)var3;
         if (var2 == 0) {
            if (ALLOWED_UUIDS.contains(var4.getUniqueId())) {
               PlayerData var5 = DataManager.getInstance().getPlayer(var4);
               boolean var10001;
               if (!var5.isDebug()) {
                  var10001 = true;
               } else {
                  var10001 = false;
               }

               var5.setDebug(var10001);
               BukkitUtil.setMeta(var4, "verus.admin", var5.isDebug());
               StringBuilder var6 = (new StringBuilder()).append(VerusPlugin.COLOR).append("You are ");
               String var10002;
               if (var5.isDebug()) {
                  var10002 = "now";
               } else {
                  var10002 = "no longer";
               }

               var4.sendMessage(var6.append(var10002).append(" in debug mode").toString());
            }
         } else if (var2 == 11) {
            GUIManager.getInstance().getCheckGui().openGui(var4);
         } else if (var2 == 13) {
            this.updateInfoStack();
         } else if (var2 == 15 && BukkitUtil.hasPermission((CommandSender)var3, "verus.restart")) {
            NMSManager.getInstance().postToMainThread(VerusPlugin::restart);
         }
      }

   }

   static {
      infoStack = new ItemStack(MaterialList.PAPER);
      blank = (new ItemBuilder()).setType(MaterialList.STAINED_GLASS_PANE).setName(" ").build();
      verusType = WordUtils.capitalize(VerusTypeLoader.getVerusType().name().toLowerCase());
      build = String.valueOf(VerusPlugin.getBuild());
      implementation = Bukkit.getServer().getClass().getName().split("\\.")[3];
      format = VerusPlugin.COLOR + "Total %s %s";
   }

   public void updateInfoStack() {
      ItemMeta var1 = infoStack.getItemMeta();
      var1.setDisplayName(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Information");
      StorageEngine var2 = StorageEngine.getInstance();
      DatabaseType var3 = var2.getType();
      API var4 = API.getAPI();
      String[] var10001 = new String[]{"", VerusPlugin.COLOR + "Type " + ChatColor.WHITE + verusType, null, null, null, null, null, null, null, null};
      StringBuilder var10004 = (new StringBuilder()).append(VerusPlugin.COLOR).append("Build ").append(ChatColor.WHITE).append(build);
      String var10005;
      if (var4 == null) {
         var10005 = "";
      } else {
         var10005 = ChatColor.GRAY + " (API v" + var4.getVersion() + ")";
      }

      var10001[2] = var10004.append(var10005).toString();
      var10001[3] = VerusPlugin.COLOR + "Implementation " + ChatColor.WHITE + implementation;
      var10004 = (new StringBuilder()).append(VerusPlugin.COLOR).append("Storage: ").append(ChatColor.WHITE);
      if (var3 != null) {
         var10005 = var3.name();
      } else {
         var10005 = "None";
      }

      var10001[4] = var10004.append(var10005).toString();
      var10001[5] = "";
      String var6 = format;
      Object[] var5 = new Object[]{"Bans", null};
      String var10008;
      if (var2.isConnected()) {
         var10008 = ChatColor.WHITE + String.valueOf(var2.getDatabase().getTotalBans());
      } else {
         var10008 = ChatColor.RED + "Not Connected";
      }

      var5[1] = var10008;
      var10001[6] = String.format(var6, var5);
      var6 = format;
      var5 = new Object[]{"Logs", null};
      if (var2.isConnected()) {
         var10008 = ChatColor.WHITE + String.valueOf(var2.getDatabase().getTotalLogs());
      } else {
         var10008 = ChatColor.RED + "Not Connected";
      }

      var5[1] = var10008;
      var10001[7] = String.format(var6, var5);
      var10001[8] = "";
      var10001[9] = ChatColor.GRAY + "Click to Refresh";
      var1.setLore(Arrays.asList(var10001));
      infoStack.setItemMeta(var1);
      this.inventory.setItem(13, infoStack);
   }

   public MainGUI() {
      super(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " AntiCheat", 27);
      int var1 = 0;

      do {
         if (var1 >= this.inventory.getSize()) {
            ArrayList var6 = new ArrayList();
            var6.add(" ");
            CheckType[] var2 = CheckType.values();
            int var3 = var2.length;
            int var4 = 0;

            do {
               if (var4 >= var3) {
                  var6.add(" ");
                  var6.add(ChatColor.GRAY + "Click to open Checks menu");
                  this.inventory.setItem(11, (new ItemBuilder(MaterialList.BOOK)).setName(VerusPlugin.COLOR + "Checks").setLore(var6).setAmount(CheckType.values().length - 1).build());
                  this.updateInfoStack();
                  this.inventory.setItem(15, (new ItemBuilder(MaterialList.REDSTONE)).setName(VerusPlugin.COLOR + "Restart").setLore(Collections.singletonList(ChatColor.GRAY + "Click to Restart " + VerusPlugin.getNameFormatted())).build());
                  return;
               }

               CheckType var5 = var2[var4];
               if (var5.ignore()) {
               } else {
                  var6.add(ChatColor.WHITE + var5.getName() + ChatColor.GRAY + " (ID: " + var5.getSuffix() + ")");
               }

               ++var4;
            } while (true);
         }

         if (var1 > 9 && var1 < 17) {
         } else {
            this.inventory.setItem(var1, blank);
         }

         ++var1;
      } while (true);
   }
}
