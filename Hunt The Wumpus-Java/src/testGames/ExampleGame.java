package testGames;

import core.Game;
import core.Maze;
import creature.ExampleCreature;
import trap.ExampleTrap;

public class ExampleGame {


	public static void main(String[] args) {

		// create a Game with cubic Maze
		Game theGame = new Game(Maze.cube());
		
		// put a couple of ExampleCreatures in it
		theGame.addInhabitant(new ExampleCreature("Bert"),0);  // in Cave 0
		theGame.addInhabitant(new ExampleCreature("Ernie"));  // random Cave
		
		// put a couple of ExampleTraps in it
		theGame.addTrap(new ExampleTrap(),1);  // in Cave 1
		theGame.addTrap(new ExampleTrap());  // random Cave
		
		// finish game setup
		theGame.setupIsComplete();
		
		// print status of Maze at beginning
		theGame.printMaze();
		
		// run the game for a few turns
		theGame.playTheGame(5);
		
		// print status at end
		theGame.printMaze();
	}

}
