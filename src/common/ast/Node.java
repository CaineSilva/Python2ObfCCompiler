package common.ast;

import java.util.ArrayList;

/**
 * Abstract class defining a node of an AST.
 * @author C.Silva, R.Cuinat
 */
public abstract class Node implements INode {
	private final ArrayList<Node> children;
	private final int line;
	private final int column;
	
	
	/**Initialize the list of children.
	 * @param line Line number corresponding to this node.
	 * @param column Column number corresponding to this node.
	 * @author C.Silva, R.Cuinat
	 */
	protected Node(int line, int column) {
		this.children = new ArrayList<>();
		this.line = line;
		this.column = column;
	}
	
	/**
	 * Returns the list of the children of this node.
	 * @return The list of the children of this node.
	 * @author C.Silva, R.Cuinat
	 */
	public ArrayList<Node> getChildren() {
		return children;
	}
	
	/**
	 * Add a the specified node to the list of children of this node. If the node is null, nothing happens.
	 * @param n Node to be added to the children of this node.
	 * @author C.Silva, R.Cuinat
	 */
	protected void attachNode(Node n) {
		if (n!= null) {
			this.children.add(n);
		}
	}
	
	/**
	 * Getter of the line field.
	 * @return The line number corresponding to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public int getLine() {
		return this.line;
	}
	
	/**
	 * Getter of the column field.
	 * @return The column number corresponding to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public int getColumn() {
		return this.column;
	}
}
