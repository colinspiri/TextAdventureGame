package textAdventureGame;

public class Shield extends Item implements Equippable {
	private int armor;

	public Shield(String name, String description, int armor, int value, int hardness) {
		super(name, description, value, 5, hardness);
		this.armor = armor;
	}
	
	//toString
	public String toStringSpecifics() {
		String returnedString = "	base armor: " + armor;
		
		return returnedString;
	}
	
	public int getArmor() { 
		return armor; 
	}

	public void equip(PlayerCharacter pc) {
		pc.equipShield(this);
	}
}







