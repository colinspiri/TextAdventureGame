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
			
			
			if(input.startsWith("help")) Command.Help.run(input);
			

			//print
			if(input.startsWith("inspect ")) {
				String itemRequested = input.substring(8);
				Command.Inspect.run(itemRequested, pc);
				continue;
			}
			else if (input.equalsIgnoreCase("inventory")) {
				Command.Inventory.run(pc);
			}
			
			else if(input.startsWith("help")) {
				Command.Help.run(input);
			}

			//exploring
			else if(input.equalsIgnoreCase("look")) {
				Command.Look.look(pc.getCurrentLocation());
				continue;
			}
			else if(input.startsWith("go ")) {
				String direction = input.substring(3);
				Command.Go.run(direction, pc);
				return;
			}


			//item management and interaction
			else if(input.startsWith("take ")) {
				if(input.substring(5).contains("from")) {
					//taking from a container
					String itemRequested = input.substring(5, input.indexOf("from") - 1);
					String containerRequested = input.substring(input.indexOf("from") + 5);
					Command.Take.run(itemRequested, containerRequested, pc);
				}
				else {
					//taking from the current location
					Command.Take.run(input.substring(5), pc);
				}
				
				return;
			}
			else if(input.startsWith("drop ")) {
				String itemRequested = input.substring(5);
				Command.Drop.run(itemRequested, pc);
				return;
			}
			else if(input.startsWith("open ")) {
				String containerRequested = input.substring(5);
				Command.Open.run(containerRequested, pc);
				return;
			}
			else if(input.startsWith("close ")) {
				String containerRequested = input.substring(6);
				Command.Close.run(containerRequested, pc);
				return;
			}
			else if(input.startsWith("put ")) {
				String itemRequested = input.substring(4, input.indexOf(" in"));
				String containerRequested = input.substring(input.indexOf(" in") + 4);
				Command.Put.run(itemRequested, containerRequested, pc);
				return;
			}
		}
	}
}


