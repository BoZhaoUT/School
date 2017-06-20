package encryption_project;


import java.util.Arrays;

/**
 * A class represents a deck of cards.
 * @author Bo Zhao
 */
public class Deck {

	private int[] deck;
	private int JOKER1 = 27;
	private int JOKER2 = 28;
	
	/**
	 * Create a new deck of cards based on given deck.
	 * @param deck
	 */
	public Deck(int[] deck) {
		this.deck = new int[JOKER2];
	}
	
	/**
	 * Change the positions of the card at given index and the
	 * card immediately following it. If the card at given index
	 * is the last card in the deck, then swap it with the first
	 * card.
	 */
	private void swapCards(int index) {
		int card = deck[index];
		int swapCard;
		// swap with first card if card index is point to
		// the last card in this deck
		if (index == deck.length - 1) {
			swapCard = deck[0];
			deck[index] = swapCard;
			deck[index + 1] = card;
		} else {
			swapCard = deck[deck.length - 1];
			deck[index] = swapCard;
			deck[0] = card;
		}
	}
	
	/**
	 * Swap JOKER1 with the card that follow it.
	 * Treat the deck as circular.
	 */
	public void moveJoker1() {
		// find index of JOKER!
		int index = Arrays.asList(deck).indexOf(JOKER1);
		swapCards(index);
	}
	
	/**
	 * Move JOKER2 two cards down. Treat the deck as circular.
	 */
	public void moveJoker2() {
		swapCards(Arrays.asList(deck).indexOf(JOKER2));
		swapCards(Arrays.asList(deck).indexOf(JOKER2));
	}
	
	/**
	 * Find and swap everything before first joker with everything after second joker.
	 */
	public void tripleCut() {
		int joker1Index = Arrays.asList(deck).indexOf(JOKER1);
		int joker2Index = Arrays.asList(deck).indexOf(JOKER2);
		int firstJokerIndex, secondJokerIndex;
		// find jokers' indexes in deck
		if (joker1Index < joker2Index) {
			firstJokerIndex = joker1Index;
			secondJokerIndex = joker2Index;
		} else {
			firstJokerIndex = joker2Index;
			secondJokerIndex = joker1Index;
		}
		// find everything before first joker with everything after second joker
		int[] before = Arrays.copyOfRange(deck, 0, firstJokerIndex);
		int[] between = Arrays.copyOfRange(deck, firstJokerIndex, secondJokerIndex + 1);
		int[] after = Arrays.copyOfRange(deck, secondJokerIndex + 1, deck.length);
		// swap everything before first joker with everything after second joker
		for(int i = 0; i < after.length; i++) {
			deck[i] = after[i];
		}
		for(int i = 0; i < between.length; i++) {
			deck[after.length + i] = between[i];
		}
		for(int i = 0; i < before.length; i++) {
			deck[after.length + between.length + i] = before[i];
		}
	}
	
	/**
	 * Insert n cards from top of the deck to just above the bottom card where n is
	 * the face value of bottom card.
	 */
	public void insertTopToBottom() {
		// no change if the bottom card is either JOKER1 or JOKER2
		int bottomCard = deck[deck.length - 1];
		if (bottomCard < JOKER1) {
			int[] top = Arrays.copyOfRange(deck, 0, bottomCard);
			int[] between = Arrays.copyOfRange(deck, bottomCard, deck.length - 1);
			// swap between with top
			for(int i = 0; i < between.length; i++) {
				deck[i] = between[i];
			}
			for(int i = 0; i < top.length; i++) {
				deck[between.length + i] = top[i];
			}
			
		}
	}
	
	/**
	 * @return the value of card at index i, where i is the face value of the top
	 * card in this deck. If the top card is JOKER2, then use the face value of
	 * JOKER1.
	 */
	public int getCardAtTopIndex() {
		int topCard = deck[0];
		// special case: if top card is JOKER2, use JOKER1 instead
		if (topCard == JOKER2) {
			topCard = JOKER1;
		}
		return deck[topCard];
	}
	
	/**
	 * Perform all 5 steps of the algorithm.
	 * @return next potential keystream value
	 */
	private int getNextValue() {
		moveJoker1();
		moveJoker2();
		tripleCut();
		insertTopToBottom();
		return getCardAtTopIndex();
	}
	
	/**
	 * Keep generating keystream value until a it is less than JOKER1.
	 * @return valid key stream value
	 */
	public int getNextKeystreamValue() {
		int result;
		do {
			result = getNextValue();
		} while (result >= JOKER1);
		return result;
	}

}
