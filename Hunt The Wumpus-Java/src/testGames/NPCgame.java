package testGames;

import core.Game;
import core.Maze;
import creature.NPC;
import trap.BottomlessPit;

public class NPCgame {

	public static int playTurns = 9;
	
	/* This a custom game which I created to test
	 * and observe the behaviours of 2 NPCs a 
	 * BottomlessPit trap. */	
	public static void main(String[] args) {

		// create a Game with K3 Maze
		//Game theGame = new Game(Maze.cube());
		Game theGame = new Game( Maze.K3());
		
		// put a couple of Creatures in it
		theGame.addInhabitant(new NPC("Bert-NPC"),0);  // in Cave 0
		theGame.addInhabitant(new NPC("Ernie-NPC"));  
		
		// add an BottomlessPit too
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
