package org.uninstal.skywars.storage;

import org.bukkit.Bukkit;
import org.uninstal.skywars.Main;

public interface Storage {
	
	public void init();
	
	public int save();
	
	public int load();
	
	default void enableAutosave(int ticks) {
		
		Bukkit.getScheduler().runTaskTimerAsynchronously(
			Main.INSTANCE, this::save, ticks, ticks);
	}
	
	public static Storage of(String storageId) {
		
		if(storageId.equalsIgnoreCase("yaml"))
			return new Yaml();
		
		return null;
	}
}