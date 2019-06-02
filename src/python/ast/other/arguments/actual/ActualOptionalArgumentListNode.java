package python.ast.other.arguments.actual;

import common.ast.Node;
import visitors.Visitor;

/**
 * Node of an AST representing the concrete optional argument list of a function. 
 * @author C.Silva, R.Cuinat
 *
 */
public class ActualOptionalArgumentListNode extends Node {

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public ActualOptionalArgumentListNode(int line, int column) {
		super(line,column);
	}
	
	/**
	 * Add the specified argument to the children of this node.
	 * @param arg ActualOptionalArgumentNode to attach to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public void addOptionalArg(ActualOptionalArgumentNode arg) {
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
