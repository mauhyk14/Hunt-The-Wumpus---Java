/**
 * 
 */
package creature;

import core.Cave;
import trap.Trap;

/**
 * This is an example of a player class for the Wumpus game, derived by extending Inhabitant class.
 * It provides all functionality specified by the Inhabitant class
 *
 * THIS IS NOT AN EXAMPLE OF THE RIGHT WAY TO GO ABOUT THINGS!
 * 
 * Your own classes will derive from Inhabitant, but should only provide functionality specific to themselves.
 * General functionality, common to all (or most) creatures, should be defined in the Inhabitant class and 
 * inherited by your subclasses.
 * 
 * The following members defined in GameThing/GameObject are inherited and available here:
 * 		game
 * 		setLocation()  {2 versions} - do not change this!
 * The following member in GameThing/GameObject is abstract and must be defined in a subclass:
 * 		warningMessage()
 * 
 * The following methods are defined in Inhabitant and will be visible in the subclasses.
 * Your subclasses MUST have all of this functionality - either by defining locally or by inheritance.
 * 		getName() getLocation() toString() - these are all supplied, don't change them
 * 		boolean isAlive()
 * 		void putToDeath()
 * 		boolean takeDamage()
 * 		boolean isAwake()
 * 		void wakeUpNow()
 * 		void goToSleep() - 2 versions
 * 		boolean moveToNewCave()
 * 		boolean moveToRandomExit()  - 2 versions
 * 		void takeATurn()
 * 		boolean handleIntruder()
 * 		boolean disturbResident()
 * 		boolean hasImmunity()
 * 
 * @author sjs
 *
 */
public class ExampleCreature extends Inhabitant {

	/**
	 * @param nom
	 */
	public ExampleCreature(String nom) {
		super(nom);
	}

	// alive/dead/damage functionality
	// this creature is alive until told otherwise, takes no damage.
	// use a boolean 
	boolean life = true;
	// now write functionality around this
	public boolean isAlive(){
		return life;
	}
	public void putToDeath(){
		life = false;
	}
	public boolean takeDamage(){
		return life;
	}
	//
	public int getDamagePoints() {
		return 3;  // this creature never loses damage, always has 3 points
	}
	
	// waking functionality - this creature doesn't sleep
	public boolean isAwake(){
		return true;
	}
	protected void wakeUpNow(){}
	public void goToSleep(){}
	public void goToSleep(int x){}
	
	// movement functionality - this very simple, not performing checks, print only simple info
	// (note that the definition requires specific info to be printed)
	public boolean moveToNewCave(Cave dest){
		boolean OK = dest.requestOccupancy(this);
		System.out.println(this.getName() + (OK?" moved.":" didn't move."));
		return OK;
	}
	public boolean moveToRandomExit(){
		 Cave source = getLocation(); //dungeon.findCave(this);
 		 return moveToNewCave(source.getRandomExit());
	}
	public boolean moveToRandomExit(Cave source){
		throw new RuntimeException("not coded this yet");
	}
	
	// take a turn.  Simple creature just moves.
	public void takeATurn(){
		boolean OK = moveToRandomExit();
	}
	
	// what to do when invaded.  This creature always stays, takes no damage.
	public boolean handleIntruder(Inhabitant other){
		return true;  // return isAlive() might be safer
	}
	// how to disturb resident when invading.  This creature does nothing.
	public boolean disturbResident(Inhabitant other){
		other.wakeUpNow();
		return true;
	}
	
	// always immune to Traps
	public boolean hasImmunity(Trap T){
		return true;
	}
	

	@Override
	public String warningMessage() {
		return null;  // this'll never be used in example;  no human players.
	}


}
