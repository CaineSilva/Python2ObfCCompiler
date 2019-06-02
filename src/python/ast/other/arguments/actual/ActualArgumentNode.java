package python.ast.other.arguments.actual;

import common.ast.Node;
import python.ast.statements.expressions.ExpressionNode;
import visitors.Visitor;

/**
 * Node of an AST representing an actual mandatory argument of a function. 
 * @author C.Silva, R.Cuinat
 *
 */
public class ActualArgumentNode extends Node {
	private final ExpressionNode expression;
	
	/**
	 * Initializes the children of this node and sets the name of the argument with the given identifier token.
	 * @param arg The expression of the actual argument.
	 * @author C.Silva, R.Cuinat
	 */
	public ActualArgumentNode(ExpressionNode arg) {
		super(arg.getLine(),arg.getColumn());
		this.expression = arg;
		this.attachNode(arg);
	}
	
	/**
	 * Getter of the expression field.
	 * @return The expression of this argument.
	 * @author C.Silva, R.Cuinat
	 */
	public ExpressionNode getExpression() {
		return this.expression;
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
