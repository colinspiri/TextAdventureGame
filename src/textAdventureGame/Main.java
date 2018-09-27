package textAdventureGame;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		Command.Look.showTutorial();

		//create important variables and show the starting location
	
		Location startingLocation = setupLocations();
		PlayerCharacter pc = new PlayerCharacter("Rabbak", startingLocation);

		showLocation(pc.getCurrentLocation());

		while(true) {
			Input.lookForInput(pc);
		}
	}

	public static Location setupLocations() {
		//to initialize the world and set a starting location
		Location startingLocation;

		//clearing
		Location clearing = new Location("a clearing", "The floor of this clearing is covered in moss and fungi. "
				+ "The trees beyond this clearing obscure the light, rendering it "
				+ "impossible to look beyond. The sound of running water comes from the east.");
			//apple
		Trinket apple = new Trinket("apple", "This is an apple.");
		Interaction appleInt = new Interaction(apple, "You see a red apple at your feet.", "");
		clearing.addInteraction(appleInt);

		//river
		Location river = new Location("a river", "You see a river. Lifeless corpses lie on the ground, freshly "
				+ "killed. Downstream, the river flows "
				+ "to the west.");
			//chest
		Container chest = new Container("wooden chest", "This wooden chest is decorated with patterns and designs all over.", 3, 10);
		Interaction chestInt = new Interaction(chest, "Beside the river is an ornate wooden chest, half buried.", "");
		river.addInteraction(chestInt);


		//add exits
		clearing.addExit(new Exit(Exit.EAST, river));
		river.addExit(new Exit(Exit.WEST, clearing));

		startingLocation = clearing;
		return startingLocation;
	}

	public static void showLocation(Location location) {
		//to print the location's description
		System.out.println();
		System.out.println(location.toString());
	}
}







