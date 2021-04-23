package me.thutson3876.magicalwands.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftBlaze;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPhantom;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPiglinBrute;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPolarBear;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSilverfish;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftZombie;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import me.thutson3876.magicalwands.Main;
import me.thutson3876.magicalwands.custommobs.CustomBlaze;
import me.thutson3876.magicalwands.custommobs.CustomGiant;
import me.thutson3876.magicalwands.custommobs.CustomPhantom;
import me.thutson3876.magicalwands.custommobs.CustomPiglinBrute;
import me.thutson3876.magicalwands.custommobs.CustomPolarBear;
import me.thutson3876.magicalwands.custommobs.CustomSilverfish;

public class EntitySpawnListener implements Listener{
	
	private Main plugin;
	private ArrayList<EntityType> customizableEntities;
	
	public EntitySpawnListener(Main plugin) {
		this.plugin = plugin;
		
		customizableEntities.addAll(Arrays.asList(EntityType.BLAZE, EntityType.SILVERFISH, EntityType.PIGLIN_BRUTE, EntityType.PHANTOM,
				EntityType.POLAR_BEAR, EntityType.ZOMBIE));
		
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}
	
	//Determines if 
	private boolean doesSpawnCustom(int chance) {
		Random rng = new Random();
		int i = rng.nextInt(plugin.getConfig().getInt("spawn"));
		if(i <= chance) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if(e.getEntityType().equals(EntityType.BLAZE) && !(((CraftBlaze)e.getEntity()).getHandle() instanceof CustomBlaze)) {
			if(doesSpawnCustom((plugin.getConfig().getInt("customblaze.spawnchance")-1))) {
				Location loc = e.getLocation();
				CustomBlaze blaze = new CustomBlaze(loc);
				blaze.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				((CraftWorld)loc.getWorld()).getHandle().addEntity(blaze, SpawnReason.CUSTOM);
				//e.getEntity().getServer().broadcastMessage("Entity Spawned");
				e.setCancelled(true);
			}
		}
		else if(e.getEntityType().equals(EntityType.SILVERFISH) && !(((CraftSilverfish)e.getEntity()).getHandle() instanceof CustomSilverfish)) {
			if(doesSpawnCustom((plugin.getConfig().getInt("customsilverfish.spawnchance")-1))) {
				Location loc = e.getLocation();
				CustomSilverfish fish = new CustomSilverfish(loc);
				fish.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				((CraftWorld)loc.getWorld()).getHandle().addEntity(fish, SpawnReason.CUSTOM);
				//e.getEntity().getServer().broadcastMessage("Entity Spawned");
				e.setCancelled(true);
			}
		}
		else if(e.getEntityType().equals(EntityType.PIGLIN_BRUTE) && !(((CraftPiglinBrute)e.getEntity()).getHandle() instanceof CustomPiglinBrute)) {
			if(doesSpawnCustom((plugin.getConfig().getInt("custompiglinbrute.spawnchance")-1))) {
				Location loc = e.getLocation();
				CustomPiglinBrute piggy = new CustomPiglinBrute(loc);
				piggy.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				((CraftWorld)loc.getWorld()).getHandle().addEntity(piggy, SpawnReason.CUSTOM);
				//e.getEntity().getServer().broadcastMessage("Entity Spawned");
				e.setCancelled(true);
			}
		}
		else if(e.getEntityType().equals(EntityType.PHANTOM) && !(((CraftPhantom)e.getEntity()).getHandle() instanceof CustomPhantom)) {
			if(doesSpawnCustom((plugin.getConfig().getInt("customphantom.spawnchance")-1))) {
				Location loc = e.getLocation();
				CustomPhantom phantom = new CustomPhantom(loc);
				phantom.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				((CraftWorld)loc.getWorld()).getHandle().addEntity(phantom, SpawnReason.CUSTOM);
				//e.getEntity().getServer().broadcastMessage("Entity Spawned");
				e.setCancelled(true);
			}
		}
		else if(e.getEntityType().equals(EntityType.POLAR_BEAR) && !(((CraftPolarBear)e.getEntity()).getHandle() instanceof CustomPolarBear)) {
			if(doesSpawnCustom((plugin.getConfig().getInt("custompolarbear.spawnchance")-1))) {
				Location loc = e.getLocation();
				CustomPolarBear PolarBear = new CustomPolarBear(loc);
				PolarBear.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				((CraftWorld)loc.getWorld()).getHandle().addEntity(PolarBear, SpawnReason.CUSTOM);
				//e.getEntity().getServer().broadcastMessage("Entity Spawned");
				e.setCancelled(true);
			}
		}
		else if(e.getEntityType().equals(EntityType.ZOMBIE) && !(((CraftZombie)e.getEntity()).getHandle() instanceof CustomGiant)) {
			if(doesSpawnCustom((plugin.getConfig().getInt("customgiant.spawnchance")-1))) {
				Location loc = e.getLocation();
				CustomGiant giant = new CustomGiant(loc);
				giant.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
				((CraftWorld)loc.getWorld()).getHandle().addEntity(giant, SpawnReason.CUSTOM);
				//e.getEntity().getServer().broadcastMessage("Entity Spawned");
				e.setCancelled(true);
			}
		}
	}
}
