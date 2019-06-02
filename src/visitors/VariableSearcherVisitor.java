package visitors;

import common.ast.Node;
import python.ast.statements.blocks.FunctionDefinitionNode;
import python.symbolTable.SymbolTable;
import python.symbolTable.VariableSymbol;
import python.symbolTable.types.UndefinedType;
import python.ast.statements.AssignmentNode;
import python.ast.statements.expressions.IdentifierNode;

/**
 * Visitor class used to discover the variables of an AST. The functions are not explored.
 * @author C.Silva, R.Cuinat
 */
public class VariableSearcherVisitor implements Visitor {
	private final SymbolTable symbolTable;

	/**
	 * Constructor of the class.
	 * @param st SymbolTable to fill with the identified variables.
	 * @author C.Silva, R.Cuinat
	 */
	public VariableSearcherVisitor(SymbolTable st) {
		this.symbolTable = st;
	}
	
	/**
	 * Start the visit of the subtree whose root node is given.
	 * @param node Node to visit.
	 * @return The filled SymbolTable.
	 * @author C.Silva, R.Cuinat
	 */
	public SymbolTable startVisit(Node node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		return this.symbolTable;
	}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(FunctionDefinitionNode node){

	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(AssignmentNode node) {
		VariableSymbol var = this.symbolTable.getVariableByName(((IdentifierNode) node.getLeftMember().getExpression()));
		if (var == null){
			var = new VariableSymbol(node, this.symbolTable);
			this.symbolTable.addVariable(var);
		}
		else if (var.getType().getName().compareTo("Undefined") == 0) {
			TypeDiscovererVisitor v = new TypeDiscovererVisitor(this.symbolTable);
			v.visit(node.getRightMember());
			if (v.getResult().getName().compareTo("Undefined") != 0 || ((UndefinedType) v.getResult()).getCause().size() < ((UndefinedType)var.getType()).getCause().size()) {
				var.setType(v.getResult());
			}
		}
	}
}
