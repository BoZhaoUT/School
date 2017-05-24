/**
 * 
 */
package game;

/**
 * @author Administrator
 *
 */
public interface Grid<T> {
	
	public void setCell(int row, int column, T item);
	
	public T getCell(int row, int column);
	
	public int getNumRows();
	
	public int getNumColumns();
	
	public boolean equals(Object other);
	
	public String toString();

}
