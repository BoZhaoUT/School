package sprites;

import game.Constants;

public class Testing {
	
	public static void main(String[] args) {
		
		// a wall object
		Wall wall = new Wall(Constants.WALL, 1, 1);
		System.out.println(wall);
		
		// a dumpster object
		Dumpster dumpster = new Dumpster(Constants.DUMPSTER, 2, 2);
		System.out.println(dumpster);
		
		// a clean hallway object
		CleanHallway cleanHallway = new CleanHallway(Constants.CLEAN, 1, 2);
		System.out.println(cleanHallway);
		
		// a dirt object
		Dirt dirt = new Dirt(Constants.DIRT, 3, 3);
		System.out.println(dirt);
		
		// a dust ball object
		DustBall dustBall = new DustBall(Constants.DUST_BALL, 3, 4);
		System.out.println(dustBall);
		
		// a vacuum object
		Vacuum vacuum = new Vacuum(Constants.P1, 2, 2, Constants.CAPACITY);
		System.out.println(vacuum);
		
		// score of a vacuum, and under of a vacuum
		System.out.println(vacuum.getScore());
		System.out.println(vacuum.getUnder());
		System.out.println();
		
		// set a under to above vacuum object
		vacuum.setUnder(dirt); 
		System.out.println(vacuum.getUnder());
		
		
		/*
		 * sample outpout
		 * 
		 * X // wall ojbect
		 * U // dumpster object
		 *   // claen hallway object
		 * . // dirt object
		 * o // dust ball object
		 * 1 // symbol of 1st player
		 * 0 // score of a vacuum
		 *   // fullness of a vacuum
		 *   // by default, a clean hallway object is under vacuum
		 * . // we set a dirt object under vacuum
		 */
	}

}
