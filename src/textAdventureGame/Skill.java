package textAdventureGame;

public abstract class Skill {
	private String name;
	private int level;
	private int mana;
	
	private boolean queued;
	private String speed;

	//constructor
	public Skill(String tempName, int tempLevel, String tempSpeed) {
		name = tempName;
		level = tempLevel;
		queued = false;
		speed = tempSpeed;
	}

	//abstract methods
	public abstract void execute(Creature source, Creature target);
	protected abstract void update();
	public abstract String getDescription();

	//toString methods
	public String toStringShort() {
		String returnedString = new String();
		returnedString += getName() + " (level " + getLevel() + ") ";
		
		return returnedString;
	}
	public String toString() {
		String returnedString = new String();
		if(getLevel() <= 0) {
			returnedString += "You have not learned this skill. At level 1:" + "\n";
			returnedString += name + " (level 1)" + "\n";
		}
		else returnedString += getName() + " (level " + getLevel() + ") " + "\n";
		returnedString += mana + " mana. " + getDescription();

		return returnedString;
	}

	//get methods
	public String getName() {
		return name;
	}
	public int getLevel() {
		return level;
	}
	public int getMana() {
		return mana;
	}
	public boolean isQueued() {
		return queued;
	}

	//set methods
	public void levelUp() {
		level++;
		update();
	}
	public void setMana(int newMana) {
		mana = newMana;
	}
	public void setQueued(boolean value) {
		queued = value;
	}
}







