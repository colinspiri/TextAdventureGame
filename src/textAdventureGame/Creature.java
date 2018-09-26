package textAdventureGame;
import java.util.*;

public abstract class Creature {
	//name and level
	private String name;
	private int level;
	
	//location
	private Location currentLocation;

	//combat stats
	private int maxHealth;
	private int currentHealth;

	private boolean dead = false;

	private int minDamage;
	private int maxDamage;

	private int defense;
	private boolean blocking;

	private int initiative;

	private boolean inCombat;

	//skills
	private ArrayList<Skill> skills;

	//constructor
	public Creature(String tempName, int tempLevel, ArrayList<Skill> tempSkills) {
		name = tempName;
		level = tempLevel;

		updateMaxHealth();
		currentHealth = maxHealth;

		updateDamage();
		updateDefense();
		updateInitiative();

		skills = tempSkills;

		inCombat = false;
	}

	//abstract methods
	public abstract void chooseAction(Creature target);
	public abstract void updateMaxHealth();
	public abstract void updateDamage();
	public abstract void updateDefense();
	public abstract void updateInitiative();

	//combat actions
	public void basicAttack(Creature target) {
		Random rng = new Random();
		int damageTaken = rng.nextInt(maxDamage - minDamage + 1) + minDamage;

		damageTaken -= target.getBlockingDefense(this);
		if(damageTaken < 0 ) damageTaken = 0;

		target.takeDamage(damageTaken);

		System.out.println("---" + name + " attacked " + target.getName() + " for " + damageTaken + " damage.");
	}
	public void setBlocking(boolean value) {
		blocking = value;

		if(blocking) {
			System.out.println("---Blocking!");
		}
	}

	//combat methods
	public boolean inCombat() {
		return inCombat;
	}
	public int getRandomInitiative() {
		Random rng = new Random();

		int initiativeRandomness = 2;
		int baseInitiative = initiative; //add bonuses to initiative here

		int minInitiative = baseInitiative - initiativeRandomness;
		int maxInitiative = baseInitiative + initiativeRandomness;

		int initiative = rng.nextInt(maxInitiative - minInitiative + 1) + minInitiative;

		return initiative;
	}
	public void takeDamage(int damageTaken) {
		currentHealth -= damageTaken;
		if(currentHealth <= 0) {
			currentHealth = 0;
			dead = true;
		}
		setBlocking(false);
	}
	public void heal(int amountHealed) {
		currentHealth += amountHealed;
		if(currentHealth > maxHealth) currentHealth = maxHealth;
	}
	public int getBlockingDefense(Creature attacker) {
		int blockingDefense = 0;
		if(blocking) {
			Random rng = new Random();
			int randomNum = rng.nextInt(101);
			if(randomNum >= 50) {
				blockingDefense = defense;
				System.out.println("Block succeeded! Blocked " + blockingDefense + " damage.");
				//unblock and choose another action
				setBlocking(false);
				this.chooseAction(attacker);
			}
		}
		return blockingDefense;
	}

	//toString
	public String toStringCombat() {
		//just return name, level, and health
		String returnedString = getName() + ", level " + getLevel() + ", health: " + getCurrentHealth() + "/" + getMaxHealth();
		return returnedString;
	}

	//get methods
	public String getName() { 
		return name; 
	}
	public int getLevel() { 
		return level; 
	}
	public Location getCurrentLocation() {
		return currentLocation;
	}
	public int getCurrentHealth() { 
		return currentHealth; 
	}
	public int getMaxHealth() { 
		return maxHealth; 
	}
	public boolean isDead() {
		return dead;
	}
	public int getMinDamage() {
		return minDamage;
	}
	public int getMaxDamage() {
		return maxDamage;
	}
	public int getDefense() { 
		return defense; 
	}
	public boolean isBlocking() { 
		return blocking; 
	}
	public int getInitiative() {
		return initiative;
	}
	public ArrayList<Skill> getSkills() {
		return skills;
	}

	//set methods
	public void setCurrentLocation(Location newLocation) {
		currentLocation = newLocation;
	}
	public void setMaxHealth(int newMaxHealth) {
		maxHealth = newMaxHealth;
	}
	public void setCurrentHealth(int newCurrentHealth) {
		currentHealth = newCurrentHealth;
	}
	public void incrementLevel() { 
		level++; 
	}
	public void setDamage(int newMinDamage, int newMaxDamage) {
		minDamage = newMinDamage;
		maxDamage = newMaxDamage;
	}
	public void setDefense(int newDefense) {
		defense = newDefense;
	}
	public void setInitiative(int newInitiative) {
		initiative = newInitiative;
	}
}