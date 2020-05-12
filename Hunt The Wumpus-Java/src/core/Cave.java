package core;

import creature.Inhabitant;
import trap.Trap;

// v2 notes:  would want package access over private, then put MAze and Cave in same package

public class Cave {

	/**
	 * limit on number of exits a Cave can have
	 */
	public static final int MAX_NUM_EXIT = 4;

	// array not ideal, but hey
	/**
	 * Data structure to hold all neighbours of this Cave
	 */
	private Cave[] neighbour = new Cave[MAX_NUM_EXIT];

	/**
	 * add an exit (a neighbouring Cave) to this Cave
	 * @param destination the Cave to add
	 * @return true on success
	 */
	boolean addOneWayPath(Cave destination) { // note package access
		if (this.canHaveMoreExits()) {
			// add to first free slot.  Again, cumbersome with arrays.
			for(int i=0; i<MAX_NUM_EXIT; i++)
				if (neighbour[i]==null) {
					neighbour[i] = destination;
					break;
				}
			return true;
		}
		return false;
	}
	/**
	 * add another Cave as an exit to this Cave, and add this as an exit to the other Cave
	 * (so they are mutually linked)
	 * @param destination the Cave to add
	 * @return true on success
	 */
	boolean addTwoWayPath(Cave destination) {
		if (!this.canHaveMoreExits()) return false;
		if (!destination.addOneWayPath(this)) return false;
		return this.addOneWayPath(destination);
	}

	/**
	 * change the exits of a Cave to be one specific other Cave.
	 * This is useful to set up Mazes for testing, so Inhabitants are forced to move in particular way.
	 * 
	 */
	boolean setSingleExit(Cave destination) {
		neighbour[0] = destination;
		for(int i=1; i<MAX_NUM_EXIT; i++) neighbour[i] = null;
		return true;
	}
	
	/**
	 * the number of exits leading from this cave 
	 * @return the number of exits
	 */
	public int numExits() {
		int num = 0;
		for(Cave foo: neighbour) if (foo!=null) num++;
		return num;
	}
	/**
	 * whether or not this Cave can hold more exits
	 * @return true if there is room for more exits
	 */
	private boolean canHaveMoreExits() {
		return numExits()<MAX_NUM_EXIT;
	}

	/**
	 * Find the neighbouring Cave having given ID.
	 * If none of the neighbouring caves has the supplied ID, return null
	 * @param id the ID of the Cave 
	 * @return the Cave, or null
	 */
	public Cave findExit(int id) {
		for(int i=0; i<MAX_NUM_EXIT; i++)
			if ((neighbour[i]!=null) && neighbour[i].getID()==id) return neighbour[i];
		return null;
	}
	/**
	 * Find the neighbouring Cave equal to the one supplied.
	 * Return that cave, or null if the supplied cave is not a neighbour.
	 * @param c the Cave to look for
	 * @return the neighbouring Cave, or null
	 */
	public Cave findExit(Cave c){
		if (c==null) return null;
		return findExit(c.getID());
	}
	/**
	 * Pick one of the Cave's exits at random
	 * @return the selcted Cave
	 */
	public Cave getRandomExit() {
		int nEx = numExits();
		if (nEx==0) return null;
		int which = Prob.getRandom(0,nEx-1);
		return neighbour[which];
	}
	/**
	 * Return all neighbouring Caves as an array 
	 * This can be used to loop over, to process each neighbouring Cave.
	 * @return an array of neighbouring Caves
	 */
	public Cave[] getAllExits() {
		// note this returns a _copy_ of array
		Cave[] allex = new Cave[numExits()];
		int ix = 0;
		for(Cave foo:neighbour){
			if (foo!=null) allex[ix++]=foo;
		}
		return allex;
	}
	
	/**
	 * static counter so each Cave can have a unique ID, even across multiple games.
	 */
	private static int count=0;
	/**
	 * the ID number of this Cave
	 */
	int id;
	/**
	 * get the ID number of this Cave
	 * @return ID number
	 */
	public int getID() { return id; };

	////
	
	/**
	 * construct Cave
	 */
	public Cave(){
		id = count++;
	}
	// dunno if we want the Caves to have a link to Maze...
	// provide ctor accepting MAze, but ignore it if we don't store it.
	//private Maze dungeon;
	/**
	 * Construct Cave.
	 * Nothing is actually done with the supplied Maze.  
	 * @param theMaze  the Maze to which this Cave belongs
	 *
	public Cave(Maze theMaze) {
		this();
	//	dungeon = theMaze;
	}*/

