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

    private final HashMap<String, Object> menuContext;

    public BaseMenu(int size, String title, HashMap<String, Object> menuContext, Player player, JavaPlugin plugin) {
        this.inventory = Bukkit.createInventory(null, size, title);
        this.menuContext = menuContext;
        this.player = player;
        this.plugin = plugin;
    }

    // 获取菜单容器
    // 用于给菜单放置操作按钮
    public Inventory getInventory() {
        return inventory;
    }

    // 获取当前操作菜单的玩家
    public Player getPlayer() {
        return player;
    }

    // 获取菜单上下文
    // 用于多个菜单之间传递消息
    public HashMap<String, Object> getMenuContext() {
        return menuContext;
    }

    // 获取菜单中所有操作按钮的位置

    public HashMap<Integer, String> getOperationItemPos() {
        return operationItemPos;
    }

    // 设置操作按钮
    public ItemStack setOptItem(Material material, String title, int index, String optSign) {
        ItemMeta im = Bukkit.getItemFactory().getItemMeta(material);
        assert im != null;
        return setOptItem(material, im, title, index, optSign);
    }

    public ItemStack setOptItem(Material material, ItemMeta itemMeta, String title, int index, String optSign) {
        ItemStack is = new ItemStack(material);
        itemMeta.setDisplayName(title);
        is.setItemMeta(itemMeta);
        inventory.setItem(index, is);
        if (StringUtils.isNotBlank(optSign)) {
            operationItemPos.put(index, optSign);
        }
        return is;
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

    // 打开菜单
    public void open(BaseMenuListener listener) {
        // 打开前注册监听器
        Bukkit.getPluginManager().registerEvents(listener, this.plugin);

        // 打开菜单
        this.player.openInventory(this.inventory);
    }

    // 关闭菜单
    public void close() {
        this.player.closeInventory();
    }
}
