package org.mcsg.minigame;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class Minigame extends JavaPlugin{

	public static Logger logger;

	
	public void onEnable(){
		SettingsManager.getInstance().setup(this);
		GameManager.getInstance().setup(this);
		getCommand("minigame").setExecutor(new CommandHandler(this));

	}
	
	public void onDisable(){
		
	}



	public static void $(String msg){
		logger.log(Level.INFO, msg);
	}

	public static void $(Level l, String msg){
		logger.log(l, msg);
	}

	public static void debug(String msg){
		if(SettingsManager.getInstance().getConfig().getBoolean("debug", false))
			$(msg);
	}

	public static void debug(int a) {
		if(SettingsManager.getInstance().getConfig().getBoolean("debug", false))
			debug(a+"");
	}
	
	public WorldEditPlugin getWorldEdit() {
		Plugin worldEdit = getServer().getPluginManager().getPlugin("WorldEdit");
		if (worldEdit instanceof WorldEditPlugin) {
			return (WorldEditPlugin) worldEdit;
		} else {
			return null;
		}
	}
}
