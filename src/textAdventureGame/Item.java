package textAdventureGame;

public abstract class Item {
	private String name;
	private String description;
	private int value;
	private int weight;
	private int hardness;
	
	//constructor no value (for paper and other simple items)
	public Item(String name, String description) {
		this.name = name;
		this.description = description;
		value = 0;
		weight = 0;
	}
	
	//constructor with value
	public Item(String name, String description, int value, int weight, int hardness) {
		this.name = name;
		this.description = description;
		this.value = value;
		this.weight = weight;
		this.hardness = hardness;
	}
	
	//hardness
	public boolean canDestroy(int effort) {
		if(effort >= hardness) return true;
		else return false;
	}
	public void destroy() {
		hardness = 0;
	}
	public boolean isDestoyed() {
		return hardness > 0;
	}
	
	//toString
	public String toString() {
		//basics
		String returnedString = name + " (";
		returnedString += value + " gp" + ", " + weight;
		if(weight == 1) returnedString += " pound";
		else returnedString += " pounds";
		returnedString += ")" + "\n";
		
		//description
		returnedString += description + "\n";
		
		//specifics
		returnedString += this.toStringSpecifics();
		
		return returnedString;
	}
	public abstract String toStringSpecifics();
	public String toStringShort() {
		String returnedString = name;
		return returnedString;
	}
	
	//get methods
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getValue() {
		return value;
	}
	public int getWeight() {
		return weight;
	}
	public int getHardness() {
		return hardness;
	}
}







