package com.github.lockoct.menu.listener;

import com.github.lockoct.menu.BaseMenu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public abstract class BaseMenuListener implements Listener {
    private final BaseMenu menu;

    public BaseMenuListener(BaseMenu menu) {
        this.menu = menu;
    }

    public BaseMenu getMenu() {
        return menu;
    }

    @EventHandler
    public boolean onClick(InventoryClickEvent e) {
        boolean validate = menu.getPlayer().equals(e.getWhoClicked()) && menu.getInventory().equals(e.getInventory());
        if (validate) {
            e.setCancelled(true);
        }
        return validate;
    }

    @EventHandler
    public boolean onDrag(InventoryDragEvent e) {
        boolean validate = menu.getPlayer().equals(e.getWhoClicked()) && menu.getInventory().equals(e.getInventory());
        if (validate) {
            e.setCancelled(true);
        }
        return validate;
    }

    @EventHandler
    public boolean onClose(InventoryCloseEvent e) {
        boolean validate = menu.getPlayer().equals(e.getPlayer()) && menu.getInventory().equals(e.getInventory());
        if (validate) {
            HandlerList.unregisterAll(this);
        }
        return validate;
    }
}
