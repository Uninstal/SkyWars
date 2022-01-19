package org.uninstal.skywars.commands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameLobby;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameMap;
import org.uninstal.skywars.util.Messenger;
import org.uninstal.skywars.util.Values;
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
		
		if(game == null) {
			// MESSAGE
			return false;
		}
		
		Messenger.protectNaN(sender, () -> {
			
			if(param.equalsIgnoreCase("lobby")) {
				
				Location location = sender.getLocation();
				game.getLobby().setLocation(location);
				// MESSAGE
				return;
			}
			
			else if(param.equalsIgnoreCase("mainlobby")) {
				
				Location location = sender.getLocation();
				GameLobby.setMainLobby(location);
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
			
			else if(param.equalsIgnoreCase("point")) {
				
				GameMap gameMap = game.getMap();
				Location location = sender.getLocation();
				int id = 0;
				
				if(args.length == 3) {
					id = gameMap.getPoints().size() + 1;
					
					gameMap.addPoint(id, location);
					sender.sendMessage(Values.COMMAND_SET_POINT
						.replace("<id>", String.valueOf(id)));
					return;
				}
				
				else {
					id = Integer.valueOf(value);
					
					if(!gameMap.getArea().checkXYZ(location)) {
						sender.sendMessage(Values.COMMAND_SET_POINT_REGION_ERROR);
						return;
					}
					
					if(id > game.getConfig().getMaxPlayers()
						|| id < 1) {
						sender.sendMessage(Values.COMMAND_SET_POINT_RANGE_ERROR);
						return;
					}
					
					gameMap.addPoint(id, location);
					sender.sendMessage(Values.COMMAND_SET_POINT
						.replace("<id>", String.valueOf(id)));
					return;
				}
			}
			
			else if(param.equalsIgnoreCase("chest")) {

				GameMap gameMap = game.getMap();
				Block block = sender.getTargetBlock(null, 4);
				
				if(!gameMap.getArea().checkXYZ(block.getLocation())) {
					sender.sendMessage(Values.COMMAND_SET_CHEST_REGION_ERROR);
					return;
				}
				
				if(block.getType() != Material.CHEST) {
					sender.sendMessage(Values.COMMAND_SET_CHEST_MATERIAL_ERROR);
					return;
				}
				
				gameMap.addChest((Chest) block);
				sender.sendMessage(Values.COMMAND_SET_CHEST);
				return;
			}
		});
		
		return true;
	}
}