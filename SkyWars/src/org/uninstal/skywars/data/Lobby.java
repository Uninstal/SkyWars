package org.uninstal.skywars.data;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Lobby {
	
	private Location location;
	private List<Player> players;
	
	public Lobby(Location location) {
		this.location = location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public void connect(Player player) {
		// Teleport in lobby.
		player.teleport(location);
		// Add to data.
		players.add(player);
	}
	
	public void disconnect(Player player) {
		// Teleport in main lobby.
		// ...
		// Remove from data.
		players.remove(player);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void reset() {
		
	}
}