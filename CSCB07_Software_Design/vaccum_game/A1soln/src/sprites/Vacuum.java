package sprites;

import game.Constants;

/**
 * An class represents a vaccum object.
 * @author Bo Zhao
 */
public class Vacuum extends Sprite implements Moveable{
	
	// the current score of this vaccum
	private int score;
	// the total capacity of this vaccum
	private int capacity;
	// the fullness of this vaccum
	private int fullness;
	// the cell or sprite object under this vaccum;
	private Sprite under;
	
	
	/**
	 * Create a new vacuum object with symbol and
	 * its coordinate (row, column).
	 * 
	 * @param symbol the symbol of a vacuum object
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 * @param capacity total capacity of this vaccum
	 */
	public Vacuum(char symbol, int row, int column, int capacity) {
		super(symbol, row, column);
		this.capacity = capacity;
		this.score = Constants.INIT_SCORE;
		this.fullness = Constants.EMPTY;
	}
	
	/**
	 * Clean a dirt or dust ball if and only if this vacuum's fullness does
	 * not exceed its capacity after cleaning the dirt.
	 * @param score the points of this vaccum earns when it cleans a dirt
	 * @return true if this vaccum's fullness tolerates, false otherwise
	 */
	public boolean clean(int score) {
		
		boolean result = false;
		int potential_fullness = this.fullness + Constants.FULLNESS_INC;
		// a vaccum can clean a dirt or a dust ball only if the sum
		// its current fullness and dirt's score is less than its capacity
		if (potential_fullness <= this.capacity) {
			this.score += score;
			this.fullness += Constants.FULLNESS_INC;
			result = true;
		}
		return result;
	}
	
	/**
	 * Empty this vaccum.
	 */
	public void empty() {
		this.fullness = Constants.EMPTY;
	}
	
	/**
	 * Return the score of this vacuum earned.
	 * @return the score of this vacuum earned
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Change the cell or sprite object under this vaccum to under.
	 * @param under the cell or sprite object under this vaccum
	 */
	public void setUnder(Sprite under) {
		this.under = under;
	}
	
	/**
	 * Return the cell or sprite under this vacuum.
	 * @return the cell or sprite under this vacuum
	 */
	public Sprite getUnder() {
		return under;
	}
	
	/**
	 * Update the coordinate of this vacuum.
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 */
	@Override
	public void moveTo(int row, int column) {
		this.row = row;
		this.column = column;
	}

}
