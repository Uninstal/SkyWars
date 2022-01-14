package org.uninstal.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.uninstal.skywars.data.Game;

public class GameDeathEvent extends Event implements GameEvent {
	
	private static HandlerList handlerList;
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}

	private Game game;
	private Player player;
	private GameDeathReason reason;
	private boolean message;
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	public GameDeathEvent(Game game, Player player, GameDeathReason reason) {
		this.game = game;
		this.player = player;
		this.reason = reason;
		this.message = true;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public GameDeathReason getReason() {
		return reason;
	}
	
	public boolean hasMessage() {
		return message;
	}
	
	public void setDeathMessage(boolean message) {
		this.message = message;
	}
}