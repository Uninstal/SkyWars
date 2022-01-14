package org.uninstal.skywars.data;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.uninstal.skywars.util.Values;

public class GameSign {
	
	private Sign sign;
	private Game game;

	public GameSign(Game game, Sign sign) {
		this.sign = sign;
		this.game = game;
	}
	
	public GameSign(Game game, Location location) {
		this.sign = (Sign) location.getBlock();
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	public Sign getSign() {
		return sign;
	}
	
	public Location getLocation() {
		return sign.getLocation();
	}
	
	public void update() {
		
		String[] lines = Values.SIGN_LINES;
		
		// Formatting lines.
		for(int i = 0; i < 4; i++) {
			String line = lines[i];
			// TODO: sign lines api
			line = line.replace("", "");
			lines[i] = line;
		}
		
		sign.setLine(0, lines[0]);
		sign.setLine(1, lines[1]);
		sign.setLine(2, lines[2]);
		sign.setLine(3, lines[3]);
		
		return;
	}
}