package python.ast.statements.blocks;

import common.lexer.Token;
import python.ast.other.BodyNode;
import python.ast.other.arguments.formal.FormalArgumentListNode;
import python.ast.statements.StatementNode;
import python.ast.statements.expressions.IdentifierNode;
import visitors.Visitor;

/**
 * Node specifying a python function.
 * @author C.Silva, R.Cuinat
 */
public class FunctionDefinitionNode extends StatementNode {
	private IdentifierNode name;
	private FormalArgumentListNode argList;
	private BodyNode body;

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public FunctionDefinitionNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Sets the function name with the given token
	 * @param t Identifier token with the name of the function.
	 * @author C.Silva, R.Cuinat
	 */
	public void setName(Token t) {
		this.name = new IdentifierNode(t);
		this.attachNode(name);
	}
	
	/**
	 * Sets the function arguments with the given ArgumentListNode
	 * @param argList Node specifying the formal arguments of the function.
	 * @author C.Silva, R.Cuinat
	 */
	public void setArgs(FormalArgumentListNode argList) {
		this.argList = argList;
		this.attachNode(this.argList);
	}
	

	/**
	 * Sets the function body with the given BodyNode
	 * @param n BodyNode specifying the body of the function
	 * @author C.Silva, R.Cuinat
	 */
	public void setBody(BodyNode n) {
		this.body = n;
		this.attachNode(this.body);
	}
	
	/**
	 * Getter of the name field.
	 * @return The name of the function.
	 * @author C.Silva, R.Cuinat
	 */
	public IdentifierNode getName() {
		return this.name;
	}

	/**
	 * Getter of the argList field.
	 * @return The argument list of the function.
	 * @author C.Silva, R.Cuinat
	 */
	public FormalArgumentListNode getArgList() {
		return this.argList;
	}
	
	/**
	 * Getter of the body field.
	 * @return The body of the function.
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
