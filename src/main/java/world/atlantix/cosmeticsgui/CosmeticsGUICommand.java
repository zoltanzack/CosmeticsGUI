package world.atlantix.cosmeticsgui;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

/**
 * The command executor for this plugin
 *
 *
 * Note: If multiple commands are to be implemented, a package name world.atlantix.<plugin>.commands may be created
 *       And commands may be put in there with the prefix of the plugin name and a suffix of "Command"
 *
 */
public class CosmeticsGUICommand implements CommandExecutor, TabCompleter {
    private final CosmeticsGUI INSTANCE;
    private final TestGUI gui;

    public CosmeticsGUICommand(CosmeticsGUI instance, TestGUI gui) {
        this.INSTANCE = instance;
        this.gui = gui;

    }

    /**
     *
     *
     * Note: Any addition or removal of commands need to be updated in the plugin.yml file
     *
     * @param sender Source of the command
     * @param command The command sent
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("cosmeticshop")) { // Is this plugin's command
            if(args.length > 0) { // Has arguments to the command
                if("reload".equalsIgnoreCase(args[0]) && sender.hasPermission("framework.command.reload")) {
                    return true;
                }
            } else {
                gui.show((HumanEntity) sender);
            }
        }

        return false;
    }

    /**
     * Returns a list of auto completions for this command
     * @param sender Source of the command
     * @param command The command sent
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("framework")) { // Is this plugin's command
            INSTANCE.getLogger().log(Level.INFO, "Framework Tab Completer");
            List<String> autoCompletes = new ArrayList<>(); // List of strings to auto-complete

            switch(args.length) {
                case 1: // First level of command
                    autoCompletes.addAll(Arrays.asList(
                            "reload",
                            "command2"
                    ));
                    break;
                default: // No previous cases were vali
            }

            return autoCompletes;
        }

        return null;
    }

    private void notifyUsage(CommandSender sender) {
        sender.sendMessage(new String[] {
                "Usage: /framework reload",
                "Usage: /framework command2",
                "Usage: /framework command3",
                "Note: This is the framework of a plugin for the Atlantix network"
        });
    }
}
