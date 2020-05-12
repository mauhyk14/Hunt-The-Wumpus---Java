package creature;

import core.Cave;
import core.Prob;
import trap.BottomlessPit;
import trap.SuperBat;
import trap.Trap;

public class UberWumpus extends Wumpus 
{

	public UberWumpus(String nom) 
	{
		super(nom);
	}
	
	
	@Override	
	public boolean handleIntruder(Inhabitant intruder) 
	{
		/* The UberWumpus acts similar to the Wumpus
		 * except it has a probability that it will 
		 * fleet the cave if this condition is met.*/		
		int n = this.getLocation().numExits();
			
		if(Prob.testProb((int)((double)(n)/(double)(n+1)*100))) 
		{
			Cave randomLocation = this.getLocation().getRandomExit();
			
			this.moveToNewCave(randomLocation);
			
			return false;
		}
		
		else 
		{
			System.out.println(intruder.name + " stmbles upon " + 
							   this.name + ", who is promptly EATEN.");
			
			intruder.putToDeath();       
			
			return true;
		}
		
	}
	
	
	@Override
	public boolean hasImmunity(Trap T) 
	{
		/* A UberWumper is only immune to SuperBat traps and
		 * BottomlessPit traps. Therefore, only these two traps
		 * return true results.*/		
		if( T.getClass() == SuperBat.class || 
		    T.getClass() == BottomlessPit.class) 
		{
			return true;
		}

		else 
		{
			return false;
		}
	}
	
	
	
	

}
