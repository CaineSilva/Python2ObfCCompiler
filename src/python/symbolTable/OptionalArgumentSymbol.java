package python.symbolTable;

import python.symbolTable.types.AbstractType;
import python.ast.other.arguments.formal.OptionalArgumentNode;
import visitors.TypeDiscovererVisitor;

/**
 * Class defining an optional argument for a FunctionSymbol.
 * @author  C.Silva, R.Cuinat
 */
public class OptionalArgumentSymbol {
	private AbstractType type;
	private final String name;
	private final OptionalArgumentNode node;

	/**
	 * Constructor of the class.
	 * @param node OptionalArgumentNode of the AST to add to the SymbolTable
	 * @param st Current SymbolTable of the context use to determine the type of the argument
	 * @author C.Silva, R.Cuinat
	 */
	public OptionalArgumentSymbol(OptionalArgumentNode node, SymbolTable st) {
		this.node = node;
		this.name = node.getName().getName();
		TypeDiscovererVisitor td = new TypeDiscovererVisitor(st);
		node.getDefault().accept(td);
		this.type = td.getResult();
	}

	/**
	 * Getter of the name field.
	 * @return Name of the argument as given in its AST.
	 * @author C.Silva, R.Cuinat
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter of the type field.
	 * @return AbstractType of the optional argument.
	 * @author C.Silva, R.Cuinat.
	 */
	public AbstractType getType() {
		return this.type;
	}

	/**
	 * Setter of the type field.
	 * @param type New type of the optional argument.
	 * @author C.Silva, R.Cuinat
	 */
	public void setType(AbstractType type) {
		this.type = type;
		
	}

	/**
	 * Getter of the node field.
	 * @return The OptionalArgumentNode which created this symbol.
	 * @author C.Silva, R.Cuinat
	 */
	public OptionalArgumentNode getNode() {
		return this.node;
	}

}