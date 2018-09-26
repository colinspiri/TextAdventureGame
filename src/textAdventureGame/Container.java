package textAdventureGame;
import java.util.*;

public class Container extends Item {
	//storing items
	private ArrayList<Item> stored;
	private int capacity;
	private int storedWeight;

	//state
	private boolean open;
	private boolean locked;

	public Container(String name, String description, int value, int weight, int capacity, int hardness) {
		super(name, description, value, weight, hardness);
		this.capacity = capacity;
		stored = new ArrayList<Item>();
		storedWeight = 0;
		open = false;
		locked = false;
	}

	//interact methods
	public void store(Item i) {
		if(open) {
			this.addItem(i);
		}
		else {
			System.out.println(this.getName() + " is closed.");
		}
	}
	public void takeOut(Item i) {
		if(open) {
			this.removeItem(i);
		}
		else {
			System.out.println(this.getName() + " is closed.");
		}
	}
	public void open() {
		if(!locked) {
			open = true;
		}
		else {
			System.out.println(this.getName() + " is locked.");
		}
	}
	public void close() {
		open = false;
	}
	public void unlock(int chance) {
		if(locked) {
			//chance is percent chance of unlocking
			int randomNum = (int) (Math.round(Math.random()*100));
			if(randomNum < chance) {
				locked = false;
				System.out.println("Unlocked " + this.getName() + ".");
			}
			else {
				System.out.println("Failed to unlock " + this.getName());
			}
		}
		else {
			System.out.println(this.getName() + " is already unlocked.");
		}
	}
	public boolean contains(Item item) {
		for(Item i : stored) {
			if(i.equals(item)) {
				return true;
			}
		}
		return false;
	}
	public boolean canHold(int amount) {
		return (storedWeight + amount <= capacity);
	}

	//toString
	public String toStringSpecifics() {
		String returnedString = "	capacity: " + storedWeight + "/" + capacity + " pounds" + "\n";
		returnedString += this.toStringContents();
		return returnedString;
	}
	private String toStringContents() {
		String returnedString = "";

		//if no contents
		if(stored.size() == 0) {
			returnedString += "	empty";
		}
		//print all items stored in container
		else {
			for(Item i : stored) {
				returnedString += "	" + i.toStringShort() + " (" + i.getWeight() + " lbs)" + "\n";
			}
		}

		return returnedString;
	}

	//get methods
	public ArrayList<Item> getStoredItems() {
		return stored;
	}
	public int getCarryingCapacity() {
		return capacity;
	}
	public int getStoredWeight() {
		return storedWeight;
	}
	public boolean isOpen() {
		return open;
	}
	public boolean isLocked() {
		return locked;
	}

	//set methods
	private void addItem(Item item) {
		if(canHold(item.getWeight())) {
			stored.add(item);
			storedWeight += item.getWeight();
		}
	}
	private void removeItem(Item item) {
		if(stored.contains(item)) {
			stored.remove(item);
			storedWeight -= item.getWeight();
		}
	}
	public void setOpen(boolean value) {
		open = value;
	}
	public void setLocked(boolean value) {
		locked = value;
	}
}


