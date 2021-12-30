package org.uninstal.skywars.commands;

import org.bukkit.entity.Player;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameConfig;
import org.uninstal.skywars.data.GameManager;

public class CommandCreate extends SWCommand {

	public CommandCreate(int minArgs, String permission) {
		super(minArgs, permission);
	}
	
	@Override
	public boolean run(Player sender, String[] args) {
		
		String id = args[1];
		int min = Integer.valueOf(args[2]);
		int max = Integer.valueOf(args[3]);
		
		GameConfig config = new GameConfig(id, min, max);
		Game game = new Game(config);
		GameManager.registerGame(game);
		
		// Message.
		sender.sendMessage("");
		return true;
	}
}