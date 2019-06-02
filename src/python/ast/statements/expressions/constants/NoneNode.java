package python.ast.statements.expressions.constants;

import visitors.Visitor;

/**
 * None Node of an AST.
 * @author C.Silva, R.Cuinat
 */
public class NoneNode extends ConstantNode {
	private final Object value;
	
	/**
	 * Constructor of the class.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public NoneNode(int line, int column) {
		super(line, column);
		this.value = null;
	}
	
	/**
	 * Getter of the value field.
	 * @return null.
	 * @author C.Silva, R.Cuinat
	 */
	public Object getValue() {
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
