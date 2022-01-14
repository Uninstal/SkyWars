package org.uninstal.skywars.data;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.uninstal.skywars.util.Utils;
import org.uninstal.skywars.util.Values;

public class GameLobby {
	
	private Game game;
	private Location location;
	private List<Player> players;
	
	public GameLobby(Game game, Location location) {
		this.location = location;
		this.game = game;
	}
	
	public Game getGame() {
		return game;
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
		player.teleport(Values.MAIN_LOBBY);
		// Remove from data.
		players.remove(player);
	}
	
	public void teleportToMap() {
		// Teleport players to map.
		players.forEach(player -> {
			GamePlayer gp = game.getPlayer(player);
			game.getMap().connect(gp);
		});
		// Clear the memory.
		players.clear();
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public void reset() {
		
	}
	
	public static GameLobby create(Game game, String format) {
		return new GameLobby(game, (Location) Utils.parse(format));
	}
}