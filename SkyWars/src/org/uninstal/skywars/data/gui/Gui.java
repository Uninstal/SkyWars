package org.uninstal.skywars.data.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class Gui {

	public final static Map<UUID, Gui> guis = new HashMap<>();

	protected Inventory inventory;
	protected int page = 1;
	protected int maxPage = 1;
	protected boolean pageable;
	protected Player player;
	protected UUID uuid;

	public Gui(Player player, boolean pageable) {
		this.player = player;
		this.pageable = pageable;
		this.uuid = player.getUniqueId();
		this.guis.put(uuid, this);
	}

	public Gui(Player player) {
		this.player = player;
		this.pageable = false;
		this.uuid = player.getUniqueId();
		this.guis.put(uuid, this);
	}

	public int getPage() {
		return page;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public boolean isPageable() {
		return pageable;
	}

	public void remove() {
		this.player.closeInventory();
		this.guis.remove(uuid);
	}

	protected void nextPage() {

		if (!pageable || getMaxPage() < 1 || page == getMaxPage())
			return;

		this.page += 1;
		this.update();
		return;
	}

	protected void backPage() {

		if (!pageable || page == 1)
			return;

		this.page -= 1;
		this.update();
		return;
	}

	protected void clear() {
		this.inventory.clear();
	}

	protected void create(String title, int size) {
		this.inventory = Bukkit.createInventory(null, size, title);
	}
	
	protected void create(String title, InventoryType type) {
		this.inventory = Bukkit.createInventory(null, type, title);
	}

	protected void setItem(int i, ItemStack item) {
		this.inventory.setItem(i, item);
	}

	protected void setItem(int horizontalPose, int verticalPose, ItemStack item) {
		this.inventory.setItem((horizontalPose - 1) + 9 * (verticalPose - 1), item);
	}

	public abstract void open();

	public abstract void update();

	public abstract void close();

	public abstract void event(InventoryClickEvent e);

	public abstract void event(InventoryCloseEvent e);
}