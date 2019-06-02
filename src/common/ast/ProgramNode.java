package common.ast;

import visitors.Visitor;

/**
 * Root Node of an AST.
 * @author C.Silva, R.Cuinat
 */
public class ProgramNode extends Node {
	
	/**
	 * Constructor of the class.
	 * @author C.Silva, R.Cuinat
	 */
	public ProgramNode() {
		super(0,0);
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