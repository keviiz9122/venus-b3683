package me.levansj01.verus.util.item;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class ItemBuilder {
   private String name;
   private Material type;
   private short damage;
   private int amount = 1;
   private List<String> lore;

   public ItemBuilder setDamage(int var1) {
      this.damage = (short)var1;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public ItemBuilder setAmount(int var1) {
      this.amount = var1;
      return this;
   }

   public ItemBuilder setName(String var1) {
      this.name = var1;
      return this;
   }

   public ItemBuilder setTypeAndData(MaterialData var1) {
      this.type = var1.getItemType();
      this.damage = (short)var1.getData();
      return this;
   }

   public ItemBuilder() {
   }

   public List<String> getLore() {
      return this.lore;
   }

   public ItemBuilder setType(Material var1) {
      this.type = var1;
      return this;
   }

   public int getAmount() {
      return this.amount;
   }

   public ItemStack build() {
      ItemStack var1 = new ItemStack(this.type);
      var1.setAmount(this.amount);
      var1.setDurability(this.damage);
      ItemMeta var2 = var1.getItemMeta();
      var2.setDisplayName(this.name);
      var2.setLore(this.lore);
      var1.setItemMeta(var2);
      return var1;
   }

   public ItemBuilder setLore(List<String> var1) {
      this.lore = var1;
      return this;
   }

   public ItemBuilder(Material var1) {
      this.type = var1;
   }

   public short getDamage() {
      return this.damage;
   }

   public Material getType() {
      return this.type;
   }
}
