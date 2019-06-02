package python.lexer;

import java.util.ArrayList;

import common.lexer.LexicalRules;

/**
 * Data Structure containing all Python lexical rules.
 * @author C.Silva, R.Cuinat
 */
public class PythonLexicalRules extends LexicalRules {
	
	/**
	 * Constructor of the class.
	 * @author C.Silva, R.Cuinat
	 */
	public PythonLexicalRules() {
		this.tags = new ArrayList<>();
		this.regexes = new ArrayList<>();
		this.create_rules();
	}
	
	/**
	 * Initialize the data structure with all rules.
	 * @author C.Silva, R.Cuinat
	 */
	private void create_rules() {
		this.put("Separator#", "\\ *\\#\\ *");
		this.put("SeparatorTab", "(\t+)|(\\ \\ \\ \\ )+");
		this.put("Separator\'\'\'", "\\ *\\'\\'\\'\\ *");
		this.put("Separator\"\"\"", "\\ *\\\"\\\"\\\"\\ *");
		this.put("KeywordFalse", "\\ *False\\ +");
		this.put("KeywordTrue", "\\ *True\\ +");
		this.put("KeywordNone", "\\ *None\\ +");
		this.put("KeywordAnd", "\\ *and\\ +");
		this.put("KeywordAssert", "\\ *assert\\ +");
		this.put("KeywordAs", "\\ *as\\ +");
		this.put("KeywordBreak", "\\ *break\\ +");
		this.put("KeywordClass", "\\ *class\\ +");
		this.put("KeywordContinue", "\\ *continue\\ +");
		this.put("KeywordDef", "\\ *def\\ +");
		this.put("KeywordDel", "\\ *del\\ +");
		this.put("KeywordElif", "\\ *elif\\ +");
		this.put("KeywordElse", "\\ *else\\ *");
		this.put("KeywordExcept", "\\ *except\\ +");
		this.put("KeywordFinally", "\\ *finally\\ +");
		this.put("KeywordFor", "\\ *for\\ +");
		this.put("KeywordFrom", "\\ *from\\ +");
		this.put("KeywordGlobal", "\\ *global\\ +");
		this.put("KeywordIf", "\\ *if\\ +");
		this.put("KeywordImport", "\\ *import\\ +");
		this.put("KeywordIn", "\\ *in\\ +");
		this.put("KeywordIs", "\\ *is\\ +");
		this.put("KeywordLambda", "\\ *lambda\\ +");
		this.put("KeywordNonlocal", "\\ *nonlocal\\ +");
		this.put("KeywordNot", "\\ *not\\ +");
		this.put("KeywordOr", "\\ *or\\ +");
		this.put("KeywordPass", "\\ *pass\\ +");
		this.put("KeywordRaise", "\\ *raise\\ +");
		this.put("KeywordReturn", "\\ *return\\ +");
		this.put("KeywordTry", "\\ *try\\ +");
		this.put("KeywordWhile", "\\ *while\\ +");
		this.put("KeywordWith", "\\ *with\\ +");
		this.put("KeywordYield", "\\ *yield\\ +");
		//Literals
		this.put("String", "(\\\".*\\\")|(\\\'.*\\\')");
		this.put("Float", "\\ *(\\ *(([0-9]*\\.[0-9]+)|([0-9]+\\.[0-9]*)))e?(\\+|\\-)?[0-9]+"
				+ "|"
				+ "(\\ *[0-9]+e(\\+|\\-)?[0-9]+)\\ *");
		this.put("Integer","\\ *\\ *[0-9]+\\ *");
		//Comparison  operator
		this.put("Operator<=", "\\ *\\<\\=\\ *");
		this.put("Operator>=", "\\ *\\>\\=\\ *");
		this.put("Operator==", "\\ *\\=\\=\\ *");
		this.put("Operator!=", "\\ *\\!\\=\\ *");
		this.put("Operator<", "\\ *\\<\\ *");
		this.put("Operator>", "\\ *\\>\\ *");
		//Assignment operator
		this.put("Operator*=", "\\ *\\*\\=\\ *");
		this.put("Operator+=", "\\ *\\+\\=\\ *");
		this.put("Operator/=", "\\ *\\/\\=\\ *");
		this.put("Operator**=", "\\ *\\*\\*\\=\\ *");
		this.put("Operator-=", "\\ *\\-\\=\\ *");
		this.put("Operator%=", "\\ *\\%\\=\\ *");
		this.put("Operator=", "\\ *\\=\\ *");
		//Arithmetic operator
		this.put("Operator+", "\\ *\\+\\ *");
		this.put("Operator-", "\\ *\\-\\ *");
		this.put("Operator**", "\\ *\\*\\*\\ *");
		this.put("Operator*", "\\ *\\*\\ *");
		this.put("Operator//", "\\ *\\/\\/\\ *");
		this.put("Operator/", "\\ *\\/\\ *");
		this.put("Operator%", "\\ *\\%\\ *");	
		//Separator
		this.put("Separator;", "\\ *;\\ *");
		this.put("Separator:", "\\ *:\\ *");
		this.put("Separator(", "\\ *\\(\\ *");
		this.put("Separator)", "\\ *\\)\\ *");
		this.put("Separator[", "\\ *\\[\\ *");
		this.put("Separator]", "\\ *\\]\\ *");
		this.put("Separator{", "\\ *\\{\\ *");
		this.put("Separator}", "\\ *\\}\\ *");
		this.put("Separator,", "\\ *\\,\\ *");
		this.put("Separator.", "\\ *\\.\\ *");
		//Identifier
		this.put("Identifier", "\\ *(([a-z]|[A-Z]|\\_)([a-z]|[A-Z]|\\_|[0-9])*)\\ *");	
	}
}
