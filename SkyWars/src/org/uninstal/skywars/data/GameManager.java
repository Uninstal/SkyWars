package org.uninstal.skywars.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;

public class GameManager {

	private static Map<String, Game> games = new HashMap<>();
	private static Map<Location, GameSign> signs = new HashMap<>();
	
	public static Map<String, Game> getGames() {
		return games;
	}
	
	public static Game getGame(String id) {
		return games.get(id);
	}
	
	public static void registerGame(Game game) {
		games.put(game.getId(), game);
	}
	
	public static GameSign getSign(Location location) {
		return signs.get(location);
	}
	
	public static List<GameSign> getSigns(Game game) {
		List<GameSign> list = new ArrayList<>();
		
		for(GameSign sign : signs.values())
			if(sign.getGame().equals(game))
				list.add(sign);
		
		return list;
	}
	
	public static void registerSign(GameSign sign) {
		signs.put(sign.getLocation(), sign);
	}
}