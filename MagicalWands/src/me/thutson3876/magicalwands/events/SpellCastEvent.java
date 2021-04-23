package me.thutson3876.magicalwands.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.thutson3876.magicalwands.wands.Wand;

public class SpellCastEvent extends Event {

	private static final HandlerList handlers = new HandlerList();
	
	public SpellCastEvent(Wand wand) {
		
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	
}
