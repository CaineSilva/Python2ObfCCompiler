package python.ast.statements.expressions.constants;

import common.lexer.Token;
import visitors.Visitor;

/**
 * Float Node of an AST.
 * @author C.Silva, R.Cuinat
 */
public class FloatNode extends ConstantNode {
	private double value;
	
	/**
	 * Constructor of the class.
	 * @param t Token representing the Float.
	 * @author C.Silva, R.Cuinat
	 */
	public FloatNode(Token t) {
		super(t.getLine(), t.getColumn());
		assert (t.getKind().compareTo("Float") == 0);
		this.value = Double.parseDouble(t.getValue());
	}
	
	/**
	 * Getter of the value field.
	 * @return The value of the Float.
	 * @author C.Silva, R.Cuinat
	 */
	public double getValue() {
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
