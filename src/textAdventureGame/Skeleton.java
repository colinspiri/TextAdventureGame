package textAdventureGame;
import java.util.*;

public class Skeleton extends Enemy {

	public Skeleton() {
		super("Skeleton", 1, null);
	}

	//choose action
	public void chooseAction(Creature target) {
		basicAttack(target);
	}
	
	//inherited update methods
	public void updateMaxHealth() {
		setMaxHealth(3);
	}
	public void updateDamage() {
		setDamage(2, 2);
	}
	public void updateDefense() {
		setDefense(1);
	}
	public void updateInitiative() {
		setInitiative(4);
	}
}






