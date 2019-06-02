package python.symbolTable;

import python.symbolTable.types.AbstractType;
import python.symbolTable.types.UndefinedType;
import python.ast.other.arguments.formal.MandatoryArgumentNode;

/**
 * Class defining a mandatory argument for a FunctionSymbol.
 * @author  C.Silva, R.Cuinat
 */
public class ArgumentSymbol {
	private AbstractType type;
	private final String name;
	private final MandatoryArgumentNode node;

	/**
	 * Constructor of the class.
	 * @param node MandatoryArgumentNode of the AST to add to the SymbolTable
	 * @author C.Silva, R.Cuinat
	 */
	ArgumentSymbol(MandatoryArgumentNode node) {
		this.name = node.getName().getName();
		this.node = node;
		this.type = new UndefinedType(null);
	}

	/**
	 * Getter of the type field.
	 * @return AbstractType of the argument.
	 * @author C.Silva, R.Cuinat.
	 */
	public AbstractType getType() {
		return this.type;
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
	 * Setter of the type field.
	 * @param type New type of the argument.
	 * @author C.Silva, R.Cuinat
	 */
	public void setType(AbstractType type) {
		this.type = type;
		
	}

	/**
	 * Getter of the node field.
	 * @return The MandatoryArgumentNode which created this symbol.
	 * @author C.Silva, R.Cuinat
	 */
	public MandatoryArgumentNode getNode() {
		return this.node;
	}
}
