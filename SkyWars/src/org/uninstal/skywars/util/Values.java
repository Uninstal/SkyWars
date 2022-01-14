package org.uninstal.skywars.util;

import java.util.List;

import org.bukkit.Location;
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
		
		// TODO: load values
	}
	
	// Console logs.
	public static String LOG_GAMES_LOAD = "&aLoaded <value> games.";
	public static String LOG_GAMES_SAVE = "&aSaved <value> games.";
	public static String LOG_MYSQL_CONNECTION_ERROR = "&cERROR: Failed to connect MySQL.";
	public static String LOG_MYSQL_STATEMENT_ERROR = "&cERROR: Failed to connect MySQL.";
	public static String LOG_ERROR_MESSAGE = "&cERROR: <message>";
	
	// Messages.
	public static String COMMANDS_HELP;
	public static String PLAYER_PERMISSION_ERROR;
	public static String PLAYER_NUMBER_ERROR;
	public static String GAME_CONNECT_ERROR;
	public static String GAME_CONNECT_ANNOUNCE;
	public static String GAME_CONNECT;
	
	// Other.
	public static String MYSQL_HOST;
	public static String MYSQL_USER;
	public static String MYSQL_BASE;
	public static String MYSQL_PASSWORD;
	public static String[] SIGN_LINES;
	public static String[] SCOREBOARD_LOBBY;
	public static String[] SCOREBOARD_MAP;
	public static Location MAIN_LOBBY;
	public static int GAME_PREPARE_TIME;
	public static int GAME_STARTING_TIME;
	public static int GAME_GLOBAL_TIME;
	public static int AUTOSAVE_TICKS;
	public static boolean THREAD_OPTIMIZATION;
	public static boolean AUTOSAVE_ENABLE;
	public static boolean ASYNC_DATAS_LOAD;
	
	// GUI.
	public static String GUI_GAMELIST_NAME;
	public static String GUI_GAMELIST_ITEM_NAME;
	public static List<String> GUI_GAMELIST_ITEM_LORE;
	public static String GUI_GAMEINFO_NAME;
	public static ItemStack[] GUI_GAMEINFO_ITEMS;
}