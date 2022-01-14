package org.uninstal.skywars.data;

public class GameConfig {
	
	// Id for sort in GUI.
	private static int lastId = 0;
	
	private final int superId;
	private final String id;
	private int time = 0;
	private int minPlayers = 0;
	private int maxPlayers = 0;
	
	public GameConfig(String id) {
		this.superId = lastId++;
		this.id = id;
	}
	
	public GameConfig(String id, int time, int minPlayers, int maxPlayers) {
		this.superId = lastId++;
		this.id = id;
		this.time = time;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}
	
	public boolean isConfigured() {
		return time > 0
			&& minPlayers > 0
			&& maxPlayers > 0;
	}
	
	public int getSuperId() {
		return superId;
	}
	
	public String getId() {
		return id;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}
	
	public void setMaxPlayers(int maxPlayers) {
		this.maxPlayers = maxPlayers;
	}
	
	public void setMinPlayers(int minPlayers) {
		this.minPlayers = minPlayers;
	}
}