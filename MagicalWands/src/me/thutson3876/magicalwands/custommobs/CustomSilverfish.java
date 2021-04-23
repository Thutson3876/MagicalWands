package me.thutson3876.magicalwands.custommobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Silverfish;

import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntitySilverfish;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

public class CustomSilverfish extends EntitySilverfish{

	@SuppressWarnings("deprecation")
	public CustomSilverfish(Location loc) {
		super(EntityTypes.SILVERFISH, ((CraftWorld)loc.getWorld()).getHandle());
		
		IChatBaseComponent chatBase = new ChatMessage(ChatColor.GOLD + "Earth Wielder");
		Silverfish craftFish = (Silverfish) this.getBukkitEntity();
		
		craftFish.setMaxHealth(16);
		this.setHealth(16);
		this.setCustomName(chatBase);
		this.setCustomNameVisible(true);
		
		this.getWorld().addEntity(this);
	}

}
