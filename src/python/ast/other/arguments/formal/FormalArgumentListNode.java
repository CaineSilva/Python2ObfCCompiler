package python.ast.other.arguments.formal;

import common.ast.Node;
import visitors.Visitor;

/**
 * Node of an AST representing the formal argument list of a function. 
 * @author C.Silva, R.Cuinat
 *
 */
public class FormalArgumentListNode extends Node {
	private MandatoryArgumentListNode mandatoryArgumentList;
	private OptionalArgumentListNode optionalArgumentList;
	
	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public FormalArgumentListNode(int line, int column) {
		super(line,column);
	}
	
	/**
	 * Attach the formal mandatory argument list to the list of argument.
	 * @param argList The formal mandatory argument list of the function
	 * @author C.Silva, R.Cuinat
	 */
	public void setMandatoryArgList(MandatoryArgumentListNode argList) {
		this.mandatoryArgumentList = argList;
		this.attachNode(this.mandatoryArgumentList);
	}
	
	/**
	 * Attach the formal optional argument list to the list of argument.
	 * @param argList The formal mandatory argument list of the function
	 * @author C.Silva, R.Cuinat
	 */
	public void setOptionalArgList(OptionalArgumentListNode argList) {
		this.optionalArgumentList = argList;
		this.attachNode(this.optionalArgumentList);
	}
	
	/**
	 * Getter of the actualArgumentList field.
	 * @return The mandatory argument list of the function.
	 * @author C.Silva, R.Cuinat
	 */
	public MandatoryArgumentListNode getMandatoryArgumentList() {
		return this.mandatoryArgumentList;
	}
	
	/**
	 * Getter of the actualOptionalArgumentList field.
	 * @return The optional argument list of the function.
	 * @author C.Silva, R.Cuinat
	 */
	public OptionalArgumentListNode getOptionalArgumentList() {
		return this.optionalArgumentList;
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
