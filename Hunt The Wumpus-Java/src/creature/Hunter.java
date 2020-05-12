package creature;

import java.util.Scanner;
import core.Cave;


public class Hunter extends Human 
{

	public Hunter(String nom) 
	{
		super(nom);
	}
	
	
	protected int arrowNumber = 3;
	
	
	@Override
	public void takeATurn() 
	{
		/* After displaying the nearby warning messages,
		 * it will ask the player to shoot or move.*/		
		super.displayWarningMessage();
		
		Scanner hunterMovement = new Scanner(System.in); 
		
		System.out.print("* " + this.getName() +", do you " + 
						 "(S)hoot or (M)ove?: ");
		
		char playerChoice = hunterMovement.next().charAt(0);
		
		/* If move action is selected, the human class takeATurn() method
		 * will be executed.*/		
		if(playerChoice == 'm' 
		   || playerChoice == 'M') 
		{	
			super.takeATurn();
		}
		
		else 
		{
			/* If shoot action is selected, first, it will test all available 
			 * caves, get all caves IDs, residents and compare them
			 * with the selected result. */			
			System.out.print("into which Cave: ");
			
			int targetCave = hunterMovement.nextInt();
			
			Cave[] currentCaveArray = this.getLocation().getAllExits();
			
			boolean hasCave = false;
			boolean hasTarget = false;
			
			int targetNumber = 0;
			
			for(int i = 0; i< currentCaveArray.length; i++) 
			{
				if(targetCave == currentCaveArray[i].getID()) 
				{
					hasCave = true;
					
					if(currentCaveArray[i].hasOccupant()) 
					{
						hasTarget = true;
						
						targetNumber = i;
					}
				}
				
			}
			
			
			/* Messages will be printed out according to possible
			 * outcomes. In the end, the number of spear will reduce by
			 * 1 and all nearby creatures are awaken by this shooting
			 * action.*/			
			if(hasCave == true && hasTarget == true && arrowNumber > 0) 
			{
				System.out.println("Bullseye! You speared " + 
									currentCaveArray[targetNumber].getOccupant().getName());
				
				currentCaveArray[targetNumber].getOccupant().putToDeath();
			}
			
			else if(hasCave == true  && hasTarget == false && arrowNumber > 0) 
			{
				System.out.println("Your spear hit nothing.");
			}
			
			else if(hasCave == false  && hasTarget == false && arrowNumber > 0) 
			{
				System.out.println("Your spear hit a wall, your shot is wasted.");
			}
			
			else if (arrowNumber <= 0)
			{
				System.out.println("You are out of spears.");
			}
			
			arrowNumber = arrowNumber - 1;
			
			for(int j = 0; j < currentCaveArray.length; j++) 
			{
				if(currentCaveArray[j].hasOccupant()) 
				{
					currentCaveArray[j].getOccupant().wakeUpNow();
					
					System.out.println("Your battle cry rouses nearby inhabitants.");
				}
			}
			
		}
		
	}

}
