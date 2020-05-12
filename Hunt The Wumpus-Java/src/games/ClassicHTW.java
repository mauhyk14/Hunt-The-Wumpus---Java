package games;

import core.Game;
import core.Maze;
import creature.Human;
import creature.Wumpus;
import creature.Hunter;
import creature.UberWumpus;
import trap.BottomlessPit;
import trap.PetrolBomb;
import trap.SuperBat;

public class ClassicHTW {

	public static int rounds = 20;  
	
	/**
	 * This is the main function of the program where I try to recreate 
	 * the classic Hunt the Wumpus text-based adventure game.
	 */
	public static Game canonicalGame(){
		Game theGame = new Game(Maze.dodecahedron());
		
		// Add a hunter, a UberWumper and several traps to the game. 
		// Do a test run before implementing the game further.
		theGame.addInhabitant(new UberWumpus("Willy"));
		
		theGame.addInhabitant(new Hunter("Tom"));

		theGame.addTrap(new BottomlessPit());
		theGame.addTrap(new BottomlessPit());
		
		theGame.addTrap(new PetrolBomb());
		theGame.addTrap(new PetrolBomb());
		
		theGame.addTrap(new SuperBat());
		theGame.addTrap(new SuperBat());   
		
		//theGame.setupIsComplete();
		
		return theGame;
	}

	public static void main(String[] args) {
		
		// setup a canonical game of Hunt the Wumpus
		Game fun = canonicalGame();  
		
		// number of rounds to play		
		int nRound = ClassicHTW.rounds;   
		
		// any further setup goes here
		
		// ensure setup is finished
		fun.setupIsComplete();
		
		fun.printMaze();
		
		// play the game
		fun.playTheGame(nRound);
		
	}

}
