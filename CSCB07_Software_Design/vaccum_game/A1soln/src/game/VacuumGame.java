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
  
  // the grid
  public Grid<Sprite> grid;
  
  // the first player
  private Vacuum vacuum1;
  
  // the second player
  private Vacuum vacuum2;
  
  // the dirt (both static dirt and mobile dust balls)
  private List<Dirt> dirts;
  
  //the dumpsters
  private List<Dumpster> dumpsters;   
  
  //potential new row of a vacuum
  private int newRow;
  
  //potential new column of a vacuum
  private int newColumn; 
  
  //determine which vacuum is to be moved
  Sprite toBeMoved = null; 

  /**
   * Creates a new VacuumGame that corresponds to the given input text file.
   * Assumes that the input file has one or more lines of equal lengths, and
   * that each character in it (other than newline) is a character that 
   * represents one of the sprites in this game.
   * @param layoutFileName path to the input grid file
   */
  public VacuumGame(String layoutFileName) throws IOException {
    dirts = new ArrayList<Dirt>();
    dumpsters = new ArrayList<Dumpster>(); // for future implementation
    random = new Random();

    // open the file, read the contents, and determine 
    // dimensions of the grid [numRows, numColumns]
    int[] dimensions = getDimensions(layoutFileName);
    int numRows = dimensions[0];
    int numColumns = dimensions[1];
    grid = new ArrayGrid<Sprite>(numRows, numColumns);
    
    // open the file again, read the contents, and store them in grid
    Scanner sc = new Scanner(new File(layoutFileName));
    
    // initialize the array grid
    String nextLine;
    char nextChar;
    Sprite currentSprite;
    // loop through the given file and create corressponding sprites
    for (int i = 0; i < numRows; i++) {
    	nextLine = sc.nextLine();
    	for (int j = 0; j < numColumns; j++) {
    		nextChar = nextLine.charAt(j);
    		// wall
    		if (nextChar == Constants.WALL) {
    			currentSprite = new Wall(Constant.WALL, i, j);
    		// dumpster
    		} else if (nextChar == Constants.DUMPSTER) {
    			currentSprite = new Dumpster(Constant.DUMPSTER, i, j);
    		// clean hallway
    		} else if (nextChar == Constants.CLEAN) {
    			currentSprite = new CleanHallway(Constant.CLEAN, i, j);
    		// dirt
    		} else if (nextChar == Constants.DIRT) {
    			currentSprite = new Dirt(Constant.DIRT, i, j, Constant.DIRT_SCORE);
        	// player 1
        	} else if (nextChar == Constants.P1) {
        		// create player 1
        		currentSprite = new Vacuum(Constant.P1, i, j, Constant.CAPACITY);
        		vacuum1 = currentSprite;
        		// create a piece of clean hall under player 1
        		Sprite under = new CleanHallway(Constants.CLEAN, i, j);
        		vacuum1.setUnder(under);
        	// player 2
        	} else if (nextChar == Constants.P2) {
        		// create player 2
        		vacuum2 = new Vacuum(Constant.P2, i, j, Constant.CAPACITY);
        		vacuum2 = currentSprite;
        		// create a piece of clean hall under player 2
        		Sprite under = new CleanHallway(Constants.CLEAN, i, j);
        		vacuum2.setUnder(under);
    		// dumpster
        	} else if (nextChar == Constants.DUMPSTER) {
        		currentSprite = new Dumpster(Constant.DUMPSTER, i, j);
    		// dustball
    		} else if (nextChar == Constants.DUSTBALL) {
    			currentSprite = new DustBall(Constant.DUSTBALL, i, j, Constant.DUST_BALL_SCORE);
    		// unrecognized char
    		} else {
    			System.out.println("unrecognized symbol in grid file");
    		}
    		// add this spirte into grid
    		grid.setCell(i, j, currentSprite);
    	}
    }
    sc.close();
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
   * TODO
   * @param nextMove direction a vaccum moves to
   * @return true if the a vacuum is moved, false otherwise
   */
  public boolean move(char nextMove) {
	  // TODO
  }
  

  /**
   * Return true if game is over (all dirts are cleaned), false otherwise.
   * @return true if game is over (all dirts are cleaned), false otherwise.
   */
  public boolean gameOver() {
	  return (dirts.size() == 0);
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
	  // TODO
}
