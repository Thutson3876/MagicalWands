package me.thutson3876.magicalwands;

import org.bukkit.plugin.java.JavaPlugin;

import me.thutson3876.magicalwands.commands.WandsCommand;
import me.thutson3876.magicalwands.listeners.EntitySpawnListener;
import me.thutson3876.magicalwands.listeners.MobDeathListener;
import me.thutson3876.magicalwands.listeners.WandListener;
import me.thutson3876.magicalwands.listeners.onEat;

public class Main extends JavaPlugin{
	
	@Override
	public void onEnable() {
		new WandListener(this);
		new onEat(this);
		new WandsCommand(this);
		new MobDeathListener(this);
		new EntitySpawnListener(this);
		this.saveDefaultConfig();
	}
}
