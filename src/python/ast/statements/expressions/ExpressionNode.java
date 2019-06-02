package python.ast.statements.expressions;

import python.ast.statements.StatementNode;
import visitors.Visitor;

/**
 * Node of the AST representing an expression.
 * @author C.Silva, R.Cuinat
 *
 */
public class ExpressionNode extends StatementNode {

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
    protected ExpressionNode(int line, int column) {
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
