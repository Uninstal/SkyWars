package org.uninstal.skywars.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.uninstal.skywars.data.Game;

public class GameQuitEvent extends Event implements GameEvent {
	
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
	
	public GameQuitEvent(Game game, Player player) {
		this.game = game;
		this.player = player;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getNewSize() {
		return game.getPlayers().size() - 1;
	}
}