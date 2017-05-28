package sprites;

/**
 * An abstract class represents sprite objects in general.
 * @author Bo Zhao
 */
public abstract class Sprite {
	
	// the string representation of this sprite
	protected char symbol;
	// the vertical coordinate of this dirt
	protected int row;
	// the horizontal coordinate of this dirt
	protected int column;
	
	/**
	 * Create a new dust ball object with symbol and
	 * its coordinate (row, column).
	 * 
	 * @param symbol the symbol of a dirt object
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 * @param value score rewarded to a player when he collect this dirt
	 */
	public Sprite(char symbol, int row, int column) {
		this.symbol = symbol;
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Return a string representation of this sprite object.
	 * @return symbol a string representation of this sprite object
	 */
	public char getSymbol() {
		return symbol;
	}
	
	/**
	 * Reteurn the vertical coordiante of this sprite object.
	 * @return the vertical coordinate of this sprite
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Reteurn the horizontal coordiante of this sprite object.
	 * @return the horizontal coordinate of this sprite
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Return a string representation of this sprite object.
	 * @return a string representation of this sprite object
	 */
	@Override
	public String toString() {
		return Character.toString(symbol);
	}

}
