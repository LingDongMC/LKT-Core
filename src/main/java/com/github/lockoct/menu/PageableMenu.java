package com.github.lockoct.menu;

import com.github.lockoct.Main;
import com.github.lockoct.utils.I18nUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * 可分页的菜单
 */
public abstract class PageableMenu extends BaseMenu {
    protected final int PAGE_SIZE = 45;
    private int currentPage;
    private int totalPage;
    private int total;

    public PageableMenu(String title, HashMap<String, Object> menuContext, Player player, JavaPlugin plugin) {
        super(54, title, menuContext, player, plugin);
        setCurrentPage(1);
    }

    public PageableMenu(int currentPage, String title, HashMap<String, Object> menuContext, Player player, JavaPlugin plugin) {
        super(54, title, menuContext, player, plugin);
        setCurrentPage(currentPage);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        setPageContent(currentPage);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        if (totalPage <= 0) {
            totalPage = 1;
        }
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    // 设置页面内容
    protected abstract void setPageContent(int page);

    // 设置翻页按钮、分页信息
    protected void setPageElement() {
        Inventory inv = this.getInventory();

        // 上一页
        if (this.currentPage > 1) {
            this.setOptItem(Material.ARROW, I18nUtil.getText(Main.plugin, getPlayer(), "menu.prePage", this.currentPage - 1), PAGE_SIZE, "prePage");
        } else {
            inv.setItem(PAGE_SIZE, null);
        }

        // 下一页
        if (this.currentPage < this.totalPage) {
            this.setOptItem(Material.ARROW, I18nUtil.getText(Main.plugin, getPlayer(), "menu.nextPage", this.currentPage + 1), 53, "nextPage");
        } else {
            inv.setItem(53, null);
        }

        // 退出
        this.setOptItem(Material.DARK_OAK_DOOR, I18nUtil.getText(Main.plugin, getPlayer(), "menu.exit"), 48, "exit");

        // 分页信息
        this.setOptItem(Material.BOOK, I18nUtil.getText(Main.plugin, getPlayer(), "menu.pageInfo", this.currentPage, this.totalPage), 49, "pageInfo");
    }
}
