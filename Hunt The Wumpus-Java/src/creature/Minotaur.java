package creature;

import core.Cave;

public class Minotaur extends Beast 
{

	public Minotaur(String nom) 
	{
		super(nom);
	}
	
	
	public String displayMessage1() 
	{
		/* The display message when Minotaur encounters 
		 * a player named "Theseus".*/		
		return "Enraged, " + this.getName() + " rips Theseus into little pieces!";
	}
	
	
	
	public String displayMessage2() 
	{
		/* The display message when a Minotaur is intruded by a player named 
		 * "Theseus".*/		
		return this.getName() + " is terrified of Theseus and runs away!";
	}
	
	
	@Override
	public boolean handleIntruder(Inhabitant intruder) 
	{
		/* A Minotaur will keep defending its cave as long
		 * as the player's name not equal to "Theseus".*/		
		if(this.isAlive() && !intruder.getName().equalsIgnoreCase("Theseus")) 
		{
			return true;
		}
		
		else 
		{
			/* else, the method will print out the message and 
			 * it will generate a neighbouring cave
			 * so the Minotaur creature can attempt to move 
			 * into it.*/			
			System.out.println(this.displayMessage2());
		
			Cave emptyCave = this.getLocation().getRandomExit();  
			
			this.moveToNewCave(emptyCave);  
				
			return false;	
		}	
	}
	
	
	@Override
	public boolean disturbResident(Inhabitant resident) 
	{
		/* The Minotaur will kill off any resided
		 * resident it encounters. This method will print out 
		 * the encounter message only if the player's name is
		 * "Theseus". */		
		if(resident.isAlive())
		{
			if(!resident.isAwake()) 
			{
				resident.wakeUpNow();
			}
			
			if(resident.getName().equalsIgnoreCase("Theseus")) 
			{
				System.out.println(this.displayMessage1());
			}
			
			resident.putToDeath(); 
		}
		
		return false;
	}
	
}
