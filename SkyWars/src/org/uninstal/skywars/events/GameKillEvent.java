package org.uninstal.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.uninstal.skywars.data.Game;

public class GameKillEvent extends Event implements GameEvent {
	
	private static HandlerList handlerList;

	public static HandlerList getHandlerList() {
		return handlerList;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	private Game game;
	private Player killer;
	private Player target;
	private boolean message;
	
	public GameKillEvent(Game game, Player killer, Player target) {
		this.game = game;
		this.killer = killer;
		this.target = target;
		this.message = true;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getKiller() {
		return killer;
	}
	
	public Player getTarget() {
		return target;
	}
	
	public void setMessage(boolean message) {
		this.message = message;
	}
	
	public boolean hasMessage() {
		return message;
	}
}