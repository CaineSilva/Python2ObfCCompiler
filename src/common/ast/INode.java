package common.ast;

import visitors.Visitor;

/**
 * Interface "visited" of Visitor design pattern.
 * @author C.Silva, R.Cuinat
 */
public interface INode {
	/**
	 * Method allowing the INode to be visited.
	 * @param v The visitor.
	 * @author C.Silva, R.Cuinat
	 */
    void accept(Visitor v);
}
