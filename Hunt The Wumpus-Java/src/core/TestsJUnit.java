package core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import creature.*;
import trap.*;

public class TestsJUnit {

	
	@Test
	public void testPetrolBomb() 
	{
		// set up the test maze
		Game testGame1 = new Game(Maze.K3());
		
		// create 1 NPC and 2 petrol bomb traps
		NPC player1 = new NPC("NPC-the test subject");
		Trap petrolBomb1 = new PetrolBomb();
		Trap petrolBomb2 = new PetrolBomb();
		
		// add the NPC and 2 traps into the maze
		testGame1.addInhabitant(player1, 0);
		testGame1.addTrap(petrolBomb1, 1);
		testGame1.addTrap(petrolBomb2, 2);
		
		// force the NPC to walk into 1 of the trap
		player1.getLocation().setSingleExit(petrolBomb1.getLocation());
		
		// print out the maze
		System.out.println(testGame1.getMaze());
		
		// print out the warning message
		System.out.println(petrolBomb1.warningMessage());
		
		// run the game for a turn
		player1.takeATurn();
		
		// check the status of the NPC (should be dead)
		assertFalse(player1.isAlive());
		
		// check the location of the NPC (should be null for
		// a dead NPC)
		assertTrue(player1.getLocation() == null);
		
		// check the location of the other petrol bomb
		assertTrue(petrolBomb2.getLocation().hasTrap());
		
		// there should be no occupant in the NPC's 
		// original cave since it has died.
		assertFalse(testGame1.getMaze().findCave(0).hasOccupant());
		
		System.out.println('\n' + "^^^^^^^^^^^^^^ My Testing 1 ^^^^^^^^^^^^^^" + '\n');
		
		return;
	}
	
	
	/* Test the functionality of the poison arrow trap
	 * -create a game with 1 NPC and 1 poison arrow trap
	 * -force the NPC to walk into the poison arrow trap
	 * -after the turn, the NPC should take 1 damage point
	 * -check the location of the NPC
	 * -check the status of the NPC after the turn
	 * -check the status of the poison arrow trap*/
	
	@Test
	public void testPoisonArrow ()
	{
		// set up the test maze
		Game testGame2 = new Game(Maze.K3());
		
		// create 1 NPC and 1 poison arrow trap
		NPC player2 = new NPC("NPC-the test subject");
		Trap PoisonArrow = new PoisonArrow();
		
		// add the trap and the NPC to the maze
		testGame2.addInhabitant(player2, 3);
		testGame2.addTrap(PoisonArrow, 4);
		
		// force the NPC to walk into the trap
		player2.getLocation().setSingleExit(PoisonArrow.getLocation());
		
		// print out the maze
		System.out.println(testGame2.getMaze());
		
		// print out the warning message
		System.out.println(PoisonArrow.warningMessage());
		
		// run the game for a turn
		player2.takeATurn();
		
		// check whether the NPC is still alive (it should be
		// alive because it still has 2 health points)
		assertTrue(player2.isAlive());
		
		// check whether the NPC's health equals to 2
		assertTrue(player2.getDamagePoints() == 2);
		
		// the poison arrow trap should still exist after firing
		// a shot because it has 2 arrows in reserves
		assertTrue(PoisonArrow.getLocation().hasTrap());
		
		// after the move,NPC's position should equal to the trap's location
		assertTrue(player2.getLocation().equals(PoisonArrow.getLocation()));
		
		// the remaining cave should be empty without traps or creatures.
		assertFalse(testGame2.getMaze().findCave(5).hasTrap());
		
		System.out.println('\n' + "^^^^^^^^^^^^^^ My Testing 2 ^^^^^^^^^^^^^^" + '\n');
		
		return;
	}
	
	
	/* Test the behaviour of the Wumpus
	 * -create a game with 1 NPC and 1 Wumpus
	 * -force the NPC to walk into Wumpus's cave
	 * -after the turn, the NPC should be dead
	 * -check the location of the NPC
	 * -check the status of the NPC after the turn
	 * -check the status of the Wumpus
	 * -check the location of the Wumpus*/
	
	@Test
	public void testWumpus() 
	{
		// set up the test maze
		Game testGame3 = new Game(Maze.K3());
		
		// create an NPC
		NPC player3 = new NPC("NPC-the test subject");
		
		// create a Wumpus
		Wumpus player4 = new Wumpus("Wumpus");
		
		// add an NPC to the maze
		testGame3.addInhabitant(player3, 6);
		
		// add a Wumpus to the maze
		testGame3.addInhabitant(player4, 7);
		
		// force the NPC to walk into the Wumpus's cave
		player3.getLocation().setSingleExit(player4.getLocation());
		
		// print out the maze
		System.out.println(testGame3.getMaze());
		
		// run the game for a turn (Wumpu's turn)
		// The Wumpu should be sleeping
		player4.takeATurn();
		
		// display the warning message
		System.out.println(player4.warningMessage());
		
		// run the game for a turn (NPC's turn)
		player3.takeATurn();
		
		// The NPC should be dead because it was eaten by the Wumpus
		assertFalse(player3.isAlive());
		
		// The Wumpus should still be alive
		assertTrue(player4.isAlive());
		
		// The NPC's location should be null because the creature has been killed
		assertTrue(player3.getLocation() == null);
		
		// The Wumpus should still have a full health because it doesn't take
		// damage from defending its position
		assertTrue(player4.getDamagePoints() == 3);
		
		// The Wumpus should still be in the same cave as the turn begun.
		assertTrue(player4.getLocation().equals(testGame3.getMaze().findCave(player4)));
		
		System.out.println('\n' + "^^^^^^^^^^^^^^ My Testing 3 ^^^^^^^^^^^^^^" + '\n');
		
		return;
	}
		
}