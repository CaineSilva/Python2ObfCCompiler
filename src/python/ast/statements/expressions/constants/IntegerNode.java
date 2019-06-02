package python.ast.statements.expressions.constants;

import common.lexer.Token;
import visitors.Visitor;

/**
 * String Node of an AST.
 * @author C.Silva, R.Cuinat
 */
public class IntegerNode extends ConstantNode {
	private int value;
	
	/**
	 * Constructor of the class.
	 * @param t Token representing the String.
	 * @author C.Silva, R.Cuinat
	 */
	public IntegerNode(Token t) {
		super(t.getLine(), t.getColumn());
		assert (t.getKind().compareTo("Integer") == 0);
		this.value = Integer.parseInt(t.getValue());
	}
	
	/**
	 * Getter of the value field.
	 * @return The value of the Integer.
	 * @author C.Silva, R.Cuinat
	 */
	public int getValue() {
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
