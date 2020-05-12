package core;

//import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Tools for generation of random integers, dice rolls, 
 * and evaluating binary random variables (i.e. boolean) with given probability
 *  
 * To generate a random number between values i and j, call:
 * 	Prob.getRandom(i,j);
 * 
 * To generate a boolean value which has X% chance of being true,
 * 	Prob.testProb(X);
 * where 0 <= X <= 100.
 * 
 * To generate the sum of rolling N dice of S sides,
 * 	Prob.getDiceRoll(N,S);
 * or
 *  Prob.getDiceRoll("NdS");
 *  
 * FOR CONTROL OVER THE RANDOM NUMBERS, for testing and debugging, use setSeed().
 * 
 * Prob.setSeed(long) sets the seed of the random number generator to the supplied
 * 		long-integer value.  Calling setSeed(long) with the same value will cause
 * 		the RNG to produce the same sequence of random numbers each time.
 * 
 * Prob.setSeed(double[]) will use the supplied array of doubles in place of the RNG.
 * 		The doubles are used in sequence, and when the end of the array is reached
 * 		the sequence is resumed from the beginning.
 * 
 * Prob.setSeed(boolean) will allow complete user control.  Instead of using the RNG,
 * 		the user is prompted to enter the output of getDiceRoll() and getRandom().
 * 
 *   
 * @author searlesj
 *
 */
public class Prob {

	/**
	 * the Random Number Generator.
	 */
	static private Random RNG = new Random();
	//
	static private double[] sequence = null;
	static private int ix = 0;
	//
	static private boolean ask = false;
	private static Scanner scin = null;

	/**
	 * Return index of seed sequence, of -1 if sequence not presently used.
	 * @return index
	 */
	public int getIx(){
		if (sequence==null)
			return -1;
		else
			return ix;
	}

	/**
	 * set the seed to the RNG so the same sequence is generated each time
	 * @param arg
	 */
	public static void setSeed(long arg){
		Prob.setSeed();
		RNG.setSeed(arg);
	}
	/**
	 * setup RNG with arbitrary seed
	 */
	public static void setSeed(){
		if (RNG==null) RNG = new Random();
		sequence = null;
		if (scin!=null) {scin.close(); scin=null;}
	}
	/** 
	 * seed the RNG with a sequence
	 */
	public static void setSeed(double[] seq){
		sequence = seq.clone();
		ix = 0;  // 12/6/17
		RNG = null;
		if (scin!=null) {scin.close(); scin=null;}
	}
	/**
	 * set RNG to be user supplied   13/6/17
	 */
	public static void setSeed(boolean promptUser){
		if (promptUser){
			sequence = null;
			ask = true;
			scin = new Scanner(System.in);
		} else {
			setSeed(); // this also handles scin & sequence
			ask = false;
		}
	}

	/**
	 * Realise an integer from a uniform distribution on a specified interval,
	 * or in english:  pick a number between min and max
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandom(int min, int max){
		int randomNum;
		if (ask) {
			// prompt user 
			printCaller();
			System.out.print("Enter int on (" + min + "," + max + "): ");
			//System.in.read(new byte[System.in.available()]);
			randomNum = scin.nextInt();
		} else if (sequence!=null){
			//System.out.println("** ix " + ix + " len " + sequence.length);
			randomNum = (int) (sequence[ix]*(max-min)+min);
			ix = (ix+1)%sequence.length;
		} else {
			randomNum = RNG.nextInt((max - min) + 1) + min;
		}
		return randomNum;
	}
	private static void printCaller(){
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		StackTraceElement s=null;
		// find first stack element not belonging to Thread or Prob
		for(StackTraceElement se: elements){
			String classname = se.getClassName();
			if ( !((classname.contains("Thread")) || classname.contains("Prob")) ){  // *** note hardwired class names
				s = se;
				break;
			}
		}
		if (s!=null)
			System.out.print("\tat " + s.getClassName() + "." + s.getMethodName()
			+ "(" + s.getFileName() + ":" + s.getLineNumber() + "): ");
		else System.out.print("\tProb: ");
	}
	/**
	 * Generate a binary value (true or false), with a specified probability
	 * of it being true.
	 * @param prob the probability of truth, specified as a percentage (on 0-100).
	 * @return
	 */
	public static boolean testProb(int prob){
		return( getRandom(0,100) <= prob);
	}


	// /  /  /  /  /  /  /  /  
	// these untested, 14/2/18
	public static int getDiceRoll(String diceStr){
		String[] nums = diceStr.split("d");
		//System.out.println(nums[0] + "," + nums[1]);
		int nd = Integer.parseInt(nums[0]);
		int ns = Integer.parseInt(nums[1]);
		return getDiceRoll(nd,ns);
	}
	public static int getDiceRoll(int ndice, int nsides){
		int randomNum=0;
		if (ask){
			// prompt user
			printCaller();
			System.out.println("");
			System.out.print("\tEnter int satisfying " + ndice + "d" + nsides + ": ");
			randomNum = scin.nextInt();
		} else if (sequence!=null){
			for(int d=0; d<ndice; d++){
				randomNum = (int) (sequence[ix]*(nsides-1)+1);
				ix = (ix+1)%sequence.length;
			}
		} else {
			for(int i=0; i<ndice; i++)
				randomNum = RNG.nextInt(nsides) + 1;
		}
		return randomNum;
	}
	
	//////////////////////////////////////////////////////////////////////

	// test routine
	/**
	 * @param args
	 */
	public static void main(String[] args){

		if (true){  // make this true to test sequence seeding
			setSeed(new double[]{0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9});
		}

		int low = 999, high = -1;
		for(int i=0; i<100; i++){
			int rr = getRandom(33,50);
			System.out.print(rr + " ");
			if (rr<low) low=rr;
			if (rr>high) high=rr;	
		}
		int N = 10000;
		int count = 0;

		if (false){  // make true to restore Random RNG
			setSeed();
		}

		for(int i=0; i<N; i++){
			if (testProb(70)) count++;
		}

		System.out.println("END\n low " + low + " high " + high + "   prob " + 100*count/N);
	}



}
