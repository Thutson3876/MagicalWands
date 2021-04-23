package me.thutson3876.magicalwands.custommobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Phantom;

import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntityPhantom;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

public class CustomPhantom extends EntityPhantom {

	@SuppressWarnings("deprecation")
	public CustomPhantom(Location loc) {
		super(EntityTypes.PHANTOM, ((CraftWorld)loc.getWorld()).getHandle());
		
		IChatBaseComponent chatBase = new ChatMessage(ChatColor.GOLD + "Wind Wielder");
		Phantom craftPhantom = (Phantom) this.getBukkitEntity();
		
		this.setSize(2*(this.getSize()));
		
		craftPhantom.setMaxHealth(40);
		this.setHealth(40);
		this.setCustomName(chatBase);
		this.setCustomNameVisible(true);
		this.getWorld().addEntity(this);
	}

}
