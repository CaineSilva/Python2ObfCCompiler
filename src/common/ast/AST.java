package common.ast;

import visitors.Visitor;

/**
 * AST class. This class only saves the root node and the source file.
 * @author C.Silva, R.Cuinat
 */
public class AST {
	private final ProgramNode root;
	private final String source_file;
	
	/**
	 * Constructor of the class.
	 * @param source_file Name of the source file 
	 * @author C.Silva, R.Cuinat
	 */
	public AST(String source_file) {
		this.source_file = source_file;
		this.root = new ProgramNode();
	}
	
	/**
	 * Getter of the tree root.
	 * @return The root of the tree.
	 * @author C.Silva, R.Cuinat
	 */
	public ProgramNode  getRoot() {
		return this.root;
	}
	
	/**
	 * Getter of the source file name.
	 * @return The String representation of the source file name.
	 * @author C.Silva, R.Cuinat
	 */
	public String getSource() {
		return this.source_file;
	}
	
	/**
	 * Attach a new node to the root of the tree.
	 * @param n Node to be attached.
	 * @author C.Silva, R.Cuinat
	 */
	public void grow(Node n) {
		this.root.attachNode(n);
	}
	
	/**
	 * Allow this AST to be visited by the specified visitor
	 * @param v The visitor v
	 * @author C.Silva, R.Cuinat
	 */
	public void accept(Visitor v) {
		v.visit(this.root);
	}
}
