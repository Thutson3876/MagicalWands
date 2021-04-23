package me.thutson3876.magicalwands.wands;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.thutson3876.magicalwands.Main;
import me.thutson3876.magicalwands.mechanics.Cooldown;
import me.thutson3876.magicalwands.mechanics.Sphere;

public class Wands {
	
	private static Entity getCursorEntity(Player p, double distance, double maxAngle) {
		List<Entity> enemies = p.getNearbyEntities(distance, distance, distance);
		double angle;
		Vector dirToDestination;
		Vector playerDirection;
		Entity e = null;
		double eAngle = maxAngle;
		boolean first = true;
		Entity entity;
		
		for(int i = 0; i < enemies.size(); i++) {
			entity = enemies.get(i);
			if(p.hasLineOfSight(entity) && entity instanceof LivingEntity) {
				dirToDestination = enemies.get(i).getLocation().toVector().subtract(p.getEyeLocation().toVector());
				playerDirection = p.getEyeLocation().getDirection();
				angle = dirToDestination.angle(playerDirection);
				
				if(angle<maxAngle && angle>-maxAngle && eAngle<angle) {
					e = entity;
					eAngle = angle;
				}
				else if(first) {
					e = entity;
					eAngle = angle;
					first = false;
				}
			}
		}
		
		if(!first || (first && eAngle<maxAngle && eAngle>-maxAngle)) {
			return e;
		}
		else {
			return null;
		}
	}
	
	public static void moveToward(Entity entity, Location to, double speed) {
        Location loc = entity.getLocation();
        double x = loc.getX() - to.getX();
        double y = loc.getY() - to.getY();
        double z = loc.getZ() - to.getZ();
        Vector velocity = new Vector(x, y, z).normalize().multiply(-speed);
        entity.setVelocity(velocity);   
    }
	
	public static boolean loseDurability(ItemStack item) {
		int[] durabilities = getDurabilities(item);
		
		if(durabilities != null) {
			ItemMeta meta = item.getItemMeta();
			String displayName = ChatColor.stripColor(meta.getDisplayName());
			int newDurability = durabilities[0]-1;
			int bracketIndex = displayName.indexOf("[");
			
			displayName = displayName.replaceFirst(String.valueOf(durabilities[0]), String.valueOf(newDurability));
			displayName = ChatColor.GOLD + displayName.substring(0, bracketIndex-1) + ChatColor.WHITE + displayName.substring(bracketIndex-1);
			
			meta.setDisplayName(displayName);
			item.setItemMeta(meta);
			
			if(newDurability <= 0) {
				item.setAmount(item.getAmount()-1);
				return true;
			}
			return false;
		}
		else {
			return false;
		}
	}
	
	public static int[] getDurabilities(ItemStack item) {
		String displayName = ChatColor.stripColor(item.getItemMeta().getDisplayName());
		boolean hasSlash = displayName.contains("/");
		boolean hasBracket1 = displayName.contains("[");
		boolean hasBracket2 = displayName.contains("]");
		
		if(hasSlash && hasBracket1 && hasBracket2) {
			int[] charIndexes = new int[3];
			charIndexes[0] = displayName.indexOf("[");
			charIndexes[1] = displayName.indexOf("/");
			charIndexes[2] = displayName.indexOf("]");
			
			int durability = Integer.parseInt((String)displayName.subSequence(charIndexes[0]+1, charIndexes[1]));
			int maxDurability = Integer.parseInt((String)displayName.subSequence(charIndexes[1]+1, charIndexes[2]));
			int[] durabilities = {durability, maxDurability};
			
			return durabilities;
		}
		else {
			return null;
		}
	}
	
	public static boolean fireballWand(Main plugin, PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection()).toLocation(p.getWorld());
		
