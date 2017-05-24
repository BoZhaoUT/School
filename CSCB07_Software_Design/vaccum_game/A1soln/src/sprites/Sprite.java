package sprites;

public abstract class Sprite {
	
	protected char symbol;
	protected int row;
	protected int column;
	
	/**
	 * Create a Sprite object with given specification
	 * @param symbol of Sprite object
	 * @param row of Sprite object in a map
	 * @param column of Sprite object in a map
	 */
	public Sprite(char symbol, int row, int column) {

		this.symbol = symbol;
		this.row = row;
		this.column = column;
	}
	

	/**
	 * @return the symbol of this Sprite
	 */
	public char getSymbol() {
		return symbol;
	}
	

	/**
	 * @return the row of this Sprite
	 */
	public int getRow() {
		return row;
	}
	

	/**
	 * @return the column of this Sprite
	 */
	public int getColumn() {
		return column;
	}


	/**
	 * return string representation of this Sprite
	 */
	@Override
	public String toString() {
		return String.valueOf(symbol);
	}
	
	/**
	 * @return true if other sprite is at the same location and has the symbol
	 */
	public boolean equals(Sprite other) {
		boolean result;
		if (row == other.getRow() && column == other.getColumn() &&
				symbol == other.getSymbol()) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
}
