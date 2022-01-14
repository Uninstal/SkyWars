package org.uninstal.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.uninstal.skywars.data.Game;

public class GameJoinEvent extends Event implements Cancellable, GameEvent {
	
	private static HandlerList handlerList = new HandlerList();
	
	public static HandlerList getHandlerList() {
		return handlerList;
	}
	
	@Override
	public HandlerList getHandlers() {
		return handlerList;
	}
	
	private Game game;
	private Player player;
	private boolean cancel;
	
	public GameJoinEvent(Game game, Player player) {
		this.game = game;
		this.player = player;
		this.cancel = false;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getNewSize() {
		return game.getPlayers().size() + 1;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
}
