package com.github.lockoct.menu;

import com.github.lockoct.menu.listener.BaseMenuListener;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public abstract class BaseMenu {
    private final Inventory inventory;
    private final Player player;
    private final JavaPlugin plugin;
    private final HashMap<Integer, String> operationItemPos = new HashMap<>();

    public BaseMenu(int size, String title, Player player, JavaPlugin plugin) {
        this.inventory = Bukkit.createInventory(null, size, title);
        this.player = player;
        this.plugin = plugin;
    }

    public HashMap<Integer, String> getOperationItemPos() {
        return operationItemPos;
    }

    public void setOptItem(Material material, String title, int index, String optSign) {
        ItemStack is = new ItemStack(material);
        ItemMeta im = is.getItemMeta();
        assert im != null;
        im.setDisplayName(title);
        is.setItemMeta(im);
        inventory.setItem(index, is);
        if (StringUtils.isNotBlank(optSign)) {
            operationItemPos.put(index, optSign);
        }
    }

    // 填充所有空位为背景物品，必须最后执行
    public void setBackGround(Material material) {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack is = inventory.getItem(i);
            if (is == null) {
                is = new ItemStack(material);
                inventory.setItem(i, is);
            }
        }
    }

    public void open(BaseMenuListener listener) {
        // 打开前注册监听器
        Bukkit.getPluginManager().registerEvents(listener, this.plugin);

        // 打开菜单
        this.player.openInventory(this.inventory);
    }

    public void close() {
        this.player.closeInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }
}
