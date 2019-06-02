package python.ast.statements.keywords;

import python.ast.statements.StatementNode;
import python.ast.statements.expressions.ExpressionNode;
import visitors.Visitor;

/**
 * Node specifying a python return statement.
 * @author C.Silva, R.Cuinat
 */
public class ReturnStatementNode extends StatementNode {
	private ExpressionNode expression;
	
	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public ReturnStatementNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Sets the  returned expression;
	 * @param expression Returned expression.
	 * @author C.Silva, R.Cuinat
	 */
	public void setExpression(ExpressionNode expression) {
		this.expression = expression;
		this.attachNode(this.expression);
	}	
	
	/**
	 * Getter of the expression field.
	 * @return The returned expression of the statement.
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
