package textAdventureGame;
import java.util.*;

public class PlayerCharacter {
	//variables
	private String name;
	private Location currentLocation;

	private int gold;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private int maxInventorySlots; 
	private int inventorySlots;

	//constructor
	public PlayerCharacter(String name, Location startingLocation) {
		this.name = name;
		currentLocation = startingLocation;

		gold = 100;
		maxInventorySlots = 30; //pocket-sized(<1 lb) = 1, one-handed(<5 lbs) = 2, two-handed(10+ lbs) = 3
		inventorySlots = 0;
	}

	//methods

	//inventory
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
	public String toStringInventory() {
		String returnedString = "----" + "\n";

		//inventory title
		returnedString += "Inventory:" + "\n";

		//gold
		returnedString += getGold() + " gold" + "\n";

		//items in inventory
		if(inventory.size() == 0) returnedString += "No items in inventory." + "\n";
		else {
			for(Item item : inventory) {
				returnedString += item.getName() + "\n";
			}
		}

		returnedString += "----";

		return returnedString;
	}


	//get methods
	public String getName() {
		return name;
	}
	public Location getCurrentLocation() {
		return currentLocation;
	}
	public int getGold() {
		return gold;
	}
	public ArrayList<Item> getInventory() {
		return inventory;
	}

	//set methods
	public void setCurrentLocation(Location newLocation) {
		currentLocation = newLocation;
	}
}








