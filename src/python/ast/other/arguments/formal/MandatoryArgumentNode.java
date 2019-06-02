package python.ast.other.arguments.formal;

import common.ast.Node;
import common.lexer.Token;
import python.ast.statements.expressions.IdentifierNode;
import visitors.Visitor;

/**
 * Node of an AST representing a formal mandatory argument of a function. 
 * @author C.Silva, R.Cuinat
 *
 */
public class MandatoryArgumentNode extends Node {
	private final IdentifierNode name;
	
	/**
	 * Initializes the children of this node and sets the name of the argument with the given identifier token.
	 * @param  arg The Token representing the argument identifier.
	 * @author C.Silva, R.Cuinat
	 */
	public MandatoryArgumentNode(Token arg) {
		super(arg.getLine(), arg.getColumn());
		this.name = new IdentifierNode(arg);
		this.attachNode(this.name);
	}
	
	/**
	 * Getter of the name field.
	 * @return The identifier of this argument.
	 * @author C.Silva, R.Cuinat
	 */
	public IdentifierNode getName() {
		return this.name;
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
