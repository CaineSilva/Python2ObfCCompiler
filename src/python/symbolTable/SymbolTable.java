package python.symbolTable;

import java.util.ArrayList;

import common.lexer.Token;
import python.symbolTable.types.StringType;
import python.ast.other.arguments.formal.MandatoryArgumentNode;
import python.ast.statements.blocks.FunctionDefinitionNode;
import python.ast.statements.expressions.IdentifierNode;

/**
 * Class defining a symbol table gathering the variables and functions of an AST.
 * @author C.Silva, R.Cuinat
 */
public class SymbolTable {
	private final ArrayList<VariableSymbol> variables;
	private final ArrayList<FunctionSymbol> functions;

	/**
	 * Constructor of the class.
	 * @author C.Silva, R.Cuinat
	 */
	public SymbolTable() {
		this.variables = new ArrayList<>();
		this.functions = new ArrayList<>();
		FunctionDefinitionNode node = new FunctionDefinitionNode(0, 0);
		node.setName(new Token("Identifier", "print", 0, 0));
		FunctionSymbol print = new FunctionSymbol(node);
		MandatoryArgumentNode arg = new MandatoryArgumentNode(new Token("Identifier", "string", 0, 0));
		print.addArg(arg);
		ArgumentSymbol argsymbol = print.getMandatoryArgs().get(0);
		argsymbol.setType(new StringType());
		this.functions.add(print);
	}

	/**
	 * Constructor of the class. This constructor is to be used to initialize a new SymbolTable with an existing one.
	 * @param symbolTable SymbolTable use to initialize the new one.
	 * @author C.Silva, R.Cuinat
	 */
	@SuppressWarnings("unchecked")
	public SymbolTable(SymbolTable symbolTable) {
		this.variables = (ArrayList<VariableSymbol>)symbolTable.getVariables().clone();
		this.functions = (ArrayList<FunctionSymbol>)symbolTable.getFunctions().clone();
	}

	/**
	 * Getter of the functions field.
	 * @return The list of all FunctionSymbol stored in the table.
	 * @author C.Silva, R.Cuinat
	 */
	public ArrayList<FunctionSymbol> getFunctions() {
		return this.functions;
	}

	/**
	 * Add the given VariableSymbol to this table.
	 * @param vs Symbol to add to the table.
	 * @author C.Silva, R.Cuinat
	 */
	public void addVariable(VariableSymbol vs) {
		this.variables.add(vs);
	}

	/**
	 * Gets the VariableSymbol whose name matches with the given IdentifierNode.
	 * @param name IdentifierNode of the variable to retrieve.
	 * @return The matching VariableSymbol. null if none.
	 * @author C.Silva, R.Cuinat
	 */
	public VariableSymbol getVariableByName(IdentifierNode name) {
		if (name == null) {
			return null;
		}
		for (VariableSymbol variable : this.variables) {
			if (variable.getName().compareTo(name.getName()) == 0) {
				return variable;
			}
		}
		return null;
	}

	/**
	 * Gets the FunctionSymbol whose name matches with the given IdentifierNode.
	 * @param name IdentifierNode of the function to retrieve.
	 * @return The matching FunctionSymbol. null if none.
	 * @author C.Silva, R.Cuinat
	 */
	public FunctionSymbol getFunctionByName(IdentifierNode name) {
		if (name == null) {
			return null;
		}
		for (FunctionSymbol function : this.functions) {
			if (function.getName().compareTo(name.getName()) == 0) {
				return function;
			}
		}
		return null;
	}

	/**
	 * Getter of the variables field.
	 * @return The list of all VariableSymbol of this table.
	 * @author C.Silva, R.Cuinat
	 */
	public ArrayList<VariableSymbol> getVariables() {
		return this.variables;
	}

	/**
	 * Gives a string representation of this symbolTable.
	 * @return A string containing the variables and their types as stored in the symbol table and the function signatures. The syntax used is the c syntax.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Variables\n");
		for (VariableSymbol var : this.variables) {
			s.append(var.getName()).append(" : ").append(var.getType().getName()).append("\n");
		}
		s.append("Functions\n");
		for (FunctionSymbol func : this.functions) {
			s.append(func.getReturnedType()).append(" ").append(func.getName()).append("(");
			for (int i=0;i<func.getMandatoryArgs().size();i++) {
				s.append(func.getMandatoryArgs().get(i).getType()).append(" ").append(func.getMandatoryArgs().get(i).getName());
				if (i != func.getMandatoryArgs().size() - 1) {
					s.append(", ");
				}
			}
			if (func.getOptionals().size() != 0) {
				s.append(", ");
			}
			for (int i=0;i<func.getOptionals().size();i++) {
				s.append(func.getOptionals().get(i).getType()).append(" ").append(func.getOptionals().get(i).getName());
				if (i != func.getOptionals().size() - 1) {
					s.append(", ");
				}
			}
			s.append(")\n");
		}
		return s.toString();
	}

	/**
	 * Returns a copy of this symbolTable.
	 * @return A copy of this object.
	 * @author C.Silva, R.Cuinat
	 */
	public SymbolTable save() {
		return new SymbolTable(this);
	}

	/**
	 * Adds a FunctionSymbol to this SymbolTable.
	 * @param func The function to add.
	 * @author C.Silva, R.Cuinat
	 */
	public void addFunction(FunctionSymbol func) {
		this.functions.add(func);
		
	}

	/**
	 * Converts a list of ArgumentSymbol into VariableSymbol and adds them to this table. This method is used to complete the symbolTable of a function context.
	 * @param mandatoryArgs The list to process.
	 * @author C.Silva, R.Cuinat
	 */
	public void addVariableFromMandatories(ArrayList<ArgumentSymbol> mandatoryArgs) {
		for (ArgumentSymbol arg : mandatoryArgs) {
			this.addVariable(new VariableSymbol(arg));
		}
	}

	/**
	 * Converts a list of OptionalArgumentSymbol into VariableSymbol and adds them to this table. This method is used to complete the symbolTable of a function context.
	 * @param optionals The list to process.
	 * @author C.Silva, R.Cuinat
	 */
	public void addVariableFromOptionals(ArrayList<OptionalArgumentSymbol> optionals) {
		for (OptionalArgumentSymbol arg : optionals) {
			this.addVariable(new VariableSymbol(arg));
		}
	}
}
