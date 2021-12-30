package org.uninstal.skywars.commands;

import org.bukkit.entity.Player;

public abstract class SWCommand {
	
	private int minArgs;
	private String permission;
	
	public SWCommand(int minArgs, String permission) {
		this.minArgs = minArgs;
		this.permission = permission;
	}
	
	public int getMinArgs() {
		return minArgs;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public abstract boolean run(Player sender, String[] args);
}