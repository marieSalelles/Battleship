package fr.battleship;

import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import salelles.marie.*;

public class TestIA {
	public static void main (String[] args)throws FileNotFoundException {
		Game game;
		PrintWriter printw= new PrintWriter(new File("ai_proof.csv"));
		

		// compteur game win
		int win1 = 0;
		int win2 = 0;
		int win3 = 0;

		printw.write("AI name; Score; AI name 2; Score 2\n");
		// ai level 1 against ai level 2
		for (int i = 0; i < 100; i++) {
			game = new Game("AI1", "AI2", 1, 2);

			play(game);

			if (game.winner().getName() == "AI1") {
				win1 = win1 + 1;
			} else {
				win2 = win2 + 1;
			}
		}

		StringBuilder sb = new StringBuilder("AI level Beginner;").append(win1).append("; AI level Medium;").append(win2).append("\n");
		
		
		// ai level 1 against ai level 3
		win1 = 0;
		win3 = 0;
		for (int i = 0; i < 100; i++) {
			game = new Game("AI1", "AI3", 1, 3);

			play(game);

			if (game.winner().getName() == "AI1") {
				win1 = win1 + 1;
			} else {
				win3 = win3 + 1;
			}
		}

		sb.append("AI level Beginner;").append(win1).append("; AI level Hard;").append(win3).append("\n");
		
		
		// ai level 2 against ai level 3
		win2 = 0;
		win3=0;
		for (int i = 0; i < 100; i++) {
			game = new Game("AI2", "AI3", 2, 3);
			play(game);
							
			if (game.winner().getName() == "AI2") {
				win2 = win2 + 1;
			} else {
				win3 = win3 + 1;
			}
		}

		sb.append("AI level Medium;").append(win2).append("; AI level Hard;").append(win3).append("\n");
		printw.write(sb.toString());
		printw.close();
	}

	
	
	static void play(Game game) {
	

	while (!game.isDone()) {

		System.out.println("Tour de " + game.getCurrentPlayer().getName());

		Coordinate whereToShoot;

		whereToShoot = ((AIBattle) (game.getCurrentPlayer())).shoot();
		System.out.println(whereToShoot);
	

		if (!game.getCurrentPlayer().canShootAt(whereToShoot)) {
			do {
					whereToShoot = ((AIBattle) (game.getCurrentPlayer())).shoot();
			} while (!game.getCurrentPlayer().canShootAt(whereToShoot));
		}

		game.getCurrentPlayer().addShot(whereToShoot);
		boolean[] result = game.getOtherPlayer().shootAt(whereToShoot);
		boolean hit = result[0];
		boolean sank = result[1];
		
		((AIBattle) (game.getCurrentPlayer())).addTouched(hit);
		if (sank == true) {
			((AIBattle) (game.getCurrentPlayer())).initialize();
		}
	}
}
}
