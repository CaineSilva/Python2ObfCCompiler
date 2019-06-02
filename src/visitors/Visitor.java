package visitors;

import common.ast.AST;
import common.ast.INode;
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
import python.ast.statements.StatementNode;
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
import python.ast.statements.expressions.constants.ConstantNode;
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
 * Interface "visitor" of the Visitor design pattern.
 * @author C.Silva, R.Cuinat
 */
public interface Visitor {
	
	/**
	 * Visits the AST
	 * @param ast AST to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(AST ast) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(INode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ConstantNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(FalseNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(FloatNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(IntegerNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(NoneNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(StringNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(TrueNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ExpressionNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(FunctionCallNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(IdentifierNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(IterableNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(OperationExpressionNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ElifBlockNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ElseBlockNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(IfBlockNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(AssertStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(AssignmentNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(BreakStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ContinueStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ForStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(FunctionDefinitionNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(IfStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(PassStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ReturnStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(StatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(WhileStatementNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ActualArgumentListNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ActualArgumentNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ActualOptionalArgumentListNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ActualOptionalArgumentNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(CallArgumentListNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(FormalArgumentListNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(MandatoryArgumentListNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(MandatoryArgumentNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(OptionalArgumentListNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(OptionalArgumentNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(BodyNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(LeftMemberNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(OperatorNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(RightMemberNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(ProgramNode node) {}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	default void visit(Node node) {}
}


