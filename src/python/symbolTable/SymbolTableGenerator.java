package python.symbolTable;

import common.ast.Node;
import visitors.FunctionSearcherVisitor;
import visitors.ReturnDiscovererVisitor;
import visitors.VariableSearcherVisitor;

/**
 * Factory class used to instantiate or complete a SymbolTable from a AST (or a subtree).
 * @author  C.Silva, R.Cuinat
 */
public class SymbolTableGenerator {
	private SymbolTable symbolTable;

	/**
	 * Constructor of the class.
	 * @param symbolTable SymbolTable to fill.
	 * @author C.Silva, R.Cuinat
	 */
	public SymbolTableGenerator(SymbolTable symbolTable) {
		this.symbolTable = new SymbolTable(symbolTable);
		
	}

	/**
	 * Factory method which fills the symbolTable with information extracted from an AST (or subtree).
	 * @param node Root node of the tree to process.
	 * @return The generated symbolTable.
	 */
	public SymbolTable generateFrom(Node node) {
		VariableSearcherVisitor vs = new VariableSearcherVisitor(this.symbolTable);
		this.symbolTable = vs.startVisit(node);
		FunctionSearcherVisitor fsv = new FunctionSearcherVisitor(this.symbolTable);
		this.symbolTable = fsv.startVisit(node);
		ReturnDiscovererVisitor rdv;
		for (FunctionSymbol func : this.symbolTable.getFunctions().subList(1, this.symbolTable.getFunctions().size())) {
			if (func.getReturnedType().getName().compareTo("Undefined") == 0) {
				rdv = new ReturnDiscovererVisitor(this.symbolTable);
				func.getNode().accept(rdv);
				func.setReturnedType(rdv.getResult());
			}
		}
		vs = new VariableSearcherVisitor(this.symbolTable);
		this.symbolTable = vs.startVisit(node);
		return this.symbolTable;
	}
}
