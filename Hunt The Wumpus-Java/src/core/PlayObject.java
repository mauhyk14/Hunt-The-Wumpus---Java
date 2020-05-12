package core;

public abstract class PlayObject {

	/**
	 * This holds a reference to the Game object to which this GameThing belongs.
	 * The GameThing (Inhabitant, Trap) can thus access the Game's public methods and data.
	 */
	protected Game game=null;
	

	/**
	 * Set the location of this GameThing to be the Cave with given ID,
	 * and set the local game instance variable to the supplied Game.
	 * The operation fails if there is no such Cave in the supplied Game.
	 * The operation will also fail if the Game is not in setup mode.
	 * @param theGame  Game object
	 * @param caveID   Cave object
	 * @return true on success
	 */
//	public boolean setLocation(Game theGame, Cave theCave){
//		boolean OK = theCave.setGameObject(this, theGame);  // Cave will do necessary checks.
//		if (OK) game = theGame;
//		return OK;
//	}
//	
	/**
	 * The warning message given to Human players prior to their move, 
	 * if this Inhabitant is in a neighbouring Cave.
	 * eg "you sense a nearby presence"
	 * @return  the warning message
	 */
	abstract public String warningMessage();
	
	// ********************
	
	// the following declared abstract here but will have full definitions
	// in Trap and Inhabitant.  Students will not be required to write them.
	
	abstract public Cave getLocation();

	abstract public boolean setLocation(Game theGame, Cave theCave);

	/**
	 * Set the location of this GameThing to be the Cave with given ID,
	 * and set the local game instance variable to the supplied Game.
	 * The operation fails if there is no such Cave in the supplied Game.
	 * The operation will also fail if the Game is not in setup mode.
	 * @param theGame  Game object
	 * @param caveID  ID of Cave
	 * @return true on success
	 */
	public boolean setLocation(Game theGame, int caveID){
		Cave theCave = theGame.getMaze().findCave(caveID); //theGame.getCaveByID(caveID);
		if (theCave==null) return false;
		return setLocation(theGame, theCave);
	}	
	
}
