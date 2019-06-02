package visitors;

import common.ast.AST;
import common.ast.Node;
import common.ast.ProgramNode;
import python.ast.other.BodyNode;
import python.ast.other.LeftMemberNode;
import python.ast.other.OperatorNode;
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
import python.ast.statements.expressions.constants.FalseNode;
import python.ast.statements.expressions.constants.FloatNode;
import python.ast.statements.expressions.constants.IntegerNode;
import python.ast.statements.expressions.constants.NoneNode;
import python.ast.statements.expressions.constants.StringNode;
import python.ast.statements.expressions.constants.TrueNode;
import python.ast.statements.keywords.AssertStatementNode;
import python.ast.statements.keywords.BreakStatementNode;
import python.ast.statements.keywords.ContinueStatementNode;
import python.ast.statements.keywords.PassStatementNode;
import python.ast.statements.keywords.ReturnStatementNode;

/**
 * Pretty printer to display the source code from the AST.
 * @author C.Silva, R.Cuinat
 */
public class PythonPrettyPrinterVisitor implements Visitor {
	private int current_tab;

	/**
	 * Constructor of the class
	 * @author C.Silva, R.Cuinat
	 */
	public PythonPrettyPrinterVisitor() {
		this.current_tab = 0;
	}
	
	/**
	 * Print a new line and the relevant tabulations
	 * @author C.Silva, R.Cuinat
	 */
	private void newLine() {
		StringBuilder chaine = new StringBuilder("\n");
		for (int i=0;i<this.current_tab;i++) {
			chaine.append("\t");
		}
		System.out.print(chaine);
	}
	
	/**
	 * Displays the AST
	 * @param ast Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(AST ast) {
		System.out.println("Visiting the AST (\""+ast.getSource()+"\") to print the source code.\n" );
		ast.accept(this);
		System.out.println("\nEnd of the visit\n");
	}
	
	/**
	 * Displays the specified node.
	 * @param root Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ProgramNode root) {
		for (Node node : root.getChildren()) {
			node.accept(this);
			this.newLine();
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FunctionDefinitionNode node) {
		System.out.print("def ");
		if (node.getName() != null){
			node.getName().accept(this);
		}
		System.out.print("(");
		if (node.getArgList() != null) {
			node.getArgList().accept(this);
		}
		System.out.print(") :");
		this.current_tab ++;
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		this.current_tab --;
		this.newLine();
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FalseNode node) {
		System.out.print("False");
	}
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FloatNode node) {
		System.out.print(node.getValue());
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IntegerNode node) {
		System.out.print(node.getValue());
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(NoneNode node) {
		System.out.print("None");
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(StringNode node) {
		System.out.print(node.getValue());
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(TrueNode node) {
		System.out.print("True");
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ExpressionNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			System.out.print(" ");
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FunctionCallNode node) {
		if (node.getName() != null) {
			node.getName().accept(this);
		}
		System.out.print("(");
		if (node.getArgList() != null) {
			node.getArgList().accept(this);
		}
		System.out.print(")");
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IdentifierNode node) {
		System.out.print(node.getName());
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(OperationExpressionNode node) {
		if (node.getLeftMember() != null) {
			System.out.print("(");
			node.getLeftMember().accept(this);
			System.out.print(")");
		}
		System.out.print(" ");
		if (node.getOperator() != null) {
			node.getOperator().accept(this);
		}
		System.out.print(" ");
		if (node.getRightMember() != null) {
			System.out.print("(");
			node.getRightMember().accept(this);
			System.out.print(")");
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ElifBlockNode node) {
		System.out.print("elif ");
		if (node.getCondition() != null) {
			node.getCondition().accept(this);
		}
		System.out.print(":");
		this.current_tab ++;
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		this.current_tab --;
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ElseBlockNode node) {
		System.out.print("else :");
		this.current_tab++;
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		this.current_tab--;
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IfBlockNode node) {
		System.out.print("if ");
		if (node.getCondition() != null) {
			node.getCondition().accept(this);
		}
		System.out.print(" :");
		this.current_tab ++;
		if (node.getBody() != null){
			node.getBody().accept(this);
		}
		this.current_tab --;
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(AssertStatementNode node) {
		System.out.print("assert ");
		node.getAssertion().accept(this);
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(AssignmentNode node) {
		if (node.getLeftMember() != null) {
			node.getLeftMember().accept(this);
		}
		System.out.print(" ");
		if (node.getOperator() != null) {
			node.getOperator().accept(this);
		}
		System.out.print(" ");
		if (node.getRightMember() != null) {
			node.getRightMember().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(BreakStatementNode node) {
		System.out.print("break");
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ContinueStatementNode node) {
		System.out.print("continue");
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ForStatementNode node) {
		System.out.print("for ");
		if (node.getVariable() != null) {
			node.getVariable().accept(this);
		}
		System.out.print(" in ");
		if (node.getIterable() != null) {
			node.getIterable().accept(this);
		}
		System.out.print(" :");
		this.current_tab ++;
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		this.current_tab --;
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IterableNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}
	
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IfStatementNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			if (node.getChildren().indexOf(n) != node.getChildren().size() - 1) {
				this.newLine();
			}
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(PassStatementNode node) {
		System.out.print("pass");
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ReturnStatementNode node) {
		System.out.print("return ");
		node.getExpression().accept(this);
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(WhileStatementNode node) {
		System.out.print("while ");
		if (node.getCondition() != null) {
			node.getCondition().accept(this);
		}
		System.out.print(" :");
		this.current_tab ++;
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		this.current_tab --;
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(CallArgumentListNode node) {
		boolean yes = false;
		if (node.getActualArgumentList() != null) {
			yes = true;
			node.getActualArgumentList().accept(this);
		}
		if (node.getActualOptionalArgumentList() != null) {
			if (yes) {
				System.out.print(", ");
			}
			node.getActualOptionalArgumentList().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualArgumentNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualOptionalArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			if (node.getChildren().indexOf(n) != node.getChildren().size() - 1) {
				System.out.print(", ");
			}
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualOptionalArgumentNode node) {
		if (node.getName() != null) {
			node.getName().accept(this);
		}
		System.out.print(" = ");
		if (node.getValue() != null) {
			node.getValue().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			if (node.getChildren().indexOf(n) != node.getChildren().size() - 1) {
				System.out.print(", ");
			}
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(FormalArgumentListNode node) {
		boolean yes = false;
		if (node.getMandatoryArgumentList() != null) {
			yes = true;
			node.getMandatoryArgumentList().accept(this);
		}
		if (node.getOptionalArgumentList() != null) {
			if (yes) {
				System.out.print(", ");
			}
			node.getOptionalArgumentList().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(MandatoryArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			if (node.getChildren().indexOf(n) != node.getChildren().size() - 1) {
				System.out.print(", ");
			}
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(MandatoryArgumentNode node) {
		if (node.getName() != null) {
			node.getName().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(OptionalArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			if (node.getChildren().indexOf(n) != node.getChildren().size() - 1) {
				System.out.print(", ");
			}
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(OptionalArgumentNode node) {
		if (node.getName() != null) {
			node.getName().accept(this);
		}
		System.out.print(" = ");
		if (node.getDefault() != null) {
			node.getDefault().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(BodyNode node) {
		for (Node n : node.getChildren()) {
			this.newLine();
			n.accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(LeftMemberNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(OperatorNode node) {
		System.out.print(node.getValue());
	}
	
	/**
	 * Displays the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(RightMemberNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}
}