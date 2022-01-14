package org.uninstal.skywars.data.gui;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameState;
import org.uninstal.skywars.util.Values;

public class GuiGameInfo extends Gui {
	
	private Game game;
	private boolean open;
	
	public GuiGameInfo(Player player, Game game) {
		super(player);
		this.game = game;
		this.open = false;
		create(Values.GUI_GAMEINFO_NAME, InventoryType.DISPENSER);
	}
	
	public Game getGame() {
		return game;
	}
	
	@Override
	public void open() {
		
		for(int i = 0; i < 9; i++) {
			
			ItemStack item = Values.GUI_GAMEINFO_ITEMS[i].clone();
			if(item == null) continue;
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(api(meta.getDisplayName()));
			List<String> lore = meta.getLore();
			lore.replaceAll(t -> api(t));
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			inventory.setItem(i, item);
			continue;
		}
		
		player.openInventory(inventory);
		this.open = true;
	}

	@Override
	public void update() {
		
		this.clear();
		this.update();
	}

	@Override
	public void close() {
		player.closeInventory();
	}

	@Override
	public void event(InventoryClickEvent e) {
		e.setCancelled(true);
	}

	@Override
	public void event(InventoryCloseEvent e) {
		if(open) remove();
	}
	
	private String api(String string) {
		
		String id = game.getId();
		int width = game.getMap().getArea().getWidth();
		int lenght = game.getMap().getArea().getLenght();
		int min = game.getConfig().getMinPlayers();
		int max = game.getConfig().getMaxPlayers();
		int curr = game.getPlayers().size();
		int inLobby = game.getLobby().getPlayers().size();
		GameState state = game.getState();
		
		return string
			.replace("<id>", id)
			.replace("<width>", String.valueOf(width))
			.replace("<lenght>", String.valueOf(lenght))
			.replace("<min>", String.valueOf(min))
			.replace("<max>", String.valueOf(max))
			.replace("<curr>", String.valueOf(curr))
			.replace("<in-lobby>", String.valueOf(inLobby))
			.replace("<state>", state.getName());
	}
}