package trap;

import core.Cave;
import creature.Inhabitant;

public class SuperBat extends Trap 
{
	public SuperBat() 
	{
		
	}
	
	
	/* If a trap is triggered by a player/creature, the trigger
	 * message will be printed out regardless. Whatever follows 
	 * depends on the creature's hasImmunity() method. The method
	 * will return false if the creature is immune to this trap
	 * else, the game engine will relocate this creature by calling 
	 * the requestOccupancy() method.*/
	@Override
	public boolean springTrap(Inhabitant ihab) 
	{				
		System.out.println(this.triggerMessage(ihab.getName()));
		
		if(ihab.hasImmunity(this)) 
		{
			System.out.println(ihab.getName() + " is too heavy to lift.");
			
			return false;
		}
		
		else
		{
			Cave randomCave = game.getMaze().pickRandomCave(ihab.getLocation());
			
			/* I know GrumpyDwarf is the sub-class of the SuperBat
			 * so the only difference I made when generating both classes
			 * is the cave selection. In SuperBat, this cave is a random
			 * cave but in GrumpyDwarf, this cave is a random neighbouring
			 * cave.*/			
			if(this.getClass() == GrumpyDwarf.class) 
			{
				randomCave = this.getLocation().getRandomExit();
			}
			
			System.out.println(ihab.getName() + " is dropped into cave # " + 
							   randomCave.getID());
			
			/* Once a cave is stored in the randomCave variable,
			 * this method will clear out the creature's current 
			 * location and move it to a new cave by calling 
			 * the requestOccupancy() method.*/			
			ihab.getLocation().clearOccupant(ihab);
				
			randomCave.requestOccupancy(ihab);
				
			return true;
		}
	}

	/* The default warning message presents to 
	 * a human player.*/
	@Override
	public String warningMessage() 
	{		
		return "you hear the rustling of wings.";
	}
	
	/* The default trigger message when the trap is triggered
	 * by a creature.*/
	@Override
	protected String triggerMessage(String name) 
	{		
		return name + " is snatched by a Super Bat!";
	}

}
