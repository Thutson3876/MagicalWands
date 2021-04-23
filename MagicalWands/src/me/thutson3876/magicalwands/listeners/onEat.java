package me.thutson3876.magicalwands.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.thutson3876.magicalwands.Main;

public class onEat implements Listener {
	
	private Main plugin;
	
	public onEat(Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onEatGrape(PlayerItemConsumeEvent e) {
		ItemStack food = e.getItem();
		if(food.getType().equals(Material.APPLE)) {
			if(food.getItemMeta().getDisplayName().equalsIgnoreCase("Big Red Grape")) {
					PotionEffect bo = new PotionEffect(PotionEffectType.BAD_OMEN, 100, 100, true, true);
					PotionEffect con = new PotionEffect(PotionEffectType.CONFUSION, 100, 100, true, true);
					PotionEffect po = new PotionEffect(PotionEffectType.POISON, 100, 100, true, true);
					PotionEffect b = new PotionEffect(PotionEffectType.BLINDNESS, 100, 100, true, true);
					PotionEffect nv = new PotionEffect(PotionEffectType.NIGHT_VISION, 100, 100, true, true);
					
					World end = e.getPlayer().getServer().getWorld("world_the_end");
					if(end != null) {
						e.getPlayer().teleport(end.getSpawnLocation());
					}
					
					e.getPlayer().addPotionEffect(bo);
					e.getPlayer().addPotionEffect(con);
					e.getPlayer().addPotionEffect(po);
					e.getPlayer().addPotionEffect(b);
					e.getPlayer().addPotionEffect(nv);
			}
		}
	}
}
