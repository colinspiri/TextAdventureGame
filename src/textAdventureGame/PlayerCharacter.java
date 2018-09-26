package textAdventureGame;
import java.util.*;

public class PlayerCharacter extends Creature {
	//mana
	private int maxMana;
	private int currentMana;

	//base stats
	private int strength;
	private int vitality;
	private int intelligence;
	private int agility;

	//progression
	private double experience;
	private int statPoints;
	private int skillPoints;
	private int totalSkillPoints;

	//inventory
	private int gold;
	private ArrayList<Item> inventory = new ArrayList<Item>();

	//equipment
	private Weapon weapon;
	private Shield shield;

	//constructor
	public PlayerCharacter(String name, int level, int tempStrength, int tempVitality, int tempIntelligence, int tempAgility, ArrayList<Skill> tempSkills) {
		super(name, level, tempSkills);

		//base stats
		strength = tempStrength;
		vitality = tempVitality;
		intelligence = tempIntelligence;
		agility = tempAgility;

		updateMaxHealth();
		setCurrentHealth(getMaxHealth());
		updateMaxMana();
		setCurrentMana(getMaxMana());

		//combat
		weapon = null;
		updateDamage();
		shield = null;
		updateDefense();
		updateInitiative();

		//inventory
		gold = 400;

		//progression, stats, and skills
		experience = 0;
		statPoints = 0;
		skillPoints = 0;
		totalSkillPoints = 0;

		updateStatPoints();
		updateSkillPoints();
	}

	//choose action and combat methods
	public void chooseAction(Creature target) {
		Scanner keyboard = new Scanner(System.in);

		//display combat stats
		System.out.println();
		System.out.println(this.toStringCombat());
		System.out.println(target.toStringCombat());

		//get input
		String input = keyboard.nextLine();

		//basic attack
		if(input.equalsIgnoreCase("attack")) {
			basicAttack(target);
		}
		//block
		else if(input.equalsIgnoreCase("block")) {
			setBlocking(true);
		}
		//skills or invalid input
		else {
			boolean inputIsValid = false;
			for(Skill s : getSkills()) {
				if(input.equalsIgnoreCase(s.getName()) && s.getLevel() >= 1) {
					if(this.canUseMana(s.getMana())) {
						s.execute(this, target);
						this.useMana(s.getMana());
						inputIsValid = true;
					}
					else {
						System.out.println("Cannot use this skill. Not enough mana.");
					}
				}
			}
			if(!inputIsValid) {
				System.out.println("Invalid input. Try again");
				chooseAction(target);
			}	
		}
	}
	public boolean canUseMana(int manaNeeded) {
		if(currentMana >= manaNeeded) return true;
		else return false;
	}
	public void useMana(int mana) {
		currentMana -= mana;
		if(currentMana < 0 ) currentMana = 0;
	}

