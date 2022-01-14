package org.uninstal.skywars.data.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.uninstal.skywars.data.Game;
import org.uninstal.skywars.data.GameConfig;
import org.uninstal.skywars.data.GameManager;
import org.uninstal.skywars.data.GameState;
import org.uninstal.skywars.util.Values;

public class GuiGameList extends Gui {
	
	private Map<Integer, Game> games = new HashMap<>();
	private boolean open;

	public GuiGameList(Player player) {
		super(player, true);
		create(Values.GUI_GAMELIST_NAME, 6*9);
		this.maxPage = 0;
		this.open = false;
	}

	@Override
	public void open() {
		
		for(Game game : GameManager
			.getGames().values()) {
			
			GameState state = game.getState();
			GameConfig config = game.getConfig();
			int superId = config.getSuperId();
			
			int min = (page - 1) * 45;
			int max = page * 45;
			if(min > superId || superId >= max)
				continue;
			
			String id = config.getId();
			int maxPlayers = config.getMaxPlayers();
			int players = game.getPlayers().size();
			
			Material material = Material.GREEN_GLAZED_TERRACOTTA;
			if(state == GameState.STARTING) material = Material.YELLOW_GLAZED_TERRACOTTA;
			else if(state == GameState.BATTLE) material = Material.RED_GLAZED_TERRACOTTA;
			
			ItemStack item = new ItemStack(material);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§f" + id);
			List<String> lore = new ArrayList<>(Values.GUI_GAMELIST_ITEM_LORE);
			lore.replaceAll(t -> t
				.replace("<max>", String.valueOf(maxPlayers))
				.replace("<state>", state.getName())
				.replace("<players>", String.valueOf(players)));
			meta.setLore(lore);
			item.setItemMeta(meta);
			
			inventory.setItem(superId, item);
			games.put(superId, game);
			continue;
		}
		
		if(getMaxPage() != getPage()) {
			// TODO: next arrow
		}
		
		if(getPage() != 1) {
			// TODO: back arrow
		}
		
		player.openInventory(inventory);
		this.open = true;
		return;
	}

	@Override
	public void update() {
		
		this.clear();
		this.open();
	}

	@Override
	public void close() {
		this.player.closeInventory();
	}
	
	@Override
	public int getMaxPage() {
		return 1 + 45 / GameManager.getGames().size();
	}

	@Override
	public void event(InventoryClickEvent e) {
		e.setCancelled(true);
		
		if(e.getSlot() == 53)
			nextPage();
		
		else if(e.getSlot() == 44)
			backPage();
	}

	@Override
	public void event(InventoryCloseEvent e) {
		if(open) remove();
	}
}