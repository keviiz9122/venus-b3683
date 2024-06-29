package me.levansj01.verus.gui;

import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class GUI implements InventoryHolder {
   public Inventory inventory;
   private final Integer size;
   private final String header;

   public Integer getSize() {
      return this.size;
   }

   public void clear() {
      try {
         (new LinkedList(this.inventory.getViewers())).forEach(HumanEntity::closeInventory);
      } catch (Throwable var2) {
         var2.printStackTrace();
         return;
      }
   }

   public GUI(String var1, Integer var2) {
      this.inventory = Bukkit.createInventory(this, var2, var1);
      this.header = var1;
      this.size = var2;
   }

   public String getHeader() {
      return this.header;
   }

   public abstract void onClick(InventoryClickEvent var1);

   public void openGui(Player var1) {
      if (this.inventory != null) {
         var1.openInventory(this.inventory);
      }

   }

   public Inventory getInventory() {
      return this.inventory;
   }
}
