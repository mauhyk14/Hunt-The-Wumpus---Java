package testGames;

import core.Game;
import core.Maze;
import creature.Minotaur;
import creature.NPC;

public class Minotaurgame {

	public static int playTurns = 9;
	
	/* This a custom game which I created to test
	 * and observe the behaviours of the Minotaur 
	 * and NPC. */	
	public static void main(String[] args) {

		// create a Game with square Maze
		//Game theGame = new Game(Maze.cube());
		Game theGame = new Game( Maze.square());
		
		// put a couple of Creatures in it
		theGame.addInhabitant(new Minotaur("Minotaur"),0);  
		theGame.addInhabitant(new NPC("Theseus"));  
		
		// add an ExampleTrap too
		//theGame.addTrap(new ExampleTrap());
		
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
