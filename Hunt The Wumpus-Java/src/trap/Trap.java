package trap;

import core.Cave;
import core.Game;
import core.PlayObject;
import creature.Inhabitant;

/**
 *  This is the root class of the trap subtree.
 *  
 *  Some methods/attributes are inherited from PlayObject.
 * Most notably the Game object "game".  This provides access to a Game
 * and its Maze and Caves.
 * 
 * All of your Trap subclasses must supply definitions for all of the 
 * methods listed in this class - either directly, or through inheritance from
 * classes above.  You MIGHT want to edit this file to change some of the abstract
 * methods into concrete methods (i.e. supply code at this level).
 * 
 */
abstract public class Trap extends PlayObject {

	/**
	 * construct a new Trap
	 */
	public Trap(){
	}
	
	/**
	 * Determine the Cave in which the Inhabitant currently resides.
	 * This will be a Cave object, or null if the Inhabitant has no location / is dead.
	 * @return the Inhabitant's location (Cave)
	 */
	public Cave getLocation() { 
		return game.getMaze().findCave(this);
	}
	
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
		boolean OK = theCave.setTrap(this, theGame);  // Cave will do necessary checks.
		if (OK) game = theGame;
		return OK;
	}

	/**
	 * spring the Trap on an Inhabitant and determine if the Trap succeeds.
	 * The Inhabitant must be in the same Cave (location) as the Trap.
	 * The trigger message of the Trap must be displayed.
	 * The Trap may fail if the Inhabitant is immune to it, or for any Trap-specific reasons.
	 * @param ihab  The Inhabitant springing the Trap
	 * @param loc  The Cave in which the Trap is sprung
	 * @return  true is the Trap succeeds, false if the Trap fails.
	 */
	abstract public boolean springTrap(Inhabitant ihab);
	

	/* (non-Javadoc)
	 * @see game.GameObject#warningMessage()
	 */
	abstract public String warningMessage();
	
	
	/**
	 * Message to be printed when the Trap is triggered by an Inhabitant.
	 * Message depends upon type of Trap but will usually include the Inhabitant's name.
	 * @param name  Inhabitant's name
	 * @return the trigger message.
	 */
	abstract protected String triggerMessage(String name);
	
}
