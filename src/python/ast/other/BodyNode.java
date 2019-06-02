package python.ast.other;

import common.ast.Node;
import python.ast.statements.StatementNode;
import visitors.Visitor;

/**
 * Node of an AST representing a python block/body
 * @author C.Silva, R.Cuinat
 */
public class BodyNode extends Node {

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public BodyNode(int line, int column) {
		super(line, column);
	}
	
	/**
	 * Add a statement to the block
	 * @param node Statement to add to the body;
	 * @author C.Silva, R.Cuinat
	 */
	public void addStatement(StatementNode node) {
		this.attachNode(node);
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
