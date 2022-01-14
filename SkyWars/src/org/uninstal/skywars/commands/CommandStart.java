package org.uninstal.skywars.commands;

import org.bukkit.entity.Player;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameState;

public class CommandStart extends SWCommand {

	public CommandStart(int minArgs, String permission) {
		super(minArgs, permission);
	}

	@Override
	public boolean run(Player sender, String[] args) {
		
		String id = args[1].toLowerCase();
		Game game = GameManager.getGame(id);
		
		if(game == null) {
			// MESSAGE
		}
		
		if(game.getState() == GameState.BATTLE) {
			// MESSAGE
		}
		
		if(game.getPlayers().size() < game.getConfig().getMinPlayers()) {
			// MESSAGE
		}
		
		if(!game.isConfigured()) {
			// MESSAGE
		}
		
		boolean started = game.start();
		if(!started) {
			// MESSAGE
		} else {
			// MESSAGE
		}
		
		return false;
	}
}