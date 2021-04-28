package world.atlantix.cosmeticsgui;

import com.github.stefvanschie.inventoryframework.util.SkullUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class MenuCategory {

    private final int x, y;
    private final String name;
    private final String command;
    private ItemStack item;

    private MenuCategory(String name, String command, int x, int y) {
        this.name = name;
        this.command = command;
        this.x = x;
        this.y = y;
    }

    public MenuCategory(String name, Material material, int x, int y) {
        this.name = name;
        this.command = null;
        this.x = x;
        this.y = y;

        this.item = new ItemStack(material);
        setItemMeta(name, null);
    }

    public MenuCategory(String name, List<String> lore, Material material, String command, int x, int y) {
        this(name, command, x, y);

        this.item = new ItemStack(material);
        setItemMeta(name, lore);
    }

    public MenuCategory(String name, List<String> lore, String texture, String command, int x, int y) {
        this(name, command, x, y);

        this.item = SkullUtil.getSkull(texture);
        setItemMeta(name, lore);

    }

    private void setItemMeta(String name, List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        if(itemMeta != null) {
            itemMeta.setDisplayName(name);
            if(lore != null)
                itemMeta.setLore(lore);

            item.setItemMeta(itemMeta);
        } else {
            throw new RuntimeException("Problem getting itemmeta for item: " + name);
        }
    }

    public String getCommand() {
        return command;
    }

    public ItemStack getItem() {
        return item;
    }

    public int[] getPosition() {
        return new int[] { x, y };
    }

}
