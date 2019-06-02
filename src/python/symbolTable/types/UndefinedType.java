package python.symbolTable.types;

import java.util.ArrayList;

import common.ast.Node;

/**
 * Class representing an undefined type.
 * @author C.Silva, R.Cuinat
 */
public class UndefinedType extends AbstractType {
	private final ArrayList<Node> cause;

	/**
	 * Constructor of the class.
	 * @param n Cause of the undefined status of the type.
	 * @author C.Silva, R.Cuinat
	 */
	public UndefinedType(Node n) {
		this.name = "Undefined";
		this.cause = new ArrayList<>();
		this.cause.add(n);
	}

	/**
	 * Determine the resulting type of an operation between this type and an IntegerType.
	 * @param type Type of the second operand of this operation
	 * @return This type.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public AbstractType operationWith(IntegerType type) {
		return this;
	}

	/**
	 * Determine the resulting type of an operation between this type and a DoubleType.
	 * @param type Type of the second operand of this operation
	 * @return This type.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public AbstractType operationWith(DoubleType type) {
		return this;
	}

	/**
	 * Determine the resulting type of an operation between this type and a StringType.
	 * @param type Type of the second operand of this operation
	 * @return This type.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public AbstractType operationWith(StringType type) {
		return type;
	}

	/**
	 * Determine the resulting type of an operation between this type and an UndefinedType.
	 * @param type Type of the second operand of this operation
	 * @return This type.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public AbstractType operationWith(UndefinedType type) {
		this.cause.addAll(type.getCause());
		return this;
	}

	/**
	 * Getter of the cause field.
	 * @return The list containing the node which were ambiguous to determine the type. This list is to be used for debugging.
	 * @author C.Silva, R.Cuinat
	 */
	public ArrayList<Node> getCause(){
		return this.cause;
	}

	/**
	 * Returns the c string representation of this type.
	 * @return the string representation of this type using the C syntax.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public String toString() {
		return "void";
	}
}
