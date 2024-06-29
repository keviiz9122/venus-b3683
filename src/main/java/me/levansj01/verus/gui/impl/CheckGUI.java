package me.levansj01.verus.gui.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.levansj01.verus.VerusPlugin;
import me.levansj01.verus.check.type.CheckType;
import me.levansj01.verus.gui.GUI;
import me.levansj01.verus.gui.manager.GUIManager;
import me.levansj01.verus.util.item.ItemBuilder;
import me.levansj01.verus.util.item.MaterialList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CheckGUI extends GUI {
   private static final ItemStack previousPage;
   private final Map<Integer, CheckType> typeById = new HashMap();

   public void onClick(InventoryClickEvent var1) {
      int var2 = var1.getSlot();
      if (var2 >= 0 && var2 < 9) {
         GUIManager.getInstance().getMainGui().openGui((Player)var1.getWhoClicked());
      } else {
         ItemStack var3 = var1.getCurrentItem();
         if (var3 != null && var3.getItemMeta() != null) {
            CheckType var4 = (CheckType)this.typeById.get(var2);
            if (var4 != null) {
               String var5 = ChatColor.stripColor(var3.getItemMeta().getDisplayName().replace(" Checks", ""));
               if (var5.equalsIgnoreCase(var4.getName())) {
                  GUIManager.getInstance().getTypeGui(var4).openGui((Player)var1.getWhoClicked());
               }
            }
         }

      }
   }

   public CheckGUI() {
      super(VerusPlugin.COLOR + VerusPlugin.getNameFormatted() + " Checks", 45);
      int var1 = 0;

      do {
         if (var1 >= 9) {
            var1 = 9;
            CheckType[] var2 = CheckType.values();
            int var3 = var2.length;
            int var4 = 0;

            do {
               if (var4 >= var3) {
                  return;
               }

               CheckType var5 = var2[var4];
               if (var5.ignore()) {
               } else {
                  this.inventory.setItem(var1, (new ItemBuilder()).setType(MaterialList.DIAMOND_SWORD).setName(VerusPlugin.COLOR + var5.getName() + " Checks").setLore(Collections.singletonList(VerusPlugin.COLOR + "ID: " + ChatColor.WHITE + var5.getSuffix())).setAmount(1).build());
                  this.typeById.put(var1++, var5);
               }

               ++var4;
            } while (true);
         }

         this.inventory.setItem(var1, previousPage);
         ++var1;
      } while (true);
   }

   static {
      previousPage = (new ItemBuilder()).setType(MaterialList.STAINED_GLASS_PANE).setName(ChatColor.RED + "Previous Page").setAmount(1).setLore(Collections.singletonList(ChatColor.GRAY + "Click to go back a page")).build();
   }

   public void clear() {
      super.clear();
      this.typeById.clear();
   }
}
