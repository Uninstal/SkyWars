package org.uninstal.skywars.data;

public class GameConfig {
	
	// Id for sort in GUI.
	private static int lastId = 0;
	
	private int superId;
	private String id;
	private int minPlayers;
	private int maxPlayers;
	
	public GameConfig(String id, 
		int minPlayers, int maxPlayers) {
		
		this.superId = lastId++;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
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