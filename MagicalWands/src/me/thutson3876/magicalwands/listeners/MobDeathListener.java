package me.thutson3876.magicalwands.listeners;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftBlaze;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPhantom;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPiglinBrute;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPolarBear;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSilverfish;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.thutson3876.magicalwands.Main;
import me.thutson3876.magicalwands.custommobs.CustomBlaze;
import me.thutson3876.magicalwands.custommobs.CustomGiant;
import me.thutson3876.magicalwands.custommobs.CustomPhantom;
import me.thutson3876.magicalwands.custommobs.CustomPiglinBrute;
import me.thutson3876.magicalwands.custommobs.CustomPolarBear;
import me.thutson3876.magicalwands.custommobs.CustomSilverfish;
import me.thutson3876.magicalwands.mechanics.Sphere;
import me.thutson3876.magicalwands.wands.Wand;

public class MobDeathListener implements Listener {
	
	private Main plugin;
	
	public MobDeathListener (Main plugin) {
		this.plugin = plugin;
		
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}
	
	@EventHandler
	public void onLand(ProjectileHitEvent e) {
		if(e.getEntity().getShooter() instanceof Entity) {
			Entity shooter = (Entity)e.getEntity().getShooter();

			if(e.getEntityType().equals(EntityType.SMALL_FIREBALL) && shooter.getType().equals(EntityType.BLAZE) && ((CraftBlaze)shooter).getHandle() instanceof CustomBlaze){
				Location loc = e.getEntity().getLocation();
				loc.getWorld().createExplosion(loc, (float)plugin.getConfig().getInt("customblaze.power"), true, true, (Entity) e.getEntity().getShooter());
				 //&& ((Entity)(e.getEntity().getShooter()) instanceof CustomBlaze)
	        }
		}
	}
	
