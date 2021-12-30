package org.uninstal.skywars.data;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.uninstal.skywars.util.Utils;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class GameMap {
	
	private final String id;
	private final Area area;
	private List<Point> points;
	private List<Chest> chests;
	
	public GameMap(String id, Area area, List<Point> points, List<Chest> chests) {
		this.id = id;
		this.area = area;
		this.chests = chests;
		this.points = points;
	}
	
	public GameMap(String id, Area area, List<Point> points) {
		this.id = id;
		this.area = area;
		this.chests = new ArrayList<>();
		this.points = points;
	}
	
	public GameMap(String id, Area area) {
		this.id = id;
		this.area = area;
		this.chests = new ArrayList<>();
		this.points = new ArrayList<>();
	}
	
	public String getId() {
		return id;
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
	
	public void registerChest(Chest chest) {
		this.chests.add(chest);
	}
	
	public Point getPoint(int id) {
		return points.get(id);
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public void addPoint(Location location) {
		points.add(new Point(location));
	}
	
	public void connect(GamePlayer player) {
		Player bukkit = player.getBukkit();
		
		for(Point point : points)
			if(!point.hasOwner()) 
				point.capture(bukkit);
	}
	
	public void fillChests() {
		
		for(Chest chest : chests) {
			
			Inventory inventory = chest.getInventory();
			// TODO: random fill the chest.
		}
	}
	
	public void clearChests() {
		
		for(Chest chest : chests)
			chest.getInventory().clear();
	}
	
	public void reset() {
		
		// Remove data from points.
		for(Point point : points)
			point.toFree();
		
		// Clear the inventory of chests.
		clearChests();
	}
	
	public class Area {
		
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
		
		public boolean checkXZ(Location location) {
			
			int x = location.getBlockX();
			int z = location.getBlockZ();
			
			return xMin <= x && x <= xMax
				&& zMin <= z && z <= zMax;
		}
		
		public boolean checkY(Location location) {
			
			int y = location.getBlockY();
			return yMin <= y && y <= yMax;
		}
	}
	
	public class Point {
		
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