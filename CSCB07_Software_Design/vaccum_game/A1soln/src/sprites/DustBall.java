package sprites;

/**
 * A class represents a dust ball object.
 * @author Bo Zhao
 */
public class DustBall extends Dirt implements Moveable {
	
	/**
	 * Create a new dust ball object with symbol and
	 * its coordinate (row, column).
	 * 
	 * @param symbol the symbol of a dirt object
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 * @param value score rewarded to a player when he collect this dirt
	 */
	public DustBall(char symbol, int row, int column, int value) {
		super(symbol, row, column, value);
	}
	
	/**
	 * Update the coordinate of this dust ball.
	 * @param row the new vertical coordinate of this dust ball
	 * @param column the new horizontal coordiante of this dust ball
	 */
	@Override
	public void moveTo(int row, int column) {
		this.row = row;
		this.column = column;
	}

}
