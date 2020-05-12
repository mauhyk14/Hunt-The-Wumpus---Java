package creature;

import core.Cave;
import core.Game;
import core.PlayObject;
import creature.Inhabitant;
import testGames.NPCgame;
import trap.SuperBat;
import trap.Trap;


/**
 * This is the root class of the Inhabitant/creature subtree.
 * 
 * Some methods/attributes are inherited from PlayObject.
 * Most notably the Game object "game".  This provides access to a Game
 * and its Maze and Caves.
 * 
 * All of your Inhabitant subclasses must supply definitions for all of the 
 * methods listed in this class - either directly, or through inheritance from
 * classes above.  You will want to edit this file to change some of the abstract
 * methods into concrete methods (i.e. supply code at this level).
 *
 */
public abstract class Inhabitant extends PlayObject 
{
    		
	// inherited:  game, setLocation(G,cave/ID)
	
	/**
	 * the name of this Inhabitant
	 */
	protected String name;
	
	/**
	 * @return the Inhabitant's name
	 */
	public String getName() { 
		return name;
	}

	/**
	 * Create an Inhabitant and name it.
	 * @param nom  the name.
	 */
	public Inhabitant(String nom) {
		name = nom;
	}
	
	/**
	 * Determine the Cave in which the Inhabitant currently resides.
	 * This will be a Cave object, or null if the Inhabitant has no location / is dead.
	 * @return the Inhabitant's location (Cave)
	 */
	public Cave getLocation() { 
		return game.getMaze().findCave(this);
	}
	
	// abstract definition from PlayObject supplied here:
	/**
	 * Set the location of this Inhabitant to be the Cave with given ID,
	 * and set the local game instance variable to the supplied Game.
	 * The operation fails if there is no such Cave in the supplied Game.
	 * The operation will also fail if the Game is not in setup mode.
	 * @param theGame  Game object
	 * @param caveID   Cave object
	 * @return true on success
	 */
	public boolean setLocation(Game theGame, Cave theCave){
		
		// the Cave will do the necessary checking
		boolean OK = theCave.setOccupant(this, theGame);  
		if (OK) game = theGame;
		return OK;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = "";
		str += name;
		Cave location = getLocation();
		if (location!=null)
			str += "  in cave " + location.getID();
		return str;
	}
	
	
	// full health of the Inhabitant
	protected int fullHealth = 3; 
	
	/**
	 * @return number of damage points remaining
	 */
	public int getDamagePoints()
	{
		/* This method will return the 
		 * current health of the creature 
		 * object by calling the 
		 * fullHealth variable.*/		
		return this.fullHealth;
	}

	/**
	 * whether or not this Inhabitant is alive.
	 * @return true if alive, false if dead.
	 */
	public boolean isAlive()
	{
		
		/* If the creature's fullHealth
		 * variable is more than 0,
		 * we know that the creature is
		 * alive. Otherwise, it is dead.*/		
		if(this.fullHealth <= 0) 
		{		
			return false;
		}
		
		else 
		{
			return true;
		}
	}
	
	/**
	 * Kill this Inhabitant immediately and remove it from its location.
	 * After this method completes, isAlive() should return FALSE
	 * and getLocation() should return NULL.
	 */
	public void putToDeath() 
	{
		/* Killing the creature immediately by making the
		 * fullHealth variable equals to 0.*/		
		this.fullHealth = 0;		
	}
	
	/**
	 * Remove a damage point from this Inhabitant.
	 * If this causes the damage points to become zero then print the message 
	 * 	"<name> succumbs to battle wounds" and kill the Inhabitant immediately.
	 * otherwise print "<name> takes damage, points now <damage points>".
	 * @return true if Inhabitant is still alive at completion.  False if Inhabitant is dead.
	 */
	public boolean takeDamage()
	{
		this.fullHealth = this.fullHealth - 1;
		
		/* After taking 1 damage point from this creature,
		 * this method will check whether this creature still has 
		 * more than 1 health point. If it is true, it means the 
		 * creature is alive and returns a true result. 
		 * Otherwise, it will return a false result.*/		
		if(isAlive()) 
		{
			System.out.println(this.name + " takes damage, points now " + 
						       this.fullHealth);
			
			return true;
		}
		
		else 
		{
			System.out.println(this.name + " succumbs to battle wounds.");
			
			return false;
		}
		
	}

		
	/**
	 * whether or not the Inhabitant is awake.
	 * @return true if awake, false if asleep.
	 */
	protected int sleepTurns = 0;
	
