package python.ast.statements.expressions.constants;

import common.lexer.Token;
import visitors.Visitor;

/**
 * String Node of an AST.
 * @author C.Silva, R.Cuinat
 */
public class StringNode extends ConstantNode {
	private String value;
	
	/**
	 * Constructor of the class.
	 * @param t Token representing the String.
	 * @author C.Silva, R.Cuinat
	 */
	public StringNode(Token t) {
		super(t.getLine(), t.getColumn());
		assert (t.getKind().compareTo("String") == 0);
		this.value = t.getValue();
	}
	
	/**
	 * Getter of the value field.
	 * @return The value of the String.
	 * @author C.Silva, R.Cuinat
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * Allow this node to be visited by the specified visitor
	 * @param v The visitor v
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}

}
