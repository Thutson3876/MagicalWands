package me.thutson3876.magicalwands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.thutson3876.magicalwands.utils.Utils;
import me.thutson3876.magicalwands.wands.Wand;
import me.thutson3876.magicalwands.Main;

public class WandsCommand implements CommandExecutor{
	public Main plugin;
	
	public WandsCommand (Main plugin) {
		this.plugin = plugin;
		
		plugin.getCommand("magicwands").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("magicwands.use")) {
			if(args.length == 0) {
				sender.sendMessage(Utils.chat("&e/magicwands wandslist"));
				sender.sendMessage(Utils.chat("&e/magicwands give <player> <wand> <amt>"));
				
				return true;
			}
			else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("wandslist")) {
					String list = "";
					int n = Wand.getWandsListString().length;
					int i = 0;
					
					for(String s : Wand.getWandsListString()) {
						list += s;
						if(i < n-1) {
							list += ", ";
						}
					}
					
					sender.sendMessage(list);
					return true;
				}
				else {
					sender.sendMessage(Utils.chat("&e/magicwands wandslist"));
					sender.sendMessage(Utils.chat("&e/magicwands give <player> <wand> <amt>"));
				}
			}
			else if(args.length == 2) {
				sender.sendMessage(Utils.chat("&e/magicwands give <player> <wand> <amt>"));
				return true;
			}
			else if(args.length == 3) {
				sender.sendMessage(Utils.chat("&e/magicwands give <player> <wand> <amt>"));
				return true;
			}
			else if(args.length == 4) {
				Player p = Bukkit.getPlayer(args[1]);
				int amt;
				
				if(p != null && isNumeric(args[3])) {
					amt = Integer.parseInt(args[3]);
					if(amt > 0 && amt < 129) {
						ItemStack wand = Wand.getWand(args[2], amt);
						if(wand == null) {
							sender.sendMessage(Utils.chat("&ePlease enter a valid wand."));
							return true;
						}
						else {
							p.getInventory().addItem(wand);
							return true;
						}
					}
				}
				else {
					sender.sendMessage(Utils.chat("&ePlease enter a valid name and amount"));
					return true;
				}
			}
			else {
				sender.sendMessage(Utils.chat("&e/magicwands wandslist"));
				sender.sendMessage(Utils.chat("&e/magicwands give <player> <wand> <amt>"));
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}
