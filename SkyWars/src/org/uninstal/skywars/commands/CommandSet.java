package org.uninstal.skywars.commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameMap;
import org.uninstal.skywars.util.Messenger;
import org.uninstal.skywars.util.WorldGuard;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class CommandSet extends SWCommand {
	
	public CommandSet(int minArgs, String permission) {
		super(minArgs, permission);
	}
	
	@Override
	public boolean run(Player sender, String[] args) {
		
		if(args.length < 2) {
			// MESSAGE
			return false;
		}
		
		String gameId = args[1];
		String param = args[2];
		String value = args.length > 3 ? args[3] : null;
		Game game = GameManager.getGame(gameId);
		
		Messenger.protectNaN(sender, () -> {
			
			if(param.equalsIgnoreCase("lobby")) {
				
				Location location = sender.getLocation();
				game.getLobby().setLocation(location);
				// MESSAGE
				return;
			}
			
			else if(param.equalsIgnoreCase("min")) {
				
				int valueInt = Integer.valueOf(value);
				game.getConfig().setMinPlayers(valueInt);
				// MESSAGE
				return;
			}
			
			else if(param.equalsIgnoreCase("max")) {
				
				int valueInt = Integer.valueOf(value);
				game.getConfig().setMaxPlayers(valueInt);
				// MESSAGE
				return;
			}
			
			else if(param.equalsIgnoreCase("map")) {
				
				if(WorldGuard.hasRegion(value)) {
					
					ProtectedRegion region = WorldGuard.getRegion(value);
					game.setMap(GameMap.create(game, region));
					
					// MESSAGE
					return;
				}
				
				else {
					
					// MESSAGE
					return;
				}
			}
		});
		
		return true;
	}
}