package python.ast.other.arguments.actual;

import common.ast.Node;
import python.ast.statements.expressions.ExpressionNode;
import visitors.Visitor;

/**
 * Node of an AST representing the actual mandatory argument list of a function. 
 * @author C.Silva, R.Cuinat
 *
 */
public class ActualArgumentListNode extends Node {

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public ActualArgumentListNode(int line, int column) {
		super(line,column);
	}
	
	/**
	 * Add the specified argument to the children of this node.
	 * @param arg ActualArgumentNode to attach to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public void addArg(ExpressionNode arg) {
		this.attachNode(arg);
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
