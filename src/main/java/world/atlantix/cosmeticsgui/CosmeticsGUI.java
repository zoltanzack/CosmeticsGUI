package world.atlantix.cosmeticsgui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import javax.management.InvalidAttributeValueException;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Entry into Plugin
 * {@inheritDoc}
 */
public class CosmeticsGUI extends JavaPlugin {

    /** Plugin-wide logger for access across plugin */
    private static Logger log;

    /** Plugin-wide instance of plugin */
    private static CosmeticsGUI instance;

    private MenuCategory[] menuCategoryList;
    private MenuCategory backButton;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLoad() {
        /* Initialise plugin instance */
        CosmeticsGUI.instance = this;

        /* Initialise logger */
        CosmeticsGUI.log = super.getLogger(); // super refers to the parent class (JavaPlugin), which defines a logger for this plugin.

        /* If no custom config file exists, save the default one as the base of the custom config */
        super.saveDefaultConfig();

        /* Load the custom configuration from the custom config file */
        super.reloadConfig();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        /* Load configuration file's data */
        try {
            loadConfigData();
        } catch (InvalidAttributeValueException e) {
            e.printStackTrace();
            super.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        /* Instantiate menuGUI object */
        MenuGUI menuGUI = new MenuGUI("Atlantix Cosmetics GUI", menuCategoryList, backButton);

        /* Register command exeecutors */
        Bukkit.getPluginCommand("cosmeticshop").setExecutor(new CosmeticsGUICommand(this, menuGUI));

    }

    /**
     * Initialises variables to config values
     */
    private void loadConfigData() throws InvalidAttributeValueException {
        ConfigurationSection categories = super.getConfig().getConfigurationSection("categories");

        if(categories != null) {
            Set<String> items = categories.getKeys(false);

            Iterator<String> setIterator = items.iterator();

            int i = 0;
            menuCategoryList = new MenuCategory[items.size()];
            for (Iterator<String> it = setIterator; it.hasNext(); ) {
                String item = it.next();

                MenuCategory category;

                List<Integer> position = super.getConfig().getIntegerList("categories.".concat(item.concat(".position")));
                if(position.size() != 2)
                    throw new InvalidAttributeValueException("Position attribute for " + item + " has invalid values");

                String name = super.getConfig().getString("categories.".concat(item.concat(".name")));
                if(name == null)
                    throw new InvalidAttributeValueException("Name attribute for " + item + " does not exist");

                String command = super.getConfig().getString("categories.".concat(item.concat(".command")));
                if(command == null)
                    throw new InvalidAttributeValueException("Command attribute for " + item + " does not exist");

                List<String> lore = super.getConfig().getStringList("categories.".concat(item.concat(".lore")));

                String material = super.getConfig().getString("categories.".concat(item.concat(".item")));
                if(material != null) {
                    category = new MenuCategory(
                            name,
                            lore,
                            Material.getMaterial(material),
                            command,
                            position.get(0), position.get(1));
                } else {
                    String texture = super.getConfig().getString("categories.".concat(item.concat(".texture")));

                    if(texture != null) {
                        category = new MenuCategory(
                                name,
                                lore,
                                texture,
                                command,
                                position.get(0), position.get(1));
                    } else {
                        throw new InvalidAttributeValueException("No \"texture\" element for " + item + " in the config!");
                    }
                }

                menuCategoryList[i] = category;
                i++;

            }

        } else {
            throw new InvalidAttributeValueException("No \"categories\" element in the config!");
        }

        List<Integer> position = super.getConfig().getIntegerList("backbutton.position");
        if(position.size() != 2)
            throw new InvalidAttributeValueException("Position attribute for backbutton has invalid values");

        String name = super.getConfig().getString("backbutton.name");
        if(name == null)
            throw new InvalidAttributeValueException("Name attribute for backbutton does not exist");

        String material = super.getConfig().getString("backbutton.item");
        if(material == null)
            throw new InvalidAttributeValueException("Item attribute for backbutton does not exist");

        backButton = new MenuCategory(
                name,
                Material.getMaterial(material),
                position.get(0), position.get(1));

    }

    public void onDisable() {
        //super.saveConfig();
    }

    public FileConfiguration getConfig() { return super.getConfig(); }

    public static Logger getLog() { return log; }

}
