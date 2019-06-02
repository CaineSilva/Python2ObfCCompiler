package python.ast.statements.expressions;

import visitors.Visitor;

/**
 * Node class defining an iterable.
 * @author C.Silva, R.Cuinat
 *
 */
public class IterableNode extends ExpressionNode {
	private final ExpressionNode expression;
	
	/**
	 * Constructor of the class.
	 * @param expression ExpressionNode defining the iterable.
	 * @author C.Silva, R.Cuinat
	 */
	public IterableNode(ExpressionNode expression) {
		super(expression.getLine(), expression.getColumn());
		this.expression = expression;
		this.attachNode(expression);
	}
	
	/**
	 * Getter of the expression field.
	 * @return The expression of the iterable.
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
