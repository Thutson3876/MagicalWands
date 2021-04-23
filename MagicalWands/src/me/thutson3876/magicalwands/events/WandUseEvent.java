package me.thutson3876.magicalwands.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WandUseEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
