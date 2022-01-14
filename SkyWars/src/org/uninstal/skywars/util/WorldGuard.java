package org.uninstal.skywars.util;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WorldGuard {
	
	private static RegionManager manager;
	
	public static void init(String world) {
		
		World w = Bukkit.getWorld(world);
		manager = WorldGuardPlugin.inst().getRegionManager(w);
		return;
	}
	
	public static ProtectedRegion getRegion(String name) {
		return manager.getRegion(name);
	}
	
	public static boolean hasRegion(String name) {
		return manager.hasRegion(name);
	}
}