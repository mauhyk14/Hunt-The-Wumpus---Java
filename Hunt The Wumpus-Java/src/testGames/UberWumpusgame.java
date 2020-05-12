package testGames;

import core.Game;
import core.Maze;
import creature.NPC;
import creature.UberWumpus;
import trap.BottomlessPit;

public class UberWumpusgame {

	public static int playTurns = 20;
	
	/* This a custom game which I created to test
	 * and observe the behaviours of the UberWumpus,
	 * 2 NPCs and a BottomlessPit trap.*/
	
	public static void main(String[] args) {

		// create a Game with square Maze
		//Game theGame = new Game(Maze.cube());
		Game theGame = new Game( Maze.square());
		
		// put a couple of Creatures in it
		theGame.addInhabitant(new UberWumpus("UberWumpus"),0);  
		theGame.addInhabitant(new NPC("NPC-1"));
		theGame.addInhabitant(new NPC("NPC-2"));
		
		// add a BottomlessPit Trap too
		theGame.addTrap(new BottomlessPit());
		
		// finish game setup
		theGame.setupIsComplete();
		
		// print status of Maze at beginning
		theGame.printMaze();
		
		// run the game for a few turns
		theGame.playTheGame(playTurns);  
		
		// print status at end
		theGame.printMaze();
	}

}
