package world.atlantix.cosmeticsgui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MenuGUI extends ChestGui {

    MenuCategory[] categories;

    public MenuGUI(@NotNull String title, MenuCategory[] categories, MenuCategory backButton) {
        super(6, title);

        this.categories = categories;

        super.setOnGlobalClick(event -> event.setCancelled(true));

        OutlinePane background = new OutlinePane(0, 0, 9, 6, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemStack(Material.WHITE_STAINED_GLASS_PANE)));
        background.setRepeat(true);

        super.addPane(background);

        StaticPane categoryPane = new StaticPane(0, 0, 9, 5);

        for (MenuCategory category : categories) {
            categoryPane.addItem(new GuiItem(category.getItem(), event -> {
                Bukkit.dispatchCommand(event.getWhoClicked(), category.getCommand());

            }), category.getPosition()[0], category.getPosition()[1]);

        }

        super.addPane(categoryPane);

        // Back Button
        StaticPane back = new StaticPane(backButton.getPosition()[0], backButton.getPosition()[1], 1, 1);
        back.addItem(new GuiItem(backButton.getItem(), event -> {
            event.setCancelled(true);
            event.getWhoClicked().closeInventory();
        }), 0, 0);

        back.setVisible(true);

        super.addPane(back);

    }

}
