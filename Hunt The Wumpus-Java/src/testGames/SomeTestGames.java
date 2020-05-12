package testGames;

import core.Game;
import core.Maze;
import creature.*;
import trap.BottomlessPit;
import trap.SuperBat;
import trap.Trap;

public class SomeTestGames {

	// THIS ONE HAS BEEN COPIED INTO ClassicHTW.java *******
	/**
	 * setup a Canonical game of Hunt The Wumpus:
	 * A dodecahedral Maze, containing 
	 * one Human player, one Wumpus, Two Bottomless Pits and Two Super Bats.
	 * @return
	 */
	public static Game canonicalGame(){
		Game theGame = new Game(Maze.dodecahedron());

		theGame.addInhabitant(new Wumpus("Willy"));

		theGame.addInhabitant(new Human("Tom"));

		theGame.addTrap(new BottomlessPit());
		theGame.addTrap(new BottomlessPit());

		theGame.addTrap(new SuperBat());
		theGame.addTrap(new SuperBat());

		//theGame.setupIsComplete();

		return theGame;
	}

	/**
	 * setup a Game for testing.
	 * @return the Game.
	 */
	public static Game testGame(){
		Game theGame = new Game(Maze.cube());
		boolean OK = true;

		NPC var;
		var = new NPC("Adam");
		OK =  theGame.addInhabitant(var,0);
		var = new Bashful("Eve");
		OK &= theGame.addInhabitant(var,4);
		var = new NPC("Cain");
		var.goToSleep(2);
		OK &= theGame.addInhabitant(var,2);
		var = new Bashful("Abel");
		var.goToSleep();
		OK &= theGame.addInhabitant(var,1);
		Wumpus varW = new Wumpus("Willy");
		OK &= theGame.addInhabitant(varW,7);

		Trap ouch;
		ouch = new BottomlessPit();
		OK &= theGame.addTrap(ouch,6);
		ouch = new BottomlessPit(); //SuperBat();
		OK &= theGame.addTrap(ouch,5);

		Human play1 = new Human("One");
		OK &= theGame.addInhabitant(play1, 3);

		if (!OK)
			System.out.println("Something went wrong in game setup.");
		//else
		//	theGame.setupIsComplete();

		return theGame;
	}

	/**
	 * Setup a Game for testing.
	 * @return the Game
	 */
	public static Game largerTestGame(){
		Game theGame = new Game(Maze.tesseract());
		boolean OK = true;

		Human play1 = new Human("One");
		OK &= theGame.addInhabitant(play1, 1);

		NPC var;
		var = new NPC("Adam");
		OK =  theGame.addInhabitant(var,13);
		var = new Bashful("Eve");
		OK &= theGame.addInhabitant(var,8);
		var = new NPC("Cain");
		var.goToSleep(2);
		OK &= theGame.addInhabitant(var,4);
		var = new Bashful("Abel");
		var.goToSleep();
		OK &= theGame.addInhabitant(var,14);
		Wumpus varW2 = new Wumpus("Willy");
		OK &= theGame.addInhabitant(varW2,10);

		Trap ouch;
		ouch = new BottomlessPit();
		OK &= theGame.addTrap(ouch,3);
		ouch = new SuperBat();
		OK &= theGame.addTrap(ouch,15);

		if (!OK)
			System.out.println("Something went wrong in game setup.");
		//else
		//	theGame.setupIsComplete();		

		return theGame;
	}
	
	public static Game labyrinthOfMinos() {
		Game theGame = new Game( Maze.cube() );
		theGame.addInhabitant(new NPC("Daedalus"));
		theGame.addInhabitant(new NPC("Icarus"));
		theGame.addInhabitant(new Hunter("Theseus"));
		theGame.addInhabitant(new Minotaur("The Minotaur"));
	
		return(theGame);
	}
	

	public static void main(String[] args){

		//Game fun = canonicalGame();  int nRound = 20;
		//Game fun = testGame();  int nRound = 5;
		//Game fun = largerTestGame();  int nRound = 5;
		//Game fun = randomStuffGame();  int nRound = 10;
		Game fun = labyrinthOfMinos();  int nRound = 9;
		
		// any further setup goes here
		//fun.addTrap(new SleepGas());

		// ensure setup is finished
		fun.setupIsComplete();

		fun.printMaze();
		
		fun.playTheGame(nRound);

	}
}


