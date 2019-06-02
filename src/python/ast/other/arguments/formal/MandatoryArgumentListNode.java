package python.ast.other.arguments.formal;

import common.ast.Node;
import common.lexer.Token;
import visitors.Visitor;

/**
 * Node of an AST representing the formal mandatory argument list of a function. 
 * @author C.Silva, R.Cuinat
 *
 */
public class MandatoryArgumentListNode extends Node {
	
	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public MandatoryArgumentListNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Add the specified argument to the children of this node.
	 * @param arg MandatoryArgumentNode to attach to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public void addArg(Token arg) {
		assert (arg.getKind().compareTo("Identifier") == 0);
		this.attachNode(new MandatoryArgumentNode(arg));
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
