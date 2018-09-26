package textAdventureGame;
//a trinket is an item with use other than existing (e.g. a feather, a gemstone)

public class Trinket extends Item {

	public Trinket(String name, String description) {
		super(name, description, 1);
	}
	
	public String toStringSpecifics() {
		return "";
	}

}


