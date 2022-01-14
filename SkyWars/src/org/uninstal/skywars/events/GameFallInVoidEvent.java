package org.uninstal.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.uninstal.skywars.data.Game;

public class GameFallInVoidEvent extends Event implements GameEvent {
	
	private static HandlerList handlerList;
	private Game game;
	private Player player;
	private boolean message;
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}
	
	public GameFallInVoidEvent(Game game, Player player) {
		this.game = game;
		this.player = player;
		this.message = true;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean hasMessage() {
		return message;
	}
	
	public void setDeathMessage(boolean message) {
		this.message = message;
	}
}