	public boolean isAwake() 
	{
		/* I create a sleepTurns variable here
		 * because I want to keep track of the 
		 * number of sleep turns the creature has.
		 * SleepTurns of != 0 indicates the creature
		 * is still sleeping. Otherwise, it
		 * is awake.*/		
		if (sleepTurns != 0) 
		{
			return false;
		}
		
		else 
		{
			return true;
		}
		
	}

	/**
	 * Wake the Inhabitant immediately, regardless of how many turns of sleep are left.
	 * Print the message
	 * 	"<name> is jolted awake."
	 */
	protected void wakeUpNow() 
	{
		/* Waking the creature immediately by making
		 * the creature's sleepTurns equals to 0.
		 * (no more sleepTurns.)*/		
		this.sleepTurns = 0;
		
		System.out.println(this.name + " is jolted awake.");
	}
	
	/**
	 * Put Inhabitant to sleep for a fixed number of turns.
	 * @param nturn # turns to sleep for.
	 */
	public void goToSleep(int nturn) 
	{
		this.sleepTurns = nturn;
	}
	
	/**
	 * Put Inhabitant to sleep forever (or until woken).
	 */
	public void goToSleep() 
	{
		this.sleepTurns = -1;
	}
	
	//  //  //  //
	// warning functionality
	// warningMessage is abstract and inherited from PlayObject.
	// You _might_ want to define an implementation here ;)
	
	public String warningMessage() 
	{
		/* The game default warning message.*/		
		return "You feel a presence.";
	}
	
	//  //  //  //  
	// move-location functionality
	
	/**
	 * Attempt a move to the specified Cave.
	 * If the specified Cave is null then the move fails.
	 * The move is deemed successful if the Inhabitant moves to ANY Cave,
	 * whether it be the desired Cave or a different one (as might happen when
	 * relocated by a SuperBat).
	 * 
	 * If the Cave is not null then print the message 
	 * 	"<name> attempts move to Cave #<id>"
	 * The move is attempted by requesting occupancy of the Cave.
	 * If the request succeeds, print 
	 *  "<name> moved, ended up in cave <id>"
	 * If the request failed and Inhabitant this is still alive then print
	 * 	"<name> failed to move."
	 * If the Inhabitant died in the attempt, do nothing more.
	 * @param dest the Cave to which a move is attempted
	 * @return true if Inhabitant moved, false if remained in same Cave.
	 */
	public boolean moveToNewCave(Cave dest) 
	{
		/* If the destination cave is null, it will return a false result.
		 * else, it will print out the appropriate messages according to
		 * the requestOccupancy method's outcomes.*/		
		if(dest != null) 
		{
			System.out.println(this.name + " attemps move to cave # "+ 
							   dest.getID());
			
			if(dest.requestOccupancy(this)) 
			{
				System.out.println(this.name + " moved, ended up in cave " + 
								   this.getLocation().getID());	
				return true;	
			}
				
			else 
			{
				System.out.println(this.name + " failed to move.");
					
				return false;
			}	
		}
		
		return false;	
	}
	
	/**
	 * choose a Cave at random from the neighbours of the Inhabitant's location,
	 * and attempt a move to it.
	 * If this succeeds then this.getLocation() will be the newly occupied Cave
	 * and this.isAlive() will be true (i.e. this didn't die)
	 * @return true if move was successful 
	 */
	public boolean moveToRandomExit() 
	{
		/* This method will generate a random neighbouring exit and 
		 * store it in the randomExit variable.*/		
		Cave randomExit = this.getLocation().getRandomExit();	
		
		/* Moving the creature by calling the moveToNewCave method. 
		 * If the move is successful (assuming the creature is still 
		 * alive and the randomExit is existed), it will set the current 
		 * location of the creature at the randomExit.*/
		if( this.moveToNewCave(randomExit)
		    && this.isAlive()) 
		{
			return true;
		}
		
		else 
		{
			return false;
		}
	}
	
