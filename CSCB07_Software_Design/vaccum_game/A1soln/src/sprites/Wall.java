package sprites;

/**
 * A class represents a wall object.
 * @author Bo Zhao
 */
public class Wall extends Sprite{

	/**
	 * Create a new wall object with symbol and
	 * its coordinate (row, column).
	 * 
	 * @param symbol
	 * @param row
	 * @param column
	 */
	public Wall(char symbol, int row, int column) {
		super(symbol, row, column);
	}

}
