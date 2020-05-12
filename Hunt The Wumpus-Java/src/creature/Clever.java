package creature;

public class Clever extends NPC 
{

	public Clever(String nom) 
	{
		super(nom);
	}
	

	@Override
	public boolean handleIntruder(Inhabitant intruder) 
	{
		/* If Clever has more than 1 health, it will
		 * defend its position.*/		
		if(this.getDamagePoints() > 1)   
		{
			return super.handleIntruder(intruder);
		}
		
		/* else, it will keep fleeting to a neighbouring cave 
		 * by calling the super.fleetingCave() method.*/		
		else 
		{
			 return super.fleetingCave();
		}
		
	}

	
}
