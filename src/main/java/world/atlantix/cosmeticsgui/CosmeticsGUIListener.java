package world.atlantix.cosmeticsgui;

import org.bukkit.event.Listener;

/**
 *
 *
 *
 * If different groups of listeners is desired, a package name world.atlantix.<plugin>.listeners may be created
 * And listeners may organised as desired, with the prefix of the plugin name and a suffix of "Listener"
 *
 */
public class CosmeticsGUIListener implements Listener {
    private final CosmeticsGUI INSTANCE;

    public CosmeticsGUIListener(CosmeticsGUI instance) {
        this.INSTANCE = instance;
    }



}
