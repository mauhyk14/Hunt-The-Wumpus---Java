package creature;

public class Beast extends Inhabitant 
{

	public Beast(String nom) 
	{
		super(nom);
	}
	
	
	@Override
	public String warningMessage() 
	{
		/* This will overwrite the warning message.*/		
		return "you hear a deep snort.";
	}
		
	
	@Override
	public boolean handleIntruder(Inhabitant intruder) 
	{
		/* The beast will handle the intruder by defending it
		 * without losing any health point.*/		
		if(this.isAlive())   
		{
			return true;
		}
		
		else 
		{
			return false;
		}
	}
	
	
	
	@Override
	public boolean disturbResident(Inhabitant resident) 
	{
		/* When a Beast walks into a cave, it will kill
		 * off any resided resident after waking it up.*/		
		if(resident.isAlive())
		{
			if(!resident.isAwake()) 
			{
				resident.wakeUpNow();
			}
			
			resident.putToDeath();
		}
		
		return false;
	}
	
}
