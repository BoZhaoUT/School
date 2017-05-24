package game;

import sprites.CleanHallway;
import sprites.Dirt;
import sprites.Dumpster;
import sprites.DustBall;
import sprites.Sprite;
import sprites.Vacuum;
import sprites.Wall;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * A class that represents the basic functionality of the vacuum game.
 * This class is responsible for performing the following operations:
 * 1. At creation, it initializes the instance variables used to store the
 *        current state of the game.
 * 2. When a move is specified, it checks if it is a legal move and makes the
 *        move if it is legal.
 * 3. It reports information about the current state of the game when asked.
 */
public class VacuumGame {

  // a random number generator to move the DustBalls
  private Random random;

  public Grid<Sprite> grid;  // the grid

  private Vacuum vacuum1;  // the first player

  private Vacuum vacuum2;  /// the second player

  // the dirt (both static dirt and mobile dust balls)
  private List<Dirt> dirts;

  private List<Dumpster> dumpsters;   // the dumpsters
  
  private int newRow; // potential new row of a vacuum
  
  private int newColumn; // potential new column of a vacuum
  
  Sprite toBeMoved = null; // determine which vacuum is to be moved

  /**
   * Creates a new VacuumGame that corresponds to the given input text file.
   * Assumes that the input file has one or more lines of equal lengths, and
   * that each character in it (other than newline) is a character that 
   * represents one of the sprites in this game.
   * @param layoutFileName path to the input grid file
   */
  public VacuumGame(String layoutFileName) throws IOException {
    dirts = new ArrayList<Dirt>();
    dumpsters = new ArrayList<Dumpster>();
    random = new Random();

    // open the file, read the contents, and determine 
    // dimensions of the grid
    int[] dimensions = getDimensions(layoutFileName);
    grid = new ArrayGrid<Sprite>(dimensions[0], dimensions[1]);
    

    // open the file again, read the contents, and store them in grid
    Scanner sc = new Scanner(new File(layoutFileName));
    
    String nextLine = sc.nextLine();
    ArrayList<String> content = new ArrayList<String>();
    content.add(nextLine);
    while (sc.hasNextLine()) {
    	nextLine = sc.nextLine();
    	content.add(nextLine);
    }
    sc.close();
    
    // loop through each character in text file.
	int numRow = 0;
    for (String line : content) {
    	int numColumn = 0;
    	for (char symbol : line.toCharArray()) {
    		// create a new sprite object
    		Sprite sprite = createNewSprite(symbol, numRow, numColumn);
    		// add new sprite object into a particular cell
    		grid.setCell(numRow, numColumn, sprite);
    		numColumn++;
    	}
    	numRow++;
    }
  }

  /**
   * Returns the dimensions of the grid in the file named layoutFileName.
   * @param layoutFileName path of the input grid file
   * @return an array [numRows, numCols], where numRows is the number
   *     of rows and numCols is the number of columns in the grid that
   *     corresponds to the given input grid file
   * @throws IOException if cannot open file layoutFileName
   */
  private int[] getDimensions(String layoutFileName) throws IOException {       

    Scanner sc = new Scanner(new File(layoutFileName));

    // find the number of columns
    String nextLine = sc.nextLine();
    int numCols = nextLine.length();

    int numRows = 1;

    // find the number of rows
    while (sc.hasNext()) {
      numRows++;
      nextLine = sc.nextLine();
    }

    sc.close();
    return new int[]{numRows, numCols};
  }
  
  /**
   * @return the entire grid object
   */
  public Grid<Sprite> getGrid() {
	  return grid;
  }
  
  /**
   * @return the 1st vacuum player
   */
  public Vacuum getVacuumOne() {
	  return vacuum1;
  }
  
  /**
   * @return the 2nd vacuum player
   */
  public Vacuum getVacuumTwo() {
	  return vacuum2;
  }
  
  /**
   * @return number of rows in this map
   */
  public int getNumRows() {
	  return grid.getNumRows();
  }
  
  /**
   * @return number of columns in this map
   */
  public int getNumColumns() {
	  return grid.getNumColumns();
  }
  
  /**
   * @param row
   * @param column
   * @return sprite at a particular cell, given row and column
   */
  public Sprite getSprite(int row, int column) {
	  Sprite sprite = grid.getCell(row, column);
	  return sprite;
  }
  
  /**
   * Move a vacuum based on the given char.
   * @param nextMove is one of "wsadijkl"
   * @return true if the corresponding vacuum is moved, false otherwise
   */
  public boolean move(char nextMove) {
	  // by default, a vacuum is not moved
	  boolean result = false;
	  // get rows and columns of two vacuums
	  int vacuum1NumRow = vacuum1.getRow(); // delete
	  int vacuum1NumColumn = vacuum1.getColumn(); // delete
	  int vacuum2NumRow = vacuum2.getRow(); // delete
	  int vacuum2NumColumn = vacuum2.getColumn(); // delete
	  int newNumRow; // potential row
	  int newNumColumn; // potential column
	  boolean moveable; // check if it's moveable
	  Sprite newUnder; // potential sprite under
	  Sprite currentUnder; // current sprite under vacuum // TODO
	  Sprite newCell; // potential cell

	  // check if a valid input char is given
	  if (isLegalInput(nextMove)) {
		  // get sprite at new cell
		  newCell = grid.getCell(newRow, newColumn);
		  if (canMoveTo(newCell)) {
			  if (toBeMoved.equals(vacuum1)) {
				  vacuum1.moveTo(newRow, newColumn);
				  newUnder = grid.getCell(newRow, newColumn);
				  vacuum1.setUnder(newUnder);
				  
			  } else { // assume vacuum2 is to be moved
				  vacuum2.moveTo(newRow, newColumn);
			  }
		  }
	  }
	  return result;
  }
  
