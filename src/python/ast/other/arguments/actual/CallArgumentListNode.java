package python.ast.other.arguments.actual;

import common.ast.Node;
import visitors.Visitor;

/**
 * Node of an AST representing the argument list of a function call. 
 * @author C.Silva, R.Cuinat
 *
 */
public class CallArgumentListNode extends Node {
	private ActualArgumentListNode actualArgumentList;
	private ActualOptionalArgumentListNode actualOptionalArgumentList;
	
	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public CallArgumentListNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Add the specified actual argument list to the children of this node.
	 * @param argList List of all actual mandatory argument of the function call.
	 * @author C.Silva, R.Cuinat
	 */
	public void addArgs(ActualArgumentListNode argList) {
		this.actualArgumentList = argList;
		this.attachNode(this.actualArgumentList);
	}
	
	/**
	 * Add the specified actual optional argument list to the children of this node.
	 * @param argList List of all actual mandatory argument of the function call.
	 * @author C.Silva, R.Cuinat
	 */
	public void addOptionalArgs(ActualOptionalArgumentListNode argList) {
		this.actualOptionalArgumentList = argList;
		this.attachNode(this.actualOptionalArgumentList);
	}
	
	/**
	 * Getter of the actualArgumentList field.
	 * @return The actual argument list of the function which is called.
	 * @author C.Silva, R.Cuinat
	 */
	public ActualArgumentListNode getActualArgumentList() {
		return this.actualArgumentList;
	}
	
	/**
	 * Getter of the actualOptionalArgumentList field.
	 * @return The actual optional argument list of the function which is called.
	 * @author C.Silva, R.Cuinat
	 */
	public ActualOptionalArgumentListNode getActualOptionalArgumentList() {
		return this.actualOptionalArgumentList;
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