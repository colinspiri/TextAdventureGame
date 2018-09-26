package textAdventureGame;
import java.util.*;

public abstract class Enemy extends Creature {

	//constructor
	public Enemy(String tempName, int tempLevel, ArrayList<Skill> tempSkills) {
		super(tempName, tempLevel, tempSkills);
	}

	//toString
	public String toString() {
		//name, level, experience
		String returnedString = getName() + ", level " + getLevel() + "\n";

		//max health and mana
		returnedString += "health: " + getCurrentHealth() + "/" + getMaxHealth() + "\n";

		//combat stats
		returnedString += "damage: " + getMinDamage() + "-" + getMaxDamage() + "\n";
		returnedString += "defense: " + getDefense() + "\n";
		returnedString += "initiative: " + getInitiative();

		return returnedString;
	}
}







