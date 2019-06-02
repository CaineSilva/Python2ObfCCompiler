package python.symbolTable.types;

/**
 * Class representing a string type.
 * @author C.Silva, R.Cuinat
 */
public class StringType extends AbstractType {

	/**
	 * Constructor of the class.
	 * @author C.Silva, R.Cuinat
	 */
	public StringType() {
		this.name = "String";
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
		return this;
	}

	/**
	 * Determine the resulting type of an operation between this type and an UndefinedType.
	 * @param type Type of the second operand of this operation
	 * @return This type.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public AbstractType operationWith(UndefinedType type) {
		return this;
	}

	/**
	 * Returns the c string representation of this type.
	 * @return the string representation of this type using the C syntax.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public String toString() {
		return "char*";
	}
}
