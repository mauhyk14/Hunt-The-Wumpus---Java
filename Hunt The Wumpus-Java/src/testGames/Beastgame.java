package testGames;

import core.Game;
import core.Maze;
import creature.Beast;
import creature.NPC;
import trap.GrumpyDwarf;
import trap.SuperBat;

public class Beastgame {

	public static int playTurns = 9;
	
	/* This a custom game which I created to test
	 * and observe the behaviours of the Beast, NPC,   
	 * SuperBat trap and GrumpyDwarf trap. */	
	public static void main(String[] args) {

		// create a Game with a cubic Maze		
		Game theGame = new Game( Maze.square());
		
		// put a couple of Creatures in it
		theGame.addInhabitant(new Beast("Beast"));  
		theGame.addInhabitant(new NPC("NPC"));  
		
		//add an SuperBat trap, GrumpyDwarf trap.
		theGame.addTrap(new SuperBat());
		//theGame.addTrap(new GrumpyDwarf());
		
		// finish the game setup
		theGame.setupIsComplete();
		
		// print status of Maze at beginning
		theGame.printMaze();
		
		// run the game for a few rounds
		theGame.playTheGame(playTurns);  
		
		// print status at the end
		theGame.printMaze();
	}

}
