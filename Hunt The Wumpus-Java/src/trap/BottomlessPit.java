package trap;

import creature.Inhabitant;

public class BottomlessPit extends Trap 
{

	public BottomlessPit() 
	{
		
	}
	
	/* If a trap is triggered by a player/creature, the trigger
	 * message will be printed out regardless. Whatever follows 
	 * depends on the creature's hasImmunity method. The method
	 * will return false if the creature is immune to this trap
	 * else, the game engine will kill off this creature by calling 
	 * the putToDeath() method.*/		
	@Override
	public boolean springTrap(Inhabitant ihab) 
	{				
		System.out.println(this.triggerMessage(ihab.getName()));
		
		if(ihab.hasImmunity(this)) 
		{
			System.out.println(ihab.getName() + " climbs out again.");
			
			return false;
		}
		
		else 
		{
			System.out.println("AIIIEEEeee!");
			
			ihab.putToDeath();
			
			return true;
		}	
	}
	
	/* The default warning message presents to 
	 * a human player.*/
	@Override
	public String warningMessage() 
	{			
		return "you feel a draft";
	}
	
	/* The default trigger message when the trap is triggered
	 * by a creature.*/	
	@Override
	protected String triggerMessage(String name) 
	{			
		return "Powerful air currents draw " + name + " into a Bottomless Pit!";
	}

}
