/**
 * 
 */
package sprites;

/**
 * @author Administrator
 *
 */
public class DustBall extends Dirt implements Moveable{

	public DustBall(char symbol, int row, int column) {
		super(symbol, row, column);
	}

	@Override
	public void moveTo(int row, int column) {
		this.row = row;
		this.column = column;
	}
}
