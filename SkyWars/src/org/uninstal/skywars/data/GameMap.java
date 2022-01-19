package org.uninstal.skywars.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.uninstal.skywars.util.Messenger;
import org.uninstal.skywars.util.Utils;
import org.uninstal.skywars.util.Values;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class GameMap {
	
	private final Game game;
	private final Area area;
	private Map<Integer, Point> points;
	private List<Chest> chests;
	private List<Item> drops;
	private List<Block> blocks;
	
	public GameMap(Game game, Area area, Map<Integer, Point> points, List<Chest> chests) {
		this.game = game;
		this.area = area;
		this.chests = chests;
		this.points = points;
		this.drops = new ArrayList<>();
	}
	
	public GameMap(Game game, Area area, Map<Integer, Point> points) {
		this.game = game;
		this.area = area;
		this.chests = new ArrayList<>();
		this.points = points;
		this.drops = new ArrayList<>();
	}
	
	public GameMap(Game game, Area area) {
		this.game = game;
		this.area = area;
		this.chests = new ArrayList<>();
		this.points = new HashMap<>();
		this.drops = new ArrayList<>();
	}
	
	public GameMap(Game game) {
		this.game = game;
		this.area = null;
		this.chests = new ArrayList<>();
		this.points = new HashMap<>();
		this.drops = new ArrayList<>();
	}
	
	public boolean isConfigured() {
		
		return area != null
			&& chests.size() > 1
			&& points.size() == game.getConfig().getMaxPlayers();
	}
	
	public Game getGame() {
		return game;
	}
	
	public Area getArea() {
		return area;
	}
	
	public List<Chest> getChests() {
		return chests;
	}
	
	public void setChests(List<Chest> chests) {
		this.chests = chests;
	}
	
	public void addChest(Chest chest) {
		this.chests.add(chest);
	}
	
	public void addPoint(int id, Point point) {
		points.put(id, point);
	}
	
	public void addPoint(int id, Location location) {
		points.put(id, new Point(location));
	}
	
	public Point getPoint(int id) {
		return points.get(id);
	}
	
	public Map<Integer, Point> getPoints() {
		return points;
	}
	
	public void handleDrop(Item item) {
		this.drops.add(item);
	}
	
	public void handlePickup(Item item) {
		this.drops.remove(item);
	}
	
	public void handlePlace(Block block) {
		this.blocks.add(block);
	}
	
	public void handleBreak(Block block) {
		this.blocks.remove(block);
	}
	
	public void connect(GamePlayer player) {
		Player bukkit = player.getBukkit();
		
		// Change scoreboard lines.
		player.getScoreboard().switchLines(Values.SCOREBOARD_MAP);
		
		for(Point point : points.values()) {
			if(!point.hasOwner()) {
				point.capture(bukkit);
				return;
			}
		}
		
		// If no point is captured.
		Messenger.console("&cERROR: No point is captured, "
			+ "but called connect to map.");
		return;
	}
	
	public void disconnect(GamePlayer player) {
		Player bukkit = player.getBukkit();
		
		for(Point point : points.values()) {
			if(point.getOwner().equals(bukkit)) {
				point.toFree();
				break;
			}
		}
		
		if(!game.getThread().isPrepare()) {
			
			Location location = bukkit.getLocation();
			Inventory inventory = bukkit.getInventory();
			ItemStack[] items = inventory.getContents();
			
			for(ItemStack item : items)
				drops.add(location.getWorld()
				.dropItem(location, item));
			
			inventory.clear();
		}
		
		bukkit.teleport(GameLobby.getMainLobby());
		return;
	}
	
	public void fillChests() {
		
		for(Chest chest : chests) {
			
			Inventory inventory = chest.getInventory();
			// TODO: random fill the chest
		}
	}
	
	public void clearChests() {
		
		for(Chest chest : chests)
			chest.getInventory().clear();
	}
	
	public void reset() {
		
		// Remove data from points.
		for(Point point : points.values())
			point.toFree();
		
		// Delete dropped items.
		for(Item item : drops)
			if(item != null) 
				item.remove();
		
		// Delete placed blocks;
		for(Block block : blocks)
			if(block != null)
				block.setType(Material.AIR);
		
		// Clear the memory.
		drops.clear();
		blocks.clear();
		
		// Clear the inventory of chests.
		clearChests();
	}
	
	public static GameMap create(Game game, ProtectedRegion region) {
		return new GameMap(game, new Area(region));
	}
	
	public static class Area {
		
		private int xMin;
		private int yMin;
		private int zMin;
		private int xMax;
		private int yMax;
		private int zMax;

		public Area(ProtectedRegion region) {
			
			this.xMin = region.getMinimumPoint().getBlockX();
			this.yMin = region.getMinimumPoint().getBlockY();
			this.zMin = region.getMinimumPoint().getBlockZ();
			this.xMax = region.getMaximumPoint().getBlockX();
			this.yMax = region.getMaximumPoint().getBlockY();
			this.zMax = region.getMaximumPoint().getBlockZ();
		}
		
		public boolean checkXYZ(Location location) {
			
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			
			return xMin <= x && x <= xMax
				&& yMin <= y && y <= yMax
				&& zMin <= z && z <= zMax;
		}
		
		public boolean checkLowY(Location location) {
			
			int y = location.getBlockY();
			return yMin <= y;
		}
		
		public int getWidth() {
			return xMax - xMin;
		}
		
		public int getLenght() {
			return zMax - zMin;
		}
	}
	
	public static class Point {
		
		private Location location;
		private Player owner;
		
		public Point(Location location) {
			this.location = fixEye(location);
		}
		
		public void toFree() {
			this.owner = null;
		}
		
		public boolean hasOwner() {
			return owner != null;
		}
		
		public Player getOwner() {
			return owner;
		}
		
		public void capture(Player owner) {
			this.owner = owner;
			this.owner.teleport(location);
		}
		
		public Location getLocation() {
			return location;
		}
		
		private Location fixEye(Location location) {
			
			int yaw = (int) location.getYaw();
			yaw = Utils.even(yaw, new int[] {0, 45, 90, 135, 180, 225, 270, 315, 360});
			location.setYaw(yaw);
			location.setPitch(0);
			
			return location;
		}
	}
}