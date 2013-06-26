package org.mcsg.minigame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.mcsg.minigame.MessageManager.PrefixType;
import org.mcsg.minigame.commands.Join;
import org.mcsg.minigame.commands.SubCommand;



public class CommandHandler implements CommandExecutor {
	private Plugin plugin;
	private HashMap < String, SubCommand > commands;
	private MessageManager msgmgr = MessageManager.getInstance();
	
	public CommandHandler(Plugin plugin) {
		this.plugin = plugin;
		commands = new HashMap < String, SubCommand > ();
		loadCommands();
	}

	private void loadCommands() {

		commands.put("join", new Join());
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd1, String commandLabel, String[] args) {
		PluginDescriptionFile pdfFile = plugin.getDescription();
		if (!(sender instanceof Player)) {
			msgmgr.logMessage(PrefixType.WARNING, "Only in-game players can use Minigame commands! ");
			return true;
		}

		Player player = (Player) sender;


		if (cmd1.getName().equalsIgnoreCase("minigame")) {
			if (args == null || args.length < 1) {
				msgmgr.sendMessage(PrefixType.INFO, "Minigame template", player);
				msgmgr.sendMessage(PrefixType.INFO, "Version " + pdfFile.getVersion() + " by Double0negative", player);
				return true;
			}
			if (args[0].equalsIgnoreCase("help")) {
				help(player);
				return true;
			}
			String sub = args[0];
			ArrayList < String > l = new ArrayList < String > ();
			l.addAll(Arrays.asList(args));
			l.remove(0);
			args = (String[]) l.toArray(new String[0]);
			if (!commands.containsKey(sub)) {
				msgmgr.sendMessage(PrefixType.WARNING, "Command doesn't exist.", player);
				msgmgr.sendMessage(PrefixType.INFO, "Type /sg help for command information", player);
				return true;
			}
			try {
				commands.get(sub).onCommand(player, args);
			} catch (Exception e) {
				e.printStackTrace();
				msgmgr.sendFMessage(PrefixType.ERROR, "error.command", player, "command-["+sub+"] "+Arrays.toString(args));
				msgmgr.sendMessage(PrefixType.INFO, "Type /sg help for command information", player);
			}
			return true;
		}
		return false;
	}

	public void help (Player p) {

		for (SubCommand v : commands.values()) {
            if (v.permission() != null) {
                if (p.hasPermission(v.permission())) {
                    msgmgr.sendMessage(PrefixType.INFO, v.help(p), p);
                } else {
                    msgmgr.sendMessage(PrefixType.WARNING, v.help(p), p);
                }
            } else {
                msgmgr.sendMessage(PrefixType.INFO, v.help(p), p);
            }
        }
	}
}