  /**
   * Create a new sprite object according to symbol, row and column
   * read from a scanner.
   * @param symbol of a sprite object
   * @param row of a sprite object
   * @param column of a sprite object
   * @return result which is a Sprite object
   */
  private Sprite createNewSprite(char symbol, int row, int column) {
	  Sprite result;
	  // create a new sprite
	  if (symbol == Constants.WALL) {
		  result = new Wall(symbol, row, column);
	  } else if (symbol == Constants.DUMPSTER) {
		  result = new Dumpster(symbol, row, column);
		  dumpsters.add((Dumpster) result);
	  } else if (symbol == Constants.CLEAN){
		  result = new CleanHallway(symbol, row, column);
	  } else if (symbol == Constants.DIRT) {
		  result = new Dirt(symbol, row, column);
		  dirts.add((Dirt) result);
	  } else if (symbol == Constants.DUST_BALL) {
		  result = new DustBall(symbol, row, column);
		  dirts.add((Dirt) result);
	  } else if (symbol == Constants.P1) {
		  // if symbol is P1 or P2, then create a clean hallway
		  // and create according vacuum
		  result = new CleanHallway(symbol, row, column);
		  vacuum1 = new Vacuum(symbol, row, column, Constants.CAPACITY);
	  } else { // assume the symbol is player2 here
		  result = new CleanHallway(symbol, row, column);
		  vacuum2 = new Vacuum(symbol, row, column, Constants.CAPACITY);
	  }
	  return result;
  }

  /**
   * Return true if game is over (all dirts are cleaned), false otherwise.
   * @return result which indicate whether a game is over
   */
  public boolean gameOver() {
	  boolean result;
	  // check if all dirts are cleaned
	  if (dirts.size() == 0) {
		  result = true;
	  } else {
		  result = false;
	  }
	  return result;
  }
  
  /**
   * Return 1 if winner is vacuum1, 2 if winner is vacuum2
   * 0 if it is a tie.
   * @return result which indicates winner of this game
   */
  public int getWinner() {
	  int result;
	  int vacuum1Score = vacuum1.getScore();
	  int vacuum2Score = vacuum2.getScore();
	  // compare two vacuums' score
	  if (vacuum1Score > vacuum2Score) {
		  result = 1;
	  } else if (vacuum1Score < vacuum2Score) {
		  result = 2;
	  } else { // assume scores of two vacuums are the same
		  result = 0;
	  }
	  return result;
  }
  
  /**
   * check if a vacuum is able to move to a new cell.
   * @return true if a vacuum is able to move onto newCell, false otherwise
   */
  private boolean canMoveTo(Sprite newCell) {
	  boolean result;
	  char newCellSymbol = newCell.getSymbol();
	  // a vacuum cannot move onto a wall or another vacuum
	  if (newCellSymbol == Constants.WALL || newCellSymbol == Constants.P1 ||
			  newCellSymbol == Constants.P2) {
		  result = false;
	  } else { // assume it's a dirt, clean hall or dumpster
		  result = true;
	  }
	  return result;
  }
  
  /**
   * Edit newRow and newColumn if a valid input character is given.
   * @return true if a valid input character is given, false otherwise
   */
  private boolean isLegalInput(char nextMove) {
	  // assume a valid nextMove is given
	  boolean result = true;
	  if (nextMove == Constants.P1_UP) {
		  newRow = vacuum1.getRow() + Constants.UP;
		  // determine which vacuum is to be moved
		  toBeMoved = vacuum1;
	  } else if (nextMove == Constants.P1_DOWN) {
		  newRow = vacuum1.getRow() + Constants.DOWN;
		  toBeMoved = vacuum1;
	  } else if (nextMove == Constants.P1_LEFT) {
		  newColumn = vacuum1.getColumn() + Constants.LEFT;
		  toBeMoved = vacuum1;
	  } else if (nextMove == Constants.P1_RIGHT) {
		  newColumn = vacuum1.getColumn() + Constants.RIGHT;
		  toBeMoved = vacuum1;
	  } else if (nextMove == Constants.P2_UP) {
		  newRow = vacuum2.getRow() + Constants.UP;
		  toBeMoved = vacuum2;
	  } else if (nextMove == Constants.P2_DOWN) {
		  newRow = vacuum2.getRow() + Constants.DOWN;
		  toBeMoved = vacuum2;
	  } else if (nextMove == Constants.P2_LEFT) {
		  newColumn = vacuum2.getColumn() + Constants.LEFT;
		  toBeMoved = vacuum2;
	  } else if (nextMove == Constants.P2_RIGHT) {
		  newColumn = vacuum2.getColumn() + Constants.RIGHT;
		  toBeMoved = vacuum2;
	  } else { // in case an input character is not valid 
		  result = false;
	  }
	  return result;
  }
  
  /**
   * Randomly change positions of dust balls.
   */
  private void dustBallsMove() {
	  for (Dirt dirt : dirts) {
		  if (dirt.getSymbol() == Constants.DUST_BALL) {
			  Dirt currentDirt = dirt;
			  random = new Random();
			  if (random.nextBoolean()) {
				  
			  }
			  
		  }
	  }
  }
}
