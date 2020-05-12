package testGames;

import core.Game;
import core.Maze;
import creature.Clever;
import creature.NPC;
import trap.PetrolBomb;
import trap.PoisonArrow;

public class Clevergame {

	public static int playTurns = 15;
	
	/* This a custom game which I created to test
	 * and observe the behaviours of the Clever, NPC,   
	 * PetrolBomb trap and PoisonArrow trap. */	
	public static void main(String[] args) {

		// create a Game with square Maze
		//Game theGame = new Game(Maze.cube());
		Game theGame = new Game( Maze.square());
		
		// put a couple of Creatures in it
		theGame.addInhabitant(new Clever("Clever"),0);  
		theGame.addInhabitant(new NPC("NPC"));  
		
		// add a few Traps too
		//theGame.addTrap(new PetrolBomb());
		theGame.addTrap(new PoisonArrow());
		
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
