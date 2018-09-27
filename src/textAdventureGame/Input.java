package textAdventureGame;
import java.util.*;

public class Input {

	//look for input
	public static void lookForInput(PlayerCharacter pc) {
		
		//ask for input
		Scanner keyboard = new Scanner(System.in);

		while(true) {
			System.out.print("> ");
			String input = keyboard.nextLine();

			//print
			if(input.startsWith("inspect ")) {
				String itemRequested = input.substring(8);
				Command.Inspect.run(itemRequested, pc);
				continue;
			}
			else if (input.equalsIgnoreCase("inventory")) {
				Command.Inventory.run(pc);
			}
			
			else if(input.startsWith("help ")) {
				Command.Look.run(pc.getCurrentLocation());
			}

			//exploring
			else if(input.equalsIgnoreCase("look")) {
				Command.Look.run(pc.getCurrentLocation());
				continue;
			}
			else if(input.startsWith("go ")) {
				String direction = input.substring(3);
				Command.Go.run(direction, pc);
				return;
			}


			//item management and interaction
			else if(input.startsWith("take ")) {
				take(input, pc);
				return;
			}
			else if(input.startsWith("drop ")) {
				drop(input, pc);
				return;
			}
			else if(input.startsWith("open ")) {
				open(input, pc);
				return;
			}
			else if(input.startsWith("close ")) {
				close(input, pc);
				return;
			}
			else if(input.startsWith("put ")) {
				put(input, pc);
				return;
			}
		}
	}

