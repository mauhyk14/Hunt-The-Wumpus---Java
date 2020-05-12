package creature;

import java.util.Scanner;

import core.Cave;

public class Human extends Inhabitant 
{

	public Human(String nom) 
	{
		super(nom);
	}
	

	public void displayWarningMessage() 
	{
		/* This method will display all nearby warning messages 
		 * from the caves to the player.*/		
		Cave[] caveArray = this.getLocation().getAllExits();
		
		System.out.print(" --> ");
		
		for(int j = 0; j < caveArray.length; j++) 
		{
			System.out.print(caveArray[j].getID() + "  ");
		}
		
		System.out.println();
		
		for(int i = 0; i < caveArray.length; i++) 
		{
			if(caveArray[i].hasOccupant()) 
			{
				System.out.print("   " + "..." + 
						         caveArray[i].getOccupant().warningMessage() + '\n');
			}
			
			if(caveArray[i].hasTrap()) 
			{
				System.out.print("   " + "..." + 
						         caveArray[i].getTrap().warningMessage() + '\n');
			}
		}
		
		System.out.println();
	}
	
	
	@Override
	public void takeATurn() 
	{
		/* After displaying all the warning messages to
		 * the player, it will ask the player to pick a
		 * cave to move into.*/		
		Cave[] caveArray = this.getLocation().getAllExits();
				
		this.displayWarningMessage();
		
		Scanner humanMove = new Scanner(System.in); 
		
		System.out.print("Move into which cave? ");
		
		int theCavenumber = humanMove.nextInt();
		
		boolean hasCave = false;
		Cave theMovinginCave = null;
		
		/* The for loop compares the player's input with
		 * any available exit of the cave. If an exit exist,
		 * hasCave becomes true.*/		
		for(int j = 0; j < caveArray.length; j++) 
		{
			if(theCavenumber == caveArray[j].getID()) 
			{
				theMovinginCave = caveArray[j];
				
				hasCave = true;
			}
		}
		
		/* If the cave number doesn't exist, it will print
		 * out the "walked into a wall!" message. Else, the 
		 * method moves this player to his/her selected cave
		 * by calling the moveToNewCave method.*/		
		if(hasCave==true) 
		{
			this.moveToNewCave(theMovinginCave);
		}
		
		else 
		{
			System.out.println(this.getName() + " walked into a wall!");
		}
		
	}
	
	
	@Override
	public boolean handleIntruder(Inhabitant intruder) 
	{
		/* A choice is given to a player when he/she 
		 * encounters an intruder. If he/she picks run 
		 * away, he/she needs to select a cave to run into.
		 * Otherwise, if he/she decides to stay and
		 * fight, he/she will loses a health point by calling
		 * the takeDamage() method.*/		
		Scanner playChoice = new Scanner(System.in);
		
		System.out.print("do you (S)tay or (R)un away? ");
		
		char pChoice = playChoice.next().charAt(0);
		
		if(this.isAlive() && pChoice == 'S' || 
		   this.isAlive() && pChoice == 's')   
		{
			boolean afterTakedamage = this.takeDamage();        
			
			return afterTakedamage;
		}
		
		else 
		{
			this.takeATurn();
			
			return false;
		}
	}

}
