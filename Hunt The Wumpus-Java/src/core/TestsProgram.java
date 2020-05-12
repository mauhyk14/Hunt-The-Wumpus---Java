package core;

import creature.*;
import trap.*;


public class TestsProgram {

	
	// check NPC behaviour.
	// - should remain in its Cave after first 2 incursion attempts
	// - should have died after 3rd attempt, with no damage points.
	// this requires NPC.takeATurn to be functioning correctly.
	public static boolean testNPC(){
		// create a game/maze
		Game testGame = new Game(Maze.K3());
		
		// add a NPC
		Inhabitant player1 = new NPC("Susie");
		testGame.addInhabitant(player1);
		
		// add another NPC
		Inhabitant player2 = new NPC("Amanda");
		testGame.addInhabitant(player2);
		
		// modify player 2's Cave so the only exit is to cave of player 1.
		Cave p1Cave = testGame.getMaze().findCave(player1);
		Cave p2Cave = testGame.getMaze().findCave(player2);
		p2Cave.setSingleExit(p1Cave);

		// print out the setup to make sure it's OK
		System.out.println(testGame.getMaze());
		
		// get player2 to move.  First time, player 1 should hold her ground.
		System.out.println("TRY 1");
		player2.takeATurn();
		boolean OK;
		OK = (player1.getLocation()==p1Cave) && (player2.getLocation()==p2Cave);
		// try again.  Second time, player 1 should hold ground.
		System.out.println("TRY 2");
		player2.takeATurn();
		OK &= (player1.getLocation()==p1Cave) && (player2.getLocation()==p2Cave);
		// third time.  This time player 2 should have no location, player 1 should take over cave.
		System.out.println("TRY 3");
		player2.takeATurn();
		OK &= (player1.getLocation()==null) && (player2.getLocation()==p1Cave);
		// by now player 1 should have died:
		OK &= !player1.isAlive();
		
		return OK;
	}
	
	
	//  //  //  //  //  //  //  //  
	
	// set up Maze so that player moves into Bottomless Pit trap
	// check afterwards that player is dead / has no location
	// and that trap room has no occupant.
	// (note that this test should not pass for a Wumpus or other immune creature)
	// This test assumes that NPC.takeATurn is working perfectly.
	static boolean testBottomlessPit(){
		Game theGame = new Game(Maze.cube());
		NPC player = new NPC("Sally");
		Trap BP = new BottomlessPit();
		theGame.addInhabitant(player);
		theGame.addTrap(BP);
		
		// set player's cave so only exit is to trap
		theGame.getMaze().findCave(player).setSingleExit(theGame.getMaze().findCave(BP));
		//boolean moved = player.moveToRandomExit();
		player.takeATurn();
		
		boolean OK = true;
		OK &= !player.isAlive();
		
		return OK;
	}
	
	///////////////////////////////////////////////////////////////
	
	// ADD YOUR TEST ROUTINES HERE  and call them from the main method.
	
	public static boolean testSleepGas() 
	{
		/* Test the functionality of the sleeping gas trap
		 * -create a game with 1 NPC and 1 sleeping gas trap
		 * -force the NPC to walk into the sleeping gas trap
		 * -after the turn, the NPC should be sleeping
		 * -check the location of the NPC
		 * -check the status of the NPC after the turn
		 * -check the sleeping status of the NPC*/
		
		System.out.println("^^^^^^^^^^^^^^ BEGIN MY OWN TESTING 4 ^^^^^^^^^^^^^^" + '\n');
		
		// set up the test maze
		Game testGame = new Game(Maze.K3());
		
		// create an NPC test subject
		NPC player1 = new NPC("NPC-the test subject");
		
		// create a sleeping gas trap
		Trap sleepingGas = new SleepGas();
		
		// add the NPC to the maze
		testGame.addInhabitant(player1);
		
		//add the trap to the maze
		testGame.addTrap(sleepingGas);  
		
		// force the NPC to walk into the sleeping gas trap
		player1.getLocation().setSingleExit(sleepingGas.getLocation());
		
		// print out the maze
		System.out.println(testGame.getMaze());
		
		// print out the warning message
		System.out.println(sleepingGas.warningMessage());
		
		// run the game for a turn
		player1.takeATurn();
		
		// if the NPC is still alive, the location equals to the trap and
		// is sleeping then we know that the trap works according to the 
		// document description
		if(player1.isAlive() && 
		   player1.getLocation().equals(sleepingGas.getLocation()) && 
		   !player1.isAwake()) 
		{
			System.out.println("=== SleepGas test is: PASS.");
			
			// if everything runs smoothly at this point,
			// it should return a true result
			return true;
		}
		
			return false;
	}
	
	
	
	
	
	////////////////////////////////////////////////////////////////
	public static void main(String[] args) {

		System.out.println("=== BEGIN TEST DRIVER ===");
		
		boolean OK = testNPC();
		System.out.println("=== NPC TEST:  " + (OK?"PASSED":"FAILED") + "\n\n");
		
		OK = testBottomlessPit();
		System.out.println("=== BottomlessPit TEST:  " + (OK?"PASSED":"FAILED") + "\n\n");
		
		// ADD CALLS TO YOUR OWN TEST ROUTINES HERE
		
		TestsProgram.testSleepGas();
		
	}
}
