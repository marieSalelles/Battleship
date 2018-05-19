package salelles.marie;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Battleship {

	static HashMap<String, Integer> shipSizes = new HashMap<String, Integer>() {
		{
			put("Carrier", 5);
			put("Battleship", 4);
			put("Cruiser", 3);
			put("Submarine", 3);
			put("Destroyer", 2);
		}
	};

	//static Game game;

	public static void main(String[] args) {
		Game game;
		boolean ai;
		int level;
		int response;
		int scan;
		Scanner scanner = new Scanner(System.in);

		// game definition
		do {
			System.out.println("Do you want a game Player vs Player (0) or Player vs AI (1) ? ");
			scan = scanner.nextInt();
		}while(scan < 0 || scan > 1);
		
		if (scan == 0) {
			ai = false;
		} else
			ai = true;
		
		String namePlayeur;
		scanner.nextLine();
		System.out.println("Enter your name Player 1 : ");
		namePlayeur = scanner.nextLine();

		if (ai) {
			do {
				System.out.println("Choose the AI level : easy (1), medium (2), hard (3) ?");
				level = scanner.nextInt();
			} while (level < 1 || level > 3);
			scanner.nextLine();
			game = new Game(namePlayeur, "AI", level);
		} else {
			String name;
			System.out.println("Enter your name Player 2 : ");
			name = scanner.nextLine();
			game = new Game(namePlayeur, name);
		}
		do {
			putShip(scanner, game);
			play(scanner, game);

			System.out.println("Do you want to replay ? no(0) yes(1)");
			response = scanner.nextInt();
			scanner.nextLine();
			if (response == 1) {
				game.replay();
			}
		} while (response == 1);
	}
	
	static public void putShip( Scanner scanner, Game game) {
		for (Map.Entry<String, Integer> entry : shipSizes.entrySet()) {
			String name = entry.getKey();
			int size = entry.getValue();
			Coordinate coords[];
			if (game.getCurrentPlayer() instanceof AIBattle) {
				coords = ((AIBattle) (game.getCurrentPlayer())).placedShip(size);
			} else {
				do {
					coords = game.getCurrentPlayer().askShipCoords(scanner, name, size);
				} while (!game.isValid(coords[0], coords[1]) || Coordinate.getDist(coords[0], coords[1]) != size);
			}

			Ship s = new Ship(name, coords[0], coords[1]);
			game.getCurrentPlayer().addShip(s);
		}
		game.nextTurn();

		for (Map.Entry<String, Integer> entry : shipSizes.entrySet()) {
			String name = entry.getKey();
			int size = entry.getValue();
			Coordinate coords[];
			if (game.getCurrentPlayer() instanceof AIBattle) {
				coords = ((AIBattle) (game.getCurrentPlayer())).placedShip(size);
			} else {
				do {
					coords = game.getCurrentPlayer().askShipCoords(scanner, name, size);
				} while (!game.isValid(coords[0], coords[1]) || Coordinate.getDist(coords[0], coords[1]) != size);
			}
			Ship s = new Ship(name, coords[0], coords[1]);
			game.getCurrentPlayer().addShip(s);
		}
		
	}

	static public void play(Scanner scanner, Game game) {
		
		game.nextTurn();
		
		while (!game.isDone()) {

			System.out.println("\nTour de " + game.getCurrentPlayer().getName());
			
			// display current player, his shots and adversary shots's player
			if (!(game.getCurrentPlayer() instanceof AIBattle)) {
				System.out.print("My shots :");
				System.out.println(game.getCurrentPlayer().getShots().toString());

				System.out.print("Shots's adversary :");
				System.out.println(game.getOtherPlayer().getShots().toString());
				
				System.out.println("My ships :");
				for (Ship s : game.getCurrentPlayer().getShips()) {
					if (!game.getCurrentPlayer().shootAtSunk(s)) {
						System.out.print(s.toString());
						System.out.print(s.getSpots());
						System.out.println(" Nombre de cases touchées: " + s.getTouched());
					}
				}
			}

			Coordinate whereToShoot;

			if (game.getCurrentPlayer() instanceof AIBattle) {
				whereToShoot = ((AIBattle) (game.getCurrentPlayer())).shoot();
				System.out.println(whereToShoot);
			} else {
				whereToShoot = game.getCurrentPlayer().askShootCoords(scanner);
			}

			if (!game.getCurrentPlayer().canShootAt(whereToShoot)) {
				do {
					if (game.getCurrentPlayer() instanceof AIBattle) {
						whereToShoot = ((AIBattle) (game.getCurrentPlayer())).shoot();
					} else {
						whereToShoot = game.getCurrentPlayer().askShootCoords(scanner);
					}
				} while (!game.getCurrentPlayer().canShootAt(whereToShoot));
			}

			game.getCurrentPlayer().addShot(whereToShoot);
			boolean[] result = game.getOtherPlayer().shootAt(whereToShoot);
			boolean hit = result[0];
			boolean sank = result[1];

			if (game.getCurrentPlayer() instanceof AIBattle) {
				((AIBattle) (game.getCurrentPlayer())).addTouched(hit);
				if (sank == true) {
					((AIBattle) (game.getCurrentPlayer())).initialize();
				}
			}
			
			System.out.println("touche ? : " + hit);
			System.out.println("coule ? : " + sank);

			game.nextTurn();
		}
		System.out.println("Partie terminee. " + game.winner().getName() + " gagne");
	}

}
