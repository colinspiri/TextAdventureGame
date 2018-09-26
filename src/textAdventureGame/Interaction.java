package textAdventureGame;

public class Interaction {
	//basics
	private String name;
	private String preview;
	private String aftermath;
	private boolean interactedWith;
	
	//creature or item
	Creature creature;
	Item item;
	
	//creature constructor
	public Interaction(Creature c, String preview, String aftermath) {
		creature = c;
		this.preview = preview;
		this.aftermath = aftermath;
	}
	
	//item constructor
	public Interaction(Item i, String preview, String aftermath) {
		item = i;
		this.preview = preview;
		this.aftermath = aftermath;
	}
	
	//get methods
	public String getName() {
		return name;
	}
	public String getPreview() {
		return preview;
	}
	public String getAftermath() {
		return aftermath;
	}
	public boolean hasBeenInteractedWith() {
		return interactedWith;
	}
	public Creature getCreature() {
		return creature;
	}
	public Item getItem() {
		return item;
	}
}







