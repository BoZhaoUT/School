package encryption_project;

public class Message {
	
	private String message;
	
	public Message(String message) {
		this.message = message;
	}
	
	/**
	 * Clear a message so that the message only contains alphabetical
	 * characters and all the alphabetical characters are convert to
	 * uppercase.
	 * @param rawMessage a piece of uncleared message
	 */
	public void cleanMessage() {
		String result = "";
		// only alphabetical characters can stay
		for(int i = 0; i < message.length(); i++) {
			if (Character.isAlphabetic(message.charAt(i))) {
				result += message.charAt(i);
			}
		}
		this.message = result.toUpperCase();
	}
	
	/**
	 * Decript a single capitalized letter using a keystream value.
	 * @param letter to encrypt
	 * @param keyStreamValue a keystream value helps encrypt a letter
	 * @return encrypted letter
	 */
	private static char encryptLetter(char letter, int keystreamValue) {
		// TODO
		return 'a';
	}

	/**
	 * Dencryptt a single encrypted letter using a keystream value.
	 * @param letter to encrypt
	 * @param keyStreamValue a keystream value helps encrypt a letter
	 * @return encrypted letter
	 */
	private static char decryptLetter(char letter, int keystreamValue) {
		return 'a';
	}
	
	/**
	 * Encrypt or decrypt a message.
	 * @param deck a deck of cards to generate keystream values
	 * @param mode'e' means encryption, 'd' means decryption
	 * @return decrypted or encrypted message
	 */
	public String processMessage(Deck deck, char mode) {
		return "";
	}
}
