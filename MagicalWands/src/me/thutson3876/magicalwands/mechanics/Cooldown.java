package me.thutson3876.magicalwands.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Cooldown {
	
	public static int getCD (Player p, Material m) {
		return p.getCooldown(m)*20;
	}
	
	public static void setCD(Player p, Material m, double cd) {
		p.setCooldown(m, (int)Math.round(cd*20));
	}
	
	public static boolean checkCD(Player p, Material m) {
		if(!p.hasCooldown(m)){
			//p.sendMessage("worked");
			return true;
		}
		else {
			//p.sendMessage("nowork");
			return false;
		}
	}
}
