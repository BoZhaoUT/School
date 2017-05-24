/**
 * 
 */
package sprites;

/**
 * @author Administrator
 *
 */
public class Dirt extends Sprite{
	
	protected int value;

	public Dirt(char symbol, int row, int column) {
		super(symbol, row, column);
	}
	
	public int getValue() {
		return value;
	}
}
