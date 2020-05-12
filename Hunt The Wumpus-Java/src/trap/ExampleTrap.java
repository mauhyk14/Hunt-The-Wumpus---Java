package trap;

import creature.Inhabitant;

/**
 * 	Again, this is a silly example and is not an example of how to best design 
 *  and write your classes.
 * 
 *  Could some functionality be implemented in the Trap superclass and inherited 
 *  by this and the other Traps?
 *  
 *
 */
public class ExampleTrap extends Trap {

	@Override
	public boolean springTrap(Inhabitant ihab) {
		System.out.println(triggerMessage(ihab.getName()));
		return true;
	}

	@Override
	public String warningMessage() {
		return "you hear a silly noise";
	}

	@Override
	protected String triggerMessage(String name) {
		return "A gnome runs out in front of " + name + " and yells BOO!";
	}

}
