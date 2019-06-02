package common.lexer;

/**
 * Class representing a token. A token is  sequence of symbol with a specific meaning in a language.
 * @author C.Silva, R.Cuinat
 */
public class Token {
	private final int line;
	private final int column;
	private final String kind;
	private final String value;
	
	/**
	 * Constructor of the class. 
	 * A token is defined by its tag, its value and its position in the code it is extracted from.
	 * @author C.Silva, R.Cuinat
	 * @param tag Tag of the token
	 * @param data Value of the token
	 * @param line Line number of the token in the original code
	 * @param column Column number of the token in the original code
	 */
	public Token(String tag, String data, int line, int column) {
		this.kind = tag;
		this.value = data;
		this.line = line;
		this.column = column;
	}
	
	/**
	 *Returns the line number of the token.
	 * @author C.Silva, R.Cuinat
	 *@return The line number of the token
	 */
	public int getLine() {
		return this.line;
	}
	
	/**
	 *Returns the tag of the token.
	 * @author C.Silva, R.Cuinat
	 *@return The tag of the token
	 */
	public String getKind() {
		return kind;
	}

	/**
	 *Returns the value of the token.
	 * @author C.Silva, R.Cuinat
	 *@return The value of the token
	 */
	public String getValue() {
		return value;
	}

	/**
	 *Returns the column number of the token.
	 * @author C.Silva, R.Cuinat
	 *@return The column number of the token
	 */
	public int getColumn() {
		return this.column;
	}
}