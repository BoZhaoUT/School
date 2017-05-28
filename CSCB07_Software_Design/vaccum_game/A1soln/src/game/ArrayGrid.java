package game;

/**
 * A 2D grid which serves as the board of this game.
 * @author Bo Zhao
 * @param <T>
 */
public class ArrayGrid<T> implements Grid<T> {

	// number of rows in this array grid
	private int numRows;
	// number of columns in this array grid
	private int numColumns;
	// the actual grid
	private T[][] grid;
	
	/**
	 * Create a new array grid object with number of rows and columns.
	 * 
	 * @param numRows the number of rows in this grid
	 * @param numColumns the number of columns in this grid
	 */
	public ArrayGrid(int numRows, int numColumns) {
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.grid = (T[][]) new Object[numRows][numColumns];
	}
	
	/**
	 * Change the sprite in this grid at (row, column)
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 * @param item the new sprite object
	 */
	@Override
	public void setCell(int row, int column, T item) {
		this.grid[row][column] = item;
	}
	
	/**
	 * Return the sprite object in this grid at (row, column).
	 * @param row the vertical coordinate of this dirt
	 * @param column the horizontal coordinate of this dirt
	 */
	@Override
	public Sprite getCell(int row, int column) {
		return grid[row][column];
	}
	
	/**
	 * Return the number of rows in this grid.
	 * @return the number of rows in this grid
	 */
	@Override
	public int getNumRows() {
		return numRows;
	}
	
	/**
	 * Return the number of columns in this grid.
	 * @return the number of columns in this grid
	 */
	@Override
	public int getNumColumns() {
		return numColumns;
	}
	
	/**
	 * Return true if two array grid are the same. False otherise.
	 * 
	 */
	@Override
	public boolean equals(Object other) {
		// coordinate of a cell
		int i, j = 0;
		// assume there is no difference in two array grids
		boolean difference = false;
		T thisCell, otherCell;
		while (i < numRows && !difference) {
			while (j < numColumns && !difference) {
				thisCell = getCell(i, j);
				otherCell = other.getCell(i, j);
				if (thisCell.toString() != otherCell.toString()) {
					difference = true;
				}
			}
		}
		return difference;
	}
	
	/**
	 * Return a string representation of this grid.
	 */
	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				result += getCell(i, j);
			}
		}
		return result;
	}

}
