package me.thutson3876.magicalwands.custommobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.GenericAttributes;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

public class CustomGiant extends EntityZombie {

	public CustomGiant(Location loc) {
		super(EntityTypes.ZOMBIE, ((CraftWorld)loc.getWorld()).getHandle());
		
		IChatBaseComponent chatBase = new ChatMessage(ChatColor.GOLD + "Tree Wielder");
		Zombie craftGiant = (Zombie) this.getBukkitEntity();
		
		this.setCustomName(chatBase);
		this.setCustomNameVisible(true);
		craftGiant.getEquipment().setItemInMainHand(new ItemStack(Material.OAK_SAPLING));
		this.setInvisible(false);
		this.setAggressive(true);
		this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(100.0);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.4);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(9.0);
		
		this.getWorld().addEntity(this);
	}

}
