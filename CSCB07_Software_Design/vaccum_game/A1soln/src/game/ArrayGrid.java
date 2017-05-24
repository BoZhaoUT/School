/**
 * 
 */
package game;

import sprites.Sprite;
import sprites.Vacuum;

/**
 * @author Administrator
 * @param <T>
 *
 */
public class ArrayGrid<T> implements Grid<T> {

	private int numRows;
	private int numColumns;
	private T cell;
	private Vacuum vacuum1;
	private Vacuum vacuum2;
	T[][] grid;
	
	public ArrayGrid(int numRows, int numColumns) {
		
		this.numRows = numRows;
		this.numColumns = numColumns;
		
		T[][] grid = (T[][]) new Sprite[numRows][numColumns];
		this.grid = grid;
	}
	
	@Override
	public void setCell(int row, int column, T item) {
		grid[row][column] = item;
		
	}
	
	@Override
	public T getCell(int row, int column) {
		return (T) grid[row][column];
	}
	
	/**
	 * @return numRows which is number of rows in this array grid
	 */
	@Override
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * @return numColumns which is number of columns in this array grid
	 */
	@Override
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * @return true if two ArrayGrid have the exact same dimensions and exact
	 * content in each cell. Otherwise false.
	 */
	@Override
	public boolean equals(Object other) {
		boolean result = false;
		
		// demension of other ArrayGrid
		ArrayGrid<T> otherGrid = (ArrayGrid<T>) other;
		int otherNumRows = otherGrid.getNumRows();
		int otherNumColumns = otherGrid.getNumColumns();
		
		if (!(numRows == otherNumRows && numColumns == otherNumColumns)) {

			return result;
		} else {
			for (int row = 0; row < numRows; row++){
				for (int column = 0; column < numColumns; column++){
					if (! this.getCell(row, column).equals(otherGrid.getCell(row, column))) {
						System.out.println("runned");
						result = false;
					}
				}
			}
			if (result != false) {
				System.out.println("runned");
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * @return result which is a string representation of this array grid
	 */
	@Override
	public String toString() {
		String result = "";
		for (int row = 0; row < numRows; row++) {
			for (int column = 0; column < numColumns; column++) {
				result += grid[row][column].toString();
			}
			result += "\n"; // may change this design
			// in order to avoid an extra line below last line
		}
		return result;
	}
}
