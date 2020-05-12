package core;

import creature.Inhabitant;
import trap.Trap;

public class Maze {

	/**
	 * Data structure to hold all the Caves in this Maze
	 */
	private Cave[] room;

	/**
	 * number of Caves in this Maze
	 * @return number of Caves
	 */
	public int nRooms(){
		if (room==null) return 0;
		return room.length;
	}
	
	//  //  //  //  //  //  //  //
	
	/**
	 * Maze constructor.  Private, so cannot create Mazes directly.
	 * Use one of the static methods instead.
	 */
	private Maze() {
	}
	private Maze(int nCave){
		room = new Cave[nCave];
		for(int i=0; i<nCave; i++)
			room[i] = new Cave();
	}
	/**
	 * Create a hypercube Maze of given dimension (up to MAX_NUM_EXIT).
	 * Not to be called directly;  use cube, square or tesseract.
	 * @param dim dimension, also number of exits in each Cave.
	 * @return the Maze
	 */
	private static Maze hyperCube(int dim){
		if (dim>Cave.MAX_NUM_EXIT){
			throw new RuntimeException("dimension too large");
		}
		Maze dungeon = new Maze();
		int nroom = (1<<dim);
		System.out.println("NROOM = " + nroom);
		dungeon.room = new Cave[nroom];
		for(int i=0; i<nroom; i++) dungeon.room[i] = new Cave(); //(dungeon);
		for(int i=0; i<nroom; i++) {
			for (int bit=0; bit<dim; bit++) {
				int mask = 1<<bit;
				int ix = i^mask;
				dungeon.room[i].addOneWayPath(dungeon.room[ix]);
			}
		}
		return dungeon;
	}
	//
	/**
	 * A square Maze of 4 Caves, 2 exits each
	 * @return the Maze
	 */
	public static Maze square(){
		return hyperCube(2);
	}
	/**
	 * A cubic Maze of 8 Caves, 3 exits per Cave.
	 * @return
	 */
	public static Maze cube(){
		return hyperCube(3);
	}
	/**
	 * A tesseract maze of 16 rooms, 4 exits each.
	 * @return
	 */
	public static Maze tesseract(){
		return hyperCube(4);
	}
	//
	/**
	 * The original 20-room, 3-exit dodecahedral Maze.
	 * @return
	 */
	public static Maze dodecahedron(){
		final int[][] exits = {
//				{2,3,4},{1,3,10},{2,4,12},{3,5,14},{1,4,6},{5,7,15},{6,8,17},{1,7,9},
//				{8,10,18},{2,9,11},{10,12,19},{3,11,13},{12,14,20},{4,13,15},{6,14,16},
//				{15,17,20},{7,16,18},{9,17,19},{11,18,20},{13,16,19}
// that's wrong!  Try this:
				{2,5,16},{1,3,15},{2,4,13},{3,5,8},{1,4,6},{5,7,18},{6,8,9},{4,7,11},
				{7,10,19},{9,11,12},{8,10,13},{10,14,20},{3,11,14},{12,13,15},{2,14,17},
				{1,17,18},{15,16,20},{6,16,19},{9,18,20},{12,17,19}
		};
		Maze dungeon = new Maze(exits.length);
		//
		// using array indices rather than Cave IDs since IDs are not invariant.
		for(int i=0; i<exits.length; i++){
			int[] exrow= exits[i];
			Cave thisCave = dungeon.room[i];
			for(int j=0; j<exrow.length; j++){
				Cave thatCave = dungeon.room[exrow[j]-1];
				thisCave.addOneWayPath(thatCave);
			}
		}
		return dungeon;
	}
	
	// a complete graph:  Each Cave is linked to every other Cave.
	// (necessarily can have only 5 nodes max, given constraint on max # exits per cave)
	public static Maze completeGraph(int nnode){
		//System.out.println("nnode " + nnode + "  MAX " + Cave.MAX_NUM_EXIT);
		if (nnode>Cave.MAX_NUM_EXIT+1)
			throw new RuntimeException("too big, must use smaller nnode");
		Maze K = new Maze(nnode);
		for(int i=0; i<K.nRooms(); i++){
			for(int j=i+1; j<K.nRooms(); j++){
				K.room[i].addTwoWayPath(K.room[j]);
			}
		}
		return K;		
	}
	public static Maze K5(){
		return completeGraph(5);
	}
	public static Maze K4(){
		return completeGraph(4);
	}
	public static Maze K3(){
		return completeGraph(3);
	}
	
