package textAdventureGame;

public class Exit {
	//numerical codes
	public static final int UNDEFINED = 0;
	public static final int NORTH = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;
	public static final int WEST = 4;
	public static final int UP = 5;
	public static final int DOWN = 6;
	public static final int NORTHEAST = 7;
	public static final int NORTHWEST = 8;
	public static final int SOUTHEAST = 9;
	public static final int SOUTHWEST = 10;
	public static final int IN = 11;
	public static final int OUT = 12;
	
	//String codes
	public static final String[] dirNames = {
			"UNDEFINED",
			"NORTH",
			"SOUTH",
			"EAST",
			"WEST",
			"UP",
			"DOWN",
			"NORTHEAST",
			"NORTHWEST",
			"SOUTHEAST",
			"SOUTHWEST",
			"IN",
			"OUT"
	};

	
	private Location leadsTo = null;
	private int direction;
	
	private String directionName;
	
	//default constructor
	public Exit() {
		direction = Exit.UNDEFINED;
		leadsTo = null;
		directionName = dirNames[UNDEFINED];
	}
	
	//full constructor
	public Exit(int tempDirection, Location tempLeadsTo) {
		direction = tempDirection;
		if(tempDirection <= dirNames.length) {
			directionName = dirNames[tempDirection];
		}
		
		leadsTo = tempLeadsTo;
	}
	
	public String toString() {
		return directionName;
	}
	
	public void setDirectionName(String dirName) {
		directionName = dirName;
	}
	public void setLeadsTo(Location newLeadsTo) {
		leadsTo = newLeadsTo;
	}
	
	public String getDirectionName() {
		return directionName;
	}
	public Location getLeadsTo() {
		return leadsTo;
	}
}







