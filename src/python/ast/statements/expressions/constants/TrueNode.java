package python.ast.statements.expressions.constants;

import visitors.Visitor;

/**
 * True Node of an AST.
 * @author C.Silva, R.Cuinat
 */
public class TrueNode extends ConstantNode {
	private final boolean value;
	
	/**
	 * Constructor of the class.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public TrueNode(int line, int column) {
		super(line, column);
		this.value = true;
	}
	
	/**
	 * Getter of the value field.
	 * @return true.
	 * @author C.Silva, R.Cuinat
	 */
	public boolean getValue() {
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