		Fireball f = (Fireball)p.getWorld().spawnEntity(spawnAt, EntityType.FIREBALL);
		f.setYield((float)plugin.getConfig().getDouble("fireball.yield"));
		f.setDirection(p.getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("fireball.velocity")));
		
		p.getWorld().playSound(spawnAt, Sound.ITEM_FIRECHARGE_USE, (float)plugin.getConfig().getDouble("fireball.volume"), 1F);
		
		Cooldown.setCD(p, e.getMaterial(), plugin.getConfig().getDouble("fireball.cooldown"));
		
		loseDurability(e.getItem());
		
		return true;
	}
	
	public static boolean iceballWand(Main plugin, PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Location spawnAt = p.getTargetBlock(null, plugin.getConfig().getInt("iceball.maxdistance")).getLocation();
		List<Location> iceball = Sphere.generateSphere(spawnAt, plugin.getConfig().getInt("iceball.radius"), true);
	
		for(int i = 0; i < iceball.size(); i++) {
			iceball.get(i).getBlock().setType(Material.PACKED_ICE);
		}
		
		p.getWorld().playSound(spawnAt, Sound.BLOCK_SNOW_PLACE, (float)plugin.getConfig().getDouble("iceball.volume"), 1F);
		
		Cooldown.setCD(p, e.getMaterial(), plugin.getConfig().getDouble("iceball.cooldown"));
		
		BukkitRunnable task = new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Location l : iceball) {
					if(l.getBlock().getType().equals(Material.PACKED_ICE)) {
						l.getBlock().setType(Material.AIR);
					}
				}
				
				p.getWorld().playSound(spawnAt, Sound.BLOCK_GLASS_BREAK, (float)plugin.getConfig().getDouble("iceball.volume"), 1F);
			}
		};
		
		loseDurability(e.getItem());
		
		task.runTaskLater(plugin, plugin.getConfig().getInt("iceball.duration")*20);
		
		return true;
	}
	
	public static boolean earthWand(Main plugin, PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Random rng = new Random();
		Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection()).toLocation(p.getWorld());
		int distance = rng.nextInt((int)p.getLocation().getY())+2;
		Location second = new Location(p.getWorld(), p.getLocation().getX(), distance, p.getLocation().getZ());
	
		while(second.getBlock().getType().getHardness()<=0) {
			second.setY(rng.nextInt((int)p.getLocation().getY())+2);
		}
		
		final Entity target = getCursorEntity(p, 8.0, 0.1);
		final Location targetLoc;
		
		if(target != null) {
			targetLoc = target.getLocation();
		}
		else {
			targetLoc = p.getLocation();
		}
		
		Block newB = second.getBlock();
		FallingBlock b = p.getWorld().spawnFallingBlock(spawnAt, newB.getBlockData());
		
		b.setFallDistance(3);
		b.setGravity(false);
		b.setHurtEntities(true);
		b.setDropItem(false);
		//b.setVelocity(p.getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("earth.velocity")));
		
		b.setVelocity(p.getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("earth.velocity")));
		
		p.getWorld().playSound(spawnAt, Sound.BLOCK_ANCIENT_DEBRIS_FALL, (float)plugin.getConfig().getDouble("earth.volume"), 1F);
		
		BukkitRunnable task2 = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(b == null || b.getLocation().distance(targetLoc) < 1.2 || b.isDead() || b.isOnGround()) {
					this.cancel();
				}
				else {
					moveToward(b, targetLoc, plugin.getConfig().getDouble("earth.killvelocity"));
				}
			}
		};
		
		BukkitRunnable task = new BukkitRunnable() {
			
			@Override
			public void run() {
				if(b == null || b.isDead()) {
					this.cancel();
				}
				else if (b.getTicksLived()>plugin.getConfig().getInt("earth.airtime")) {
					b.setGravity(true);
					task2.runTaskTimer(plugin, 1, 1);
					this.cancel();
				}
				else {
					b.setVelocity(b.getVelocity().setY(0.0).rotateAroundY(plugin.getConfig().getDouble("earth.angle")).add(new Vector(0, plugin.getConfig().getDouble("earth.y"), 0)));
				}
			}
		};
		
		task.runTaskTimer(plugin, 1, 1);
		
		Cooldown.setCD(p, e.getMaterial(), plugin.getConfig().getDouble("earth.cooldown"));
		
		loseDurability(e.getItem());
		
		return true;
	}
	
	public static boolean windWand (Main plugin, PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Vector dirToDestination;
		Vector playerDirection;
		double angle;
		double distance = plugin.getConfig().getDouble("wind.distance");
		List<Entity> enemies = p.getNearbyEntities(distance, distance, distance);

		double maxAngle = plugin.getConfig().getDouble("wind.angle");
		for(int i = 0; i < enemies.size(); i++) {
			if(p.hasLineOfSight(enemies.get(i))) {
				dirToDestination = enemies.get(i).getLocation().toVector().subtract(p.getEyeLocation().toVector());
				playerDirection = p.getEyeLocation().getDirection();
				angle = dirToDestination.angle(playerDirection);
				
				if(angle<maxAngle && angle>-maxAngle) {
					enemies.get(i).setVelocity(enemies.get(i).getVelocity().add(p.getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("wind.velocity"))));
				}
			}
		}
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PHANTOM_FLAP, (float)plugin.getConfig().getDouble("wind.volume"), 1F);
		Block b = p.getTargetBlockExact(plugin.getConfig().getInt("wind.jumpdistance"));
		
		if(b != null) {
			p.setVelocity(p.getEyeLocation().getDirection().multiply(-1.25));
		}

		Cooldown.setCD(p, e.getMaterial(), plugin.getConfig().getDouble("wind.cooldown"));
		
		loseDurability(e.getItem());
		
		return true;
	}

	public static boolean wondrousWand (Main plugin, PlayerInteractEvent e) {
		Random rng = new Random();
		Player p = e.getPlayer();
		int n = rng.nextInt(4);
		double cd = plugin.getConfig().getDouble("wondrous.cooldown");
		
		if(n == 0) {
			if(fireballWand(plugin, e)) {
				Cooldown.setCD(p, e.getMaterial(), cd);
				return true;
			}
		}
		else if(n == 1) {
			if(earthWand(plugin, e)) {
				Cooldown.setCD(p, e.getMaterial(), cd);
				return true;
			}
		}
		else if(n == 2) {
			if(windWand(plugin, e)) {
				Cooldown.setCD(p, e.getMaterial(), cd);
				return true;
			}
		}
		else if(n == 3) {
			if(iceballWand(plugin, e)) {
				Cooldown.setCD(p, e.getMaterial(), cd);
				return true;
			}
		}
		else if(n == 4) {
			if(pigWand(plugin, e)) {
				Cooldown.setCD(p, e.getMaterial(), cd);
				return true;
			}
		}
		else if(n == 5) {
			if(treeWand(plugin, e)) {
				Cooldown.setCD(p, e.getMaterial(), cd);
				return true;
			}
		}
		return false;
	}

	public static boolean pigWand (Main plugin, PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Location spawnAt = p.getEyeLocation().toVector().add(p.getEyeLocation().getDirection()).toLocation(p.getWorld());
		Vector direction = p.getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("pig.velocity"));
		Pig pig = (Pig) p.getWorld().spawnEntity(spawnAt, EntityType.PIG);
		
		p.getWorld().playSound(spawnAt, Sound.ENTITY_PIG_AMBIENT, (float)plugin.getConfig().getDouble("pig.volume"), 1F);
		pig.addPassenger(p);
		pig.setVelocity(direction);
		
		Cooldown.setCD(p, e.getMaterial(), plugin.getConfig().getDouble("pig.cooldown"));
		
		loseDurability(e.getItem());
		
		return true;
	}
	
	public static boolean duckWand (Main plugin, PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Location spawnAt = p.getTargetBlock(null, 20).getLocation();
		spawnAt.setY(spawnAt.getY()+1);
		
		Chicken chick = (Chicken) p.getWorld().spawnEntity(spawnAt, EntityType.CHICKEN);
		
		p.getWorld().playSound(spawnAt, Sound.ENTITY_CHICKEN_AMBIENT, (float)plugin.getConfig().getDouble("duck.volume"), 1F);
		
		ItemStack grape = new ItemStack(Material.APPLE);
		ItemMeta name = grape.getItemMeta();
		name.setDisplayName("Big Red Grape");
		
		grape.setItemMeta(name);
		
		chick.getWorld().dropItem(chick.getLocation(), grape);
		
		Cooldown.setCD(p, e.getMaterial(), plugin.getConfig().getDouble("duck.cooldown"));
		
		loseDurability(e.getItem());
		
		return true;
	}
	
	public static boolean treeWand (Main plugin, PlayerInteractEvent e) {
		
		Random rng = new Random();
		Player p = e.getPlayer();
		Location spawnAt = p.getTargetBlock(null, plugin.getConfig().getInt("tree.maxdistance")).getLocation();
			
		TreeType[] types = {TreeType.ACACIA, TreeType.BIG_TREE, TreeType.BIRCH, TreeType.COCOA_TREE, TreeType.JUNGLE, TreeType.JUNGLE_BUSH, 
				TreeType.REDWOOD, TreeType.SMALL_JUNGLE, TreeType.SWAMP, TreeType.TALL_BIRCH, TreeType.TALL_REDWOOD, TreeType.TREE};
		
		int i;
		do {
			i = rng.nextInt(types.length);
		} while((types[i] == null));
		
		if(p.getWorld().generateTree(spawnAt, types[i])) {
			p.getWorld().playSound(spawnAt, Sound.ITEM_CROP_PLANT, (float)plugin.getConfig().getDouble("tree.volume"), 1F);
			Cooldown.setCD(p, e.getMaterial(), plugin.getConfig().getDouble("tree.cooldown"));
			
			loseDurability(e.getItem());
			
			return true;
		}
		else {
			return false;
		}
	}
}