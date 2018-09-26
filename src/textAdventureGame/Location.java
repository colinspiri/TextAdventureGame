package textAdventureGame;
import java.util.*;

public class Location {
	//basics
	private String name;
	private String description;
	private ArrayList<Exit> exits;

	//interactions
	private ArrayList<Interaction> interactions;

	//full constructor
	public Location(String tempName, String tempDescription) {
		name = tempName;
		description = tempDescription;
		exits = new ArrayList<Exit>();
		interactions = new ArrayList<Interaction>();
	}

	//toString
	public String toString() {
		//basic description
		String returnedString = description;

		//interactions
		for(Interaction i : interactions) {
			if(i.hasBeenInteractedWith() == false) returnedString += " " + i.getPreview();
		}
		
		//items
		ArrayList<Item> items = this.getItems();
		if(items.size() > 0) {
			returnedString += "\n" + "Items here: ";
			for(int a = 0; a < items.size(); a++) {
				Item i = items.get(a);
				returnedString += i.getName();
				if(a < items.size() - 1) {
					returnedString += ", ";
				}
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
		return description;
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
	public void setDescription(String tempDescription) {
		description = tempDescription;
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