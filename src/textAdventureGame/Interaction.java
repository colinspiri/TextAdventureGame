package textAdventureGame;

public class Interaction {
	//basics
	private String name;
	private String preview;
	private String aftermath;
	private boolean interactedWith;

	Item item;
	
	//normal constructor
	public Interaction(String preview, String aftermath) {
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
	public Item getItem() {
		return item;
	}
}







