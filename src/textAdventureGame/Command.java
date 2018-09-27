package textAdventureGame;

public abstract class Command {


	//location
	public abstract static class Look {
		public static void showTutorial() {
			//prints the command's tutorial
			System.out.println("look --- The \"look\" command gives you a description of your current location.");
		}

		public static void run(Location location) {
			//prints the location
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
				if(direction.equalsIgnoreCase(exit.getDirectionName())) {
					pc.setCurrentLocation(exit.getLeadsTo());
					System.out.println();
					System.out.println(pc.getCurrentLocation().getDescription());
					return;
				}
			}
			System.out.println("You can't go " + direction + ".");
		}	
	}

	//items
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

	////	private static void take(String input, PlayerCharacter pc) {
	//	//take item from container
	//	if(input.contains(" from ")) {
	//		String itemName = input.substring(5, input.indexOf(" from "));
	//		String containerName = input.substring(input.indexOf(" from ") + 6);
	//
	//		Item item = null;
	//		Container container = null;
	//
	//		System.out.println(containerName);
	//
	//		//find container
	//		//check current location
	//		for(Item i : pc.getCurrentLocation().getItems()) {
	//			if(i instanceof Container && i.getName().equalsIgnoreCase(containerName)) {
	//				container = (Container) i;
	//			}
	//		}
	//		//check inventory
	//		for(Item i : pc.getInventory()) {
	//			if(i instanceof Container && i.getName().equalsIgnoreCase(containerName)) {
	//				container = (Container) i;
	//			}
	//		}
	//
	//		if(container == null) {
	//			System.out.println("Requested container doesn't exist in your location or your inventory.");
	//			return;
	//		}
	//
	//		//find item in container
	//		for(int a = 0; a < container.getStoredItems().size(); a++) {
	//			if(container.getStoredItems().get(a).getName().equalsIgnoreCase(itemName)) {
	//				item = container.getStoredItems().get(a);
	//				if(container.isOpen()) {
	//					pc.addToInventory(item);
	//					container.attemptToRemove(item);
	//					System.out.println("Taken from " + container.getName() + ".");
	//					return;
	//				}
	//				else {
	//					System.out.println("The " + container.getName() + " is closed.");
	//					return;
	//				}
	//			}
	//		}
	//	}
	//
	//	//take item from location
	//	if(input.contains(" from ") == false) {
	//		String itemInput = input.substring(5);
	//
	//		//item in current location
	//		for(int a = 0; a < pc.getCurrentLocation().getItems().size(); a++) {
	//			Item i = pc.getCurrentLocation().getItems().get(a);
	//
	//			//match
	//			if(i.getName().equalsIgnoreCase(itemInput)) {
	//				pc.addToInventory(i);
	//				pc.getCurrentLocation().removeItem(i);
	//				System.out.println("Taken.");
	//				return;
	//			}
	//		}
	//	}
	//	System.out.println("No such item exists here.");

	public abstract static class Take {
		public static void showTutorial() { 
			//prints the command's tutorial
			System.out.println("take --- The \"take\" command puts an item from your current location in your inventory."
					+ "The \"take\" command takes an item as input. Some items are not able to be taken.");
		} 

		public static void run(String itemRequested, String containerName, PlayerCharacter pc) {
			
			//if a container was requested
			if(containerName != "") {
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
				System.out.println("Requested container isn't in your current location or your inventory.");

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
			
			//if no container requested, take the item from the current location
			else {
				for(int i = 0; i < pc.getCurrentLocation().getItems().size(); i++) {
					Item item = pc.getCurrentLocation().getItems().get(i);
					
				}
			}
			
		}
	}
}
