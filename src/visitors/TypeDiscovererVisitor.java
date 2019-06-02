package visitors;

import common.ast.Node;
import python.symbolTable.FunctionSymbol;
import python.symbolTable.SymbolTable;
import python.symbolTable.VariableSymbol;
import python.symbolTable.types.AbstractType;
import python.symbolTable.types.DoubleType;
import python.symbolTable.types.IntegerType;
import python.symbolTable.types.StringType;
import python.symbolTable.types.UndefinedType;
import python.ast.other.LeftMemberNode;
import python.ast.other.RightMemberNode;
import python.ast.other.arguments.actual.ActualArgumentNode;
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

/**
 * Visitor Class determining the type of an expression. To be fully efficient, the visit is to be started with a symbolTable containing as much information as possible.
 * @author C.Silva, R.Cuinat
 */
public class TypeDiscovererVisitor implements Visitor {
	private AbstractType result;
	private final SymbolTable symbolTable;

	/**
	 * Constructor of the class.
	 * @param st SymbolTabel used to determine expression types.
	 * @author C.Silva, R.Cuinat
	 */
	public TypeDiscovererVisitor(SymbolTable st) {
		this.symbolTable = st;
	}

	/**
	 * Method used to clear the previous result in order to launch a new visit with the same visitor.
	 * @author C.Silva, R.Cuinat
	 */
	public void clear() {
		this.result = null;
	}
	
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(FalseNode node) {
		this.result = new IntegerType();
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(FloatNode node) {
		this.result = new DoubleType();
		
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IntegerNode node) {
		this.result = new IntegerType();		
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(NoneNode node) {
		this.result = new UndefinedType(node);
	}
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(StringNode node) {
		this.result = new StringType();
	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(TrueNode node) {
		this.result = new IntegerType();
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
		FunctionSymbol function = this.symbolTable.getFunctionByName(node.getName());
		if (function == null) {
			this.result = new UndefinedType(node);
		}
		else {
			this.result = function.getReturnedType();
		}
	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IdentifierNode node) {
		VariableSymbol variable = this.symbolTable.getVariableByName(node);
		if (variable == null) {
			this.result = new UndefinedType(node);
		}
		else {
			this.result = variable.getType();
		}
		
	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(OperationExpressionNode node) {
		node.getLeftMember().getExpression().accept(this);
		AbstractType temp = this.result;
		node.getRightMember().getExpression().accept(this);
		switch (temp.getName()) {
		case "Integer" :
			this.result = this.result.operationWith(new IntegerType());
			break;
		case "Double" :
			
			this.result = this.result.operationWith(new DoubleType());
			break;
		case "String" :
			this.result = this.result.operationWith(new StringType());
			break;
		case "Undefined" :
			this.result = this.result.operationWith((UndefinedType) temp);
			break;
		}
	}

	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(LeftMemberNode node) {
		node.getExpression().accept(this);
	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(RightMemberNode node) {
		node.getExpression().accept(this);
	}

	/**
	 * Method used to get the result of the visit.
	 * @return The determined AbstractType of the ExpressionNode the visit was started on.
	 * @author C.Silva, R.Cuinat
	 */
	public AbstractType getResult() {
		return this.result;
	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(IterableNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
		}
	}
	
	/**
	 * Visits the specified node.
	 * @param node Node to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualArgumentNode node) {
		node.getExpression().accept(this);
	}
}
