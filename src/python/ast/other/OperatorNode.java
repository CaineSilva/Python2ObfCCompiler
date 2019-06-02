package python.ast.other;

import java.util.ArrayList;

import common.ast.Node;
import common.lexer.Token;
import visitors.Visitor;

/**
 * Node of an AST representing an operator (assignment or operation). 
 * @author C.Silva, R.Cuinat
 *
 */
public class OperatorNode extends Node {
	private String value;
	private static ArrayList<String> operators;

	/**
	 * Initializes the children of this node and sets the expression of the member with the given expression.
	 * @param t The token representing the operator.
	 * @author C.Silva, R.Cuinat
	 */
	public OperatorNode(Token t) {
		super(t.getLine(),t.getColumn());
		if (OperatorNode.operators == null) {
			OperatorNode.init();
		}
		assert OperatorNode.operators.contains(t.getKind());
		this.value = t.getValue().replaceAll(" ", "");
	}
	
	/**
	 * Initializes the list of operators.
	 * @author C.Silva, R.Cuinat
	 */
	private static void init() {
		OperatorNode.operators = new ArrayList<>();
		OperatorNode.operators.add("KeywordAnd");
		OperatorNode.operators.add("KeywordOr");
		OperatorNode.operators.add("Operator<=");
		OperatorNode.operators.add("Operator>=");
		OperatorNode.operators.add("Operator==");
		OperatorNode.operators.add("Operator!=");
		OperatorNode.operators.add("Operator<");
		OperatorNode.operators.add("Operator>");
		OperatorNode.operators.add("Operator+");
		OperatorNode.operators.add("Operator-");
		OperatorNode.operators.add("Operator**");
		OperatorNode.operators.add("Operator*");
		OperatorNode.operators.add("Operator//");
		OperatorNode.operators.add("Operator/");
		OperatorNode.operators.add("Operator%");
		OperatorNode.operators.add("Operator*=");
		OperatorNode.operators.add("Operator+=");
		OperatorNode.operators.add("Operator/=");
		OperatorNode.operators.add("Operator**=");
		OperatorNode.operators.add("Operator-=");
		OperatorNode.operators.add("Operator%=");
		OperatorNode.operators.add("Operator=");
	}
	
	/**
	 * Getter of the value field.
	 * @return The String representation of the operator.
	 * @author C.Silva, R.Cuinat
	 */
	public String getValue() {
		return this.value;
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
