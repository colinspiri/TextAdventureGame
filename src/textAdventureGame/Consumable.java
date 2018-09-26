package textAdventureGame;

public abstract class Consumable extends Item {
	private int healthRestored;

	public Consumable(String name, String description, int healthRestored, int value) {
		super(name, description, value, 1, 1);
		this.healthRestored = healthRestored;
	}
	
	//toString
	public String toStringSpecifics() {
		String returnedString;
		
		if(healthRestored >= 0) {
			returnedString = "	health restored: " + healthRestored;
		}
		else {
			returnedString = "	damage taken: " + healthRestored;
		}
		
		return returnedString;
	}

	//consume
	public void consume(Creature c) {
		//heal creature
		c.heal(healthRestored);
		System.out.println("(+" + healthRestored + " health)");

		//make sure this item is gone
		if(c instanceof PlayerCharacter) {
			((PlayerCharacter)c).removeFromInventory(this);
			((PlayerCharacter) c).getCurrentLocation().removeItem(this);
		}
	}
	
	//get methods
	public int getHealthRestored() {
		return healthRestored;
	}

	//set methods
	public void setHealthRestored(int newHealthRestored) {
		healthRestored = newHealthRestored;
	}
}


