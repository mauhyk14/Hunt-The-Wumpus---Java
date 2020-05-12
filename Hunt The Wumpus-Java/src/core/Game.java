package core;

import java.util.Arrays;
import java.util.Scanner;

import creature.*;
import file.PrintStatus;
import trap.*;

public class Game {

	/**
	 * if whether or not the Game is in setup mode.
	 */
	private boolean setupFlag = false;
	/**
	 * Call this to stop setup of Game.
	 * Once this has been called, no more Traps or Inhabitants can be added.
	 * However the Game can now be played.
	 */
	public void setupIsComplete(){
		setupFlag = true;
	}
	/**
	 * Whether or not setup mode is finished.
	 * @return true
	 */
	public boolean readyToPlay(){ return setupFlag; }
	
	/**
	 * The network of Caves associated with this Game
	 */
	private Maze dungeon;
	/**
	 * public routine to print dungeon, since dungeon itself is private
	 */
	public void printMaze(){
		System.out.println(dungeon);
	}
	/**
	 * The Inhabitants associated with this Game
	 */
	private Inhabitant[] players;  
	/**
	 * get an array of the Inhabitants in this Game
	 * (for use with PrintStatus)
	 * @return newly created array containing references to each Inhabitant
	 */
	public Inhabitant[] getArrayOfPlayers(){ 	// note access so can see from PrintStatus.
		//return players;
		return Arrays.copyOf(players,players.length);  //  makes a shallow copy, yes?
	}
	/**
	 * The Traps associated with this Game.
	 */
	private Trap[] traps;          // ditto
	/**
	 * number of Traps and Inhabitants associated with this Game.
	 */
	private int nIhab=0, nTrap=0;
	
	/**
	 * Provide access to the Maze (network of Caves)
	 * @return
	 */
	public Maze getMaze() { return dungeon; }
	
	/**
	 * Find the Inhabitant with the given name.
	 * If there is no such Inhabitant in this Game then return null.
	 * @param name The name of the Inhabitant
	 * @return the named Inhabitant, or null
	 */
	public Inhabitant getPlayerByName(String name){
		for (int i=0; i<players.length; i++){
			if (players[i]!=null && players[i].getName().equals(name)){
				return players[i];
			}
		}
		return null;
	}
	
	/////////////////////////////////////////////////////
	
	/**
	 * set up empty data structures for Inhabitants and Traps.
	 */
	private void configure(){
		// array of nRooms = overkill
		players = new Inhabitant[dungeon.nRooms()];
		traps = new Trap[dungeon.nRooms()];
		nIhab = 0;
		nTrap = 0;
		setupFlag = false;
	}
	
	/**
	 * create new Game around supplied Maze (Cave network).
	 * @param M the Maze
	 */
	public Game(Maze M) {
		dungeon = M;
		configure();
	}
	
	/**
	 * Add an Inhabitant to this Game.
	 * This will fail if the nominated location either does not exist or is already occupied.
	 * @param ihab the Inhabitant
	 * @param loc the ID of the Cave in which to add this Inhabitant
	 * @return true on success, false if Inhabitant was not added.
	 */
	public boolean addInhabitant(Inhabitant ihab, int loc){
		return addInhabitant(ihab, dungeon.findCave(loc));
	}
	/**
	 * Add an Inhabitant to this Game.
	 * This will fail if the nominated location either does not exist or is already occupied.
	 * @param ihab the Inhabitant
	 * @param cave the Cave in which to add this Inhabitant
	 * @return true on success, false if Inhabitant was not added.
	 */
	public boolean addInhabitant(Inhabitant ihab, Cave cave){
		if (readyToPlay() || nIhab==players.length) return false;
		
//		// 14/1/19.  Not ideal.  May scrub this.
//		if (ihab instanceof Wumpus){
//			// find any existing Wupuses
//			dungeon.
//		}
//  need a "find foo in Maze" method for Maze.  Or an iterator over Caves.
//  cannot be bothered doing this;  adds more complexity.  May need different Exception.
		
		if (ihab.setLocation(this,cave) ){
			players[nIhab++] = ihab;
			return true;
		}
		return false;
	}
	/**
	 * Add an Inhabitant to this Game in a random location
	 * This may fail if an empty location cannot be found.
	 * @param ihab the Inhabitant
	 * @return true on success, false if Inhabitant was not added.
	 */
	public boolean addInhabitant(Inhabitant ihab){
		final int ntry = 20;
		for(int i=0; i<ntry; i++){
			Cave dest = dungeon.pickRandomCave();
			if (dest.hasOccupant() || dest.hasTrap()) continue;
			return addInhabitant(ihab, dest);
		}
		// ran out of tries
		return false;
	}
//	// ***************** TESTING: ************************
//	// *** of course this don't work.  dynamic typing happens to caller, not params.
//	public boolean addInhabitant(Wumpus woo){
//		System.out.println("WUMPUS ADDER");
//		return this.addInhabitant((Inhabitant)woo);
//	}
	
