package org.uninstal.skywars.data;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.uninstal.skywars.Main;
import org.uninstal.skywars.util.Utils;

public class GameThread extends BukkitRunnable {
	
	private boolean running;
	private int starting;
	private int prepare;
	private Game game;
	
	public GameThread(Game game) {
		
		this.running = false;
		this.game = game;
	}
	
	public void start() {
		runTaskTimerAsynchronously(Main.INSTANCE, 0L, 20L);
	}
	
	public int getStarting() {
		return starting;
	}
	
	public boolean isPrepare() {
		return prepare != 0;
	}
	
	public boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		if(!running) cancel();
		
		if(game.getState() == GameState.STARTING) {
			
			if(starting == 0) {
				prepare = 5;
				// Start.
				game.setState(GameState.BATTLE);
				// Actions on participants.
				game.getPlayers().forEach(gp -> {
					Player player = gp.getBukkit();
					// Teleport on map.
					
					// Freeze the player.
					Utils.freeze(player);
				});
				return;
			}
			
			starting++;
			return;
		}
		
		if(game.getState() == GameState.BATTLE) {
			
			if(isPrepare()) {
				
				// Play prepare sound.
				Sound sound = prepare == 1 ? Sound.BLOCK_NOTE_CHIME : Sound.BLOCK_NOTE_PLING;
				game.getPlayers().forEach(p -> {
					
					Player player = p.getBukkit();
					Location location = player.getLocation();
					player.playSound(location, sound, 1F, 1F);
				});
				
				prepare--;
				return;
			}
		}
	}
}