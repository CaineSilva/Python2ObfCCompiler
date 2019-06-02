package python.symbolTable;

import java.util.ArrayList;

import python.symbolTable.types.AbstractType;
import python.ast.other.arguments.actual.ActualOptionalArgumentNode;
import python.ast.other.arguments.formal.MandatoryArgumentNode;
import python.ast.other.arguments.formal.OptionalArgumentNode;
import python.ast.statements.blocks.FunctionDefinitionNode;
import python.ast.statements.expressions.IdentifierNode;

/**
 * Class defining a function in a symbolTable.
 * @author  C.Silva, R.Cuinat
 */
public class FunctionSymbol {
	private final FunctionDefinitionNode node;
	private final String name;
	private AbstractType returnedType;
	private final ArrayList<ArgumentSymbol> mandatoryArgs;
	private final ArrayList<OptionalArgumentSymbol> optionalArgs;

	/**
	 * Constructor of the class.
	 * @param node FunctionDefinitionNode of the AST to add to the SymbolTable
	 * @author C.Silva, R.Cuinat
	 */
	public FunctionSymbol(FunctionDefinitionNode node) {
		this.node = node;
		this.name = node.getName().getName();
		this.mandatoryArgs = new ArrayList<>();
		this.optionalArgs = new ArrayList<>();
	}

	/**
	 * Getter of the name field.
	 * @return Name of the function as given in its AST.
	 * @author C.Silva, R.Cuinat
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Getter of the returnedType field.
	 * @return AbstractType of the expression returned by this function..
	 * @author C.Silva, R.Cuinat.
	 */
	public AbstractType getReturnedType() {
		return this.returnedType;
	}

	/**
	 * Getter of the mandatoryArgs field.
	 * @return The list of all mandatory arguments (type ArgumentSymbol) of this function.
	 * @author C.Silva, R.Cuinat
	 */
	public ArrayList<ArgumentSymbol> getMandatoryArgs() {
		return this.mandatoryArgs;
	}

	/**
	 * Getter of the optionalArgs field.
	 * @return The list of all optional arguments (type OptionalArgumentSymbol) of this function.
	 * @author C.Silva, R.Cuinat
	 */
	public ArrayList<OptionalArgumentSymbol> getOptionals() {
		return this.optionalArgs;
	}

	/**
	 * Get the OptionalArgumentSymbol of this function corresponding to the given ActualOptionalArgumentNode.
	 * @param actualOpt ActualOptionalArgumentNode whose corresponding symbol is required.
	 * @return The corresponding OptionalArgumentSymbol of this function. null if none.
	 * @author C.Silva, R.Cuinat
	 */
	public OptionalArgumentSymbol getOptionalFromActual(ActualOptionalArgumentNode actualOpt) {
		for (OptionalArgumentSymbol opt : this.optionalArgs) {
			if (opt.getName().compareTo(actualOpt.getName().getName()) == 0) {
				return opt;
			}
		}
		return null;		
	}

	/**
	 * Adds the given argument to the list of mandatory arguments of this function.
	 * @param arg The MandatoryArgumentNode to convert and add.
	 * @author C.Silva, R.Cuinat
	 */
	public void addArg(MandatoryArgumentNode arg) {
		this.mandatoryArgs.add(new ArgumentSymbol(arg));
	}

	/**
	 * Get the ArgumentSymbol representing the same argument as the given IdentifierNode.
	 * @param arg IdentifierNode representing the name of the argument to find.
	 * @return The corresponding ArgumentSymbol. null if none.
	 * @author C.Silva, R.Cuinat
	 */
	public ArgumentSymbol getMandatoryByName(IdentifierNode arg) {
		for (ArgumentSymbol as : this.mandatoryArgs) {
			if (as.getName().compareTo(arg.getName()) == 0){
				return as;
			}
		}
		return null;
	}

	/**
	 * Adds the given argument to the list of optional arguments of this function.
	 * @param opt The OptionalArgumentNode to convert and add.
	 * @param st Current TableSymbol used to convert the node into a symbol.
	 * @author C.Silva, R.Cuinat
	 */
	public void addOptionalArg(OptionalArgumentNode opt, SymbolTable st) {
		this.optionalArgs.add(new OptionalArgumentSymbol(opt, st));
		
	}

	/**
	 * Get the OptionalArgumentSymbol representing the same optional argument as the given IdentifierNode.
	 * @param arg IdentifierNode representing the name of the argument to find.
	 * @return The corresponding OptionalArgumentSymbol. null if none.
	 * @author C.Silva, R.Cuinat
	 */
	public OptionalArgumentSymbol getOptionalByName(IdentifierNode arg) {
		for (OptionalArgumentSymbol opt : this.optionalArgs) {
			if (opt.getName().compareTo(arg.getName()) == 0) {
				return opt;
			}
		}
		return null;
	}

	/**
	 * Getter of the node field.
	 * @return The FunctionDefinitionNode corresponding to the definition of this symbol in the an AST.
	 * @author C.Silva, R.Cuinat
	 */
	public FunctionDefinitionNode getNode() {
		return this.node;
	}

	/**
	 * Setter of the returnedType field.
	 * @param result New returned type of this function.
	 * @author C.Silva, R.Cuinat
	 */
	public void setReturnedType(AbstractType result) {
		this.returnedType = result;
	}
}