	//progression
	public void gainExperience(double gainedExperience) {
		experience += gainedExperience;
		while(experience >= 100) {
			experience -= 100;
			levelUp();
		}
	}
	private void levelUp() {
		incrementLevel();
		updateStatPoints();
		updateSkillPoints();
	}
	private void updateStatPoints() {
		int currentStatTotal = getStrength() + getVitality() + getIntelligence() + getAgility();
		int targetStatTotal = 16 + ((getLevel()-1)*3);
		if(currentStatTotal < targetStatTotal) {
			statPoints = targetStatTotal-currentStatTotal;
			spendStatPoints();
		}
	}
	private void spendStatPoints() {
		while(getStatPoints() > 0) {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Add to your stats. Type either \'strength\', \'vitality\', \'intelligence\', \n"
					+ "or \'agility\'. " + getStatPoints() + " stat points available.");
			while(true) {
				String input = keyboard.nextLine();

				if(input.equals("strength")) {
					incrementStrength();
					statPoints--;
					System.out.println("Strength incremented by 1. Strength is now " + getStrength() + ".");
					updateDamage();
					break;
				}
				else if(input.equals("vitality")) {
					incrementVitality();
					statPoints--;
					System.out.println("Vitality incremented by 1. Vitality is now " + getStrength() + ".");
					break;
				}
				else if(input.equals("intelligence")) {
					incrementIntelligence();
					statPoints--;
					System.out.println("Intelligence incremented by 1. Intelligence is now " + getIntelligence() + ".");
					break;
				}
				else if(input.equals("agility")) {
					incrementAgility();
					statPoints--;
					System.out.println("Agility incremented by 1. Agility is now " + getAgility() + ".");
					break;
				}
				else {
					System.out.println("Input is invalid.");
				}
			}
		}
	}
	private void updateSkillPoints() {
		totalSkillPoints = 0;
		for(Skill s : getSkills()) {
			totalSkillPoints += s.getLevel();
		}
		skillPoints = getLevel() - totalSkillPoints;
		spendSkillPoints();
	}
	private void spendSkillPoints() {
		while(getSkillPoints() > 0) {
			Scanner keyboard = new Scanner(System.in);
			System.out.println("Choose a skill. " + skillPoints + " skill points remaining.");
			for(Skill s : getSkills()) {
				System.out.println(s.toStringShort());
			}
			String input = keyboard.nextLine();

			for(Skill s : getSkills()) {
				if(input.equalsIgnoreCase(s.getName())) {
					s.levelUp();
					skillPoints--;
					totalSkillPoints++;
					break;
				}
			}
		}
	}

	//inventory
	public boolean canAfford(int amount) {
		if(gold >= amount) return true;
		else return false;
	}

	//inherited update methods
	public void updateMaxHealth() {
		setMaxHealth( 17 + (getVitality()*2) );
	}
	public void updateMaxMana() {
		setMaxMana( 3 + getIntelligence() );
	}
	public void updateDamage() {
		int minDamageStrengthBonus = (int)Math.round(( ((double)getStrength() + 2) /5 ));
		int maxDamageStrengthBonus = (int)Math.round(( ((double)getStrength() + 1) /4 ));
		if(getStrength() % 8 == 6) maxDamageStrengthBonus++;
		if(weapon instanceof Weapon) {
			int minDamage = weapon.getMinDamage() + minDamageStrengthBonus;
			int maxDamage = weapon.getMaxDamage() + maxDamageStrengthBonus;
			setDamage(minDamage, maxDamage);
		}
		else if(weapon == null) {
			int minDamage = minDamageStrengthBonus;
			int maxDamage = maxDamageStrengthBonus;
			setDamage(minDamage, maxDamage);
		}
	}
	public void updateDefense() {
		int levelBonus = getLevel() - 1;
		if(shield instanceof Shield) setDefense(shield.getArmor() + levelBonus);
		else if(shield == null) setDefense(levelBonus);
	}
	public void updateInitiative() {
		setInitiative(agility);
	}
	public void checkIfDead() {
		if(getCurrentHealth() <= 0) {
			System.out.println("YOU DIED. GAME OVER.");
			System.exit(1);
		}
	}

	//toString
	public String toString() {
		return toStringStats() + "\n" + toStringInventory() + "\n" + toStringSkills();
	}
	public String toStringStats() {
		String returnedString = "----" + "\n";

		//stats title
		returnedString += "Stats:" + "\n";

		//name, level, experience
		returnedString += getName() + ", level " + getLevel() + ", experience: " + Math.round(getExperience()) + "/100" + "\n";

		//health and mana
		returnedString += "health: " + getCurrentHealth() + "/" + getMaxHealth() + ", mana: " + getCurrentMana() + "/" + getMaxMana() + "\n";

		//base stats
		//returnedString += "\n";
		returnedString += "STR: " + getStrength() + "\n";
		returnedString += "VIT: " + getVitality() + "\n";
		returnedString += "INT: " + getIntelligence() + "\n";
		returnedString += "AGI: " + getAgility() + "\n";
		//returnedString += "\n";

		//combat stats
		if(weapon == null) returnedString += "weapon: - (" + getMinDamage() + "-" + getMaxDamage() + ")" +"\n";
		else returnedString += "weapon: " + getWeapon().getName() + " (" + getMinDamage() + "-" + getMaxDamage() + ")" + "\n";
		if(shield == null) returnedString += "shield: - (" + getDefense() + ")" + "\n";
		else returnedString += "shield: " + getShield().getName() + " (" + getDefense() + ")" + "\n";
		returnedString += "initiative: " + getInitiative() + "\n";

		returnedString += "----";

		return returnedString;
	}
	//TODO
	//display equipped items in inventory as equipped and also fix input so that (sell stick) would work if stick is equipped
	public String toStringInventory() {
		String returnedString = "----" + "\n";

		//inventory title
		returnedString += "Inventory:" + "\n";

		//gold
		returnedString += getGold() + " gold" + "\n";

		//items in inventory
		if(inventory.size() == 0) returnedString += "No items in inventory." + "\n";
		else {
			for(Item i : inventory) {
				returnedString += i.toStringShort() + "\n";
			}
		}

		returnedString += "----";

		return returnedString;
	}
	public String toStringSkills() {
		String returnedString = "----" + "\n";

		//skills title
		returnedString += "Skills: " + "\n";

		//skills in list
		for(Skill s : getSkills()) {
			returnedString += s.getName() + " (level " + s.getLevel() + ")" + "\n";
		}

		returnedString += "----";

		return returnedString;
	}
	public String toStringCombat() {
		//name, level, experience
		String returnedString = getName() + ", level " + getLevel();

		//max health and mana
		returnedString += ", health: " + getCurrentHealth() + "/" + getMaxHealth() + ", mana: " + getCurrentMana() + "/" + getMaxMana();

		return returnedString;
	}

	//get methods
	public int getCurrentMana() { 
		return currentMana; 
	}
	public int getMaxMana() { 
		return maxMana; 
	}
	public int getStrength() { 
		return strength; 
	}
	public int getVitality() { 
		return vitality; 
	}
	public int getIntelligence() { 
		return intelligence; 
	}
	public int getAgility() { 
		return agility; 
	}
	public double getExperience() {
		return experience;
	}
	public int getGold() {
		return gold;
	}
	public ArrayList<Item> getInventory() {
		return inventory;
	}
	public int getStatPoints() { 
		return statPoints; 
	}
	public int getSkillPoints() {
		return skillPoints;
	}
	public int getTotalSkillPoints() {
		return totalSkillPoints;
	}
	public Weapon getWeapon() {
		return weapon;
	}
	public Shield getShield() {
		return shield;
	}

	//set methods
	public void setMaxMana(int newMaxMana) {
		maxMana = newMaxMana;
	}
	public void setCurrentMana(int newCurrentMana) {
		currentMana = newCurrentMana;
	}
	public void incrementStrength() { 
		strength++; 
	}
	public void incrementVitality() { 
		vitality++; 
	}
	public void incrementIntelligence() { 
		intelligence++; 
	}
	public void incrementAgility() { 
		agility++; 
	}
	public void addGold(int amount) {
		gold += amount;
	}
	public void removeGold(int amount) {
		gold -= amount;
	}
	public void addToInventory(Item item) {
		inventory.add(item);
	}
	public void removeFromInventory(Item item) {
		if(inventory.contains(item)) {
			inventory.remove(item);
		}
	}
	public void equipWeapon(Weapon newWeapon) {
		Weapon oldWeapon = weapon;
		weapon = newWeapon;
		updateDamage();
		if(oldWeapon instanceof Weapon) {
			inventory.add(oldWeapon);
		}
	}
	public void equipShield(Shield newShield) {
		Shield oldShield = shield;
		shield = newShield;
		updateDefense();
		if(oldShield instanceof Shield) {
			inventory.add(oldShield);
		}
	}
}








