package org.uninstal.skywars.storage;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.uninstal.skywars.Main;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameConfig;
import org.uninstal.skywars.data.GameLobby;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameMap;
import org.uninstal.skywars.util.Utils;
import org.uninstal.skywars.util.WorldGuard;

public class Yaml implements Storage {
	
	private File file;
	private YamlConfiguration yaml;

	@Override
	public void init() {
		
		try {
			
			Main main = Main.INSTANCE;
			file = new File(main.getDataFolder() + File.separator + "games.yml");
			if(!file.exists()) file.createNewFile();
			yaml = YamlConfiguration.loadConfiguration(file);
			
		} catch (Exception e) {
			// MESSAGE
		}
	}

	@Override
	public int save() {
		
		// Create new file for save new data.
		YamlConfiguration games = 
			new YamlConfiguration();
		
		int saved = 0;
		// TODO: save games
		
		try {
			games.save(file);
			return saved;
		} catch (IOException e) {
			// MESSAGE
			return 0;
		}
	}

	@Override
	public int load() {
		int loaded = 0;
		
		for(String key : yaml.getKeys(false)) {
			
			String id = yaml.getString(key + ".id");
			int time = yaml.getInt(key + ".time", 0);
			int min = yaml.getInt(key + ".min-players", 0);
			int max = yaml.getInt(key + ".max-players", 0);
			
			GameConfig config = new GameConfig(id, time, min, max);
			Game game = new Game(config);
			GameManager.registerGame(game);
			
			if(yaml.contains(key + ".map")) {
				
				String regionId = yaml.getString(key + ".map.region");
				game.setMap(GameMap.create(game, WorldGuard.getRegion(regionId)));
			}
			
			if(yaml.contains(key + ".lobby")) {
				
				String format = yaml.getString(key + ".lobby");
				Location location = (Location) Utils.parse(format);
				GameLobby lobby = new GameLobby(game, location);
				game.setLobby(lobby);
			}
			
			++loaded;
			continue;
		}
		
		return loaded;
	}
}