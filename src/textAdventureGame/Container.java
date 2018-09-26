package textAdventureGame;
import java.util.*;
//a container is an item that can store other items inside (e.g. a chest, a backpack)

public class Container extends Item {
	//variables
	private ArrayList<Item> storedItems;
	private int maxSlots;
	private int storedSlots;
	private boolean open;

	//constructor
	public Container(String name, String description, int size, int maxSlots) {
		super(name, description, size);
		this.maxSlots = maxSlots;
		storedItems = new ArrayList<Item>();
		storedSlots = 0;
		open = false;
	}

	//methods
	//interact methods
	private void addItem(Item item) {
		if(canHold(item.getSize())) {
			storedItems.add(item);
			storedSlots += item.getSize();
		}
	}
	private void removeItem(Item item) {
		if(storedItems.contains(item)) {
			storedItems.remove(item);
			storedSlots -= item.getSize();
		}
	}
	public void attemptToAdd(Item item) {
		if(open) {
			this.addItem(item);
		}
		else {
			System.out.println(this.getName() + " is closed.");
		}
	}
	public void attemptToRemove(Item i) {
		if(open) {
			this.removeItem(i);
		}
		else {
			System.out.println(this.getName() + " is closed.");
		}
	}
	public boolean contains(Item searchedItem) {
		for(Item item : storedItems) {
			if(item.equals(searchedItem)) {
				return true;
			}
		}
		return false;
	}
	public boolean canHold(int additionalSlots) {
		return (storedSlots + additionalSlots <= maxSlots);
	}
	public void open() {
		open = true;
	}
	public void close() {
		open = false;
	}

	//toString
	public String toStringSpecifics() {
		String returnedString = "	capacity: " + storedSlots + "/" + maxSlots + " slots" + "\n";
		returnedString += this.toStringContents();
		return returnedString;
	}
	private String toStringContents() {
		String returnedString = "";

		//if no contents
		if(storedSlots == 0) {
			returnedString += "	empty";
		}
		//print all items stored in container
		else {
			for(Item item : storedItems) {
				returnedString += "	" + item.getName() + " (size: " + item.getSize() + ")" + "\n";
			}
		}

		return returnedString;
	}

	//get methods
	public ArrayList<Item> getStoredItems() {
		return storedItems;
	}
	public int getMaxSlots() {
		return maxSlots;
	}
	public int getStoredSlots() {
		return storedSlots;
	}
	public boolean isOpen() {
		return open;
	}

	//set methods
	public void setOpen(boolean value) {
		open = value;
	}
}


