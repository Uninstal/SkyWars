package org.uninstal.skywars.util;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public class Values {
	
	private static YamlConfiguration config;
	private static YamlConfiguration messages;
	private static YamlConfiguration guis;
	
	public static void setConfig(YamlConfiguration config) {
		Values.config = config;
	}
	
	public static void setMessages(YamlConfiguration messages) {
		Values.messages = messages;
	}
	
	public static void setGuis(YamlConfiguration guis) {
		Values.guis = guis;
	}
	
	public static void read() {
		
		COMMANDS_HELP = ValuesUtils.loadString(messages, "commands-help");
		COMMAND_SET_POINT = ValuesUtils.loadString(messages, "command-set-point");
		COMMAND_SET_POINT_RANGE_ERROR = ValuesUtils.loadString(messages, "command-set-point-range-error");
		COMMAND_SET_POINT_REGION_ERROR = ValuesUtils.loadString(messages, "command-set-point-region-error");
		COMMAND_SET_CHEST = ValuesUtils.loadString(messages, "command-set-chest");
		COMMAND_SET_CHEST_MATERIAL_ERROR = ValuesUtils.loadString(messages, "command-set-chest-material-error");
		COMMAND_SET_CHEST_REGION_ERROR = ValuesUtils.loadString(messages, "command-set-chest-region-error");
		COMMAND_PERMISSION_ERROR = ValuesUtils.loadString(messages, "command-permission-error");
		COMMAND_NUMBER_ERROR = ValuesUtils.loadString(messages, "command-number-error");
		GAME_CONNECT_ERROR = ValuesUtils.loadString(messages, "game-connect-error");
		GAME_CONNECT_ANNOUNCE = ValuesUtils.loadString(messages, "game-connect-announce");
		GAME_CONNECT = ValuesUtils.loadString(messages, "game-connect");
		
		SIGN_LINES = ValuesUtils.loadStringArray(config, "sign.lines");
		GAME_PREPARE_TIME = ValuesUtils.loadInt(config, "game-settings.time.prepare");
		STORAGE_ASYNC = ValuesUtils.loadBoolean(config, "storage.async");
		
		GUI_GAMELIST_ITEM_LORE = ValuesUtils.loadStringList(guis, "gamelist.item.lore");
		GUI_GAMEINFO_ITEMS = ValuesUtils.loadItemsSection(guis, "gameinfo.items");
		// TODO: load values
	}
	
	// Console logs.
	public static String LOG_GAMES_LOAD = "&aLoaded <value> games.";
	public static String LOG_GAMES_SAVE = "&aSaved <value> games.";
	public static String LOG_MYSQL_CONNECTION_ERROR = "&cERROR: Failed to connect MySQL.";
	public static String LOG_MYSQL_STATEMENT_ERROR = "&cERROR: Failed create statement.";
	public static String LOG_ERROR_MESSAGE = "&cERROR: <message>";
	public static String LOG_INCORRECT_ITEM = "&cFailed to load item \"<format>\"";
	
	// Messages.
	public static String COMMANDS_HELP;
	public static String COMMAND_SET_POINT;
	public static String COMMAND_SET_POINT_RANGE_ERROR;
	public static String COMMAND_SET_POINT_REGION_ERROR;
	public static String COMMAND_SET_CHEST;
	public static String COMMAND_SET_CHEST_MATERIAL_ERROR;
	public static String COMMAND_SET_CHEST_REGION_ERROR;
	public static String COMMAND_PERMISSION_ERROR;
	public static String COMMAND_NUMBER_ERROR;
	public static String GAME_CONNECT_ERROR;
	public static String GAME_CONNECT_ANNOUNCE;
	public static String GAME_CONNECT;
	
	// Other.
	public static String STORAGE_TYPE;
	public static String MYSQL_HOST;
	public static String MYSQL_USER;
	public static String MYSQL_BASE;
	public static String MYSQL_PASSWORD;
	public static String[] SIGN_LINES;
	public static String[] SCOREBOARD_LOBBY;
	public static String[] SCOREBOARD_MAP;
	public static int GAME_PREPARE_TIME;
	public static int GAME_STARTING_TIME;
	public static int GAME_GLOBAL_TIME;
	public static int AUTOSAVE_TICKS;
	public static boolean THREAD_OPTIMIZATION;
	public static boolean AUTOSAVE_ENABLE;
	public static boolean STORAGE_ASYNC;
	
	// GUI.
	public static String GUI_GAMELIST_NAME;
	public static String GUI_GAMELIST_ITEM_NAME;
	public static List<String> GUI_GAMELIST_ITEM_LORE;
	public static String GUI_GAMEINFO_NAME;
	public static ItemStack[] GUI_GAMEINFO_ITEMS;
	
	private static class ValuesUtils {
		
		public static String loadString(YamlConfiguration file, String path) {
			return file.getString(path).replace("&", "§");
		}
		
		public static String[] loadStringArray(YamlConfiguration file, String path) {
			List<String> list = file.getStringList(path);
			list.replaceAll(t -> t.replace("&", "§"));
			return (String[]) list.toArray();
		}
		
		public static List<String> loadStringList(YamlConfiguration file, String path) {
			List<String> list = file.getStringList(path);
			list.replaceAll(t -> t.replace("&", "§"));
			return list;
		}
		
		public static ItemStack[] loadItemsSection(YamlConfiguration file, String path) {
			
			// 6*9 - max size of inventory.
			ItemStack[] items = new ItemStack[6*9];
			
			for(String key : file.getConfigurationSection(path)
				.getKeys(false)) {
				
				ConfigurationSection section = file.getConfigurationSection(path + "." + key);
				ItemStack item = Utils.itemBuild(section);
				int slot = Integer.valueOf(key);
				
				items[slot - 1] = item;
				continue;
			}
			
			return items;
		}
		
		public static int loadInt(YamlConfiguration file, String path) {
			return file.getInt(path);
		}
		
		public static boolean loadBoolean(YamlConfiguration file, String path) {
			return file.getBoolean(path);
		}
	}
}