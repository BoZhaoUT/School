package sprites;

import game.Constants;

public class Vacuum extends Sprite implements Moveable{
	
	private int score;
	private int capacity;
	public int fullness;
	private Sprite under;
	
	public Vacuum(char symbol, int row, int column, int capacity) {
		super(symbol, row, column);
		this.capacity = capacity;
		this.score = Constants.INIT_SCORE;
		this.fullness = Constants.EMPTY;
		this.under = new CleanHallway(Constants.CLEAN, row, column);
	}
	
	/**
	 * Move vacuum to new given position.
	 */
	@Override
	public void moveTo(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Clean a current sell and increase score.
	 * @score the score of a dirt
	 */
	public boolean clean(int score) {
		boolean result;
		if ((fullness + Constants.FULLNESS_INC) <= capacity) {
			this.score += score;
			fullness += score;
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	/**
	 * empty a vacuum. In other words, the fullness becomes 0.
	 */
	public void empty() {
		fullness = Constants.EMPTY;
	}

	/**
	 * @return the score of a vacuum
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the Sprite under a vacuum.
	 */
	public Sprite getUnder() {
		return under;
	}
	
	/**
	 * Replace old under sprite with new under sprite.
	 */
	public void setUnder(Sprite newUnder) {
		under = newUnder;
	}
}
