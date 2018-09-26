package textAdventureGame;

public class Fireball extends Skill {
	private int damage;

	//constructor
	public Fireball(int level) {
		super("Fireball", level, "normal");
		update();
	}

	//execute
	public void execute(Creature source, Creature target) {
		int damageTaken = damage;

		damageTaken -= target.getBlockingDefense(source);
		if(damageTaken < 0 ) damageTaken = 0;

		target.takeDamage(damageTaken);

		System.out.println("---" + source.getName() + " hurled a fireball at " + target.getName() + " for " + damageTaken + " damage.");
	}

	//update methods
	protected void update() {
		if(getLevel() <= 0) {
			damage = 2;
			setMana(2);
		}
		else {
			damage = 2 + ( (getLevel() - 1) *3 );
			setMana( 2 + ( getLevel() - 1 ) );
		}
	}

	//description
	public String getDescription() {
		return "Hurl a fireball at your target, dealing " + damage + " damage.";
	}
}







