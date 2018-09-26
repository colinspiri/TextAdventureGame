package textAdventureGame;

public class Weapon extends Item implements Equippable {
	private int minDamage;
	private int maxDamage;

	public Weapon(String name, String description, int minDamage, int maxDamage, int value, int hardness) {
		super(name, description, value, 5, hardness);
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
	}
	
	//toString
	public String toStringSpecifics() {
		String returnedString = "	base damage: " + minDamage + "-" + maxDamage;
		
		return returnedString;
	}
	
	public int getMinDamage() { 
		return minDamage; 
	}
	public int getMaxDamage() { 
		return maxDamage; 
	}

	public void equip(PlayerCharacter pc) {
		pc.equipWeapon(this);
	}
}







