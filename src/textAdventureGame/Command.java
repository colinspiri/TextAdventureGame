package textAdventureGame;

public abstract class Command {
	
	
	public abstract static class Help {
		
	}

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
					System.out.println("You");
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
	


}