	/**
	 * Attempt a move to a random Cave chosen from the neighbours of the parameter Cave source.
	 * If this succeeds then this.getLocation() will be the newly occupied Cave
	 * and this.isAlive() will be true (i.e. this didn't die).
	 * @param source  
	 * @return
	 */
	public boolean moveToRandomExit(Cave source) 
	{
		/* If the move is successful (assuming the creature is alive), 
		 * it will set the current location as source and clear out the 
		 * previous location. */		
		if( source.requestOccupancy(this)
		    && this.isAlive()) 
		{
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	
		
	/**
	 * Let Inhabitant take his/her turn.
	 * This might involve any of:  attempt a move, throw a spear, spend turn asleep.
	 * Dead Inhabitants should not do anything.
	 * 
	 * If an Inhabitant is asleep then print 
	 * 	"<name> sleeps peacefully."
	 * unless this is its final turn sleeping, in which case print
	 *  "<name> yawns and stretches..."
	 * 
	 * If the Inhabitant moves or throws a spear then appropriate messages
	 * (described elsewhere) must be printed.
	 * 
	 * SUGGESTION: you might like to decompose this into several component methods,
	 * then call each one in turn / as needed.  This will allow you to override 
	 * individual components in subclasses as required.
	 * 
	 */
	
	protected boolean firsttime =true;
	
	public void takeATurn() 
	{
		/* A system generated creature who moves on the first round then 
		 * stays in the cave.*/		
		if(firsttime) 
		{
			this.moveToRandomExit();
			
			firsttime = false;
		}
		
		/* The following method will execute only if the creature goes
		 * to sleep. It will continuously print out "..sleeps peacefully."
		 * until the creature gets disturbed or in the final round.*/		
		if(this.isAlive() && !this.isAwake()) 
		{
	
			if(this.sleepTurns == 1) 
			{
				System.out.println(this.getName() + " yawns and stretches...");
				
				this.sleepTurns = 0;
			}
		
			else if(this.sleepTurns != 0) 
			{
				this.sleepTurns--;
				
				System.out.println(this.getName() + " sleeps peacefully.");	
			}
		}
		
	}
	
	/**
	 * How this Inhabitant reacts when another creature attempts to move into its Cave.
	 * What happens depends very much upon the type of Inhabitant.
	 * At the end of this method, any of the following is possible:
	 * - the inhabitant chooses to remain, and is still alive (return true)
	 * - the inhabitant dies (return false)
	 * - the inhabitant chooses to flee the cave (return false)          
	 * NOTE that fleeing the Cave should not be done in this method.
	 *      Simply return false, and the Cave will move the Inhabitant.
	 * @param intruder other Inhabitant attempting to displace this Inhabitant from its current location (Cave).
	 * @return true if this Inhabitant is alive and chooses to remain in its Cave, false if this Inhabitant either 
	 * 		   dies or chooses to move.
	 */
	public boolean handleIntruder(Inhabitant intruder) 
	{
		
		if(this.isAlive())   
		{
			/* By default, the method will take one damage point from the defender.
			 * After calling the takeDamage() method, it will determine whether 
			 * this creature is still alive or dead and return the result.*/			
			boolean afterTakedamage = this.takeDamage();        
			
			return afterTakedamage;
		}
		
		else 
		{
			return false;
		}
	}

	/**
	 * This method is called when the Inhabitant enters a Cave, to affect any other Inhabitant which is
	 * resident in that Cave.  In general this will wake the resident; further effects depend upon 
	 * the actual type of this Inhabitant.
	 * NOTE this method should not remove resident from the Cave;
	 * Movement is organised by the Cave, based on return values of this method and handleIntruder().
	 * @param resident  the other Inhabitant whose location is the Cave to which this Inhabitant wants entry.
	 * @return true if resident is still alive and able to handle the intrusion, false otherwise.
	 */
	public boolean disturbResident(Inhabitant resident) 
	{
		/* If the creature is alive, calling this method will wake up 
		 * the resided resident and display the wake-up message.*/		
		if(resident.isAlive())
		{
			if(!resident.isAwake()) 
			{
				resident.wakeUpNow();                           
			}
			
			return true;
		}
		
		else 
		{
			return false;
		}
	}
	
	
	//  //  //  //  //  //  //  //  
	// Trappy functionality
	
	/**
	 * Whether or not the Inhabitant is immune to a given Trap.
	 * By default Inhabitants will not be immune to any Traps.
	 * @param T  the Trap
	 * @return true if Inhabitant is unaffected by Trap, false otherwise.
	 */
	public boolean hasImmunity(Trap T) 
	{
		/* By default, creatures are not immune to all
		 * traps.*/		
		return false;
	}
	
}
