package visitors;

import java.util.ArrayList;

import common.ast.Node;
import common.ast.ProgramNode;
import python.symbolTable.ArgumentSymbol;
import python.symbolTable.FunctionSymbol;
import python.symbolTable.OptionalArgumentSymbol;
import python.symbolTable.SymbolTable;
import python.symbolTable.types.UndefinedType;
import python.ast.other.BodyNode;
import python.ast.other.LeftMemberNode;
import python.ast.other.RightMemberNode;
import python.ast.other.arguments.actual.ActualOptionalArgumentNode;
import python.ast.other.arguments.formal.MandatoryArgumentNode;
import python.ast.other.arguments.formal.OptionalArgumentNode;
import python.ast.statements.AssignmentNode;
import python.ast.statements.blocks.FunctionDefinitionNode;
import python.ast.statements.blocks.IfStatementNode;
import python.ast.statements.blocks.WhileStatementNode;
import python.ast.statements.blocks.ifBlocks.ElifBlockNode;
import python.ast.statements.blocks.ifBlocks.ElseBlockNode;
import python.ast.statements.blocks.ifBlocks.IfBlockNode;
import python.ast.statements.expressions.ExpressionNode;
import python.ast.statements.expressions.FunctionCallNode;
import python.ast.statements.expressions.OperationExpressionNode;
import python.ast.statements.keywords.AssertStatementNode;

/**
 * Visitor class filling a symbolTable with all functions found in a AST. (Functions defined inside functions will be ignored).
 * This visit aims at finding the function names and arguments symbols. The returned type is not determined during this visit.
 * @author C.Silva, R.Cuinat
 */
public class FunctionSearcherVisitor implements Visitor {
	private final SymbolTable symbolTable;

	/**
	 * Constructor fo the class.
	 * @param symbolTable SymbolTable to fill throughout the visit.
	 */
	public FunctionSearcherVisitor(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}

	/**
	 * Method used to start the visit.
	 * @param node Root node of the AST to search.
	 * @return The filled SymbolTable
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
	public void visit(FunctionCallNode node) {
		TypeDiscovererVisitor td;
		FunctionSymbol func = this.symbolTable.getFunctionByName(node.getName());
		if (func == null) {
			System.err.println("Error during function search : Call of undefined function (L"+node.getLine() + ", C" + node.getColumn() + ").");
			System.exit(1);
		}
		else {
			// Mandatory arguments
			ArrayList<ArgumentSymbol> args = func.getMandatoryArgs();
			if (node.getArgList().getActualArgumentList() == null) {
				if (args.size() != 0) {
					System.err.println("Error during function search : Wrong number of argument in function call (L"+node.getLine() + ", C" + node.getColumn() + ").");
					System.exit(1);
				}
			}
			else {
				if (node.getArgList().getActualArgumentList().getChildren().size() == args.size()) {
					for (int i=0;i<args.size();i++) {
						if (args.get(i).getType().getName().compareTo("Undefined") == 0) {
							td = new TypeDiscovererVisitor(this.symbolTable);
							node.getArgList().getActualArgumentList().getChildren().get(i).accept(td);
							args.get(i).setType(td.getResult());
							td.clear();
						}
						
					}
				}
				else {
					System.err.println("Error during function search : Wrong number of argument in function call (L"+node.getLine() + ", C" + node.getColumn() + ").");
					System.exit(1);
				}
			}
			// Optional Arguments
			if (node.getArgList().getActualOptionalArgumentList() != null) {
				td = new TypeDiscovererVisitor(this.symbolTable);
				for (Node n : node.getArgList().getActualOptionalArgumentList().getChildren()) {
					ActualOptionalArgumentNode actualOpt = (ActualOptionalArgumentNode) n;
					OptionalArgumentSymbol opt = func.getOptionalFromActual(actualOpt);
					if (opt == null) {
						System.err.println("Error during function search : Unknown optional argument in function call (L"+node.getLine() + ", C" + node.getColumn() + ").");
						System.exit(1);
					}
					actualOpt.accept(td);
					if (opt.getType().getName().compareTo("Undefined") == 0) {
						opt.setType(td.getResult());
					}
					else {
						if (td.getResult().getName().compareTo("Undefined") != 0 && td.getResult().getName().compareTo(opt.getType().getName()) != 0){
							System.err.println("Error during function search : Invalid type for optional argument in function call (L"+node.getLine() + ", C" + node.getColumn() + ").");
							System.exit(1);
						}
					}
				}
			}
		}
	}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(OperationExpressionNode node) {
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
	public void visit(AssertStatementNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(AssignmentNode node) {
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
		FunctionSymbol func = this.symbolTable.getFunctionByName(node.getName());
		if (func != null) {
			System.err.println("Error during function search : redefinition of function (L"+node.getLine() + ", C" + node.getColumn() + ").");
			System.exit(1);
		}
		func = new FunctionSymbol(node);
		func.setReturnedType(new UndefinedType(null));
		this.symbolTable.addFunction(func);
		//Mandatory Argument
		if (node.getArgList() != null && node.getArgList().getMandatoryArgumentList() != null) {
			for (Node n : node.getArgList().getMandatoryArgumentList().getChildren()) {
				MandatoryArgumentNode arg = (MandatoryArgumentNode) n;
				ArgumentSymbol known = func.getMandatoryByName(arg.getName());
				if (known == null) {
					func.addArg(arg);
				}
				else {
					System.err.println("Error during function search : redefinition of argument (L"+node.getLine() + ", C" + node.getColumn() + ").");
					System.exit(1);
				}
			}
		}
		//Optional
		if (node.getArgList() != null && node.getArgList().getOptionalArgumentList() != null) {
			for (Node n : node.getArgList().getOptionalArgumentList().getChildren()) {
				OptionalArgumentNode opt = (OptionalArgumentNode) n;
				OptionalArgumentSymbol optKnown = func.getOptionalByName(opt.getName());
				if (optKnown == null) {
					func.addOptionalArg(opt, this.symbolTable);
				}
				else {
					System.err.println("Error during function search : redefinition of optional argument (L"+node.getLine() + ", C" + node.getColumn() + ").");
					System.exit(1);
				}
			}
		}
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
	public void visit(WhileStatementNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	public void visit(BodyNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}

    /**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(LeftMemberNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}

    /**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(RightMemberNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ProgramNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
}
