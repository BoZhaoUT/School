package sprites;

/**
 * A class represents a dirt object.
 * @author Bo Zhao
 */
public class Dirt extends Sprite {
	
	// score rewarded to a player when he collect this dirt
	protected int value;
	
	/**
	 * Create a new dirt object with symbol,
	 * its coordinate (row, column) and value.
	 * 
	 * @param symbol the symbol of a dirt object
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 * @param value score rewarded to a player when he collect this dirt
	 */
	public Dirt(char symbol, int row, int column, int value) {
		super(symbol, row, column);
		this.value = value;
	}
	
	/**
	 * Getter that return the value of this dirt object.
	 * @return the value of this dirt
	 */
	public int getValue() {
		return this.value;
	}

}
