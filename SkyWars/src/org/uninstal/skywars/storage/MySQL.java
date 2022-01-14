package org.uninstal.skywars.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameConfig;
import org.uninstal.skywars.data.GameLobby;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameMap;
import org.uninstal.skywars.util.Messenger;
import org.uninstal.skywars.util.Values;
import org.uninstal.skywars.util.WorldGuard;

public class MySQL implements Storage {
	
	private final String url = "jdbc:mysql://<host>/<base>?autoReconnect=true&useSSL=false";
	private final String types = "id TEXT PRIMARY KEY, time INT, min INT, max INT, region TEXT, lobby TEXT";
	private final String values = "id, time, min, max, region, lobby";
	private final String table = "skywars";
	
	private Connection connection;
	
	@Override
	public void init() {
		
		String host = Values.MYSQL_HOST;
		String base = Values.MYSQL_BASE;
		String user = Values.MYSQL_USER;
		String password = Values.MYSQL_PASSWORD;
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
			connection = DriverManager.getConnection(url
				.replace("<host>", host).replace("<base>", base),
				user, password);
			
		} catch (Exception e) {
			Messenger.console(Values.LOG_MYSQL_CONNECTION_ERROR);
			Messenger.console(Values.LOG_ERROR_MESSAGE.replace("<message>", e.getMessage()));
			return;
		}
		
		String create = "CREATE TABLE IF NOT EXISTS " + table + " (" + types + ")";
		String alter = "ALTER TABLE " + table + " CONVERT TO CHARACTER SET utf8";
		
		try {
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(create);
			statement.executeUpdate(alter);
			statement.close();
			
		} catch (SQLException e) {
			Messenger.console(Values.LOG_MYSQL_STATEMENT_ERROR);
			Messenger.console(Values.LOG_ERROR_MESSAGE.replace("<message>", e.getMessage()));
		}
	}

	@Override
	public int save() {
		return 0;
	}

	@Override
	public int load() {
		
		try {

			String command = "SELECT * FROM " + table;
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(command);
			int loaded = 0;
			
			while(result.next()) {
				
				String id = result.getString(1);
				int time = result.getInt(2);
				int min = result.getInt(3);
				int max = result.getInt(4);
				String region = result.getString(5);
				String lobby = result.getString(6);
				
				GameConfig config = new GameConfig(id, time, min, max);
				Game game = new Game(config);
				GameManager.registerGame(game);
				
				if(!region.isEmpty())
					game.setMap(GameMap.create(game, WorldGuard.getRegion(region)));
				
				if(!lobby.isEmpty())
					game.setLobby(GameLobby.create(game, lobby));
				
				loaded++;
				continue;
			}
			
			return loaded;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
}