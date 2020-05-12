package trap;

import creature.Inhabitant;

public class PetrolBomb extends Trap 
{

	public PetrolBomb() 
	{
		
	}
	
	protected int bombCount = 1;
	
	/* If a trap is triggered by a player/creature, the trigger
	 * message will be printed out regardless. Whatever follows 
	 * depends on the creature's hasImmunity method. The method
	 * will return false if the creature is immune to this trap
	 * else, the game engine will kill off this creature by calling 
	 * the putToDeath() method and reduce the bombCount by 1.*/		
	@Override
	public boolean springTrap(Inhabitant ihab) 
	{				
		System.out.println(this.triggerMessage(null));

		if(ihab.hasImmunity(this)) 
		{
			return false;
		}
		
		else if(bombCount < 1) 
		{
			return false;
		}
		
		else 
		{
			System.out.println("BOOM! " + ihab.getName() + 
							   " is killed in the explosion.");
			
			ihab.putToDeath();
			
			bombCount = bombCount - 1;
			
			return true;
		}
	}
	
	/* The default warning message presents to 
	 * a human player.*/	
	@Override
	public String warningMessage() 
	{				
		return "you smell gasoline.";
	}
	
	/* The default trigger message when the trap is triggered
	 * by a creature.*/
	@Override
	protected String triggerMessage(String name) 
	{				
		return "click....";
	}

}
