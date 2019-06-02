package python.ast.other.arguments.actual;

import common.ast.Node;
import common.lexer.Token;
import python.ast.statements.expressions.ExpressionNode;
import python.ast.statements.expressions.IdentifierNode;
import visitors.Visitor;

/**
 * Node of an AST representing an actual optional argument.
 * @author C.Silva, R.Cuinat
 */
public class ActualOptionalArgumentNode extends Node {
	private ExpressionNode value;
	private IdentifierNode name;

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public ActualOptionalArgumentNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Sets the name of the argument with the given identifier token.
	 * @param name Name of the optional argument.
	 * @author C.Silva, R.Cuinat
	 */
	public void setName(Token name) {
		this.name = new IdentifierNode(name);
		this.attachNode(this.name);
	}
	
	/**
	 * Sets the value of the argument with the given expression
	 * @param expression value of the optional argument.
	 * @author C.Silva, R.Cuinat
	 */
	public void setValue(ExpressionNode expression) {
		this.value = expression;
		this.attachNode(this.value);
	}
	
	/**
	 * Getter of the name field.
	 * @return The name of the optional argument.
	 * @author C.Silva, R.Cuinat
	 */
	public IdentifierNode getName() {
		return this.name;
	}
	
	/**
	 * Getter of the value field.
	 * @return The value of the optional argument.
	 * @author C.Silva, R.Cuinat
	 */
	public ExpressionNode getValue() {
		return this.value;
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
