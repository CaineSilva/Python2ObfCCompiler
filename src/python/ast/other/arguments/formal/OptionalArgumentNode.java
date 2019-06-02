package python.ast.other.arguments.formal;

import common.ast.Node;
import common.lexer.Token;
import python.ast.statements.expressions.ExpressionNode;
import python.ast.statements.expressions.IdentifierNode;
import visitors.Visitor;

/**
 * Node of an AST representing a formal optional argument.
 * @author C.Silva, R.Cuinat
 */
public class OptionalArgumentNode extends Node {
	private IdentifierNode name;
	private ExpressionNode default_value;
	
	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public OptionalArgumentNode(int line, int column) {
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
	 * Sets the default value of the argument with the given expression
	 * @param expression Default value.
	 * @author C.Silva, R.Cuinat
	 */
	public void setDefault(ExpressionNode expression) {
		this.default_value = expression;
		this.attachNode(this.default_value);
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
	 * Getter of the default_value field.
	 * @return The default value of the optional argument.
	 * @author C.Silva, R.Cuinat
	 */
	public ExpressionNode getDefault() {
		return this.default_value;
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
