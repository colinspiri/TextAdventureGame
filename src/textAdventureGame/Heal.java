package textAdventureGame;

public class Heal extends Skill {
	private int healingAmount;

	//constructor
	public Heal(int level) {
		super("Heal", level, "normal");
		update();
	}

	//execute
	public void execute(Creature source, Creature target) {
		source.heal(healingAmount);

		System.out.println("---" + source.getName() + " healed for " + healingAmount + " health.");
	}

	//update methods
	protected void update() {
		if(getLevel() <= 0) {
			healingAmount = 2;
			setMana(2);
		}
		else {
			healingAmount = 2 + ( (getLevel() - 1) *3 );
			setMana( 2 + ( getLevel() - 1 ) );
		}
	}

	public String getDescription() {
		return "Heal yourself for " + healingAmount + " health.";
	}
}







