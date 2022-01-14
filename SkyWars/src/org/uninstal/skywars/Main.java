package org.uninstal.skywars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.uninstal.skywars.commands.CommandCreate;
import org.uninstal.skywars.commands.CommandSet;
import org.uninstal.skywars.commands.CommandStart;
import org.uninstal.skywars.commands.SWCommand;
import org.uninstal.skywars.storage.Storage;
import org.uninstal.skywars.util.Messenger;
import org.uninstal.skywars.util.Utils;
import org.uninstal.skywars.util.Values;

public class Main extends JavaPlugin {
	
	public static Main INSTANCE;
	public static Storage STORAGE;
	private static Files files;
	private static Map<String, SWCommand> commands;
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		
		// Load config-files.
		this.files = new Files(this);
		YamlConfiguration config = files.registerNewFile("config");
		YamlConfiguration messages = files.registerNewFile("messages");
		YamlConfiguration guis = files.registerNewFile("guis");
		
		// Init values.
		Values.setConfig(config);
		Values.setMessages(messages);
		Values.setGuis(guis);
		Values.read();
		
		// Load datas.
		Runnable run = () -> {
			STORAGE = Storage.of("yaml");
			STORAGE.init();
			
			int loaded = STORAGE.load();
			String log = Values.LOG_GAMES_LOAD
				.replace("<value>", String.valueOf(loaded));
			Messenger.console(log);
			
			if(Values.AUTOSAVE_ENABLE)
				STORAGE.enableAutosave(
					Values.AUTOSAVE_TICKS);
		};
		
		if(Values.ASYNC_DATAS_LOAD) 
			Utils.async(run);
		else run.run();
		
		// Registering commands.
		commands = new HashMap<>();
		commands.put("create", new CommandCreate(2, "skywars.game.create"));
		commands.put("set", new CommandSet(1, "skywars.game.set"));
		commands.put("start", new CommandStart(2, "skywars.game.start"));
	}
	
	@Override
	public void onDisable() {
		
		// Save datas.
		int saved = STORAGE.save();
		if(saved != 0){
			String log = Values.LOG_GAMES_SAVE
				.replace("<value>", String.valueOf(saved));
			Messenger.console(log);
		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(command.getName().equalsIgnoreCase("skywars")) {
			
			// Params.
			int lenght = args.length;
			String arg = args[0].toLowerCase();
			
			if(lenght == 0) {
				
				
				return false;
			}
			
			else if(commands.containsKey(arg)) {
				
				SWCommand cmd = commands.get(arg);
				int minLenght = cmd.getMinArgs();
				String perm = cmd.getPermission();
				
				if(!sender.hasPermission(perm)) {
					sender.sendMessage(Values.PLAYER_PERMISSION_ERROR);
					return false;
				}
				
				if(lenght < minLenght) {
					sender.sendMessage(Values.COMMANDS_HELP);
					return false;
				}
				
				cmd.run((Player) sender, args);
				return false;
			}
			
			else {
				
				// MESSAGE
				return false;
			}
		}
		
		return false;
	}
}