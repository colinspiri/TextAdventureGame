package textAdventureGame;

public abstract class Command {

	public abstract static class Help {
		//shows the usage of the command
		private static void showTutorial() {
			System.out.println("help --- The \"help\" command gives you a list of usable commands. When used with a command as an input, "
					+ "the \"help\" command prints a description of the specified command's usage.");
		}
		
		//run command executed from Input class; parses the input and decides which version of the command to execute
		public static void run(String input) {
			//helpGeneral - no additional input
			if(input.equalsIgnoreCase("help")) {
				Command.Help.helpGeneral();
			}
			//helpWithCommand - takes name of command as input
			else {
				String commandRequested = input.substring(5);
				Command.Help.helpWithCommand(commandRequested);
			}
		}

		//prints a list of all available commands
		public static void helpGeneral() {
			System.out.println("Commands:");
			System.out.println(" help");
			System.out.println(" look");
			System.out.println(" go");
			System.out.println(" inspect");
			System.out.println(" take");
			System.out.println(" drop");
			System.out.println(" put");
			System.out.println(" open");
			System.out.println(" close");
			System.out.println();
		}

		//prints the specific usage of a given command
		public static void helpWithCommand(String commandRequested) {
			switch (commandRequested) {
			case "help" : Help.showTutorial();
			break;
			case "look" : Look.showTutorial();
			break;
			case "go" : Go.showTutorial();
			break;
			case "inspect" : Inspect.showTutorial();
			break;
			case "take" : Take.showTutorial();
			break;
			case "drop" : Drop.showTutorial();
			break;
			case "put" : Put.showTutorial();
			break;
			case "open" : Open.showTutorial();
			break;
			case "close" : Close.showTutorial();
			break;
			default : Help.helpGeneral();
			}

			System.out.println();
		}
	}

	public abstract static class Look {
		private static void showTutorial() {
			System.out.println("look --- The \"look\" command gives you a description of your current location.");
		}

		//only use of look command; prints the current location
		public static void look(Location location) {
			System.out.println();
			System.out.println(location.toString());	
		}
	}

	public abstract static class Go {
		public static void showTutorial() {
			//prints the command's tutorial
			System.out.println("go --- The \"go\" command navigates you to a different location. As input, "
					+ "the \"go\" command takes a direction (e.g. north, west, down, up, in, out)");
		}

		public static void run(String direction, PlayerCharacter pc) {
			//checks for Exits in the pc's location that match the requested direction
			for(Exit exit : pc.getCurrentLocation().getExits()) {
				if(exit.getDirection().equalsIgnoreCase(direction)) {
					pc.setCurrentLocation(exit.getLeadsTo());
					System.out.println();
					System.out.println(pc.getCurrentLocation().toString());
					return;
				}
			}
			System.out.println("You can't go " + direction + ".");
		}	
	}

	public abstract static class Inspect {
		public static void showTutorial() {
			//prints the command's tutorial
			System.out.println("inspect --- The \"inspect\" command gives you a description of a certain object. As input, "
					+ "the \"inspect\" command takes an object to be inspected.");
		}

		public static void run(String itemRequested, PlayerCharacter pc) {
			//look for item in inventory
			for(Item item : pc.getInventory()) {
				if(item.getName().equalsIgnoreCase(itemRequested)) {
					System.out.println(item.toString());
					return;
				}
			}

			//look for item in current location
			for(Item item : pc.getCurrentLocation().getItems()) {
				if(item.getName().equalsIgnoreCase(itemRequested)) {
					System.out.println(item.toString());
					return;
				}
			}

			//if item isn't found anywhere, print message
			System.out.println("Item requested is not in your inventory or your current location.");
		}
	}

	public abstract static class Inventory {
		public static void showTutorial() {
			//prints the command's tutorial
			System.out.println("inventory --- The \"inventory\" command gives you a list of items currently stored in "
					+ "your inventory. To get more information about a specific item, use the \"inspect\" command.");
		}

		public static void run(PlayerCharacter pc) {
			System.out.println(pc.toStringInventory());
		}
	}

	public abstract static class Take {
		public static void showTutorial() { 
			//prints the command's tutorial
			System.out.println("take --- The \"take\" command puts an item from your current location in your inventory."
					+ "The \"take\" command takes an item as input. Some items are not able to be taken.");
		} 

		public static void run(String itemRequested, PlayerCharacter pc) {
			for(int i = 0; i < pc.getCurrentLocation().getItems().size(); i++) {
				Item item = pc.getCurrentLocation().getItems().get(i);

				if(item.getName().equalsIgnoreCase(itemRequested)) {
					pc.addToInventory(item);
					pc.getCurrentLocation().removeItem(item);
					System.out.println("Taken.");
					return;
				}
			}
			System.out.println("No such item exists in your current location.");
		}

		public static void run(String itemRequested, String containerName, PlayerCharacter pc) {
			Container container = null;

			//look in the current location for the container
			for(Item item : pc.getCurrentLocation().getItems()) {
				if(item.getName().equalsIgnoreCase(containerName)) {
					if(item instanceof Container) container = (Container) item;
					else {
						System.out.println("The " + item.getName() + " is not a container.");
						return;
					}
				}
			}
			//look in pc's inventory for the container
			for(Item item : pc.getInventory()) {
				if(item.getName().equalsIgnoreCase(containerName)) {
					if(item instanceof Container) container = (Container) item;
					else {
						System.out.println("The " + item.getName() + " is not a container.");
						return; 
					}
				}
			}

			//if no container was found, throw error message
			if(container == null) {
				System.out.println("Requested container isn't in your current location or your inventory.");
				return;
			}

			//container exists
			//find the item from the container
			for(int i = 0; i < container.getStoredItems().size(); i++) {
				Item item = container.getStoredItems().get(i);
				//if the name of the item matches, 
				if(item.getName().equalsIgnoreCase(itemRequested)) {
					//if container is open, take the item
					if(container.isOpen()) {
						pc.addToInventory(item);
						container.removeItem(item);
						System.out.println("Taken from " + container.getName() + ".");

					}
					//if container is closed, print error msg
					else {
						System.out.println("The " + container.getName() + " is closed.");
					}

				}
			}
		}
	}

