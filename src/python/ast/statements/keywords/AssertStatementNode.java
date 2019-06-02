package python.ast.statements.keywords;

import python.ast.statements.StatementNode;
import python.ast.statements.expressions.ExpressionNode;
import visitors.Visitor;

/**
 * Node specifying a python assert statement.
 * @author C.Silva, R.Cuinat
 */
public class AssertStatementNode extends StatementNode {
	private ExpressionNode assertion;
	
	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public AssertStatementNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Sets the condition of the assertion.
	 * @param expression Condition of the assertion.
	 * @author C.Silva, R.Cuinat
	 */
	public void setAssertion(ExpressionNode expression) {
		this.assertion = expression;
		this.attachNode(this.assertion);
	}
	
	/**
	 * Getter of the condition field.
	 * @return The condition of the if block.
	 * @author C.Silva, R.Cuinat
	 */
	public ExpressionNode getAssertion() {
		return this.assertion;
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
