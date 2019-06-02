package python.ast.statements.expressions;

import common.lexer.Token;
import python.ast.other.arguments.actual.CallArgumentListNode;
import visitors.Visitor;

/**
 * Node of an AST representing a function call.
 * @author C.Silva, R.Cuinat
 */
public class FunctionCallNode extends ExpressionNode {
	private IdentifierNode name;
	private CallArgumentListNode argList;
	
	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public FunctionCallNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Attach the name of the called function to this node.
	 * @param t Token representing the name of the function which is called.
	 * @author C.Silva, R.Cuinat
	 */
	public void setName(Token t) {
		this.name = new IdentifierNode(t);
		this.attachNode(this.name);
	}
	
	/**
	 * Attach a call argument list to this node.
	 * @param argList Node containing the actual argument of the call.
	 * @author C.Silva, R.Cuinat
	 */
	public void setArgList(CallArgumentListNode argList) {
		this.argList = argList;
		this.attachNode(argList);
	}
	
	/**
	 * Getter of the name field.
	 * @return The name of the function which is called.
	 * @author C.Silva, R.Cuinat
	 */
	public IdentifierNode getName() {
		return this.name;
	}

	/**
	 * Getter of the argList field.
	 * @return The argument list of the function which is called.
	 * @author C.Silva, R.Cuinat
	 */
	public CallArgumentListNode getArgList() {
		return argList;
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
