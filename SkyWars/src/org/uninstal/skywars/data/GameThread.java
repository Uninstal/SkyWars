package org.uninstal.skywars.data;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.uninstal.skywars.Main;
import org.uninstal.skywars.util.Utils;
import org.uninstal.skywars.util.Values;

public class GameThread extends BukkitRunnable {
	
	private boolean running;
	private int starting;
	private int prepare;
	private int time;
	private Game game;
	
	public GameThread(Game game) {
		
		this.running = false;
		this.game = game;
	}
	
	public int getRemainTime() {
		return time;
	}
	
	public void start() {
		this.runTaskTimerAsynchronously(Main.INSTANCE, 0L, 20L);
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
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void startBattle() {
		prepare = Values.GAME_PREPARE_TIME;
		time = Values.GAME_GLOBAL_TIME;
		// Start.
		game.setState(GameState.BATTLE);
		// Teleport players to map.
		game.getLobby().teleportToMap();
		// Actions on participants.
		game.getPlayers().forEach(gp -> {
			Player player = gp.getBukkit();
			// Freeze the player.
			Utils.freeze(player);
		});
		// Fill the chests.
		game.getMap().fillChests();
	}
	
	public void stopWithoutWinner() {
		
		game.getPlayers().forEach(gp -> {
			Player player = gp.getBukkit();
			// Teleport on main lobby.
			game.getMap().disconnect(gp);
			// MESSAGE
		});
		
		// Reset.
		game.reset();
		// Set waiting state.
		game.setState(GameState.WAIT);
		return;
	}
	
	public void stop(String winner) {
		
		game.getPlayers().forEach(gp -> {
			Player player = gp.getBukkit();
			// Teleport on main lobby.
			game.getMap().disconnect(gp);
			// MESSAGE
		});
		
		// Reset.
		game.reset();
		// Set waiting state.
		game.setState(GameState.WAIT);
		return;
	}
	
	public void handleConnection() {
		this.starting = Values.GAME_STARTING_TIME;
		if(!isRunning()) start();
	}

	@Override
	public void run() {
		if(!running) cancel();
		
		// A little optimization.
		if(Values.THREAD_OPTIMIZATION) {
			if(game.getPlayers().size() == 0) {
				cancel();
				return;
			}
		}
		
		if(game.getState() == GameState.STARTING) {
			
			if(starting == 0) {
				startBattle();
				return;
			} else --starting;
		}
		
		else if(game.getState() == GameState.BATTLE) {
			
			// If prepare state.
			if(isPrepare()) {
				
				// Play prepare sound.
				Sound sound = prepare == 1 ? Sound.BLOCK_NOTE_CHIME : Sound.BLOCK_NOTE_PLING;
				game.getPlayers().forEach(gp -> {
					
					Player player = gp.getBukkit();
					Location location = player.getLocation();
					player.playSound(location, sound, 5F, 1F);
				});
				
				if(prepare == 1) {
					
					// Unfreeze players.
					game.getPlayers().forEach(gp -> {
						Utils.unfreeze(gp.getBukkit());
					});
				}
				
				--prepare;
				return;
			}
			
			// If time is out.
			if(time == 0) {
				
				// Stop game because time is out.
				stopWithoutWinner();
				return;
			} else --time;
		}
		
		// Update the game.
		game.update();
		return;
	}
}