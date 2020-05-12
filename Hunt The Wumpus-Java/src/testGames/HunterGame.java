package testGames;

import core.Game;
import core.Maze;
import creature.Hunter;
import creature.NPC;
import trap.BottomlessPit;
import trap.PetrolBomb;

public class HunterGame {
	
	/* This a custom game which I created to test
	 * and observe the behaviours of the Hunter, NPC,   
	 * BottomlessPit trap and PetrolBomb trap. */

	public static void main(String[] args) {

		// create a Game with cubic Maze
		Game theGame = new Game(Maze.cube());  
		
		// put a couple of ExampleCreatures in it
		theGame.addInhabitant(new NPC("NPC-1"),0);  
		theGame.addInhabitant(new Hunter("Hunter"));  
		theGame.addInhabitant(new NPC("NPC-2"),4);
		
		// put a couple of ExampleTraps in it
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
