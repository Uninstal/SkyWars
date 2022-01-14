package org.uninstal.skywars.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.uninstal.skywars.data.gui.Gui;
import org.uninstal.skywars.data.gui.GuiGameInfo;
import org.uninstal.skywars.data.gui.GuiGameList;
import org.uninstal.skywars.events.GameEvent;
import org.uninstal.skywars.events.GameJoinEvent;
import org.uninstal.skywars.events.GameQuitEvent;

public class Game {
	
	private GameConfig config;
	private GameThread thread;
	private GameMap map;
	private GameLobby lobby;
	private GameState state;
	private String lastWinner;
	private Map<String, GamePlayer> gamePlayers;
	
	public Game(GameConfig config, GameMap map, GameLobby lobby) {
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
	
	public void setLastWinner(String player) {
		this.lastWinner = player;
	}
	
	public String getLastWinner() {
		return lastWinner;
	}
	
	public void setMap(GameMap map) {
		this.map = map;
	}
	
	public void setLobby(GameLobby lobby) {
		this.lobby = lobby;
	}
	
	public Collection<GamePlayer> getPlayers() {
		return gamePlayers.values();
	}
	
	public Collection<GamePlayer> getRemainPlayers() {
		List<GamePlayer> players = new ArrayList<>();
		
		for(GamePlayer player : gamePlayers.values())
			if(!player.isSpectator())
				players.add(player);
		
		return players;
	}
	
	public GamePlayer getPlayer(String name) {
		return gamePlayers.get(name);
	}
	
	public GamePlayer getPlayer(Player player) {
		return gamePlayers.get(player.getName());
	}
	
	public GameConfig getConfig() {
		return config;
	}
	
	public GameMap getMap() {
		return map;
	}
	
	public GameLobby getLobby() {
		return lobby;
	}
	
	public GameState getState() {
		return state;
	}
	
	public GameState getRealState() {
		
		if(state == GameState.BATTLE) return GameState.BATTLE;
		else if(lobby.getPlayers().size() >= config.getMinPlayers()) return GameState.STARTING;
		else return GameState.WAIT;
	}
	
	public void setState(GameState state) {
		this.state = state;
	}
	
	public boolean start() {
		
		if(getRealState() != GameState.STARTING)
			return false;
		
		thread.startBattle();
		return true;
	}
	
	public boolean isConfigured() {
		
		return map != null
			&& config != null
			&& config.isConfigured()
			&& map.isConfigured();
	}
	
	public boolean connect(Player player) {
		
		if(state == GameState.BATTLE
			|| gamePlayers.size() >= config.getMaxPlayers()
			|| lobby == null || map == null)
			return false;
		
		if(!GameEvent.handle(new GameJoinEvent(this, player)).isCancelled()) {
			
			GamePlayer gamePlayer = new GamePlayer(this, player);
			this.gamePlayers.put(player.getName(), gamePlayer);
			
			// Connect player to lobby.
			lobby.connect(player);
			return true;
		}
		
		return false;
	}
	
	public void disconnect(Player player) {
		GameEvent.handle(new GameQuitEvent(this, player));
		
		if(state == GameState.WAIT || state == GameState.STARTING) {
			this.lobby.disconnect(player);
			this.gamePlayers.remove(player.getName());
		}
		
		if(state == GameState.BATTLE) {
			this.map.disconnect(getPlayer(player));
			this.gamePlayers.remove(player.getName());
		}
	}
	
	public boolean contains(Player player) {
		return gamePlayers.values()
			.stream().anyMatch(gp -> 
			gp.getBukkit().equals(player));
	}
	
	public void update() {
		
		// Updating guis.
		Gui.guis.values().forEach(gui -> {
			
			if(gui instanceof GuiGameList
				|| (gui instanceof GuiGameInfo 
				&& ((GuiGameInfo) gui).getGame()
				.equals(this))) 
				gui.update();
		});
		
		// Updating scoreboard.
		getPlayers().forEach(gp -> {
			gp.getScoreboard().update();
		});
	}
	
	public void reset() {
		
		lobby.reset();
		map.reset();
	}
}