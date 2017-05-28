package sprites;

/**
 * An interface defines moveable objects.
 * @author Bo Zhao
 */
public interface Moveable {
	
	/**
	 * Update this objects coordinate.
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 */
	public void moveTo(int row, int column);
	
}