	public abstract static class Drop {
		public static void showTutorial() {
			//prints the command's tutorial
			System.out.println("drop --- The \"drop\" command drops an item from your inventory, leaving it on the ground "
					+ "in your current location. The \"drop\" command takes an item as input.");
		}

		public static void run(String itemRequested, PlayerCharacter pc) {
			//check inventory for item
			for(int i = 0; i < pc.getInventory().size(); i++) {
				Item item = pc.getInventory().get(i);

				//on a match, create an Interaction to store the item
				if(item.getName().equalsIgnoreCase(itemRequested)) {
					pc.removeFromInventory(item);
					String itemInteractionDescription;
					//decide whether or not to use "a" or "an" in saying "An item lies on the ground."
					if(item.getName().startsWith("a") || item.getName().startsWith("e") || item.getName().startsWith("i")) itemInteractionDescription = "An " + item.getName() + " lies on the ground.";
					else itemInteractionDescription = "A " + item.getName() + " lies on the ground.";
					//create the interaction and print a response
					pc.getCurrentLocation().addInteraction(new Interaction(item, itemInteractionDescription, ""));
					System.out.println("You dropped the " + item.getName() + " at your location.");
					return;
				}
			}
		}
	}

	public abstract static class Put {
		public static void showTutorial() {
			System.out.println("put --- The \"put\" command is used to put an item in a container. If a container is full, "
					+ "no more items can be stored in it. Additionally, you cannot put a container in another container. As input, "
					+ "the \"put\" command takes an item to be stored and a container to store the item in.");
		}

		public static void run(String itemRequested, String containerRequested, PlayerCharacter pc) {
			Item item = null;
			Container container = null;

			//check current location for item and container
			for(Item searchedItem : pc.getCurrentLocation().getItems()) {

				//if it matches item
				if(searchedItem.getName().equalsIgnoreCase(itemRequested)) {
					item = searchedItem;
				}

				//if it matches container and is actually a container
				if(searchedItem.getName().equalsIgnoreCase(containerRequested) && searchedItem instanceof Container) {
					container = (Container) searchedItem;
				}
			}
			//check inventory for item and container
			for(Item searchedItem : pc.getInventory()) {

				//if it matches item
				if(searchedItem.getName().equalsIgnoreCase(itemRequested)) {
					item = searchedItem;
				}

				//if it matches container and is actually a container
				if(searchedItem.getName().equalsIgnoreCase(containerRequested) && searchedItem instanceof Container) {
					container = (Container) searchedItem;
				}
			}

			//check to make sure item and container are both valid
			if(item == null) {
				System.out.println("Your requested item is not in your current location or your inventory.");
				return;
			}
			if(container == null) {
				System.out.println("Your requested container is not in your current location or your inventory.");
				return;
			}
			if(item instanceof Container) {
				System.out.println("You cannot store a container inside another container.");
				return;
			}

			//if both were found, store item in container

			//if container has room
			if(container.canHold(item.getSize())) {
				//if container is open
				if(container.isOpen()) {
					container.addItem(item);
					pc.removeFromInventory(item);
					pc.getCurrentLocation().removeItem(item);
					System.out.println("The " + item.getName() + " was put in the " + container.getName() + ".");
					return;
				}
				else {
					System.out.println("The " + container.getName() + " is closed.");
					return;
				}
			}
			else {
				System.out.println("The "+ container.getName() + " is full.");
				return;
			}
		}
	}

	public abstract static class Open {
		public static void showTutorial() {
			System.out.println("open --- The \"open\" command opens a container. As input, the \"open\" command "
					+ "takes the name of a container in your inventory or current location.");
		}

		public static void run(String containerRequested, PlayerCharacter pc) {

			//look in current location
			for(Item item : pc.getCurrentLocation().getItems()) {
				if(item instanceof Container) {
					Container container = (Container) item;
					if(container.getName().equalsIgnoreCase(containerRequested)) {
						container.open();
						System.out.println(container.toString());
						return;
					}
				}
			}

			//look in pc's inventory
			for(Item item : pc.getInventory()) {
				if(item instanceof Container) {
					Container container = (Container) item;
					if(container.getName().equalsIgnoreCase(containerRequested)) {
						container.open();
						System.out.println(container.toString());
						return;
					}
				}
			}
		}
	}

	public abstract static class Close {
		public static void showTutorial() {
			System.out.println("close --- The \"close\" command closes a container. As input, the \"close\" command "
					+ "takes the name of a container in your inventory or current location.");
		}

		public static void run(String containerRequested, PlayerCharacter pc) {

			//look in current location
			for(Item item : pc.getCurrentLocation().getItems()) {
				if(item instanceof Container) {
					Container container = (Container) item;
					if(container.getName().equalsIgnoreCase(containerRequested)) {
						container.close();
						System.out.println("The " + container.getName() + " was closed.");
						return;
					}
				}
			}

			//look in pc's inventory
			for(Item item : pc.getInventory()) {
				if(item instanceof Container) {
					Container container = (Container) item;
					if(container.getName().equalsIgnoreCase(containerRequested)) {
						container.close();
						System.out.println("The " + container.getName() + " was closed.");
						return;
					}
				}
			}
		}
	}


}
