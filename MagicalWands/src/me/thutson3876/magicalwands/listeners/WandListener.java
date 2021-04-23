package me.thutson3876.magicalwands.listeners;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.magicalwands.Main;
import me.thutson3876.magicalwands.mechanics.Cooldown;
import me.thutson3876.magicalwands.wands.Wand;
import me.thutson3876.magicalwands.wands.Wands;

public class WandListener implements Listener{
	
	private Main plugin;
	
	public WandListener(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this,  plugin);
	}
	
	public static void checkDurability(Player p, ItemStack item) {
		if(Wands.loseDurability(item)) {
			p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.3F, 1.0F);
		}
	}
	
	@EventHandler
	public void onRMB (PlayerInteractEvent e) {
        
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			ItemStack wand = e.getItem();
			if(wand == null) {
				return;
			}
			
			List<String> lore = wand.getItemMeta().getLore();
			if(lore != null) {
				if(lore.contains(Wand.FIREBALL_WAND.getLore())) {
					Player p = e.getPlayer();
					if(Cooldown.checkCD(p, e.getMaterial())) {
						Wands.fireballWand(plugin, e);
						
					}
					else {
						return;
					}
				}
				
				else if(lore.contains(Wand.ICEBALL_WAND.getLore())) {
					Player p = e.getPlayer();
					if(p.getTargetBlock(null, 20).getType().equals(Material.AIR)) {
							return;
					}
					else {
						if(Cooldown.checkCD(p, e.getMaterial())) {
							Wands.iceballWand(plugin, e);
						}
						else {
							return;
						}	
					}
				}
				
				else if(lore.contains(Wand.EARTH_WAND.getLore())) {
					Player p = e.getPlayer();
					
					if(Cooldown.checkCD(p, e.getMaterial())) {
						Wands.earthWand(plugin, e);
					}
					else {
						return;
					}
				}
				
				else if(lore.contains(Wand.WIND_WAND.getLore())) {
					Player p = e.getPlayer();
					
					if(Cooldown.checkCD(p, e.getMaterial())) {
						Wands.windWand(plugin, e);
					}
					else {
						return;
					}
				}
				
				else if(lore.contains(Wand.WONDROUS_WAND.getLore())) {
					Player p = e.getPlayer();
					
					if(Cooldown.checkCD(p, e.getMaterial())) {
						Wands.wondrousWand(plugin, e);
					}
					else {
						return;
					}
				}
				
				else if(lore.contains(Wand.PIG_WAND.getLore())) {
					Player p = e.getPlayer();
					
					if(Cooldown.checkCD(p, e.getMaterial())) {
						Wands.pigWand(plugin, e);
					}
					else {
						return;
					}
				}
				
				else if(wand.isSimilar(Wand.DUCK_WAND.getItemStack())) {
					Player p = e.getPlayer();
					if(p.getTargetBlock(null, 20).getType().equals(Material.AIR)) {
						return;
					}
					
					if(Cooldown.checkCD(p, e.getMaterial())) {
						Wands.duckWand(plugin, e);
					}
					else {
						return;
					}
				}
				
				else if(lore.contains(Wand.TREE_WAND.getLore())) {
					Player p = e.getPlayer();
					if(p.getTargetBlock(null, 20).getType().equals(Material.AIR)) {
						return;
					}
					
					if(Cooldown.checkCD(p, e.getMaterial())) {
						Wands.treeWand(plugin, e);
					}
					else {
						return;
					}
				}
				else {
					return;
				}
			}
		}
	}
}
