package me.thutson3876.magicalwands.custommobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.PolarBear;


import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntityPolarBear;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

public class CustomPolarBear extends EntityPolarBear{

	@SuppressWarnings("deprecation")
	public CustomPolarBear(Location loc) {
		super(EntityTypes.POLAR_BEAR, ((CraftWorld)loc.getWorld()).getHandle());
		
		IChatBaseComponent chatBase = new ChatMessage(ChatColor.GOLD + "Ice Wielder");
		PolarBear craftPolarBear = (PolarBear) this.getBukkitEntity();
		
		
		craftPolarBear.setMaxHealth(60);
		this.setHealth(60);
		this.setCustomName(chatBase);
		this.setCustomNameVisible(true);

		this.getWorld().addEntity(this);
	}

}
