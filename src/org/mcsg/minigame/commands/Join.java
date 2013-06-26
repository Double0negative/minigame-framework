package org.mcsg.minigame.commands;

import org.mcsg.minigame.GameManager;
import org.bukkit.entity.Player;

public class Join implements SubCommand{
	@Override
	public boolean onCommand(Player player, String[] args) {
		if(player.hasPermission(permission())){
			GameManager.getInstance().getGame(Integer.parseInt(args[0])).addPlayer(player);
			return true;
		}
		return false;
	}
	@Override
	public String help(Player p) {
		return "/mingame join - joins a game";

	}
	@Override
	public String permission() {
		return "minigame.join";
	}

}
