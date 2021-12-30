package org.uninstal.skywars.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.uninstal.skywars.data.Game;

public class GameQuitEvent extends Event {
	
	private static HandlerList handlerList = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	private Game game;
	
	public GameQuitEvent() {
		// TODO: event
	}
	
	public Game getGame() {
		return game;
	}
}
