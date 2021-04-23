package me.thutson3876.magicalwands.wands;

import java.util.List;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.thutson3876.magicalwands.Main;

public enum Wand {
	
	FIREBALL_WAND(Material.BLAZE_ROD, "Fireball Wand", Arrays.asList("Shoots a fireball"), 30), WIND_WAND(Material.FEATHER, "Wind Wand", Arrays.asList("Shoots a blast of wind"), 125), 
	ICEBALL_WAND(Material.PRISMARINE_SHARD, "Iceball Wand", Arrays.asList("Spawns a ball of ice"), 50), EARTH_WAND(Material.BRICK, "Earth Wand", Arrays.asList("Shoots a block of earth"), 200),
	TREE_WAND(Material.STICK, "Tree Wand", Arrays.asList("Spawns a tree"), 15), PIG_WAND(Material.GOLD_INGOT, "Pig Wand", Arrays.asList("Launches the user on a pig"), 100), 
	DUCK_WAND(Material.NETHER_WART, "Duck Wand", Arrays.asList("So the duck walked up to the lemonade stand..."), 1), WONDROUS_WAND(Material.EMERALD, "Wondrous Wand", Arrays.asList("Want to roll the dice?"), 100);
	
	private Material material;
	private String displayName;
    private List<String> lore;
    private String shortName;
    private int durability;
    private final int maxDurability;
	
	private Wand(Material material, String displayName, List<String> lore, int maxDurability) {
		this.material = material;
		this.displayName = displayName;
		this.lore = lore;
		this.shortName = displayName.replaceAll("\\s", "_");
		this.maxDurability = maxDurability;
		this.durability = maxDurability;
	}
	
	 public ItemStack getItemStack() {
         ItemStack itemstack = new ItemStack(material, 1);
         ItemMeta itemMeta = itemstack.getItemMeta();
         itemMeta.setDisplayName(ChatColor.GOLD + displayName + ChatColor.WHITE + " [" + durability + "/" + maxDurability + "]");
         itemMeta.setLore(lore);
         itemstack.setItemMeta(itemMeta);
         return itemstack;
     }
	 
	 private static Wand[] getWandsList() {
		 Wand[] list = {FIREBALL_WAND, ICEBALL_WAND, WIND_WAND, EARTH_WAND, TREE_WAND, 
				 PIG_WAND, DUCK_WAND, WONDROUS_WAND};
		 
		 return list;
	 }
	 
	 public static String[] getWandsListString() {
		 String[] list = new String[getWandsList().length];
		 int i = 0;
		 for(Wand w : getWandsList()) {
			 list[i] = w.shortName;
			 i++;
		 }
		 
		 return list;
	 }
	 
	 public static ItemStack getWand(String input, int amt) {
		 for(Wand w : getWandsList()) {
			 if(w.shortName.equalsIgnoreCase(input)) {
				 ItemStack item = w.getItemStack();
				 item.setAmount(amt);
				 return item;
			 }
		 }
		 return null;
	 }
	 
	 public String getLore() {
		 return this.lore.get(0);
	 }
	 
	 public Material getMaterial() {
		 return this.material;
	 }
	 
	 public String getShortName() {
		 return this.shortName;
	 }
	 
	 public void loseDurability() {
		 durability--;
		 
		 if(durability <= 0) {
			 ItemStack item = this.getItemStack();
			 item.setAmount(item.getAmount()-1);
		 }
		 this.updateDisplayName();
	 }
	 
	 public void setDurability(int newDurability) {
		 durability = newDurability;
	 }
	 
	 private void updateDisplayName() {
		 ItemStack itemstack = this.getItemStack();
         ItemMeta itemMeta = itemstack.getItemMeta();
		 itemMeta.setDisplayName(ChatColor.GOLD + displayName + ChatColor.WHITE + " [" + durability + "/" + maxDurability + "]");
		 itemstack.setItemMeta(itemMeta);
	 }
	 
	 public int getUses(Main plugin) {
		 if(lore.equals(FIREBALL_WAND.lore)) {
			 return plugin.getConfig().getInt("fireball.uses");
		 }
		 else if(lore.equals(ICEBALL_WAND.lore)) {
			 return plugin.getConfig().getInt("iceball.uses");
		 }
		 else if(lore.equals(EARTH_WAND.lore)) {
			 return plugin.getConfig().getInt("earth.uses");
		 }
		 else if(lore.equals(WIND_WAND.lore)) {
			 return plugin.getConfig().getInt("wind.uses");
		 }
		 else if(lore.equals(PIG_WAND.lore)) {
			 return plugin.getConfig().getInt("iceball.uses");
		 }
		 else if(lore.equals(TREE_WAND.lore)) {
			 return plugin.getConfig().getInt("iceball.uses");
		 }
		 else if(lore.equals(WONDROUS_WAND.lore)) {
			 return plugin.getConfig().getInt("wondrous.uses");
		 }
		 else if(lore.equals(DUCK_WAND.lore)) {
			 return plugin.getConfig().getInt("duck.uses");
		 }
		 else {
			 return 1;
		 }
	 }
}
