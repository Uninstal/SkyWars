package org.uninstal.skywars;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameConfig;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameMap;
import org.uninstal.skywars.data.GameMap.Area;
import org.uninstal.skywars.data.GameSign;
import org.uninstal.skywars.data.GameState;
import org.uninstal.skywars.events.GameDeathEvent;
import org.uninstal.skywars.events.GameDeathReason;
import org.uninstal.skywars.events.GameEvent;
import org.uninstal.skywars.events.GameFallInVoidEvent;
import org.uninstal.skywars.events.GameJoinEvent;
import org.uninstal.skywars.events.GameKillEvent;
import org.uninstal.skywars.events.GameQuitEvent;

public class Handler implements Listener {
	
	@EventHandler
	public void onBlockExplosion(BlockExplodeEvent e) {
		boolean enter = false;
		
		for(Game game : GameManager.getActiveGames()) {
			
			GameMap map = game.getMap();
			Area area = map.getArea();
			
			for(Block block : e.blockList()) {
				
				Location location = block.getLocation();
				if(area.checkXYZ(location)) {
					enter = true;
					break;
				}
			}
		}
		
		if(enter) {
			e.blockList().clear();
			return;
		}
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent e) {
		
		Player player = e.getEntity();
		Game game = GameManager.getGame(player);
		
		if(game != null) {
			e.getDrops().clear();
			
			Player killer = player.getKiller();
			GameDeathReason reason = GameDeathReason.UNKNOWN;
			
			if(killer != null && game.contains(player))
				reason = GameDeathReason.PLAYER;
			else if(!game.getMap().getArea().checkLowY(player.getLocation()))
				reason = GameDeathReason.VOID;
			
			boolean message = GameEvent.handle(new GameDeathEvent(game, player, reason)).hasMessage();
			if(message) {
				// MESSAGE
				return;
			}
		}
	}
	
	@EventHandler
	public void onDeath(GameDeathEvent e) {
		
		Game game = e.getGame();
		Player player = e.getPlayer();
		Player killer = player.getKiller();
		
		if(e.getReason() == GameDeathReason.PLAYER)
			e.setDeathMessage(GameEvent.handle(new GameKillEvent(game, killer, player)).hasMessage());
		else if(e.getReason() == GameDeathReason.VOID)
			e.setDeathMessage(GameEvent.handle(new GameFallInVoidEvent(game, player)).hasMessage());
		
		return;
	}
	
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
			if(!connected) {
				
				// MESSAGE
				return;
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		
		Player player = e.getPlayer();
		Block block = e.getBlock();
		Game game = GameManager.getGame(player);
		
		if(game != null && block != null)
			game.getMap().handlePlace(block);
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		Player player = e.getPlayer();
		Block block = e.getBlock();
		Game game = GameManager.getGame(player);
		
		if(game != null && block != null)
			game.getMap().handleBreak(block);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		
		Player player = e.getPlayer();
		Item item = e.getItemDrop();
		Game game = GameManager.getGame(player);
		
		if(game != null && item != null)
			game.getMap().handleDrop(item);
	}
	
	@EventHandler
	public void onPickup(EntityPickupItemEvent e) {
		
		Entity entity = e.getEntity();
		Item item = e.getItem();
		
		if(entity.getType() == EntityType.PLAYER) {
			
			Player player = (Player) entity;
			Game game = GameManager.getGame(player);
			
			if(game != null && item != null)
				game.getMap().handlePickup(item);
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
				// MESSAGE
				return;
			} else {
				
				GameSign gameSign = new GameSign(game, sign);
				GameManager.registerSign(gameSign);
				gameSign.update();
				
				// MESSAGE
				return;
			}
		}
	}
	
	@EventHandler
	public void onJoinGame(GameJoinEvent e) {
		Game game = e.getGame();
		
		if(game.getThread().isCancelled()) {
			
			game.getThread().handleConnection();
			game.setState(GameState.STARTING);
		}
	}
	
	@EventHandler
	public void onQuitGame(GameQuitEvent e) {
		
		Game game = e.getGame();
		GameConfig config = game.getConfig();
		GameState state = game.getState();
		
		if(state == GameState.STARTING
			&& e.getNewSize() < config.getMinPlayers())
			game.setState(GameState.WAIT);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		Game game = GameManager.getGame(player);
		
		if(game != null) 
			game.disconnect(player);
	}
}