	//items
	private static void take(String input, PlayerCharacter pc) {
		//take item from container
		if(input.contains(" from ")) {
			String itemName = input.substring(5, input.indexOf(" from "));
			String containerName = input.substring(input.indexOf(" from ") + 6);

			Item item = null;
			Container container = null;

			System.out.println(containerName);

			//find container
			//check current location
			for(Item i : pc.getCurrentLocation().getItems()) {
				if(i instanceof Container && i.getName().equalsIgnoreCase(containerName)) {
					container = (Container) i;
				}
			}
			//check inventory
			for(Item i : pc.getInventory()) {
				if(i instanceof Container && i.getName().equalsIgnoreCase(containerName)) {
					container = (Container) i;
				}
			}

			if(container == null) {
				System.out.println("Requested container doesn't exist in your location or your inventory.");
				return;
			}

			//find item in container
			for(int a = 0; a < container.getStoredItems().size(); a++) {
				if(container.getStoredItems().get(a).getName().equalsIgnoreCase(itemName)) {
					item = container.getStoredItems().get(a);
					if(container.isOpen()) {
						pc.addToInventory(item);
						container.attemptToRemove(item);
						System.out.println("Taken from " + container.getName() + ".");
						return;
					}
					else {
						System.out.println("The " + container.getName() + " is closed.");
						return;
					}
				}
			}
		}

		//take item from location
		if(input.contains(" from ") == false) {
			String itemInput = input.substring(5);

			//item in current location
			for(int a = 0; a < pc.getCurrentLocation().getItems().size(); a++) {
				Item i = pc.getCurrentLocation().getItems().get(a);

				//match
				if(i.getName().equalsIgnoreCase(itemInput)) {
					pc.addToInventory(i);
					pc.getCurrentLocation().removeItem(i);
					System.out.println("Taken.");
					return;
				}
			}
		}
		System.out.println("No such item exists here.");
	}
	private static void drop(String input, PlayerCharacter pc) {
		//name of item
		String itemInput = input.substring(5);

		//check inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			Item i = pc.getInventory().get(a);

			//match
			if(i.getName().equalsIgnoreCase(itemInput)) {
				pc.removeFromInventory(i);
				//"a stick" vs. "an apple"
				if(i.getName().startsWith("a")) {
					pc.getCurrentLocation().addInteraction(new Interaction(i, "An " + i.getName() + " lies on the ground.", ""));
				}
				else {
					pc.getCurrentLocation().addInteraction(new Interaction(i, "A " + i.getName() + " lies on the ground.", ""));
				}
				System.out.println(i.getName() + " dropped at your location.");
				return;
			}
		}
		System.out.println("You don't have that item.");
	}
	private static void open(String input, PlayerCharacter pc) {
		String containerName = input.substring(5);

		//check current location
		ArrayList<Item> list = pc.getCurrentLocation().getItems();
		for(int a = 0; a < list.size(); a++) {
			if(list.get(a) instanceof Container) {
				Container c = (Container) list.get(a);
				if(c.getName().equalsIgnoreCase(containerName)) {
					c.open();
					System.out.println(c.toString());
					return;
				}
			}
		}

		//check inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			if(pc.getInventory().get(a) instanceof Container) {
				Container c = (Container) pc.getInventory().get(a);
				if(c.getName().equalsIgnoreCase(containerName)) {
					c.open();
					System.out.println(c.toString());
					return;
				}
			}
		}
	}
	private static void close(String input, PlayerCharacter pc) {
		String containerName = input.substring(6);

		//check current location
		ArrayList<Item> list = pc.getCurrentLocation().getItems();
		for(int a = 0; a < list.size(); a++) {
			if(list.get(a) instanceof Container) {
				Container c = (Container) list.get(a);
				if(c.getName().equalsIgnoreCase(containerName)) {
					c.close();
					System.out.println(c.getName() + " closed.");
					return;
				}
			}
		}

		//check inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			if(pc.getInventory().get(a) instanceof Container) {
				Container c = (Container) pc.getInventory().get(a);
				if(c.getName().equalsIgnoreCase(containerName)) {
					c.close();
					System.out.println(c.getName() + " closed.");
					return;
				}
			}
		}
	}
	private static void put(String input, PlayerCharacter pc) {
		//order: 'put MOVEDITEM in CONTAINER'
		String movedItemName = input.substring(4, input.indexOf(" in"));
		String containerName = input.substring(input.indexOf(" in") + 4);

		Item movedItem = null;
		Container container = null;

		//check current location
		for(int a = 0; a < pc.getCurrentLocation().getItems().size(); a++) {
			Item i = pc.getCurrentLocation().getItems().get(a);

			//match movedItem
			if(i.getName().equalsIgnoreCase(movedItemName)) {
				movedItem = i;
			}
			//match container
			if(i.getName().equalsIgnoreCase(containerName) && i instanceof Container) {
				container = (Container) i;
			}
		}

		//check inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			Item i = pc.getInventory().get(a);

			//match movedItem
			if(i.getName().equalsIgnoreCase(movedItemName)) {
				movedItem = i;
			}
			//match container
			if(i.getName().equalsIgnoreCase(containerName) && i instanceof Container) {
				container = (Container) i;
			}
		}

		//couldn't find movedItem
		if(movedItem == null) {
			System.out.println("Item requested is not in your location or your inventory.");
			return;
		}
		//couldn't find container 
		if(container == null) {
			System.out.println("Container requested is not in your location or your inventory.");
			return;
		}
		//movedItem is a container
		if(movedItem instanceof Container) {
			System.out.println("Cannot put a container inside another container.");
			return;
		}

		//if both were found, try to store item in container
		if(container.canHold(movedItem.getSize())) {
			//store item
			//check if open
			if(container.isOpen()) {
				container.attemptToAdd(movedItem);
				System.out.println("The " + movedItem.getName() + " was put in the " + container.getName() + ".");
			}
			//if closed
			else {
				System.out.println("The " + container.getName() + " is closed.");
			}
		}
		else {
			System.out.println("The " + container.getName() + " doesn't have enough room.");
		}

		if(container.contains(movedItem)) {
			pc.getCurrentLocation().removeItem(movedItem);
			pc.removeFromInventory(movedItem);
		}
		return;
	}

}


