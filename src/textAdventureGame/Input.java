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
			if(input.equalsIgnoreCase("tutorial") || input.equalsIgnoreCase("help")) {
				tutorial();
				continue;
			}
			else if(input.equalsIgnoreCase("stats")) {
				stats(pc);
				continue;
			}
			else if(input.equalsIgnoreCase("inventory")) {
				inventory(pc);
				continue;
			}
			else if(input.startsWith("item ")) {
				item(input, pc);
				continue;
			}
			else if(input.equalsIgnoreCase("skills")) {
				skills(pc);
				continue;
			}
			else if(input.startsWith("skill ")) {
				skill(input, pc);
				continue;
			}
			else if(input.equalsIgnoreCase("info")) {
				info(pc);
				continue;
			}

			//exploring
			else if(input.equalsIgnoreCase("look")) {
				look(pc);
				continue;
			}
			else if(input.startsWith("go ")) {
				go(input, pc);
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
			else if(input.startsWith("equip ")) {
				equip(input, pc);
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
			else if(input.startsWith("eat ") || input.startsWith("drink ") || input.startsWith("consume ")) {
				consume(input, pc);
				return;
			}
			else if(input.startsWith("attack ") || input.startsWith("break ") || input.startsWith("destroy ")) {
				attack(input, pc);
				return;
			}
			else if(input.startsWith("talk to ")) {
				talkTo(input, pc);
				return;
			}
			else if(input.startsWith("give ")) {
				give(input, pc);
				return;
			}

			//location specific input
			if(pc.getCurrentLocation() instanceof Shop) {
				((Shop) pc.getCurrentLocation()).lookForInput(input, pc);
			}
			if(pc.getCurrentLocation() instanceof Tavern) {
				((Tavern) pc.getCurrentLocation()).lookForInput(input, pc);
			}
		}
	}

	//print
	private static void tutorial() {
		Main.showTutorial();
	}
	private static void stats(PlayerCharacter pc) {
		System.out.println(pc.toStringStats());
	}
	private static void inventory(PlayerCharacter pc) {
		System.out.println(pc.toStringInventory());
	}
	private static void item(String input, PlayerCharacter pc) {
		String itemRequested = input.substring(5);

		//find item in inventory
		for(Item item : pc.getInventory()) {
			if(item.getName().equalsIgnoreCase(itemRequested)) {
				System.out.println(item.toString());
				return;
			}
		}

		System.out.println("Item requested is not in your inventory.");
	}
	private static void skills(PlayerCharacter pc) {
		System.out.println(pc.toStringSkills());
	}
	private static void skill(String input, PlayerCharacter pc) {
		String skillName = input.substring(6);
		for(Skill s : pc.getSkills()) {
			if(skillName.equalsIgnoreCase(s.getName())) {
				System.out.println(s.toString());
				return;
			}
		}
	}
	private static void info(PlayerCharacter pc) {
		System.out.println(pc.toString());
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
						container.takeOut(item);
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
	private static void equip(String input, PlayerCharacter pc) {
		//get name of item wanted
		String itemInput = input.substring(6);

		//if item is in current location
		for(int a = 0; a < pc.getCurrentLocation().getItems().size(); a++) {
			Item i = pc.getCurrentLocation().getItems().get(a);

			//match
			if(i.getName().equalsIgnoreCase(itemInput)) {
				if(i instanceof Equippable) {
					pc.getCurrentLocation().removeItem(i);
					((Equippable) i).equip(pc);
					System.out.println(i.getName() + " taken and equipped.");
					return;
				}
				else {
					System.out.println("Item requested is not equippable.");
				}
				return;
			}
		}

		//if item is in inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			Item i = pc.getInventory().get(a);

			//match
			if(i.getName().equalsIgnoreCase(itemInput)) {
				if(i instanceof Equippable) {
					pc.removeFromInventory(i);
					((Equippable) i).equip(pc);
					System.out.println(i.getName() + " equipped from inventory.");
					return;
				}
				else {
					System.out.println("Item requested is not equippable.");
				}
				return;
			}
		}

		System.out.println("Item requested is not in your location or your inventory.");
		return;
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
		if(container.canHold(movedItem.getWeight())) {
			//store item
			//check if open
			if(container.isOpen()) {
				container.store(movedItem);
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
	private static void consume(String input, PlayerCharacter pc) {
		//get name of item requested
		String itemInput;
		if(input.startsWith("consume ")) itemInput = input.substring(8);
		else if(input.startsWith("eat ")) itemInput = input.substring(4);
		else itemInput = input.substring(6);

		//if item is in current location
		for(int a = 0; a < pc.getCurrentLocation().getItems().size(); a++) {
			Item i = pc.getCurrentLocation().getItems().get(a);

			//match
			if(i.getName().equalsIgnoreCase(itemInput)) {
				if(i instanceof Consumable) {
					System.out.print(i.getName() + " taken and consumed. ");
					((Consumable) i).consume(pc);
					return;
				}
				else {
					System.out.println("Item requested is not consumable.");
				}
				return;
			}
		}

		//if item is in inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			Item i = pc.getInventory().get(a);

			//match
			if(i.getName().equalsIgnoreCase(itemInput)) {
				if(i instanceof Consumable) {
					pc.removeFromInventory(i);
					System.out.print(i.getName() + " taken from inventory and consumed. ");
					((Consumable) i).consume(pc);
					return;
				}
				else {
					System.out.println("Item requested is not consumable.");
				}
				return;
			}
		}
	}

	//exploring
	private static void look(PlayerCharacter pc) {
		Main.showLocation(pc);
	}
	private static void attack(String input, PlayerCharacter pc) {
		//get name of item requested
		String nameInput;
		if(input.startsWith("attack ")) nameInput = input.substring(7);
		else if(input.startsWith("break ")) nameInput = input.substring(6);
		else nameInput = input.substring(8);

		//look for creatures in current location
		for(int a = 0; a < pc.getCurrentLocation().getCreatures().size(); a++) {
			Creature c = pc.getCurrentLocation().getCreatures().get(a);
			if(c.getName().equalsIgnoreCase(nameInput)) {
				pc.basicAttack(c);
				Main.engageCombat(pc, c);
			}
		}
		//look for item in current location
		Item targetItem = null;
		for(int a = 0; a < pc.getCurrentLocation().getItems().size(); a++) {
			Item i = pc.getCurrentLocation().getItems().get(a);
			if(i.getName().equalsIgnoreCase(nameInput)) {
				targetItem = i;
			}
		}

		//look for item in inventory
		for(int a = 0; a < pc.getInventory().size(); a++) {
			Item i = pc.getInventory().get(a);
			if(i.getName().equalsIgnoreCase(nameInput)) {
				targetItem = i;
			}
		}

		//if item not found, exit
		if(targetItem == null) {
			System.out.println("Requested item not found in current location or your inventory.");
			return;
		}

		//try to destroy target item
		//if bare hands
		if(pc.getWeapon() == null) {
			//if item is easily breakable
			if(targetItem.canDestroy(1)) {
				targetItem.destroy();

				pc.getCurrentLocation().removeItem(targetItem);
				pc.removeFromInventory(targetItem);

				System.out.println(targetItem.getName() + " destroyed with your bare hands.");
			}
			//if item is hard
			else {
				System.out.println("Cannot destroy " + targetItem.getName() + " with your bare hands.");
			}
		}
		//if weapon can destroy item
		else if(targetItem.canDestroy(pc.getWeapon().getHardness())) {
			targetItem.destroy();

			pc.getCurrentLocation().removeItem(targetItem);
			pc.removeFromInventory(targetItem);

			System.out.println(targetItem.getName() + " destroyed with your " + pc.getWeapon().getName() + ".");
		}
		//if weapon is too weak
		else {
			System.out.println("Cannot destroy " + targetItem.getName() + " with your " + pc.getWeapon().getName() + ".");
		}
	}
	private static void talkTo(String input, PlayerCharacter pc) {
		String nameInput = input.substring(8);

		//look for creatures in current location
		for(Creature c : pc.getCurrentLocation().getCreatures()) {
			if(c.getName().equalsIgnoreCase(nameInput)) {
				if(c instanceof NPC) {
					((NPC) c).talk();
					return;
				}
				else {
					System.out.println(c.getName() + "doesn't talk, despite your prodding.");
					return;
				}
			}
		}
		System.out.println("No such creature is here.");
		return;
	}
	private static void give(String input, PlayerCharacter pc) {
		//order: 'give ITEM to CREATURE'
		String itemName = input.substring(5, input.indexOf(" to"));
		String creatureName = input.substring(input.indexOf(" to") + 4);
		Item item = null;
		Creature creature = null;

		//check inventory for item
		for(int a = 0; a < pc.getInventory().size(); a++) {
			Item i = pc.getInventory().get(a);
			if(i.getName().equalsIgnoreCase(itemName)) {
				item = i;
			}
		}
		if(item == null) {
			System.out.println("Requested item not found in inventory.");
			return;
		}

		//check location for creature
		for(Creature c : pc.getCurrentLocation().getCreatures()) {
			if(c.getName().equalsIgnoreCase(creatureName)) {
				creature = c;
			}
		}
		if(creature == null) {
			System.out.println("Requested creature not found in current location.");
			return;
		}

		//give item to creature
		if(creature instanceof Enemy) {
			System.out.println("The " + creature.getName() + " ignores your offering.");
			return;
		}
		else if(creature instanceof NPC) {
			((NPC) creature).give(item);
			pc.removeFromInventory(item);
			return;
		}
	}
	private static void go(String input, PlayerCharacter pc) {
		String direction = input.substring(3);
		for(Exit e : pc.getCurrentLocation().getExits()) {
			if(direction.equalsIgnoreCase(e.getDirectionName())) {
				pc.setCurrentLocation(e.getLeadsTo());
				Main.showLocation(pc);
				return;
			}
		}
		System.out.println("Direction not available.");
		return;
	}
}


