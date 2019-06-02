package python.ast.statements.blocks.ifBlocks;

import python.ast.other.BodyNode;
import python.ast.statements.StatementNode;
import python.ast.statements.expressions.ExpressionNode;
import visitors.Visitor;

/**
 * Node specifying an elif block.
 * @author C.Silva, R.Cuinat
 */
public class ElifBlockNode extends StatementNode {
	private ExpressionNode condition;
	private BodyNode body;

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public ElifBlockNode(int line, int column) {
		super(line, column);
	}

	/**
	 * Sets the condition of the elif block with the specified expression.
	 * @param expression Condition of the block.
	 * @author C.Silva, R.Cuinat
	 */
	public void setCondition(ExpressionNode expression) {
		this.condition = expression;
		this.attachNode(this.condition);
	}
	
	/**
	 * Sets the block body with the given BodyNode
	 * @param n BodyNode specifying the body of the block
	 * @author C.Silva, R.Cuinat
	 */
	public void setBody(BodyNode n) {
		this.body = n;
		this.attachNode(this.body);
	}
	
	/**
	 * Getter of the condition field.
	 * @return The condition of the elif block.
	 * @author C.Silva, R.Cuinat
	 */
	public ExpressionNode getCondition() {
		return this.condition;
	}
	
	/**
	 * Getter of the body field.
	 * @return The body of the elif block.
	 * @author C.Silva, R.Cuinat
	 */
	public BodyNode getBody() {
		return this.body;
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
