package org.uninstal.skywars;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameSign;
import org.uninstal.skywars.events.GameJoinEvent;

public class Handler implements Listener {
	
	@EventHandler
	public void onBlockClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		Location location = block.getLocation();
		Action action = e.getAction();
		
		if(block.getType().name().contains("SIGN")
			&& action == Action.RIGHT_CLICK_BLOCK) {
			
			GameSign sign = GameManager.getSign(location);
			if(sign == null) return;
			
			boolean connected = sign.getGame().connect(player);
			if(connected) return;
			else {
				
				// Error connect.
				return;
			}
		}
	}
	
	@EventHandler
	public void onCreateSign(SignChangeEvent e) {
		
		String[] lines = e.getLines();
		Sign sign = (Sign) e.getBlock();
		
		if(lines[0].equalsIgnoreCase("[skywars]")) {
			
			String id = lines[1];
			Game game = GameManager.getGame(id);
			
			if(game == null) {
				// Message.
				return;
			} else {
				
				GameSign gameSign = new GameSign(game, sign);
				GameManager.registerSign(gameSign);
				gameSign.update();
				
				// Message.
				return;
			}
		}
	}
	
	@EventHandler
	public void onJoinGame(GameJoinEvent e) {
		
		
	}
}