	public static Maze loop(int nnode, boolean twoway){
		if (nnode<2)
			throw new RuntimeException("need at least 2 Caves!");
		Maze M = new Maze(nnode);
		for(int i=0; i<M.nRooms(); i++){
			if (twoway)
				M.room[i].addTwoWayPath(M.room[(i+1)%M.nRooms()]);
			else
				M.room[i].addOneWayPath(M.room[(i+1)%M.nRooms()]);
			//M.room[i]
		}
		return M;
	}
	//  //  //  //  //  //  //
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		String str = "";
		for(int i=0; i<room.length; i++) str += room[i].fullString() + "\n";  // or fullContents, etc
		return str;
	}
	
	////////////////////////////////////// 
	
	// again array not the best, but we hide the implementation behind interface
	/**
	 * find Cave having given ID in this Maze
	 * @param id ID of the Cave to search for
	 * @return the Cave, or null if none found with given ID.
	 */
	public Cave findCave(int id){
		for (int i=0; i<room.length; i++) 
			if (room[i].getID()==id) return room[i];
		return null;
	}
	
	/**
	 * find Cave which is occupied by given Inhabitant
	 * @param ihab the Inhabitant to look for
	 * @return the Cave, or null if Cave with Inhabitant not found.
	 */
	public Cave findCave(Inhabitant ihab){
		for(int i=0; i<room.length; i++)
			if (room[i].hasOccupant() && room[i].getOccupant()==ihab) return room[i];
			//                                                ^^ note not equals()
		return null;
	}
	
	/** find Cave which contains given Trap
	 * @param hazard the Trap to look for
	 * @return the Cave, or null if no Cave with this Trap was found.
	 */
	public Cave findCave(Trap hazard){
		for(int i=0; i<room.length; i++)
			if (room[i].hasTrap() && room[i].getTrap()==hazard) return room[i];
		return null;
	}
	
	/**
	 * pick one of the Caves in this Maze at random.
	 * @return the Cave
	 */
	public Cave pickRandomCave(){
		int ix = Prob.getRandom(0, room.length-1);
		return room[ix];
	}
	/**
	 * pick one of the Caves in this Maze at random, except for the specified Cave.
	 * @param notThisOne
	 * @return the Cave  (or null in the VERY unlikely event this fails)
	 */
	public Cave pickRandomCave(Cave notThisOne){
		Cave randomCave = notThisOne;
		int tries = 0;
		while(randomCave==notThisOne && tries++<20)
			randomCave = pickRandomCave();
		return randomCave;
	}
	
	//  //  //  //  //  //  //  //  //  
	
//	/**
//	 * the Game object to which this Maze belongs.
//	 * Might not be used by anything.
//	 */
//	private Game theGame = null;  // UNUSED?
//	/**
//	 * associate this Maze with specified Game.
//	 * This will likely never be used;  not sure why I leave it in the code...
//	 * @param G
//	 * @return
//	 */
//	boolean associateWithGame(Game G){   // note package access
//		if (G!=null)
//			if (G.readyToPlay()) return false;
//		theGame = G;
//		// make sure we have a pristine Cave network
//		for(Cave r: room){
//			r.setOccupant(null,G);
//			r.setTrap(null, G);
//		}
//		return true;
//	}
	
	/////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {

		Maze labyrinth;
		labyrinth = Maze.cube();
		//labyrinth = Maze.dodecahedron();
		System.out.println(labyrinth);
		
		Cave foo = labyrinth.findCave(3);
		System.out.println("----\n" + foo);
		foo = labyrinth.findCave(999);
		if (foo!=null) System.out.println("should never see this message");
	}

}
