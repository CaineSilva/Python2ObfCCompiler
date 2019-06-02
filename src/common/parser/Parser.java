package common.parser;

import java.text.ParseException;
import java.util.ArrayList;

import common.ast.AST;
import common.lexer.Lexer;
import common.lexer.Token;

/**
 * This class aims at parsing a tokenized code to create the AST.
 * @author C.Silva, R.Cuinat
 *
 */
public abstract class Parser {
	protected ArrayList<Token> tokens;
	protected final String source;
	
	/**
	 * Constructor of the class. Initializes the Parser with the tokenized source code and the AST to create.
	 * @param lexer Lexer containing the tokenized code to parse.
	 * @author C.Silva, R.Cuinat
	 */
    protected Parser(Lexer lexer) {
		this.source = lexer.getSource();
		this.tokens = lexer.getTokens();
	}
	
	/**
	 * Main method of the parser. It parses the program to create the AST.
	 * @author C.Silva, R.Cuinat
	 * @return The AST extracted from the source code.
	 */
	abstract public AST parse();
	
	
	/**
	 * Returns the next token to process without modifying the list of tokens.
	 * @return The next Token to process.
	 * @author C.Silva, R.Cuinat
	 */
	protected Token show_next() {
		Token next_token;
		if (this.tokens.size() > 0) {
			next_token = this.tokens.get(0);
		}
		else {
			next_token = null;
		}
		return next_token;
	}
	
	/**
	 * Process the next token if its kind matches the one given. If not, a ParseException is raised.
	 * @param kind Expected kind of the next token to process.
	 * @return The token which is processed.
	 * @throws ParseException When the next token kind doesn't match the given one.
	 * @author C.Silva, R.Cuinat
	 */
	protected Token expect(String kind) throws ParseException {
		Token actual_token = this.show_next();
		if (actual_token.getKind().compareTo(kind) == 0) {
			this.accept_it();
			return actual_token;
		}
		else {
			System.err.println("\nError at (" + actual_token.getLine() + " , " + actual_token.getColumn() + "): expected "+kind+", got "+actual_token.getKind()+" instead.");
			throw new ParseException("",0);
		}
	}
	
	/** Process the next token.
	 * @return The token which was accepted.
	 * @author C.Silva, R.Cuinat
	 */
	protected Token accept_it() {
		Token token = this.tokens.remove(0);
		if (token.getKind().compareTo("CarriageReturn") == 0) {
			System.out.print("\n");
		}
		else if (token.getKind().compareTo("SeparatorTab") == 0) {
			for (int i=0;i<this.sizeOfTab(token);i++) {
				System.out.print("\t");
			}
		}
		else {
			System.out.print(token.getValue() + " ");
		}
		return token;
	}
	
	/** Computes the number of tabulation of the specified tabulation token.
	 * @param token The tab token to process.
	 * @return The number of tabulation of the given tab token.
	 * @author C.Silva, R.Cuinat
	 */
	protected int sizeOfTab(Token token) {
		assert (token.getKind().compareTo("SeparatorTab") == 0);
		int count_tab = 0;
		int count_space = 0;
		for (int i=0;i<token.getValue().length();i++) {
			if (token.getValue().charAt(i) == "\t".charAt(0) ) {
				count_tab++;
			}
			else {
				count_space++;
			}
		}
		return (count_tab + count_space/4);
	}
}
