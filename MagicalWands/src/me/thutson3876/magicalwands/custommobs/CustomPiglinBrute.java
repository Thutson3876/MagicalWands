package me.thutson3876.magicalwands.custommobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.PiglinBrute;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.EntityPiglinBrute;
import net.minecraft.server.v1_16_R3.EntityTypes;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;

public class CustomPiglinBrute extends EntityPiglinBrute {
	@SuppressWarnings("deprecation")
	public CustomPiglinBrute(Location loc) {
		super(EntityTypes.PIGLIN_BRUTE, ((CraftWorld)loc.getWorld()).getHandle());
		
		IChatBaseComponent chatBase = new ChatMessage(ChatColor.GOLD + "Pig Wielder");
		PiglinBrute craftPiglinBrute = (PiglinBrute) this.getBukkitEntity();
		
		craftPiglinBrute.setMaxHealth(100);
		this.setHealth(100);
		this.setCustomName(chatBase);
		this.setCustomNameVisible(true);
		ItemStack[] armor = new ItemStack[4];
		armor[3] = new ItemStack(Material.GOLDEN_HELMET);
		armor[2] = new ItemStack(Material.GOLDEN_CHESTPLATE);
		armor[1] = new ItemStack(Material.GOLDEN_LEGGINGS);
		armor[0] = new ItemStack(Material.GOLDEN_BOOTS);
		craftPiglinBrute.getEquipment().setArmorContents(armor);
		craftPiglinBrute.getEquipment().setItemInMainHand(new ItemStack(Material.GOLDEN_AXE));
		this.getWorld().addEntity(this);
	}
}
