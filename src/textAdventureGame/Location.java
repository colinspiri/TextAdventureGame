package textAdventureGame;
import java.util.*;

public class Location {
	//basics
	private String name;
	private String description1;
	private String description2;
	private ArrayList<Exit> exits;

	//interactions
	private ArrayList<Interaction> interactions;

	//constructor
	public Location(String tempName, String description1, String description2) {
		name = tempName;
		this.description1 = description1;
		this.description2 = description2;
		exits = new ArrayList<Exit>();
		interactions = new ArrayList<Interaction>();
	}

	//toString
	public String toString() {
		//first part of the description
		String returnedString = description1;

		//interactions
		for(Interaction i : interactions) {
			if(i.hasBeenInteractedWith() == false) returnedString += " " + i.getPreview();
		}

		//second part of the description
		returnedString += " " + description2;

		//describe any exits
		for(Exit exit : exits) {
			returnedString += " " + exit.getDescription();
		}
		
		//add any developer notes
		returnedString += this.toStringDev();

		returnedString += "\n";
		return returnedString;
	}
	public String toStringDev() {
		//to print anything meant to be ignored by the normal player

		String returnedString = "";

		//items
		ArrayList<Item> items = this.getItems();
		if(items.size() > 0) {
			returnedString += "\n" + "---Items: ";
			for(int a = 0; a < items.size(); a++) {
				Item i = items.get(a);
				returnedString += i.getName();
				if(a < items.size() - 1) {
					returnedString += ", ";
				}
			}
		}

		//exits
		if(exits.size() > 0) {
			returnedString += "\n" + "---Exits: ";
			for(Exit exit : exits) {
				returnedString += exit.toString() + "\n";
			}
		}

		returnedString += "\n";
		return returnedString;
	}

	//get methods
	public String getName() { 
		return name; 
	}
	public String getDescription() {
		return description1 + description2;
	}
	public ArrayList<Exit> getExits() { 
		return exits; 
	}
	public ArrayList<Interaction> getInteractions() {
		return interactions;
	}
	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		for(Interaction i : interactions) {
			if(i.getItem() instanceof Item) items.add(i.getItem());
		}
		return items;
	}

	//set methods
	public void setName(String tempName) {
		name = tempName;
	}
	public void setDescription(String description1, String description2) {
		this.description1 = description1;
		this.description2 = description2;
	}
	public void addExit(Exit newExit) {
		exits.add(newExit);
	}
	public void removeExit(Exit newExit) {
		if(exits.contains(newExit)) {
			exits.remove(newExit);
		}
	}
	public void addInteraction(Interaction newInteraction) {
		interactions.add(newInteraction);
	}
	public void removeInteraction(Item newInteraction) {
		if(interactions.contains(newInteraction)) {
			interactions.remove(newInteraction);
		}
	}
	public void removeItem(Item item) {
		for(int a = 0; a < interactions.size(); a++) {
			if(interactions.get(a).getItem() != null) {
				if(interactions.get(a).getItem().getName().equalsIgnoreCase(item.getName())) {
					interactions.remove(interactions.get(a));
				}
			}
		}
	}
}