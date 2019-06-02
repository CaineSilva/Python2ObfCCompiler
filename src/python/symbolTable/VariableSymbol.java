package python.symbolTable;

import common.ast.Node;
import python.symbolTable.types.AbstractType;
import python.ast.statements.AssignmentNode;
import python.ast.statements.expressions.IdentifierNode;
import visitors.TypeDiscovererVisitor;

/**
 * Class defining a variable of a SymbolTable.
 * @author C.Silva, R.Cuinat
 */
public class VariableSymbol {
	private final Node node;
	private final String name;
	private AbstractType type;

	/**
	 * Constructor of the class.
	 * @param node AssignmentNode defining a variable of the AST to add to the SymbolTable
	 * @param st Current SymbolTable of the context use to determine the type of the variable.
	 * @author C.Silva, R.Cuinat
	 */
	public VariableSymbol(AssignmentNode node,SymbolTable st) {
		this.node = node;
		this.name = ((IdentifierNode)node.getLeftMember().getExpression()).getName();
		TypeDiscovererVisitor v = new TypeDiscovererVisitor(st);
		v.visit(node.getRightMember());
		this.type = v.getResult();
	}

	/**
	 * Constructor of the class used to instantiate a VariableSymbol from an ArgumentSymbol.
	 * @param arg ArgumentSymbol to convert into a VariableSymbol.
	 * @author C.Silva, R.Cuinat
	 */
	public VariableSymbol(ArgumentSymbol arg) {
		this.node = arg.getNode();
		this.type = arg.getType();
		this.name = arg.getName();
	}

	/**
	 * Constructor of the class used to instantiate a VariableSymbol from an OptionalArgumentSymbol.
	 * @param arg OptionalArgumentSymbol to convert into a VariableSymbol.
	 * @author C.Silva, R.Cuinat
	 */
	public VariableSymbol(OptionalArgumentSymbol arg) {
		this.node = arg.getNode();
		this.type = arg.getType();
		this.name = arg.getName();
	}

	/**
	 * Getter of the name field.
	 * @return The name of the variable as specified in its AST.
	 * @author C.Silva, R.Cuinat
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter of the type field.
	 * @return The AbstractType of this variable.
	 * @author C.Silva, R.Cuinat
	 */
	public AbstractType getType() {
		return this.type;
	}

	/**
	 * Getter of the node field.
	 * @return The node used to instantiate this VariableSymbol
	 * @author C.Silva, R.Cuinat
	 */
	public Node getNode() {
		return this.node;
	}

	/**
	 * Setter of the type field.
	 * @param type New AbstractType of this variable.
	 * @author C.Silva, R.Cuinat
	 */
	public void setType(AbstractType type) {
		this.type = type;
	}
}