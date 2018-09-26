package textAdventureGame;
import java.util.*;

public class Shop extends Location {
	private ArrayList<Item> wares;

	//constructor
	public Shop(String name, String description, ArrayList<Item> wares) {
		super(name, description);
		this.wares = wares;
	}

	//shop input methods
	public void lookForInput(String input, PlayerCharacter pc) {
		if(input.startsWith("buy ")) {
			buyWares(input, pc);
			return;
		}
		else if(input.startsWith("sell ")) {
			sellWares(input, pc);
			return;
		}
		else {
			System.out.println("Command not recognized.");
		}
	}
	private static void buyWares(String input, PlayerCharacter pc) {
		String wareRequested = input.substring(4);

		Shop shop = (Shop) pc.getCurrentLocation();
		ArrayList<Item> wares = shop.getWares();

		//check all wares
		for(int a = 0; a < wares.size(); a++) {
			Item ware = wares.get(a);

			//match
			if(ware.getName().equalsIgnoreCase(wareRequested)) {
				//if can afford
				if(pc.canAfford(ware.getValue())) {
					//buy item
					System.out.println(ware.getName() + " bought.");
					pc.removeGold(ware.getValue());
					pc.addToInventory(ware);
					shop.removeWare(ware);
					return;
				}
				//if not enough funds
				else {
					System.out.println("Not enough funds.");
					return;
				}
			}
		}
		//if no match found
		System.out.println("That isn't for sale here.");
	}
	private static void sellWares(String input, PlayerCharacter pc) {
		String itemRequested = input.substring(5);

		Shop shop = (Shop) pc.getCurrentLocation();
		ArrayList<Item> wares = shop.getWares();

		//check inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			Item item = pc.getInventory().get(a);

			//match
			if(item.getName().equalsIgnoreCase(itemRequested)) {
				int itemSellingValue = (int)Math.round( (double)item.getValue()/2 );
				//verify sale
				System.out.println("The shop will buy " + item.getName() + " for " + itemSellingValue + " gold. Proceed?");
				Scanner keyboard = new Scanner(System.in);
				while(true) {
					String newInput = keyboard.nextLine();

					if(newInput.equalsIgnoreCase("yes")) {
						//sell item
						pc.removeFromInventory(item);
						shop.addWare(item);
						pc.addGold(itemSellingValue);
						System.out.println(item.getName() + " sold for " + itemSellingValue + " gold.");
						return;
					}
					else if(newInput.equalsIgnoreCase("no")) {
						//do nothing
						System.out.println(item.getName() + " not sold.");
						return;
					}
				}
			}
		}
	}


	//toString
	public String toString() {
		String returnedString = super.toString() + "\n";
		//print wares in addition to normal description of locations
		returnedString += "Wares: " + "\n";
		for(int a = 0; a < wares.size(); a++) {
			Item ware = wares.get(a);
			returnedString += ware.getName() + " (" + ware.getValue() + " gold)" + "\n";
		}
		return returnedString;
	}

	//get methods
	public ArrayList<Item> getWares() {
		return wares;
	}

	//set methods
	public void addWare(Item ware) {
		wares.add(ware);
	}
	public void removeWare(Item ware) {
		if(wares.contains(ware)) {
			wares.remove(ware);
		}
	}
}