	// this added 11/2/19 to expedite testing
	/**
	 * Add an Inhabitant to this Game in a random location
	 * This may fail if an empty location cannot be found.
	 * @param ihab the Inhabitant
	 * @return Inhabitant's location (Cave) if successful, null if Inhabitant was not added.
	 */
	public Cave addInhabitantGetLocn(Inhabitant ihab){
		boolean OK = this.addInhabitant(ihab);
		if (!OK) return null;
		return ihab.getLocation();
	}
	/**
	 * Add a Trap to a particular Cave in this Game
	 * @param T the Trap
	 * @param loc the ID of the Cave in which to add Trap
	 * @return true on success, false if failed to add Trap.
	 */
	public boolean addTrap(Trap T, int loc){
		return addTrap(T, dungeon.findCave(loc));
	}
	/**
	 * Add a Trap to a particular Cave in this Game
	 * @param T the Trap
	 * @param cave the Cave in which to add Trap
	 * @return true on success, false if failed to add Trap.
	 */
	public boolean addTrap(Trap T, Cave cave){
		if (readyToPlay() || nTrap==traps.length) return false;
		if (T.setLocation(this,cave) ){
			traps[nTrap++] = T;
			return true;
		}
		return false;
	}
	/**
	 * Add a Trap to this Game in a random Cave
	 * @param T the Trap
	 * @return true on success, false if failed to add Trap.
	 */
	public boolean addTrap(Trap T){
		// *** SIMILARITY CODE ... ****  can we share this code?
		final int ntry = 20;
		for(int i=0; i<ntry; i++){
			Cave dest = dungeon.pickRandomCave();
			if (dest.hasOccupant() || dest.hasTrap()) continue;
			return addTrap(T, dest);
		}
		// ran out of tries
		return false;
	}
	
	//  //  //  //  //  //
	
	/**
	 * Play this Game.
	 * The setupIsFinished() method must be called prior to calling this method.
	 * @param nRound number of Rounds to play game for.
	 */
	public void playTheGame(int nRound){
		if (!readyToPlay()) 
			throw new RuntimeException("Must call setupIsComplete() prior to playTheGame()");
		for(int round=1; round<=nRound; round++){
			if (numLivingPlayers()==0) break;
			System.out.println("\n   *** ROUND " + round + " ***\n");
			for (Inhabitant ihab: players){
				if (ihab!=null && ihab.isAlive()){
					System.out.print(ihab.getName() + "'s turn");
					//if (ihab.getLocation()!=null)  // just in case. Should never happen.
						System.out.println(" begins in cave #" + ihab.getLocation().getID() + ".");
					//else
					//	System.out.println(".");

						ihab.takeATurn();

					if (ihab.isAlive())
						System.out.println(ihab.getName() + " ends turn in cave #" + ihab.getLocation().getID());
					else
						System.out.println(ihab.getName() + " has died, GAME OVER.");
					System.out.println("   --------");
				}
			}

		}
		
		System.out.print("\nNo more rounds.");
		System.out.println("  " + numLivingPlayers() + " players remain.");
		
		PrintStatus.printToFile(this);
	}
	
	private int numLivingPlayers(){
		int count=0;
		for(Inhabitant ihab: players) if (ihab!=null&&ihab.isAlive()) count++;
		return count;
	}	

	//  //  //  //  //  //  //  //  //  //  //  //  //  //  //
	
}