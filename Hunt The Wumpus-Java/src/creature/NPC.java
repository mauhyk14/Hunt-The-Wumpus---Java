package creature;

import core.Cave;

public class NPC extends Inhabitant 
{
	public NPC(String nom) 
	{
		super(nom);
	}
	
	
	/* This a self-made method which I create to handle 
	 * the fleeting of Bashful/Clever creatures by relocating 
	 * them to different caves.(I assume both creatures are the
	 * sub-class of NPC.)*/	
	public boolean fleetingCave() 
	{
		/* This statement will generate a neighbouring cave 
		 * and store it in the randomCave variable.*/		
		Cave randomCave = this.getLocation().getRandomExit(); 
		
		/* If the generated cage is not null, 
		 * Bashful/Clever loses its retrieving cave. 
		 * The game engine will kill off the
		 * creature.*/		
		if(randomCave.getOccupant() != null) 
		{
			return false;
		}
		
		/* If the creature has an empty cave to
		 * move into, it will relocate to this 
		 * new cave by calling the moveToRandomExit
		 * method.*/		
		boolean moved = this.moveToRandomExit(randomCave);
		
		/* This method will always return a false result 
		 * because the creature chooses to move. This 
		 * method is align with the handleIntruder() method.*/		
		return false;
	}
	
	
	
}
