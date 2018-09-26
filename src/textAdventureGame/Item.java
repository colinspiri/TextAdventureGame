package textAdventureGame;

public abstract class Item {
	//variables
	private String name;
	private String description;
	private int size;
	
	//constructor
	public Item(String name, String description, int size) {
		this.name = name;
		this.description = description;
		this.size = size;
	}
	
	//methods
	//toString
	public String toString() {
		//name
		String returnedString = name + " (";
		
		//size
		returnedString += "size: " + size;
		if(size == 1) returnedString += " slot";
		else returnedString += " slots";
		returnedString += ")" + "\n";
		
		//description
		returnedString += description + "\n";
		
		//specifics, if any
		returnedString += this.toStringSpecifics();
		
		return returnedString;
	}
	public abstract String toStringSpecifics();
	
	//get methods
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getSize() {
		return size;
	}
}







