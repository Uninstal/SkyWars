package org.uninstal.skywars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.uninstal.skywars.commands.CommandCreate;
import org.uninstal.skywars.commands.CommandSet;
import org.uninstal.skywars.commands.SWCommand;

public class Main extends JavaPlugin {
	
	public static Main INSTANCE;
	private static Map<String, SWCommand> commands;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		
		// Registering commands.
		commands = new HashMap<>();
		commands.put("create", new CommandCreate(4, "skywars.game.create"));
		commands.put("set", new CommandSet(1, "skywars.game.set"));
	}
	
	@Override
	public void onDisable() {
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("skywars")) {
			
			// Params.
			int lenght = args.length;
			String arg = args[0].toLowerCase();
			
			if(lenght == 0) {
				
				// TODO: message
				return false;
			}
			
			if(lenght >= 1 && arg.equals("admin")) {
				
				// TODO: message
				return false;
			}
			
			else if(commands.containsKey(arg)) {
				
				SWCommand cmd = commands.get(arg);
				int minLenght = cmd.getMinArgs();
				String perm = cmd.getPermission();
				
				if(!sender.hasPermission(perm)) {
					// TODO: message
				}
				
				if(lenght < minLenght) {
					// TODO: message
				}
				
				cmd.run((Player) sender, args);
				return false;
			}
			
			else {
				
				// TODO: message
				return false;
			}
		}
		
		return false;
	}
}