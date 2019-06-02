package common.lexer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract data structure representing the lexical rules of a language.
 * This class is to be extended and supplied with the rules of a specific language.
 * @author C.Silva, R.Cuinat
 */
public abstract class LexicalRules {
	protected ArrayList<String> tags;
	protected ArrayList<String> regexes;
	
	/**
	 * Method tokenizing the given line.
	 * @author C.Silva, R.Cuinat
	 * @param line Line to tokenize
	 * @param line_number Line number of the line.
	 * @param tokens List of Tokens to fill with the determined tokens
	 */
	public void process(String line, int line_number, ArrayList<Token> tokens) {
		int position = 0;
		while (position < line.length()) {
			Matcher match = null;
			for (String tag : this.getTags()) {
				String pattern = this.get(tag);
				Pattern regex = Pattern.compile(pattern);
				match = regex.matcher(line.substring(position, line.length()));
				if (match.lookingAt()) {
					String data = match.group(0);
					if(tag.compareTo("SeparatorTab") != 0 && tag.compareTo("String") != 0) {
						tokens.add(new Token(tag,data.replaceAll(" ",""),line_number,position+1));
					}
					else {
						tokens.add(new Token(tag,data,line_number,position+1));
					}
					
					break;
				}
			}
			match.reset();
			if (!match.find()) {
				System.out.println(line);
				System.out.println("No match");
				System.exit(1);
			}
			else {
				position += match.end(0);
			}
		}
	}
	
	/**
	 * Maps the specified tag with the specified regex in the LexicalRule.
	 * This method is only to be used by subclasses to initialize the rules.
	 * @author C.Silva, R.Cuinat
	 * @param tag Tag of the rule
	 * @param regex Regex of the rule
	 */
	protected void put(String tag, String regex) {
		this.tags.add(tag);
		this.regexes.add(regex);
	}
	
	/**
	 * Returns the regex of the rules maps with the specified tag.
	 * If the given tag is not known, null is returned
	 * @author C.Silva, R.Cuinat
	 * @param tag The tag of the requested rule regex.
	 * @return The regex mapped with the given tag or null if the tag is unknown.
	 */
    private String get(String tag) {
		int i = this.tags.indexOf(tag);
		if (i == -1) {
			return null;
		}
		return this.regexes.get(i);
	}
	
	/**
	 * Returns a copy of the tag list.
	 * The tags are sorted in the same order they were put in the LexicalRule object.
	 * @author C.Silva, R.Cuinat
	 * @return A list containing the tags of the rules.
	 */
	@SuppressWarnings("unchecked")
    private ArrayList<String> getTags(){
		return (ArrayList<String>) this.tags.clone();
	}
	
	/**
	 * Returns a String representation of this object.
	 * This representation is composed of an enumeration of the tags and the associated regex.
	 */
	@Override
	public String toString() {
		StringBuilder chaine = new StringBuilder();
		for (int i=0;i<this.tags.size();i++) {
			chaine.append(this.tags.get(i)).append(" --> ").append(this.regexes.get(i)).append("\n");
		}
		return chaine.substring(0,chaine.length()-1);
	}
}
