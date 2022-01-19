package org.uninstal.skywars.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class Messenger {
	
	private static String PREFIX = "[SkyWars]";
	
	public static void send(Player player, String message) {
		String lower = message.toLowerCase();
		
		if(lower.startsWith("title:")) {
			
			String title;
			String subtitle;
			
			if(lower.contains("subtitle:")) {
				int index = lower.indexOf("subtitle:");
				title = message.substring(6, index);
				subtitle = message.substring(index + 9);
			}
			
			else {
				title = message;
				subtitle = new String();
			}
			
			player.sendTitle(title, subtitle, 10, 40, 10);
			return;
		}
		
		if(lower.startsWith("actionbar:")) {
			message = message.substring(10);
			
			BaseComponent component = new TextComponent(message);
			player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
			return;
		}
		
		player.sendMessage(message);
		return;
	}
	
	public static void protectNaN(Player target, Code code) {
		
		try {
			code.run();
		} catch (NumberFormatException e) {
			target.sendMessage(Values.COMMAND_NUMBER_ERROR);
			return;
		}
	}
	
	public static void announce(String message) {
		
		for(Player player : Bukkit.getOnlinePlayers())
			send(player, message);
	}
	
	public static void console(String message) {
		Bukkit.getConsoleSender().sendMessage(PREFIX + " " + message.replace("&", "§"));
	}
}