package python.ast.statements;

import common.ast.Node;
import visitors.Visitor;

/**
 * Node of an AST representing a statement.
 * @author C.Silva, R.Cuinat
 */
public abstract class StatementNode extends Node {

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	protected StatementNode(int line, int column) {
		super(line, column);
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
