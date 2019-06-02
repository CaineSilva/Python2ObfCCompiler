package visitors;

import java.util.ArrayList;

import common.ast.AST;
import common.ast.Node;
import common.ast.ProgramNode;
import python.ast.other.BodyNode;
import python.ast.other.LeftMemberNode;
import python.ast.other.RightMemberNode;
import python.ast.other.arguments.actual.ActualArgumentListNode;
import python.ast.other.arguments.actual.ActualArgumentNode;
import python.ast.other.arguments.actual.ActualOptionalArgumentListNode;
import python.ast.other.arguments.actual.ActualOptionalArgumentNode;
import python.ast.other.arguments.actual.CallArgumentListNode;
import python.ast.other.arguments.formal.FormalArgumentListNode;
import python.ast.other.arguments.formal.MandatoryArgumentListNode;
import python.ast.other.arguments.formal.MandatoryArgumentNode;
import python.ast.other.arguments.formal.OptionalArgumentListNode;
import python.ast.other.arguments.formal.OptionalArgumentNode;
import python.ast.statements.AssignmentNode;
import python.ast.statements.blocks.ForStatementNode;
import python.ast.statements.blocks.FunctionDefinitionNode;
import python.ast.statements.blocks.IfStatementNode;
import python.ast.statements.blocks.WhileStatementNode;
import python.ast.statements.blocks.ifBlocks.ElifBlockNode;
import python.ast.statements.blocks.ifBlocks.ElseBlockNode;
import python.ast.statements.blocks.ifBlocks.IfBlockNode;
import python.ast.statements.expressions.ExpressionNode;
import python.ast.statements.expressions.FunctionCallNode;
import python.ast.statements.expressions.IdentifierNode;
import python.ast.statements.expressions.IterableNode;
import python.ast.statements.expressions.OperationExpressionNode;
import python.ast.statements.keywords.AssertStatementNode;
import python.ast.statements.keywords.ReturnStatementNode;

/**
 * Check the existence of the variables found in the expressions of the AST.
 * @author C.Silva, R.Cuinat
 */
public class VariableCheckerVisitor implements Visitor {
	private final ArrayList<String> vars;
	private static ArrayList<String> reservedVars;
	private final ArrayList<String> var_buffer;
	
	/**
	 * Constructor of the class
	 * @author C.Silva, R.Cuinat
	 */
	public VariableCheckerVisitor() {
		vars = new ArrayList<>();
		VariableCheckerVisitor.init();
		vars.addAll(VariableCheckerVisitor.reservedVars);
		this.var_buffer = new ArrayList<>();
	}
	
	/**
	 * Initializes useful resources for the check.
	 * @author C.Silva, R.Cuinat
	 */
	private static void init() {
		if (VariableCheckerVisitor.reservedVars == null) {
			VariableCheckerVisitor.reservedVars = new ArrayList<>();
			VariableCheckerVisitor.reservedVars.add("__name__");
		}		
	}
	
	/**
	 * Checks the AST
	 * @param ast Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(AST ast) {
		System.out.println("Checking variables");
		ast.accept(this);
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(Node node) {
		
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(AssignmentNode node) {
		if (node.getRightMember() != null) {
			node.getRightMember().accept(this);
			IdentifierNode varname = ((IdentifierNode)node.getLeftMember().getChildren().get(0));
			if (VariableCheckerVisitor.reservedVars.contains(varname.getName())) {
				System.err.println("\nError : Assigning reserved variable " + varname + " (L"+varname.getLine() + ", C"+varname.getColumn()+")");
			}
			else {
				this.vars.add(varname.getName());	
			}
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IdentifierNode node) {
		if (!this.vars.contains(node.getName())){
			System.err.println("\nError : Undefined variable " + node.getName() + " (L" + node.getLine() + ", C" + node.getColumn() + ")");
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FunctionDefinitionNode node) {
		int initial_size_v = vars.size();
		if (node.getArgList() != null) {
			this.var_buffer.clear();
			node.getArgList().accept(this);
			this.vars.addAll(this.var_buffer);
		}
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FormalArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(MandatoryArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(MandatoryArgumentNode node) {
		if (node.getName() != null) {
			this.var_buffer.add(node.getName().getName());
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(OptionalArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(OptionalArgumentNode node) {
		if (node.getName() != null) {
			this.var_buffer.add(node.getName().getName());
			node.getDefault().accept(this);
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ForStatementNode node) {
		int initial_size_v = vars.size();
		node.getIterable().accept(this);
		this.vars.add(node.getVariable().getName());
		node.getBody().accept(this);
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FunctionCallNode node) {
		if (node.getArgList() != null) {
			node.getArgList().accept(this);
		}
	}
	
	/**
	 * Process the node
	 * @param node Node to process
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ActualOptionalArgumentNode node) {
		node.getValue().accept(this);
	}
	
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ExpressionNode node) {
		
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}

	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IterableNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(OperationExpressionNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ElifBlockNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ElseBlockNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IfBlockNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(AssertStatementNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}

	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IfStatementNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}

	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ReturnStatementNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}

	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(WhileStatementNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualArgumentListNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualArgumentNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualOptionalArgumentListNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}

	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(CallArgumentListNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(BodyNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(LeftMemberNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(RightMemberNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
	/**
	 * Process the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ProgramNode node) {
		int initial_size_v = vars.size();
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
		if (vars.size() > initial_size_v) {
			vars.subList(initial_size_v, vars.size()).clear();
		}
	}
}
