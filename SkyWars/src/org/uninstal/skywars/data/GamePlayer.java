package org.uninstal.skywars.data;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GamePlayer {
	
	private final Player player;
	private boolean spectator;
	private int kills;

	public GamePlayer(Player player) {
		this.player = player;
		this.spectator = false;
		this.kills = 0;
	}
	
	public Player getBukkit() {
		return player;
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