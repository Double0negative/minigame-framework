package org.mcsg.minigame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.mcsg.minigame.GameManager;
import org.mcsg.minigame.Game.GameState;

public class Game {

	public static enum GameState {
		DISABLED, LOADING, INACTIVE, WAITING,
		STARTING, INGAME, FINISHING, RESETING, ERROR
	}

	private GameState state = GameState.DISABLED;
	private ArrayList < Player > activePlayers = new ArrayList < Player > ();
	private ArrayList < Player > inactivePlayers = new ArrayList < Player > ();
	private ArrayList < String > spectators = new ArrayList < String > ();
	private ArrayList < Player > queue = new ArrayList < Player > ();
	
	
	private Arena arena;
	private int gameID;
	
	public Game(int a) {
		// TODO Auto-generated constructor stub
	}

	public void countdown(int i) {
		// TODO Auto-generated method stub
		
	}
	
	public void startGame(){
		
		
	}

	public void addPlayer(Player p) {
		GameManager.getInstance().removeFromOtherQueues(p, gameID);
		if(state == GameState.LOADING && GameManager.getInstance().getPlayerGame(p) == null){
			
		}
	}
	
	
	
	
	public void disable() {
		// TODO Auto-generated method stub
		
	}
	
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	
	public void killPlayer(){
		
		
	}
	
	//the boolean is for if they logged out.
	public void removePlayer(Player p, boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void removeSpectator(Player p) {
		// TODO Auto-generated method stub
		
	}

	public void playerWin(Player p){
		
		
	}
	
	
	public void resetGame(){
		
		
	}

	
	
	
	public boolean isBlockInArena(Location v) {
		return arena.containsBlock(v);
	}

	public int getID() {
		// TODO Auto-generated method stub
		return gameID;
	}

	public boolean isPlayerActive(Player p) {
		return activePlayers.contains(p);
	}
	
	public boolean isPlayerInactive(Player player) {
		return inactivePlayers.contains(player);
	}

	public boolean isSpectator(Player p) {
		return spectators.contains(p);
	}

	public boolean isInQueue(Player p) {
		return queue.contains(p);
	}

	public void removeFromQueue(Player p) {
		queue.remove(p);
	}

	public ArrayList < Player > getAllPlayers() {
		ArrayList < Player > all = new ArrayList < Player > ();
		all.addAll(activePlayers);
		all.addAll(inactivePlayers);
		return all;
	}

	


	public GameState getState() {
		return state;
	}
	
	
	public Player[][] getPlayers() {
		return new Player[][] {
				activePlayers.toArray(new Player[0]), inactivePlayers.toArray(new Player[0])
		};
	}



}
