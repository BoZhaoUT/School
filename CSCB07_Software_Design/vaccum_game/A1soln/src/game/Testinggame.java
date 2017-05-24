package game;

import java.io.IOException;
import java.util.Random;

import sprites.Sprite;
import sprites.Wall;

public class Testinggame {
	
	public static void main(String[] args) throws IOException {
		
		Grid<Sprite> grid = new ArrayGrid<Sprite>(2, 2);
		grid.setCell(0, 0, new Wall('X', 0, 0));
		grid.setCell(0, 1, new Wall('X', 0, 1));
		grid.setCell(1, 0, new Wall('X', 1, 0));
		grid.setCell(1, 1, new Wall('X', 1, 1));
		
		System.out.println(grid);
		
		Grid<Sprite> otherGrid = new ArrayGrid<Sprite>(2, 2);
		otherGrid.setCell(0, 0, new Wall('X', 0, 0));
		otherGrid.setCell(0, 1, new Wall('X', 0, 1));
		otherGrid.setCell(1, 0, new Wall('X', 1, 0));
		otherGrid.setCell(1, 1, new Wall('X', 1, 1));
		
		
		Sprite first = grid.getCell(0, 0);
		Sprite second = grid.getCell(0, 0);
		
		System.out.println(first.equals(second));
		
		VacuumGame vacuumGame = new VacuumGame("C:/Users/Administrator/Desktop/cscb07/a1/A1soln/src/gridtesting.txt");
		System.out.println(vacuumGame.grid);
		
		Random random = new Random();
		System.out.println(random.nextBoolean());
	}
	
	/**
	 *  sample output
	 *  XX
	 *  XX
	 *  
	 *  true
	 *  XXXX
	 *  X12X
	 *  XU.X
	 *  XXXX
	 */
	

}
