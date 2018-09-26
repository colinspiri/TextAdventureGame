package textAdventureGame;
import java.util.*;

public class Zombie extends Enemy {

	public Zombie() {
		super("Zombie", 1, null);
	}

	//choose action
	public void chooseAction(Creature target) {
		basicAttack(target);
	}
	
	//inherited update methods
	public void updateMaxHealth() {
		setMaxHealth(4);
	}
	public void updateDamage() {
		setDamage(3, 3);
	}
	public void updateDefense() {
		setDefense(1);
	}
	public void updateInitiative() {
		setInitiative(3);
	}
}






