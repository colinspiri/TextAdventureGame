package textAdventureGame;
import java.util.*;

public abstract class NPC extends Creature {

	public NPC(String tempName, int tempLevel) {
		super(tempName, tempLevel, null);
	}

	//abstract
	public abstract void talk();
	public abstract void give(Item i);

	//choose action
	public void chooseAction(Creature target) {
		int healthPercent = (int) ( (double)getCurrentHealth()/getMaxHealth() * 100 );
		String message = "";

		if(healthPercent == 100) message = "cowers in fear.";
		else if(healthPercent >= 66) message = "doubles over, bruised and bleeding.";
		else if(healthPercent >= 33) message = "rolls on the ground, yelling in pain.";
		else message = "lies unconscious, body jerking uncontrollably and sputtering with blood.";

		System.out.println(this.getName() + " " + message);
	}


	//inherited update methods
	//updateMaxHealth passed on to subclass
	public void updateDamage() {
		setDamage(0, 0);
	}
	public void updateDefense() {
		setDefense(0);
	}
	public void updateInitiative() {
		setInitiative(4);
	}
}




