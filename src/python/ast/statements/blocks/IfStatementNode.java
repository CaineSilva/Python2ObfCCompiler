package python.ast.statements.blocks;

import python.ast.statements.StatementNode;
import python.ast.statements.blocks.ifBlocks.ElifBlockNode;
import python.ast.statements.blocks.ifBlocks.ElseBlockNode;
import python.ast.statements.blocks.ifBlocks.IfBlockNode;
import visitors.Visitor;

/**
 * Node specifying an if statement.
 * @author C.Silva, R.Cuinat
 */
public class IfStatementNode extends StatementNode {

	/**
	 * Initializes the children of this node.
	 * @param line Line of the token used to initialize this node
	 * @param column Column of the token used to initialize this node
	 * @author C.Silva, R.Cuinat
	 */
	public IfStatementNode(int line, int column) {
		super(line,column);
	}
	
	/**
	 * Sets the if block of the statement with the specified Node.
	 * @param block IfBlockNode to attach to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public void setIfBlock(IfBlockNode block) {
		this.attachNode(block);
	}
	
	/**
	 * Add an elif block to the statement with the specified Node.
	 * @param block ElifBlockNode to attach to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public void addElifBlock(ElifBlockNode block) {
		this.attachNode(block);
	}
	
	/**
	 * Add an else block to the statement with the specified Node.
	 * @param block ElseBlockNode to attach to this node.
	 * @author C.Silva, R.Cuinat
	 */
	public void addElseBlock(ElseBlockNode block) {
		this.attachNode(block);
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
