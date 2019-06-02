package python.ast.statements.expressions;

import common.lexer.Token;
import visitors.Visitor;

/**
 * Node class defining an identifier.
 * @author C.Silva, R.Cuinat
 *
 */
public class IdentifierNode extends ExpressionNode {
	private String name;
	
	/**
	 * Constructor of the class.
	 * @param name String value of the identifier.
	 * @author C.Silva, R.Cuinat
	 */
	public IdentifierNode(Token name) {
		super(name.getLine(), name.getColumn());
		assert (name.getKind().compareTo("Identifier") == 0);
		this.name = name.getValue().replaceAll(" ", "");
	}
	
	/**
	 * Getter of the name field.
	 * @return The String representation of the identifier.
	 * @author C.Silva, R.Cuinat
	 */
	public String getName() {
		return this.name;
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
