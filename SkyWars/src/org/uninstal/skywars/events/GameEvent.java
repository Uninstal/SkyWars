package org.uninstal.skywars.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.uninstal.skywars.data.Game;

public interface GameEvent {
	
	public Game getGame();
	
	public static <T> T handle(T event) {
		
		GameEvent gameEvent = (GameEvent) event;
		gameEvent.getGame().update();
		
		Bukkit.getPluginManager().callEvent((Event) event);
		return event;
	}
}