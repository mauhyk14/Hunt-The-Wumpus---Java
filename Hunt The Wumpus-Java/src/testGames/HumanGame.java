package testGames;

import core.Game;
import core.Maze;
import creature.Human;
import creature.NPC;
import trap.BottomlessPit;
import trap.PetrolBomb;

public class HumanGame {
	
	/* This a custom game which I created to test
	 * and observe the behaviours of the Human, NPC,   
	 * BottomlessPit trap and PetrolBomb trap. */
	public static void main(String[] args) {

		// create a Game with k4 Maze
		Game theGame = new Game(Maze.K4());  
		
		// put a couple of ExampleCreatures in it
		theGame.addInhabitant(new NPC("NPC"),0);  
		theGame.addInhabitant(new Human("Human"));  
		
		// put a couple of Traps in it
		theGame.addTrap(new BottomlessPit(),3);  
		theGame.addTrap(new PetrolBomb(), 2);  
		
		// finish game setup
		theGame.setupIsComplete();
		
		// print status of Maze at beginning
		theGame.printMaze();
		
		// run the game for a few turns
		theGame.playTheGame(9);
		
		// print status at end
		theGame.printMaze();
	}

}
