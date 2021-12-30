package org.uninstal.skywars.util;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Utils {
	
	private final static PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 10000, 255);
	
	public static void freeze(Player player) {
		player.addPotionEffect(effect);
	}
	
	public static void unfreeze(Player player) {
		player.removePotionEffect(effect.getType());
	}
	
	public static int even(int value, int[] values) {
		int last = 361;
		
		for(int k : values)
			if(Math.abs(value - k) < last)
				last = k;
		
		return last;
	}
}