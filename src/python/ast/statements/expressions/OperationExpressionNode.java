package python.ast.statements.expressions;

import common.lexer.Token;
import python.ast.other.LeftMemberNode;
import python.ast.other.OperatorNode;
import python.ast.other.RightMemberNode;
import visitors.Visitor;

/**
 * Node of an AST representing an operation expression.
 * @author C.Silva, R.Cuinat
 */
public class OperationExpressionNode extends ExpressionNode {
	private RightMemberNode rightMember;
	private LeftMemberNode leftMember;
	private OperatorNode operator;

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public OperationExpressionNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Sets the left member of the operation.
	 * @param expression Expression of the left member.
	 * @author C.Silva, R.Cuinat
	 */
	public void setLeftMember(ExpressionNode expression) {
		this.leftMember = new LeftMemberNode(expression);
		this.attachNode(this.leftMember);
	}
	
	/**
	 * Sets the left member of the operation.
	 * @param expression Expression of the left member.
	 * @author C.Silva, R.Cuinat
	 */
	public void setRightMember(ExpressionNode expression) {
		this.rightMember = new RightMemberNode(expression);
		this.attachNode(this.rightMember);
	}
	
	/**
	 * Sets the operator of the operation.
	 * @param t Token representing the assignment operator.
	 * @author C.Silva, R.Cuinat
	 */
	public void setOperator(Token t) {
		this.operator = new OperatorNode(t);
		this.attachNode(this.operator);
	}
	
	/**
	 * Getter of the rightMember field.
	 * @return The right member of the operation.
	 * @author C.Silva, R.Cuinat
	 */
	public RightMemberNode getRightMember() {
		return this.rightMember;
	}
	
	/**
	 * Getter of the operator field.
	 * @return The operator of the operation.
	 * @author C.Silva, R.Cuinat
	 */
	public OperatorNode getOperator() {
		return this.operator;
	}
	
	/**
	 * Getter of the leftMember field.
	 * @return The left member of the operation.
	 * @author C.Silva, R.Cuinat
	 */
	public LeftMemberNode getLeftMember() {
		return this.leftMember;
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
