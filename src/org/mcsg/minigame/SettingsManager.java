package org.mcsg.minigame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class SettingsManager {

	//makes the config easily accessible

	private static SettingsManager instance = new SettingsManager();
	private static Plugin p;
	private FileConfiguration spawns;
	private FileConfiguration system;
	private FileConfiguration kits;
	private FileConfiguration messages;
	private FileConfiguration chest;


	private File f; //spawns
	private File f2; //system
	private File f3; //kits
	private File f4; //messages
	private File f5; //chest
	
	private static final int KIT_VERSION = 0;
	private static final int MESSAGE_VERSION = 0;
	private static final int CHEST_VERSION = 0;
	private static final int SPAWN_VERSION = 0;
	private static final int SYSTEM_VERSION = 0;
	
	
	
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 *     RE-IMPLEMENT THIS CLASS FOR W/E YOU NEED
	 *     LEAVING AS A REFRENCE
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	
	
	
	
	
	private SettingsManager() {

	}

	public static SettingsManager getInstance() {
		return instance;
	}

	public void setup(Plugin p) {
		SettingsManager.p = p;
	/*	if (p.getConfig().getInt("config-version") == Assassins.config_version) {
			Assassins.config_todate = true;
		}else{
			File config = new File(p.getDataFolder(), "config.yml");
			config.delete();
		}*/
		
		p.getConfig().options().copyDefaults(true);
		p.saveDefaultConfig();
		
		f = new File(p.getDataFolder(), "spawns.yml");
		f2 = new File(p.getDataFolder(), "system.yml");
		f3 = new File(p.getDataFolder(), "kits.yml");
		f4 = new File(p.getDataFolder(), "messages.yml");
		f5 = new File(p.getDataFolder(), "chest.yml");

		try {
			if (!f.exists()) 	f.createNewFile();
			if (!f2.exists())	f2.createNewFile();
			if (!f3.exists()) 	loadFile("kits.yml");
			if (!f4.exists()) 	loadFile("messages.yml");
			if (!f5.exists()) 	loadFile("chest.yml");

		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		reloadSystem();
		saveSystemConfig();
		
		reloadSpawns();
		saveSpawns();
		
		reloadKits();
		//saveKits();
		
		reloadChest();
		
		reloadMessages();
		saveMessages();
		
		
	}

	public void set(String arg0, Object arg1) {
		p.getConfig().set(arg0, arg1);
	}

	public FileConfiguration getConfig() {
		return p.getConfig();
	}

	public FileConfiguration getSystemConfig() {
		return system;
	}

	public FileConfiguration getSpawns() {
		return spawns;
	}

	public FileConfiguration getKits() {
		return kits;
	}
	
	public FileConfiguration getChest() {
		return chest;
	}
	
	public FileConfiguration getMessageConfig() {
		//System.out.println("asdf"+messages.getString("prefix.main"));
		return messages;
	}


	public void saveConfig() {
		// p.saveConfig();
	}

	public static World getGameWorld(int game) {
		if (SettingsManager.getInstance().getSystemConfig().getString("sg-system.arenas." + game + ".world") == null)
			return null;
			//LobbyManager.getInstance().error(true);
		
		return p.getServer().getWorld(SettingsManager.getInstance().getSystemConfig().getString("sg-system.arenas." + game + ".world"));
	}

	public void reloadSpawns() {
		spawns = YamlConfiguration.loadConfiguration(f);
		if(spawns.getInt("version", 0) != SPAWN_VERSION){
			Minigame.$("Deleting outdated config file. "+f.getName());
			f.delete();
			reloadSpawns();
		}
		spawns.set("version", SPAWN_VERSION);
		saveSpawns();
	}

	public void reloadSystem() {
		system = YamlConfiguration.loadConfiguration(f2);
		if(system.getInt("version", 0) != SYSTEM_VERSION){
			Minigame.$("Deleting outdated config file. "+f2.getName());
			f2.delete();
			reloadSystem();
		}
		system.set("version", SYSTEM_VERSION);
		saveSystemConfig();
	}

	public void reloadKits() {
		kits = YamlConfiguration.loadConfiguration(f3);
		if(kits.getInt("version", 0) != KIT_VERSION){
			Minigame.$("Deleting outdated config file. "+f3.getName());
			f3.delete();
			loadFile("kits.yml");
			reloadKits();
		}
	}
	
	
	public void reloadMessages() {
		messages = YamlConfiguration.loadConfiguration(f4);
		if(messages.getInt("version", 0) != MESSAGE_VERSION){
			Minigame.$("Deleting outdated config file. "+f4.getName());
			f4.delete();
			loadFile("messages.yml");
			reloadKits();
		}
		messages.set("version", MESSAGE_VERSION);
		saveMessages();
	}
	
	public void reloadChest() {
		chest = YamlConfiguration.loadConfiguration(f5);
		if(chest.getInt("version", 0) != CHEST_VERSION){
			Minigame.$("Deleting outdated config file. "+f5.getName());
			f5.delete();
			loadFile("chest.yml");
			reloadKits();
		}
	}





	public void saveSystemConfig() {
		try {
			system.save(f2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveSpawns() {
		try {
			spawns.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveKits() {
		try {
			kits.save(f3);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveMessages() {
		try {
			messages.save(f4);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveChest() {
		try {
			chest.save(f5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getSpawnCount(int gameid) {
		return spawns.getInt("spawns." + gameid + ".count");
	}

	public Location getLobbySpawn() {
		try{
			return new Location(Bukkit.getWorld(system.getString("sg-system.lobby.spawn.world")),
				system.getInt("sg-system.lobby.spawn.x"),
				system.getInt("sg-system.lobby.spawn.y"),
				system.getInt("sg-system.lobby.spawn.z"));
		} catch(Exception e){
			return null;
		}
	}

	public Location getSpawnPoint(int gameid, int spawnid) {
		return new Location(getGameWorld(gameid),
				spawns.getInt("spawns." + gameid + "." + spawnid + ".x"),
				spawns.getInt("spawns." + gameid + "." + spawnid + ".y"),
				spawns.getInt("spawns." + gameid + "." + spawnid + ".z"));
	}
	
	public void setLobbySpawn(Location l) {
		system.set("sg-system.lobby.spawn.world", l.getWorld().getName());
		system.set("sg-system.lobby.spawn.x", l.getBlockX());
		system.set("sg-system.lobby.spawn.y", l.getBlockY());
		system.set("sg-system.lobby.spawn.z", l.getBlockZ());
	}


	public void setSpawn(int gameid, int spawnid, Vector v) {
		spawns.set("spawns." + gameid + "." + spawnid + ".x", v.getBlockX());
		spawns.set("spawns." + gameid + "." + spawnid + ".y", v.getBlockY());
		spawns.set("spawns." + gameid + "." + spawnid + ".z", v.getBlockZ());
		if (spawnid > spawns.getInt("spawns." + gameid + ".count")) {
			spawns.set("spawns." + gameid + ".count", spawnid);
		}
		try {
			spawns.save(f);
		} catch (IOException e) {

		}
		GameManager.getInstance().getGame(gameid).addSpawn();

	}

	public static String getSqlPrefix() {
		return getInstance().getConfig().getString("sql.prefix");
	}


	public void loadFile(String file){
		File t = new File(p.getDataFolder(), file);
		System.out.println("Writing new file: "+ t.getAbsolutePath());
			
			try {
				t.createNewFile();
				FileWriter out = new FileWriter(t);
				System.out.println(file);
				InputStream is = getClass().getResourceAsStream("/"+file);
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String line;
				while ((line = br.readLine()) != null) {
					out.write(line+"\n");
					System.out.println(line);
				}
				out.flush();
				is.close();
				isr.close();
				br.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
}
