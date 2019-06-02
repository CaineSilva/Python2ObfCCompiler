package visitors;

import java.util.ArrayList;

import common.ast.Node;
import python.symbolTable.FunctionSymbol;
import python.symbolTable.SymbolTable;
import python.symbolTable.types.AbstractType;
import python.symbolTable.types.UndefinedType;
import python.ast.other.BodyNode;
import python.ast.statements.blocks.ForStatementNode;
import python.ast.statements.blocks.FunctionDefinitionNode;
import python.ast.statements.blocks.IfStatementNode;
import python.ast.statements.blocks.WhileStatementNode;
import python.ast.statements.blocks.ifBlocks.ElifBlockNode;
import python.ast.statements.blocks.ifBlocks.ElseBlockNode;
import python.ast.statements.blocks.ifBlocks.IfBlockNode;
import python.ast.statements.expressions.ExpressionNode;
import python.ast.statements.keywords.ReturnStatementNode;

/**
 * Visitor class determining the return type of the functions in an AST. All functions must already have been added to teh symbolTable for this visit to proceed.
 * The visit is to be started from a FunctionDefinitionNode to be relevant.
 * @author C.Silva, R.Cuinat
 */
public class ReturnDiscovererVisitor implements Visitor {
	private SymbolTable symbolTable;
	private final ArrayList<AbstractType> result;
	private FunctionDefinitionNode node;

	/**
	 * Constructor of the class
	 * @param st SymbolTable containing the variables of the tree to visit and its functions.
	 * @author C.Silva, R.Cuinat
	 */
	public ReturnDiscovererVisitor(SymbolTable st) {
		this.symbolTable = new SymbolTable(st);
		this.result = new ArrayList<>();
	}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(Node node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}	
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ExpressionNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ElifBlockNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ElseBlockNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IfBlockNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ForStatementNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(FunctionDefinitionNode node) {
		this.node = node;
		FunctionSymbol func = this.symbolTable.getFunctionByName(node.getName());
		if (func == null) {
			System.err.println("Error during function discovery : Unknown function (L"+node.getLine()+", C"+node.getColumn()+").");
			System.exit(1);
		}
		this.symbolTable.addVariableFromMandatories(func.getMandatoryArgs());
		this.symbolTable.addVariableFromOptionals(func.getOptionals());
		VariableSearcherVisitor vsv = new VariableSearcherVisitor(this.symbolTable);
		this.symbolTable = vsv.startVisit(node.getBody());
		node.getBody().accept(this);
		
		
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IfStatementNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ReturnStatementNode node) {
		TypeDiscovererVisitor tdv = new TypeDiscovererVisitor(this.symbolTable);
		node.getExpression().accept(tdv);
		this.result.add(tdv.getResult());
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(WhileStatementNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(BodyNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}

	/**
	 * Method used to get the result of the visit.
	 * @return The returned type of the visited function.
	 * @author C.Silva, R.Cuinat
	 */
	public AbstractType getResult() {
		AbstractType r = new UndefinedType(null);
		for (AbstractType type : this.result) {
			if (r.getName().compareTo("Undefined") == 0) {
				r = type;
			}
			else {
				if (r.getName().compareTo(type.getName()) == 0 || type.getName().compareTo("Undefined") == 0) {
				}
				else {
					System.err.println("Error during function discovery : Return type inconsistency with function (L"+this.node.getLine()+", C"+this.node.getColumn()+").");
					System.exit(1);
				}
			}
		}
		return r;
	}
}
