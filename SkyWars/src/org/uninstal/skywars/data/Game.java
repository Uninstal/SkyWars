package org.uninstal.skywars.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

public class Game {
	
	private GameConfig config;
	private GameThread thread;
	private GameMap map;
	private Lobby lobby;
	private GameState state;
	private Map<String, GamePlayer> gamePlayers;
	
	private int starting = 0;
	
	public Game(GameConfig config, GameMap map, Lobby lobby) {
		this.config = config;
		this.thread = new GameThread(this);
		this.lobby = lobby;
		this.map = map;
		this.gamePlayers = new HashMap<>();
		this.state = GameState.WAIT;
	}
	
	public Game(GameConfig config) {
		this.config = config;
		this.thread = new GameThread(this);
		this.gamePlayers = new HashMap<>();
		this.state = GameState.WAIT;
	}
	
	public String getId() {
		return config.getId();
	}
	
	public GameThread getThread() {
		return thread;
	}
	
	public Collection<GamePlayer> getPlayers() {
		return gamePlayers.values();
	}
	
	public GamePlayer getPlayer(String name) {
		return gamePlayers.get(name);
	}
	
	public GameConfig getConfig() {
		return config;
	}
	
	public GameMap getMap() {
		return map;
	}
	
	public Lobby getLobby() {
		return lobby;
	}
	
	public GameState getState() {
		return state;
	}
	
	public int getStarting() {
		return starting;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
	
	public boolean connect(Player player) {
		
		if(state == GameState.BATTLE
			|| gamePlayers.size() >= config.getMaxPlayers()
			|| lobby == null || map == null)
			return false;
		
		GamePlayer gamePlayer = new GamePlayer(player);
		this.gamePlayers.put(player.getName(), gamePlayer);
		
		lobby.connect(player);
		return true;
	}
	
	public void reset() {
		
		lobby.reset();
		map.reset();
	}
}