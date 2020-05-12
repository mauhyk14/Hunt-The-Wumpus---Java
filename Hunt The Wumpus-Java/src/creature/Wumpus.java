package creature;

import games.ClassicHTW;
import testGames.Wumpusgame;
import trap.Trap;

public class Wumpus extends Inhabitant 
{
	private int wpusRound = 0;
	
	public Wumpus(String nom) 
	{
		super(nom);
	}
	
	
	@Override
	public String warningMessage() 
	{
		/* The default warning message
		 * for Wumpus.*/		
		return "You smell a Wumpus!";
	}
	
	
	@Override	
	public void takeATurn() 
	{
		/* wpusRound will count how many rounds
		 * it has played.*/		
		wpusRound++;
		
		/* Initially, the Wumpus will begin the
		 * turn asleep. The sleep turns will match
		 * the rounds which the game will play.*/		
		if(firsttime) 
		{
			this.goToSleep(ClassicHTW.rounds); 
			
			firsttime = false;
		}
		
		
		if(this.isAlive()) 
		{
			if(!this.isAwake())
			{
				/* As long as the Wumpus is alive and asleep,
				 * it will continuously print out the "sleeps
				 * peacefully" message until the second final round.
				 * In the final round, it will print out 
				 * "yawns and stretches..."*/		
				if(this.sleepTurns == 1 || 
				   this.wpusRound == ClassicHTW.rounds) 
				{
					System.out.println(this.getName() + " yawns and stretches...");
				}
				
				else if(this.sleepTurns != 0) 
				{
					this.sleepTurns--;
					System.out.println(this.getName() + " sleeps peacefully.");	
				}
			}
			
			/* Once the Wumpus is awake, it will
			 * move to a nearby cave then go back 
			 * to sleep again.*/			
			else 
			{
				this.moveToRandomExit();
				
				this.goToSleep(); 
			}
		}
	}
	
	
	@Override	
	public boolean handleIntruder(Inhabitant intruder) 
	{
		if(this.isAlive())   
		{	
			/* As long as the Wumpus is alive,
			 * it will kill off all intruders. */			
			System.out.println(intruder.name + " stmbles upon " + 
					   	       this.name + ", who is promptly EATEN.");
			
			intruder.putToDeath();       
			
			return true;    
		}
		
		else 
		{
			return false;
		}
	}
	
	
	
	@Override
	public boolean disturbResident(Inhabitant resident) 
	{
		if(resident.isAlive())
		{
			if(!resident.isAwake())
			{
				resident.wakeUpNow();                           
			}
			
			/* Again,the Wumpus will put any
			 * creature to death if it encounters 
			 * in a cave.*/			
			System.out.println(this.name + " stmbles upon " + 
						       resident.name + ", who is promptly EATEN.");
			
			resident.putToDeath();  
			
			return false;
		}
		
		else 
		{
			return false;
		}
	}
	
	
	@Override
	public boolean hasImmunity(Trap T) 
	{
		/* A Wumpus is immune to all traps.
		 * so a true result is expected.*/		
		return true;
	}
	
}
