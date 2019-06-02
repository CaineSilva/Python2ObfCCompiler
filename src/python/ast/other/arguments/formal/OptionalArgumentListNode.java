package python.ast.other.arguments.formal;

import common.ast.Node;
import visitors.Visitor;

/**
 * Node of an AST representing the formal optional argument list of a function. 
 * @author C.Silva, R.Cuinat
 *
 */
public class OptionalArgumentListNode extends Node {

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public OptionalArgumentListNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Add the specified argument to the children of this node.
	 * @param arg OptionalArgumentNode to attach to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public void addOptionalArg(OptionalArgumentNode arg) {
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