	////
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = "cave #" + id;
		if (numExits()>0) {
			str += "  --> ";
			for (Cave foo: neighbour)
				if (foo!=null)  str += foo.getID() + "  ";
		}
		return str;
	}
	/**
	 * a String detailing this Cave's contents (i.e. occupant and trap) 
	 * @return the contents String
	 */
	public String contentsString(){
		String str="";
		if (this.hasOccupant()){
			str+=  "\t" + this.occupant.getName() + " the " + this.occupant.getClass().getSimpleName() + " is here";
			if (this.occupant.isAwake()) str += ".";
			else str += ", asleep.";
		}

		if (hazard!=null){
			if (this.hasOccupant()) str += "\n";
			str += "\tThere is a " + hazard.getClass().getSimpleName() + " trap.";
		}

		return str;
	}

	/**
	 * concatenation of toString() with contentsString()
	 * @return the full String
	 */
	public String fullString(){
		//return toString() + "\n" + contentsString();
		String cs = contentsString();
		//System.out.println("CS: >" + cs + "<");
		if (cs.length()>0) return toString()+"\n"+cs;
		else return toString();
	}

	/**
	 * return the warning Strings of any objects (Traps or Inhabitants) resident in this Cave.
	 * Could be used by Human.allWarningStrings()
	 * @return String of warning messages
	 */
	public String warningString(){
		String warns = "";
		if (this.hasOccupant()) warns += "    ..." + this.occupant.warningMessage() + "\n";
		if (this.hasTrap()) warns += "   ..." + this.hazard.warningMessage() + "\n";
		return warns;
	}

	//////////////////////////////////////// 
	
	/**
	 * Inhabitant which resides in this cave
	 */
	private Inhabitant occupant;
	
	/**
	 * whether or not this Cave has an occupant
	 * @return true if there is an occupant
	 */
	public boolean hasOccupant(){ 
		// could just return occupant!=null, but I want some sanity checks.
		if (occupant==null) return false;
		if (occupant.isAlive()) return true;
		//System.out.println("Cave.hasOccupant(): found a corpse! FIX THiS");
		occupant = null; 
		return false;
	}
	/**
	 * get the occupant of this Cave, or null if there is no occupant.
	 * @return the occupant
	 */
	public Inhabitant getOccupant(){  // ** NOTE package access so Maze can see
		return occupant;
	}
	/**
	 * If the supplied Game is in setup mode, and this Cave belongs to that Game's Maze,
	 * then set the given Inhabitant as the occupant.
	 * This must  be used only to setup the game, it cannot be called once setup is finished.
	 * @param ihab
	 * @param G
	 * @return true on success, false otherwise.
	 */
	public boolean setOccupant(Inhabitant ihab, Game G){
		// if a Game is supplied then check that it is in setup mode AND this Cave belongs to it.
		if (!canSetObjects(G)) return false;
		occupant = ihab;
		return true;
	}
	/**
	 * Determine if this Cave is allowed to set Traps and Occupants,
	 * Based on association with game G.
	 * The Cave must be in G's Maze, and G must be in setup mode.
	 * @param G  associated Game
	 * @return  true if setup is allowed, false if not.
	 */
	private boolean canSetObjects(Game G){
		// no Game was supplied;  disallow setup?
		// (might conceivably return true in this case?)
		if (G==null) return false;
		// check that this Cave belongs to the Game G
		if (G.getMaze().findCave(this.getID())==null) return false;
		// check that game G is still in setup mode
		if (G.readyToPlay()) return false;
		// OK, we can happily set GameObjects in this Cave
		return true;
	}
	
	/**
	 * Attempt to enter the specified Inhabitant as the occupant of this Cave during gameplay.
	 * 
	 * Upon calling, the incoming Inhabitant's location may be either a Cave or null.
	 *   If its location is null then it can not retreat;  if this move fails it will die.
	 * 
	 * First any existing occupant is dealt with:
	 * - the incoming Inhabitant disturbs the Cave's occupant,
	 * - the occupant then handles the incomer (stays or flees)
	 * If after this the occupant is alive and has not been dislodged, the move fails, and if 
	 *   the incoming Inhabitant has no location then it is put to death.
	 * If however the occupant has moved or died, then the income becomes the new occupant.
	 * 
	 * Second any existing trap is enacted on the (new) occupant.
	 * If, after the trap is applied, the (new) occupant is still alive and has a non-null location
	 * then the move is successful.  Otherwise the move has failed.
	 * 
	 * @param incoming The prospective new inhabitant
	 * @return true if inhabitant successfully moved (to any location, and is still alive)
	 */
	public boolean requestOccupancy(Inhabitant incoming){

		// entrant's original cave  MAYBE THIS IS NULL, in which case no going back
		Cave source = incoming.getLocation();

		// final destination of entrant, which might be this cave, other cave, or null
		//Cave destination = null;
		boolean moved = false;
		
		// handle existing occupant first, otherwise can't enter.
		if (this.hasOccupant()){

			// who stays and who goes?
			if (incoming.disturbResident(this.occupant)&&this.occupant.handleIntruder(incoming)){
				// existing occupant stays.
				// if we have a source then retreat to it - otherwise DIE (unless already dead)
				
				if (incoming.isAlive()){
					
					System.out.print(incoming.getName() + " failed to displace " + this.occupant.getName() + ".");
					if (source!=null ) {
						System.out.println("  Retreating to origin.");
					} else {
						System.out.println("  Can't retreat... SLAIN!");
						incoming.putToDeath();
					}
				} 
			}else{
				// existing occupant is defeated, move it on if it is alive, AFTER entering new occupant.
				Inhabitant former = occupant;
				setNewOccupant(incoming);
				//destination = this;  // commented 4/1/19, see below
				// former's location should now be null;  no retreat!
				// HMMMM, prolly don't need to check alive, should've removed from Cave on death.
				if (former!=null && former.isAlive()) former.moveToRandomExit(this);
				//                                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
				// can override this for Wumpus, to give probability of remaining -> EAT!
				// ... or could use as-is, and do fight/flight based on probability test.
				
				// 4/1/19
				// there is an incredibly slim chance that the displacement of players
				// will result in the death (or displacement) of the newly-installed player
				// before this method returns.  So check final destination again.
				//destination = incoming.isAlive()?incoming.getLocation():null;
				moved = incoming.isAlive();
			}
		} else {
			setNewOccupant(incoming);
			//destination = this;
			moved = true;
		}
		
		// trap to be sprung IF we successfully entered.
		//if (destination==this && this.hasTrap()){
		if (moved==true && this.hasTrap()){
			boolean sprung = this.hazard.springTrap(incoming); //, this);
			if (sprung){
				//destination = incoming.getLocation();
				moved = incoming.isAlive() && (incoming.getLocation()!=null);
			}
		}
		
		//return destination;
		return moved;
	}
	
	/**
	 * Utility function to move Inhabitant out of the Cave it presently exists in (if any),
	 * and then enter it as occupant in this Cave.
	 * @param incoming
	 */
	private void setNewOccupant(Inhabitant incoming){
		Cave loc = incoming.getLocation();
		if (loc!=null) incoming.getLocation().clearOccupant(incoming);
		this.occupant = incoming;
	}

	/**
	 * Remove occupant of this Cave IF it is the same as the supplied Inhabitant,
	 * AND it is dead.
	 * @param ihab
	 * @return
	 */
	public boolean removeDeadOccupant(Inhabitant ihab){
		if (occupant==ihab && !occupant.isAlive()){
			occupant = null;
			return true;
		}
		return false;
	}
	/**
	 * Clear the occupancy slot of this Cave IF it is currently occupied by the supplied Inhabitant.
	 * @param ihab
	 * @return
	 */
	public boolean clearOccupant(Inhabitant ihab){
		if (occupant!=ihab) return false;
		occupant = null;
		return true;
	}
	
		
	/**
	 * the Trap resident in this Cave
	 */
	Trap hazard;

	/**
	 * Set supplied Trap in this Cave IF this Cave is part of the supplied Game's Maze,
	 * AND if the Game is still being set up.
	 * Once the Game has been set up this routine can no longer be called.
	 * @param T the Trap
	 * @param G the Game
	 * @return true on success, false otherwise
	 */
	public boolean setTrap(Trap T, Game G){
		// if a Game is supplied then check that it is in setup mode AND this Cave belongs to it.
		if (!canSetObjects(G)) return false;
		hazard = T;
		return true;
	}
	
	/**
	 * Determine if this Cave has a Trap
	 * @return true if there is a Trap, false otherwise.
	 */
	public boolean hasTrap(){ return hazard!=null; }
	
	/**
	 * Return the Trap present in this Cave.
	 * @return the Trap, or null if there is none.
	 */
	public Trap getTrap(){ return hazard; }
	
//	/**
//	 *  14/1/19
//	 * a front-end to setTrap and setOccupant which takes GameObject parameter.
//	 * @param GO	A GameObject, either a Trap or an Inhabitant.
//	 * @param G		the Game.
//	 * @return
//	 */
//	// 
//	public boolean setGameObject(GameObject GO, Game G){
//		// need to dynamically resolve GO type - using instanceof for now.
//		// (there are "better" ways)
//		
//		//System.out.println("setGO " + GO.getClass().getSimpleName());
//		if (GO instanceof Trap) return setTrap((Trap)GO, G);
//		else return setOccupant((Inhabitant)GO, G);
//	}

}