	@EventHandler
	public void onTarget(EntityTargetEvent e) {
		if(e.getEntityType().equals(EntityType.PIGLIN_BRUTE) && ((CraftPiglinBrute)e.getEntity()).getHandle() instanceof CustomPiglinBrute) {
			if(e.getEntity() != null && e.getTarget() != null) {
				double distance = e.getEntity().getLocation().distance(e.getTarget().getLocation());
				
				if(distance >= 10) {
					Location loc1 = e.getEntity().getLocation();
					Location loc2 = e.getTarget().getLocation();
					double gauge = distance/5;
					loc2.setY(loc2.getY() + gauge);
					
					LivingEntity piggy = (LivingEntity)e.getEntity();
					
					Location spawnAt = loc1;
					Vector direction = (loc2.toVector().subtract(loc1.toVector())).multiply(plugin.getConfig().getDouble("custompiglinbrute.velocity"));
					Pig pig = (Pig) piggy.getWorld().spawnEntity(spawnAt, EntityType.PIG);
					
					piggy.getWorld().playSound(spawnAt, Sound.ENTITY_PIG_AMBIENT, 1F, 1F);
					pig.addPassenger(piggy);
					pig.setVelocity(direction);
					
					BukkitRunnable task = new BukkitRunnable() {
						
						@Override
						public void run() {
							if(e.getEntity().isInsideVehicle()) {
								e.getEntity().leaveVehicle();
							}
						}
					};
					
					task.runTaskLater(plugin, Math.round(gauge)*10);
				}
			}
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		//Custom Effects
		if(e.getEntity().getType().equals(EntityType.SILVERFISH) && ((CraftSilverfish)e.getEntity()).getHandle() instanceof CustomSilverfish) {
			LivingEntity fish = (LivingEntity)e.getEntity();
			Random rng = new Random();
			Location spawnAt = e.getDamager().getLocation();
			spawnAt.setY(spawnAt.getY()+e.getDamager().getHeight()+plugin.getConfig().getDouble("customsilverfish.addedheight"));
			
			if(spawnAt.getBlock().isPassable()) {
				int distance = rng.nextInt((int)fish.getLocation().getY())+7;
				if(distance > fish.getLocation().getY()-6) {
					distance -= 6;
				}
				
				Location second = fish.getLocation();
				second.setY(distance);
				Block newB = second.getBlock();
				FallingBlock b = fish.getWorld().spawnFallingBlock(spawnAt, newB.getBlockData());
				
				b.setFallDistance((float)plugin.getConfig().getDouble("customsilverfish.startheight"));
				b.setGravity(true);
				b.setHurtEntities(true);
				b.setDropItem(false);
				b.setVelocity(b.getVelocity().multiply(plugin.getConfig().getDouble("customsilverfish.velocity")));
				
				fish.getWorld().playSound(spawnAt, Sound.BLOCK_ANCIENT_DEBRIS_FALL, 0.6F, 1F);
			}	
		}
		else if(e.getDamager().getType().equals(EntityType.SILVERFISH) && ((CraftSilverfish)e.getDamager()).getHandle() instanceof CustomSilverfish) {
			e.setDamage(e.getDamage()*plugin.getConfig().getDouble("customsilverfish.dmgmod"));
		}
		else if(e.getDamager().getType().equals(EntityType.PIGLIN_BRUTE) && ((CraftPiglinBrute)e.getDamager()).getHandle() instanceof CustomPiglinBrute) {
			e.setDamage(e.getDamage()*plugin.getConfig().getDouble("custompiglinbrute.dmgmod"));
		}
		else if(e.getDamager().getType().equals(EntityType.PHANTOM) && ((CraftPhantom)e.getDamager()).getHandle() instanceof CustomPhantom) {
			LivingEntity phantom = (LivingEntity)e.getDamager();
			Vector direction = phantom.getEyeLocation().getDirection();
			
			e.setDamage(e.getDamage()*plugin.getConfig().getDouble("customphantom.dmgmod"));
			
			direction = direction.add(new Vector(0, plugin.getConfig().getDouble("customphantom.addedheight"), 0));
			phantom.getWorld().playSound(phantom.getLocation(), Sound.ENTITY_PHANTOM_FLAP, 1.4F, 1F);
			e.getEntity().setVelocity(e.getEntity().getVelocity().add(direction.multiply(plugin.getConfig().getDouble("customphantom.velocity"))));
		}
		else if(e.getDamager().getType().equals(EntityType.POLAR_BEAR) && ((CraftPolarBear)e.getDamager()).getHandle() instanceof CustomPolarBear) {
			LivingEntity polarbear = (LivingEntity)e.getEntity();
			Location spawnAt = e.getEntity().getLocation();
			List<Location> iceball = Sphere.generateSphere(spawnAt, plugin.getConfig().getInt("custompolarbear.radius"), true);
			
			e.setDamage(e.getDamage()*plugin.getConfig().getDouble("custompolarbear.dmgmod"));
			
			for(int i = 0; i < iceball.size(); i++) {
				iceball.get(i).getBlock().setType(Material.PACKED_ICE);
			}
			
			polarbear.getWorld().playSound(spawnAt, Sound.BLOCK_SNOW_PLACE, 1.5F, 1F);
			
			BukkitRunnable task = new BukkitRunnable() {
				
				@Override
				public void run() {
					for(Location l : iceball) {
						if(l.getBlock().getType().equals(Material.PACKED_ICE)) {
							l.getBlock().setType(Material.AIR);
						}
					}
					
					polarbear.getWorld().playSound(spawnAt, Sound.BLOCK_GLASS_BREAK, 1.4F, 1F);
				}
			};
			task.runTaskLater(plugin, plugin.getConfig().getInt("custompolarbear.duration")*20);
		}
		else if(e.getDamager().getType().equals(EntityType.ZOMBIE) && ((CraftZombie)e.getDamager()).getHandle() instanceof CustomGiant) {
			Entity target = e.getEntity();
			Location spawnAt = target.getLocation();
			spawnAt.setY(spawnAt.getY()-1);
			spawnAt.getBlock().setType(Material.DIRT);
			
			e.setDamage(e.getDamage()*plugin.getConfig().getDouble("customgiant.dmgmod"));
			
			target.getWorld().playSound(spawnAt, Sound.ITEM_CROP_PLANT, 1.4F, 1F);
			target.getWorld().generateTree(spawnAt, TreeType.BIG_TREE);
		}
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		
		//Wand Drops
		if(e.getEntity().getType().equals(EntityType.BLAZE) && ((CraftBlaze)e.getEntity()).getHandle() instanceof CustomBlaze) {
			e.getDrops().clear();
			e.getDrops().add(Wand.FIREBALL_WAND.getItemStack());
		}
		
		else if(e.getEntity().getType().equals(EntityType.SILVERFISH) && ((CraftSilverfish)e.getEntity()).getHandle() instanceof CustomSilverfish) {
			e.getDrops().clear();
			e.getDrops().add(Wand.EARTH_WAND.getItemStack());
		}
		
		else if(e.getEntity().getType().equals(EntityType.PIGLIN_BRUTE) && ((CraftPiglinBrute)e.getEntity()).getHandle() instanceof CustomPiglinBrute) {
			e.getDrops().clear();
			e.getDrops().add(Wand.PIG_WAND.getItemStack());
		}
		
		else if(e.getEntity().getType().equals(EntityType.PHANTOM) && ((CraftPhantom)e.getEntity()).getHandle() instanceof CustomPhantom) {
			e.getDrops().clear();
			e.getDrops().add(Wand.WIND_WAND.getItemStack());
		}
		
		else if(e.getEntity().getType().equals(EntityType.POLAR_BEAR) && ((CraftPolarBear)e.getEntity()).getHandle() instanceof CustomPolarBear) {
			e.getDrops().clear();
			e.getDrops().add(Wand.ICEBALL_WAND.getItemStack());
		}
		
		else if(e.getEntity().getType().equals(EntityType.ZOMBIE) && ((CraftZombie)e.getEntity()).getHandle() instanceof CustomGiant) {
			e.getDrops().clear();
			e.getDrops().add(Wand.TREE_WAND.getItemStack());
		}
	}
}