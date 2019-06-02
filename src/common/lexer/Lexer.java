package common.lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Lexer class. The purpose of a lexer is to tokenize a code. 
 * @author C.Silva, R.Cuinat
 */
public class Lexer {
	private final String source;
	private final LexicalRules lexicalRules;
	private final ArrayList<Token> tokens;
	
	/**
	 * Constructor of the class.
	 * @author C.Silva, R.Cuinat
	 * @param source String representation of the source code path.
	 * @param rules LexicalRules to be employed to tokenize.
	 */
	public Lexer(String source, LexicalRules rules) {
		this.source = source;
		this.lexicalRules = rules;
		this.tokens = new ArrayList<>();
	}
	
	/**
	 * Main method of the class. This method analyze the source code to determine the tokens.
	 * @author C.Silva, R.Cuinat
	 */
	public void tokenize() {
		BufferedReader reader = null;
		int line_number = 0;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File("./"+this.source)), StandardCharsets.UTF_8));
		} catch (FileNotFoundException e) {
			System.out.println("Source file not found");
			e.printStackTrace();
		}
		try {
			String line = null;
			while ((line = reader.readLine()) != null) {
				line_number++;
				this.lexicalRules.process(line,line_number,this.tokens);
				if (this.tokens.size() > 0){
					this.tokens.add(new Token("CarriageReturn", "\\n", line_number, this.tokens.get(this.tokens.size()-1).getColumn()+1));
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}	
	
	/**
	 * Getter of the instance variable Source. This variable contains the name of the source code file.
	 * @return The name of the source code file.
	 * @author C.Silva, R.Cuinat
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Getter of the instance variable Tokens. It contains the tokenized code.
	 * @return A copy of the list of tokens created via the tokenize() method.
	 * @author C.Silva, R.Cuinat
	 */
	public ArrayList<Token> getTokens() {
		return new ArrayList<>(this.tokens);
	}

	/**
	 * Return a string representation of the Lexer. 
	 * This representation is composed of of an enumeration of the determined tokens including their type, value, line and column.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Token token : this.tokens) {
			string.append(token.getKind()).append("\t|\t").append(token.getValue()).append("\t|\t").append(token.getLine()).append("\t|\t").append(token.getColumn()).append("\t|\n");
		}
		return string.substring(0,string.length()-1);
	}		
}