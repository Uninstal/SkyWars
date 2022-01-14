package org.uninstal.skywars.data;

public enum GameState {

	WAIT("§aWait"), STARTING("§eStarting"), BATTLE("§cBattle");
	
	private String stateName;

	private GameState(String stateName) {
		this.stateName = stateName;
	}
	
	public String getName() {
		return stateName;
	}
	
	@Override
	public String toString() {
		return stateName;
	}
}