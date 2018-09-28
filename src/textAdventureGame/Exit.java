package textAdventureGame;

public class Exit {
	//numerical codes
	public static final int NORTH = 0;
	public static final int SOUTH = 1;
	public static final int EAST = 2;
	public static final int WEST = 3;
	public static final int NORTHEAST = 4;
	public static final int NORTHWEST = 5;
	public static final int SOUTHEAST = 6;
	public static final int SOUTHWEST = 7;
	public static final int UP = 8;
	public static final int DOWN = 9;

	//String codes
	public static final String[] directionNames = {
			"NORTH",
			"SOUTH",
			"EAST",
			"WEST",
			"NORTHEAST",
			"NORTHWEST",
			"SOUTHEAST",
			"SOUTHWEST",
			"UP",
			"DOWN"
	};


	private Location leadsTo = null;
	private String direction;
	private String description;

	//constructor using numerical codes for directions
	public Exit(int directionCode, Location leadsTo, String description) {
		if(directionCode <= directionNames.length) direction = directionNames[directionCode];
		else throw new IllegalArgumentException(directionCode + " is not a valid direction code!");

		this.leadsTo = leadsTo;
		this.description = description;
	}

	//constructor using custom String direction
	public Exit(String direction, Location leadsTo, String description) {
		this.direction = direction;
		this.leadsTo = leadsTo;
		this.description = description;
	}

	//toString
	public String toString() {
		return direction + " to " + leadsTo.getName();
	}

	//get methods
	public String getDirection() {
		return direction;
	}
	public Location getLeadsTo() {
		return leadsTo;
	}
	public String getDescription() {
		return description;
	}
}







