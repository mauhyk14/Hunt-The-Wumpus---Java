package trap;

import creature.Inhabitant;

public class SleepGas extends Trap 
{

	public SleepGas() 
	{
		
	}
	
	protected int gasCount = 2;
	
	/* If a trap is triggered by a player/creature, the trigger
	 * message will be printed out regardless. Whatever follows 
	 * depends on the creature's hasImmunity method. The method
	 * will return false if the creature is immune to this trap
	 * else, this method will put the creature to sleep for 3 
	 * turns assuming the trap hasn't run out the gas.*/	
	@Override
	public boolean springTrap(Inhabitant ihab) 
	{
				
		System.out.println(this.triggerMessage(ihab.getName()));
		
		if(ihab.hasImmunity(this) || gasCount <= 0) 
		{
			System.out.println("It smells only of fresh air.");
		
			return false;
		}
		
		else 
		{
			ihab.goToSleep(3);
			
			System.out.println("ZZZzzz...");
		
			gasCount = gasCount - 1;
			
			return true;
		}
		
	}

	/* The default warning message presents to 
	 * a human player.*/
	@Override
	public String warningMessage() 
	{				
		return "you smell a sweet odour";
	}

	/* The default trigger message when the trap is triggered
	 * by a creature.*/
	@Override
	protected String triggerMessage(String name) 
	{		
		return name + " is enveloped is gas!";
	}

}
