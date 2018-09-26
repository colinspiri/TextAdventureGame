package textAdventureGame;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		PlayerCharacter pc = createNewPlayerCharacter("Rabbak", 1, "mage");
		pc.takeDamage( pc.getMaxHealth()/2 );
		
		System.out.println("testing");

		showTutorial();
		
		Location startingLocation = setupLocations();
		pc.setCurrentLocation(startingLocation);
		
		pc.addToInventory(new Shield("wooden shield", "", 3, 10, 2));

		System.out.println(pc.toString());
		System.out.println("You wake up and find yourself lying in the middle of a forest. "
				+ "Your clothes are torn and bloodied, your shoes are gone, and "
				+ "you feel hungry. Worst of all, however, is that you remember "
				+ "nothing; your memory is a blank slate. You stand up and look "
				+ "around.");
		showLocation(pc);
		
		while(true) {
			Input.lookForInput(pc);
		}
	}

	public static void showTutorial() {	
		//title
		System.out.println("-----");
		System.out.println("TUTORIAL");

		//basic commands
		System.out.println();
		System.out.println("Basic Commands:");
		System.out.println("The \'HELP\' command displays this tutorial.");
		System.out.println("The \'STATS\' command displays your player stats.");
		System.out.println("The \'INVENTORY\' command displays your inventory.");
		System.out.println("The \'ITEM (NAME)\' command displays the details of the specified item from your inventory.");
		System.out.println("The \'SKILLS\' command displays your skills.");
		System.out.println("The \'SKILL (NAME)\' command displays the details of the specified skill.");
		System.out.println("The \'INFO\' command displays your stats, inventory, and skills.");
		System.out.println();
		
		//navigation
		System.out.println("Navigation:");
		System.out.println("You can navigate the world by using the command \'GO (DIRECTION)\'. Valid directions include "
				+ "north, south, east, west, in, out, up, down, northwest, northeast, southwest, and southeast. Most locations "
				+ "only have certain egresses, so read the description to figure out where you can go. The \'LOOK\' command "
				+ "prints a description of your current location.");
		System.out.println();
		
		//combat
		System.out.println("Fighting:");
		System.out.println("When you are engaged by an enemy or attack an unsuspecting creature, you will enter combat. "
				+ "In combat, you and your opponent take turns taking actions. Who goes first is determined by your initiative, "
				+ "related to your agility stat. Useful commands here are \'ATTACK\', which attacks your opponent with your "
				+ "equipped weapon, \'BLOCK\', which gives you a majority chance to reduce the damage of your opponent's "
				+ "next attack by your defense score and gain an extra attack of opportunity, and \'(SKILLNAME)\', which "
				+ "lets you use a skill against your opponent, costing you mana. If you wish to attack an unsuspecting "
				+ "creature outside of combat, you can use the command \'ATTACK (NAME)\'.");
		System.out.println();
		
		//items and verbs
		System.out.println("Items and Verbs:");
		System.out.println("The text parser is fairly stupid, but it understands some basic verbs, such as TAKE, DROP, "
				+ "EQUIP, OPEN, CLOSE, PUT, EAT, DRINK, BUY, SELL, TALK, and GIVE. These verbs can be applied to items "
				+ "with basic phrases. Use your best judgement for word order; the parser can understand phrases such "
				+ "as \'take stick from chest\', \'give bread to beggar\', \'eat apple\', \'open chest\', "
				+ "\'put iron shield in box\', and \'talk to beggar\'. Good luck.");
		System.out.println();
		
		//containment
		System.out.println("Containment:");
		System.out.println("Some items may act as containers, able to hold a certain weight of other items. "
				+ "These containers can be opened and closed. For you to access a container's contents - put "
				+ "an item in or take an item out - the container must be open. Although containers can contain "
				+ "items and containers are technically items themselves, containers cannot themselves hold "
				+ "more containers. (although it might be fun)");
		System.out.println();
	}

	public static Location setupLocations() {
		Location startingLocation;

		//clearing
		Location clearing = new Location("Clearing", "The floor of this clearing is covered in moss and fungi. "
				+ "The trees beyond this clearing obscure the light, rendering it "
				+ "impossible to look beyond. There is a beaten-up path leading "
				+ "west, and the sound of running water comes from the east. To the north you can hear groaning, "
				+ "and to the south is another small dirt path.");
		//stick
		Weapon stick = new Weapon("stick", "This is a normal stick.", 1, 2, 0, 1);
		Interaction stickInt = new Interaction(stick, "A small stick lies on the ground.", "");
		clearing.addInteraction(stickInt);
		//apple
		Apple apple = new Apple();
		Interaction appleInt = new Interaction(apple, "You see a red apple at your feet. Your stomach rumbles.", "");
		clearing.addInteraction(appleInt);

		//river
		Location river = new Location("River", "You see a river. Lifeless corpses lie on the ground, freshly "
				+ "killed. Looking upstream, you can see the river continue north. Downstream, the river flows "
				+ "to the west.");
		Container chest = new Container("wooden chest", "This wooden chest is decorated with patterns and designs all over.", 10, 10, 10, 2);
		Interaction chestInt = new Interaction(chest, "Beside the river is an ornate wooden chest, half buried.", "");
		river.addInteraction(chestInt);
		
		//tavern
		Tavern tavern = new Tavern("Tavern", "Inside the tavern, a tough-looking woman tends to the bar, a group of "
				+ "intimidating men play cards, and an older man seems to be renting out rooms to weary travelers."
				+ "The river flows southward.", 4);
		//TODO FIX TAVERN AND MAKE RIVER PLACE HAVE EXIT OF 'IN' TO THE TAVERN

		//house
		Location house = new Location("House", "You see a wooden cabin. A light is on inside and "
				+ "the door is wide open. A beaten-up path goes east.");
		
		//bush
		Location bush = new Location("Bush", "This section of the forest is dark and cramped. Several bushes obscure your vision. "
				+ "Light comes from the south.");
		Interaction zombieInt = new Interaction(new Zombie(), "One of the bushes rustles, and you can hear groans from the dark. "
				+ "A zombie emerges from the bush and charges toward you.", "The zombie's corpse lies in the foliage, already "
						+ "beginning to decompose.");
		bush.addInteraction(zombieInt);

		//beggar
		Location stump = new Location("Stump", "You see a tree stump, about 3 feet tall, standing out from the rest of the dense forest. "
				+ "A dirt path leads north.");
		Interaction beggarInt = new Interaction(new Beggar("Beggar"), "A beggar sits on the stump, clenching his stomach with his bony hands.", "");
		stump.addInteraction(beggarInt);

		//shop
		ArrayList<Item> wares = new ArrayList<Item>();
		wares.add(new Weapon("wooden sword", "This wooden sword looks poorly constructed.", 2, 3, 50, 2));
		wares.add(new Bread());
		wares.add(new Shield("iron shield", "This iron shield is engraved with the image of a dragon.", 5, 100, 3));
		Shop shop = new Shop("Barry's wares",  "Inside the cabin is what looks like a shop. Assorted weapons "
				+ "and shields decorate the walls, and a large grizzly man "
				+ "stands behind the counter. He shows you his wares and asks "
				+ "you if you want to buy anything.", 
				wares);

		//add exits
		clearing.addExit(new Exit(Exit.EAST, river));
		clearing.addExit(new Exit(Exit.WEST, house));
		clearing.addExit(new Exit(Exit.SOUTH, stump));
		clearing.addExit(new Exit(Exit.NORTH, bush));
		bush.addExit(new Exit(Exit.SOUTH, clearing));
		river.addExit(new Exit(Exit.WEST, clearing));
		river.addExit(new Exit(Exit.NORTH, tavern));
		tavern.addExit(new Exit(Exit.SOUTH, river));
		house.addExit(new Exit(Exit.EAST, clearing));
		house.addExit(new Exit(Exit.IN, shop));
		shop.addExit(new Exit(Exit.OUT, house));
		stump.addExit(new Exit(Exit.NORTH, clearing));

		startingLocation = clearing;
		return startingLocation;
	}

	public static void showLocation(PlayerCharacter pc) {
		Location location = pc.getCurrentLocation();
		
		//print location's description
		System.out.println();
		System.out.println(location.toString());
		
		//if there's a hostile enemy, initiate combat
		for(Creature c : location.getCreatures()) {
			if(c instanceof Enemy) engageCombat(pc, c);
		}
		

	}

	//when initiating combat, whoever has higher initiative goes first
	//if user types 'attack beggar', if beggar has higher initiative:
	//'the beggar notices your advancement and attacks you first!
	//TODO
	public static void engageCombat(PlayerCharacter pc, Creature opponent) {
		Creature victor = null;
		Creature loser = null;

		System.out.println("\n" + "Combat Initiated! " + pc.getName() + " against " + opponent.getName() + ".");

		//find who goes first
		//based on attack speed
		Creature first;
		Creature second;
		if(pc.getInitiative() > opponent.getInitiative()) {
			first = pc;
			second = opponent;
		}
		else if(pc.getInitiative() < opponent.getInitiative()) {
			first = opponent;
			second = pc;
		}
		//random order
		else {
			double randomNum = Math.random();
			if(randomNum < 0.5) {
				first = pc;
				second = opponent;
			}
			else {
				first = opponent;
				second = pc;
			}
		}

		while(victor == null) {
			//actions
			first.chooseAction(second);
			if(second.isDead()) {
				victor = first;
				loser = second;
				break;
			}

			second.chooseAction(first);
			if(first.isDead()) {
				victor = second;
				loser = first;
				break;
			}
		}
		//battle over
		System.out.println();
		System.out.println(victor.getName() + " KILLED " + loser.getName());
		if(victor instanceof PlayerCharacter) {
			int levelDifference = opponent.getLevel() - pc.getLevel();
			double experience = 0;
			//gain experience based on level difference
			//level difference / experience gained
			// <=-2 / 16.67
			// -1   / 20
			//  0   / 25
			//  1   / 33.33
			// >=2 	/ 50
			if(levelDifference <= -2) experience = 100/6;
			else if(levelDifference >= 2) experience = 50;
			switch(levelDifference) {
			case -1: experience = 20;
			case 0: experience = 25;
			case 1: experience = 100/3;
			}
			System.out.println("Gained " + experience + " experience.");
			((PlayerCharacter) victor).gainExperience(experience);

			//remove enemy from current location
			pc.getCurrentLocation().removeCreature(loser);
		}
		else {
			System.out.println("YOU DIED. GAME OVER.");
			System.exit(0);
		}
	}

	public static PlayerCharacter createNewPlayerCharacter(String name, int level, String archetype) {
		switch(archetype) {
		case "warrior": {
			ArrayList<Skill> skills = new ArrayList<Skill>();
			return new PlayerCharacter(name, level, 6, 5, 2, 3, skills);
		}
		case "mage": {
			ArrayList<Skill> skills = new ArrayList<Skill>();
			skills.add(new Fireball(0));
			skills.add(new Heal(1));
			return new PlayerCharacter(name, level, 3, 2, 6, 5, skills);
		}
		case "rogue": {
			ArrayList<Skill> skills = new ArrayList<Skill>();
			return new PlayerCharacter(name, level, 4, 3, 4, 5, skills);
		}
		default: {
			ArrayList<Skill> skills = new ArrayList<Skill>();
			return new PlayerCharacter(name, level, 4, 4, 4, 4, skills);
		}
		}
	}


}







