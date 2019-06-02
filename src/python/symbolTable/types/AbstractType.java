package python.symbolTable.types;

/**
 * Abstract class generalizing the possible types of an expression.
 * @author C.Silva, R.Cuinat
 */
public abstract class AbstractType {
	String name;

	/**
	 * Determine the resulting type of an operation between this type and an IntegerType.
	 * @param type Type to operate with
	 * @return The resulting type of the operation.
	 * @author C.Silva, R.Cuinat
	 */
	public abstract AbstractType operationWith(IntegerType type);
	/**
	 * Determine the resulting type of an operation between this type and a DoubleType.
	 * @param type Type to operate with
	 * @return The resulting type of the operation.
	 * @author C.Silva, R.Cuinat
	 */
	public abstract AbstractType operationWith(DoubleType type);
	/**
	 * Determine the resulting type of an operation between this type and a StringType.
	 * @param type Type to operate with
	 * @return The resulting type of the operation.
	 * @author C.Silva, R.Cuinat
	 */
	public abstract AbstractType operationWith(StringType type);
	/**
	 * Determine the resulting type of an operation between this type and an UndefinedType.
	 * @param type Type to operate with
	 * @return The resulting type of the operation.
	 * @author C.Silva, R.Cuinat
	 */
	public abstract AbstractType operationWith(UndefinedType type);

	/**
	 * Getter of the name field.
	 * @author C.Silva, R.Cuinat
	 * @return A string representation of the type.
	 * @author C.Silva, R.Cuinat
	 */
	public String getName() {
		return this.name;
	}
}
