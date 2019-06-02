package python.ast.statements.blocks;

import common.lexer.Token;
import python.ast.other.BodyNode;
import python.ast.statements.StatementNode;
import python.ast.statements.expressions.ExpressionNode;
import python.ast.statements.expressions.IdentifierNode;
import python.ast.statements.expressions.IterableNode;
import visitors.Visitor;

/**
 * Node specifying a for statement.
 * @author C.Silva, R.Cuinat
 */
public class ForStatementNode extends StatementNode {
	private BodyNode body;
	private IdentifierNode variable;
	private IterableNode iterable;

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public ForStatementNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Sets the iteration variable of the for statement.
	 * @param var Token representing the identifier of the iteration variable.
	 * @author C.Silva, R.Cuinat
	 */
	public void setVariable(Token var) {
		this.variable = new IdentifierNode(var);
		this.attachNode(this.variable);
	}
	
	/**
	 * Sets the iterable of the for statement.
	 * @param expression ExpressionNode representing the iterable of the for statement.
	 * @author C.Silva, R.Cuinat
	 */
	public void setIterable(ExpressionNode expression) {
		this.iterable = new IterableNode(expression);
		this.attachNode(this.iterable);
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
	 * Getter of the body field.
	 * @return The body of the for statement.
	 * @author C.Silva, R.Cuinat
	 */
	public BodyNode getBody() {
		return this.body;
	}
	
	/**
	 * Getter of the variable field.
	 * @return The iteration variable of the for statement.
	 * @author C.Silva, R.Cuinat
	 */
	public IdentifierNode getVariable() {
		return this.variable;
	}
	
	/**
	 * Getter of the iterable field.
	 * @return The iterable of the for statement.
	 * @author C.Silva, R.Cuinat
	 */
	public IterableNode getIterable() {
		return this.iterable;
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
