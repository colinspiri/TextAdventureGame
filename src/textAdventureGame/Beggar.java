package textAdventureGame;
import java.util.*;

public class Beggar extends NPC implements Talkable {
	private boolean hungry;

	public Beggar(String tempName) {
		super(tempName, 1);
		hungry = true;
	}

	//inherited
	public void updateMaxHealth() {
		setMaxHealth(20);
	}

	//inherited action methods
	public void talk() {
		String message = "";
		
		if(hungry) {
			Random rng = new Random();
			int randomNum = rng.nextInt(10) + 1;

			switch(randomNum) {
			case 1 : {
				message += "Kind one. Please spare some food for a fellow human in need.";
				break;
			}
			case 2 : {
				message += "May Kar'saq bless you. In the name of Kar'saq, save us all.";
				break;
			}
			case 3 : {
				message += "Just one piece. I need just one piece of food. One piece...please.";
				break;
			}
			case 4 : {
				message += "Help a lonely soul. Kar'saq praises you.";
				break;
			}
			case 5 : {
				message += "Let yourself give. Generosity leads to wealth.";
				break;
			}
			case 6 : {
				message += "I beg you. I'm starving. Food, anything, something.";
				break;
			}
			case 7 : {
				message += "To live in excess is to reject the path of Kar'saq. Food, please.";
				break;
			}
			case 8 : {
				message += "Young traveler...accept the path of Kar'saq and give a beggar some food.";
				break;
			}
			case 9 : {
				message += "please.";
				break;
			}
			case 10 : {
				message = "I have not eaten in forever. I need food...";
				break;
			}
			}
		}
		else {
			message = "Thank you!";
		}

		System.out.println("\"" + message + "\"");
	}
	public void give(Item i) {
		if(i instanceof Consumable) {
			System.out.print("Thank you for your generosity! Kar'saq blesses you... "
					+ "*The beggar starts eating* ");
			((Consumable) i).consume(this);
			hungry = false;
		}
		else {
			System.out.println("I don't have any use for this. Please give me some food.");
		}
	}
}




