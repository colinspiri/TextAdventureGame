package textAdventureGame;

import java.util.Scanner;

public class Tavern extends Location {
	private int drinkCost;

	public Tavern(String name, String description, int drinkCost) {
		super(name, description);
		this.drinkCost = drinkCost;
	}

	//TODO
	//tavern input methods
	public void lookForInput(String input, PlayerCharacter pc) {
		if(input.equalsIgnoreCase("buy drink")) {
			buyDrink(pc);
			return;
		}
		else {
			System.out.println("Command not recognized.");
		}
	}
	private static void buyDrink(PlayerCharacter pc) {
		int drinkCost = ((Tavern) pc.getCurrentLocation()).getDrinkCost();
		if(pc.canAfford(drinkCost)) {
			pc.removeGold(drinkCost);
			System.out.println("You put " + drinkCost + " gold pieces on the bar and the bartender hands you a drink.");
			System.out.println("While drinking your liquor, the bartender offers you another one for the same price. Accept?");
			//ask for another drink
			Scanner keyboard = new Scanner(System.in);
			while(true) {
				String input = keyboard.nextLine();
				if(input.equalsIgnoreCase("yes")) {
					if(pc.canAfford(drinkCost)) {
						pc.removeGold(drinkCost);
						System.out.println("You dig " + drinkCost + " more gold pieces out of your bag and exchange them for a drink. "
								+ "You let the smooth liquid flow down your throat, then you start to feel dizzy. You lose your balance "
								+ "and stumble into a large man. He turns around, looks at your clumsy self, then punches you in the face.");
						//START COMBAT HERE WITH MAN ATTACKING
						//TODO
						return;
					}
					else {
						System.out.println("You start looking for gold in your pack. You don't immediately find any, and once the bartender "
								+ "notices that you're out of money, he walks away and starts serving another customer.");
						return;
					}
				}
				else if(input.equalsIgnoreCase("no")) {
					System.out.println("You decline the bartender's offer.");
					return;
				}
				else {
					System.out.println("Command not recognized. Expected YES or NO answer.");
				}
			}
		}
		else {
			System.out.println("You cannot afford to buy a drink.");
			return;
		}
	}

	//toString
	public String toString() {
		String returnedString = super.toString() + "\n";

		returnedString += "This tavern sells drinks for " + drinkCost + " gold pieces each.";

		return returnedString;
	}

	//get methods
	public int getDrinkCost() {
		return drinkCost;
	}
}


