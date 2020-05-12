package testGames;

import core.Game;
import core.Maze;
import creature.Bashful;
import creature.NPC;

public class Bashfulgame {

	public static int playTurns = 9;
	
	/* This a custom game which I created to test
	 * out the behaviours of Bashfuls, NPCs 
	 * and PetrolBombs. */	
	public static void main(String[] args) {
		 
		// create a game with a square maze
		Game theGame = new Game( Maze.square());
		
		theGame.addInhabitant(new Bashful("Bashful-1"));  
		theGame.addInhabitant(new Bashful("Bashful-2"));
		theGame.addInhabitant(new NPC("NPC"));
		
		// add an PetrolBomb Trap 
		//theGame.addTrap(new PetrolBomb());
		
		// finish the game setup
		theGame.setupIsComplete();
		
		// print out the status of the Maze at beginning
		theGame.printMaze();
		
		// run the game for a few rounds
		theGame.playTheGame(playTurns); 
		
		// print out status at end
		theGame.printMaze();
	}

}
