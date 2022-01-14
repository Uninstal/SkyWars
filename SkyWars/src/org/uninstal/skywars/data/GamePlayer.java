package org.uninstal.skywars.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GamePlayer {
	
	private final Game game;
	private final GameScoreboard board;
	private final Player player;
	private boolean spectator;
	private int kills;

	public GamePlayer(Game game, Player player) {
		this.game = game;
		this.player = player;
		this.spectator = false;
		this.kills = 0;
		
		// Registering and showing scoreboard.
		this.board = new GameScoreboard(this);
		this.board.show();
	}
	
	public Game getGame() {
		return game;
	}
	
	public Player getBukkit() {
		return player;
	}
	
	public boolean inLobby() {
		return game.getLobby()
			.getPlayers().contains(player);
	}
	
	public GameScoreboard getScoreboard() {
		return board;
	}
	
	public String getNickname() {
		return getBukkit().getName();
	}
	
	public Location getLocation() {
		return getBukkit().getLocation();
	}
	
	public int getKills() {
		return kills;
	}
	
	public boolean isSpectator() {
		return spectator;
	}
	
	public void setSpectator(boolean spectator) {
		this.spectator = spectator;
	}
}