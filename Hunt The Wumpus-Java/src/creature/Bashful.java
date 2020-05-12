package creature;

public class Bashful extends NPC 
{
	
	public Bashful(String nom) 
	{
		super(nom);
	}
	
	
	@Override
	public boolean handleIntruder(Inhabitant intruder) 
	{
		
		if(this.isAlive() && intruder.isAlive())
		{	
			return super.fleetingCave();	
		}
		
		/* This method will always have a
		 * false result because Bashful
		 * deal with intruders by fleeing.*/		
		return false;	
	}
	
	
	
	
}
