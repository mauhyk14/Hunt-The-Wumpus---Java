package trap;

import creature.Inhabitant;

public class PoisonArrow extends Trap {

	public PoisonArrow() {
		
	}

	protected int arrNumber = 3;
	
	
	/* If a trap is triggered by a player/creature, the trigger
	 * message will be printed out regardless. Whatever follows 
	 * depends on the creature's hasImmunity method. The method
	 * will return false if the creature is immune to this trap
	 * else, the game engine will take one health point from this 
	 * creature by calling the takeDamage() method (assuming the
	 * trap has arrows).*/	
	@Override
	public boolean springTrap(Inhabitant ihab) 
	{			
		System.out.println(this.triggerMessage(null));
		
		if(ihab.hasImmunity(this) || arrNumber <= 0) 
		{
			return false;
		}
		
		else 
		{
			System.out.println(ihab.getName() + " is hit by a poison arrow!");
			
			ihab.takeDamage();
			
			arrNumber = arrNumber - 1;
			
			return true;
		}
	}
	
	/* The default warning message presents to 
	 * a human player.*/	
	@Override
	public String warningMessage() 
	{				
		return "you hear the twang of a bow.";
	}
	
	/* The default trigger message when the trap is triggered
	 * by a creature.*/
	@Override
	protected String triggerMessage(String name) 
	{				
		return "twang...";
	}

	
}
