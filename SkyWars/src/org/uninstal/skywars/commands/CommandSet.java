package org.uninstal.skywars.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameManager;

public class CommandSet extends SWCommand {
	
	private final Map<String, SetFunction<Game, Player, String>> functions = new HashMap<>();

	public CommandSet(int minArgs, String permission) {
		super(minArgs, permission);
		
		functions.put("min", (g, p, v) -> g.getConfig().setMinPlayers(Integer.valueOf(v)));
		functions.put("max", (g, p, v) -> g.getConfig().setMaxPlayers(Integer.valueOf(v)));
		functions.put("setlobby", (g, p, v) -> g.getLobby().setLocation(p.getLocation()));
	}
	
	@Override
	public boolean run(Player sender, String[] args) {
		
		if(args.length < 3) {
			
			// Send help message.
			return false;
		}
		
		String id = args[1];
		Game game = GameManager.getGame(id);
		String param = args[2];
		String value = args[3];
		
		SetReturn setReturn = null;
		
		try {
			functions.get(param).apply(game, sender, value);
			setReturn = SetReturn.ACCEPT;
		} catch (Exception e) {
			setReturn = SetReturn.EXCEPTION;
		}
		
		if(setReturn == SetReturn.ACCEPT) {
			// Send accept message.
		} else if(setReturn == SetReturn.EXCEPTION) {
			// Send exception message.
		}
		
		return true;
	}
	
	private interface SetFunction<K, L, V> {
		
		void apply(K k, L l, V v);
	}
	
	private enum SetReturn {
		
		ACCEPT, EXCEPTION;
	}
}