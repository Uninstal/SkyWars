package org.uninstal.skywars.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.uninstal.skywars.Main;

public class Utils {
	
	private final static PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 10000, 255);
	
	public static void freeze(Player player) {
		player.addPotionEffect(effect);
	}
	
	public static void unfreeze(Player player) {
		player.removePotionEffect(effect.getType());
	}
	
	public static String timeFormat(long time, String format) {
		
		long seconds = time % 60;
		long minutes = time / 60 % 60;
		long hours = time / 3600 % 24;
		long days = time % 86400;
		
		return format
			.replace("ss", String.valueOf(seconds))
			.replace("mm", String.valueOf(minutes))
			.replace("hh", String.valueOf(hours))
			.replace("dd", String.valueOf(days));
	}
	
	public static Object parse(Object objectToParse) {
		
		if(objectToParse instanceof Location) {
			Location location = (Location) objectToParse;
			return location.getWorld().getName()
				+ "_" + location.getX()
				+ "_" + location.getY()
				+ "_" + location.getZ();
		}
		
		if(objectToParse instanceof String) {
			String format = (String) objectToParse;
			String[] args = format.split("_");
			World world = Bukkit.getWorld(args[0]);
			double x = Double.valueOf(args[1]);
			double y = Double.valueOf(args[2]);
			double z = Double.valueOf(args[3]);
			return new Location(world, x, y, z);
		}
		
		return null;
	}
	
	public static boolean isNumber(String value) {
		try {
			Integer.valueOf(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static int even(int value, int[] values) {
		int last = 361;
		int out = 0;
		
		for(int k : values) {
			int abs = Math.abs(value - k);
			if(abs < last){
				last = abs;
				out = k;
			}
		}
		
		return out;
	}
	
	public static void async(Runnable runnable) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.INSTANCE, runnable);
	}
	
	public static void sync(Runnable runnable) {
		Bukkit.getScheduler().runTask(Main.INSTANCE, runnable);
	}
}