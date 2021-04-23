package me.thutson3876.magicalwands.custommobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Blaze;

import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntityBlaze;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

public class CustomBlaze extends EntityBlaze {

	@SuppressWarnings("deprecation")
	public CustomBlaze(Location loc) {
		super(EntityTypes.BLAZE, ((CraftWorld)loc.getWorld()).getHandle());
		
		IChatBaseComponent chatBase = new ChatMessage(ChatColor.GOLD + "Fireball Wielder");
		Blaze craftBlaze = (Blaze) this.getBukkitEntity();
		
		craftBlaze.setMaxHealth(40);
		this.setHealth(40);
		this.setCustomName(chatBase);
		this.setCustomNameVisible(true);
		
		this.getWorld().addEntity(this);
	}
}
