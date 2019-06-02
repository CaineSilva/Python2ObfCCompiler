package python.ast.other;

import common.ast.Node;
import python.ast.statements.expressions.ExpressionNode;
import visitors.Visitor;

/**
 * Node of an AST representing a left member of an expression. 
 * @author C.Silva, R.Cuinat
 *
 */
public class LeftMemberNode extends Node {
	private final ExpressionNode expression;
	/**
	 * Initializes the children of this node and sets the expression of the member with the given expression.
	 * @param expression The expression of the left member.
	 * @author C.Silva, R.Cuinat
	 */
	public LeftMemberNode(ExpressionNode expression) {
		super(expression.getLine(), expression.getColumn());
		this.expression = expression;
		this.attachNode(this.expression);
	}
	
	/**
	 * Getter of the expression field.
	 * @return The expression of the member.
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
