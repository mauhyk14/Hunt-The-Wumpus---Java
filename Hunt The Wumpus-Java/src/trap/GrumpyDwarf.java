package trap;

public class GrumpyDwarf extends SuperBat 
{

	public GrumpyDwarf() 
	{
	}
	
	/* The GrumpyDwarf trap acts very similar to the SuperBat
	 * trap. Please refer to the SuperBat trap for an explanation.*/	
	@Override
	public String warningMessage() 
	{
		return "you hear muttered curses.";
	}

	@Override
	protected String triggerMessage(String name) 
	{
		return "A dwarf screams at " + name + 
			   " to get out of his cave!";
	}

}
