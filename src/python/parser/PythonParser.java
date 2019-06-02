package python.parser;

import java.text.ParseException;
import java.util.ArrayList;

import common.ast.AST;
import common.lexer.Lexer;
import common.lexer.Token;
import common.parser.Parser;
import python.ast.other.BodyNode;
import python.ast.other.arguments.actual.ActualArgumentListNode;
import python.ast.other.arguments.actual.ActualOptionalArgumentListNode;
import python.ast.other.arguments.actual.ActualOptionalArgumentNode;
import python.ast.other.arguments.actual.CallArgumentListNode;
import python.ast.other.arguments.formal.FormalArgumentListNode;
import python.ast.other.arguments.formal.MandatoryArgumentListNode;
import python.ast.other.arguments.formal.OptionalArgumentListNode;
import python.ast.other.arguments.formal.OptionalArgumentNode;
import python.ast.statements.AssignmentNode;
import python.ast.statements.StatementNode;
import python.ast.statements.blocks.ForStatementNode;
import python.ast.statements.blocks.FunctionDefinitionNode;
import python.ast.statements.blocks.IfStatementNode;
import python.ast.statements.blocks.WhileStatementNode;
import python.ast.statements.blocks.ifBlocks.ElifBlockNode;
import python.ast.statements.blocks.ifBlocks.ElseBlockNode;
import python.ast.statements.blocks.ifBlocks.IfBlockNode;
import python.ast.statements.expressions.ExpressionNode;
import python.ast.statements.expressions.FunctionCallNode;
import python.ast.statements.expressions.IdentifierNode;
import python.ast.statements.expressions.OperationExpressionNode;
import python.ast.statements.expressions.constants.ConstantNode;
import python.ast.statements.expressions.constants.FalseNode;
import python.ast.statements.expressions.constants.FloatNode;
import python.ast.statements.expressions.constants.IntegerNode;
import python.ast.statements.expressions.constants.NoneNode;
import python.ast.statements.expressions.constants.StringNode;
import python.ast.statements.expressions.constants.TrueNode;
import python.ast.statements.keywords.AssertStatementNode;
import python.ast.statements.keywords.BreakStatementNode;
import python.ast.statements.keywords.ContinueStatementNode;
import python.ast.statements.keywords.PassStatementNode;
import python.ast.statements.keywords.ReturnStatementNode;

public class PythonParser extends Parser {
	private int current_tab;
	private static ArrayList<String> expression_starters;
	private static ArrayList<String> operators;
	private static ArrayList<String> assignment_operators;
	private static ArrayList<String> constants;
	private static ArrayList<String> expression_enders;

	/**
	 * Constructor of the parser. It initializes the parameters of the parser.
	 * @param lexer Lexer containing the tokenized code to parse.
	 * @author C.Silva, R.Cuinat
	 */
	public PythonParser(Lexer lexer) {
		super(lexer);
		this.current_tab = 0;
		if (PythonParser.expression_enders == null) {
			PythonParser.init_resources();
		}
	}
	
	/**
	 * Initializes some relevant lists for parsing python code.
	 * @author C.Silva, R.Cuinat
	 */
	private static void init_resources() {
		PythonParser.expression_starters = new ArrayList<>();
		PythonParser.expression_starters.add("Identifier");
		PythonParser.expression_starters.add("String");
		PythonParser.expression_starters.add("Operator-");
		PythonParser.expression_starters.add("Operator+");
		PythonParser.expression_starters.add("Integer");
		PythonParser.expression_starters.add("Float");
		PythonParser.expression_starters.add("Separator(");
		PythonParser.expression_starters.add("KeywordNone");
		PythonParser.expression_starters.add("KeywordTrue");
		PythonParser.expression_starters.add("KeywordFalse");
		PythonParser.expression_starters.add("KeywordNot");
		
		PythonParser.operators = new ArrayList<>();
		PythonParser.operators.add("KeywordAnd");
		PythonParser.operators.add("KeywordOr");
		PythonParser.operators.add("Operator<=");
		PythonParser.operators.add("Operator>=");
		PythonParser.operators.add("Operator==");
		PythonParser.operators.add("Operator!=");
		PythonParser.operators.add("Operator<");
		PythonParser.operators.add("Operator>");
		PythonParser.operators.add("Operator+");
		PythonParser.operators.add("Operator-");
		PythonParser.operators.add("Operator**");
		PythonParser.operators.add("Operator*");
		PythonParser.operators.add("Operator//");
		PythonParser.operators.add("Operator/");
		PythonParser.operators.add("Operator%");
		
		PythonParser.assignment_operators = new ArrayList<>();
		PythonParser.assignment_operators.add("Operator*=");
		PythonParser.assignment_operators.add("Operator+=");
		PythonParser.assignment_operators.add("Operator/=");
		PythonParser.assignment_operators.add("Operator**=");
		PythonParser.assignment_operators.add("Operator-=");
		PythonParser.assignment_operators.add("Operator%=");
		PythonParser.assignment_operators.add("Operator=");
		
		PythonParser.constants = new ArrayList<>();
		PythonParser.constants.add("String");
		PythonParser.constants.add("Integer");
		PythonParser.constants.add("Float");
		PythonParser.constants.add("KeywordTrue");
		PythonParser.constants.add("KeywordFalse");
		PythonParser.constants.add("KeywordNone");
		
		PythonParser.expression_enders = new ArrayList<>();
		PythonParser.expression_enders.add("CarriageReturn");
		PythonParser.expression_enders.add("Separator)");
		PythonParser.expression_enders.add("Separator:");
		PythonParser.expression_enders.add("Separator;");
		PythonParser.expression_enders.add("Separator,");
	}
	
	/**
	 * Factory of ConstantNode
	 * @param t Token to create the node with.
	 * @return The generated ConstantNode.
	 * @author C.Silva, R.Cuinat
	 */
	private ConstantNode create_constant(Token t) {
		assert PythonParser.constants.contains(t.getKind());
		ConstantNode node = null;
		switch (t.getKind()){
		case "String" :
			node = new StringNode(t);
			break;
		case "Integer" : 
			node = new IntegerNode(t);
			break;
		case "Float" : 
			node = new FloatNode(t);
			break;
		case "KeywordTrue" : 
			node = new TrueNode(t.getLine(), t.getColumn());
			break;
		case "KeywordFalse" : 
			node = new FalseNode(t.getLine(), t.getColumn());
			break;
		case "KeywordNone" : 
			node = new NoneNode(t.getLine(), t.getColumn());
			break;
		}
		return node;
	}
	
	/**
	 * Main method of the parser. It parses the program to create the AST.
	 * @return The AST extracted from the source
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public AST parse() {
		AST ast;
		try {
			System.out.println("\nParsing program "+ this.source + "\n");
			this.remove_comments();
			ast = this.parse_program();
		}
		catch (ParseException e) {
			ast = null;
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("\nProgram successfully parsed.\n");
		return ast;
	}
	
	/**
	 * Parses the program.
	 * @return The AST extracted from the source.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private AST parse_program() throws ParseException {
		AST ast = new AST(this.source);
		Token token = this.show_next();
		while (token != null) {
			ast.grow(this.parse_statement());
			token = this.show_next();
		}
		return ast;
	}
	
	/**
	 * Parses a statement.
	 * @return The Statement Node generated from the parsed statement
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private StatementNode parse_statement() throws ParseException{
		StatementNode node = null;
		Token token = this.show_next();
		if (token == null) {// End of file
			node = null;
		}
		if (PythonParser.expression_starters.contains(token.getKind())) {
			if (token.getKind().compareTo("Identifier") != 0) { //Expression
				node = this.parse_expression();
				this.expect("CarriageReturn");
			}
			else {
				Token name = this.accept_it();
				token = this.show_next();
				if (token == null) { //Only a identifier on the line and end of file
					node = null;
				}
				if (PythonParser.assignment_operators.contains(token.getKind())) { // assignment
					node = this.parse_assignment(name);
					this.expect("CarriageReturn");
				}
				else if(token.getKind().compareTo("Separator(") == 0) { //Function call
					node = new FunctionCallNode(token.getLine(), token.getColumn());
					((FunctionCallNode)node).setName(name);
					this.accept_it();
					((FunctionCallNode)node).setArgList(this.parse_call_arguments());
					this.expect("Separator)");
					token = this.show_next();
					if (token == null) {//Function call then end of file
						node = null;
					}
					this.expect("CarriageReturn");
				}
				else {//Expression autre
					node = this.parse_expression();
					this.expect("CarriageReturn");
				}
			}
		}
		else if (token.getKind().compareTo("KeywordDef") == 0) { //Function definition
			node = this.parse_function();
		}
		else if (token.getKind().compareTo("KeywordAssert") == 0) { // Assert statement
			Token assert_t = this.accept_it();
			node = new AssertStatementNode(assert_t.getLine(), assert_t.getColumn());
			((AssertStatementNode)node).setAssertion(this.parse_expression());
			this.expect("CarriageReturn");
		}
		else if (token.getKind().compareTo("KeywordReturn") == 0) { // Return Statement
			this.accept_it();
			node = new ReturnStatementNode(token.getLine(), token.getColumn());
			((ReturnStatementNode)node).setExpression(this.parse_expression());
			this.expect("CarriageReturn");
		}
		else if (token.getKind().compareTo("KeywordIf") == 0) { // If Statement
			node = this.parse_if_statement();
		}
		else if (token.getKind().compareTo("KeywordWhile") == 0) {// While Statement
			node = this.parse_while_statement();
		}
		else if (token.getKind().compareTo("KeywordFor") == 0) {// For statement
			node = this.parse_for_statement();
		}
		else if (token.getKind().compareTo("KeywordPass") == 0) {// Pass Statement
			Token pass_t = this.accept_it();
			node = new PassStatementNode(pass_t.getLine(), pass_t.getColumn());
			this.expect("CarriageReturn");
		}
		else if (token.getKind().compareTo("KeywordBreak") == 0) {// Break Statement
			Token break_t = this.accept_it();
			node = new BreakStatementNode(break_t.getLine(), break_t.getColumn());
			this.expect("CarriageReturn");
		}
		else if (token.getKind().compareTo("KeywordContinue") == 0) { // Continue Statement
			Token continue_t = this.accept_it();
			node = new ContinueStatementNode(continue_t.getLine(), continue_t.getColumn());
			this.expect("CarriageReturn");
		}
		else if (token.getKind().compareTo("CarriageReturn") == 0) { //Empty line
			this.accept_it();
		}
		else {
			for ( byte b : token.getValue().getBytes()){
				System.out.println("byte : " + b);
			}
			System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Expected Statement starter but got " + token.getValue() + " instead.");
			throw new ParseException("", 0);
		}
		return node;
	}
	
	/**
	 * Parses a function
	 * @return The FunctionDefinitionNode generated from the parse of the function
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private FunctionDefinitionNode parse_function() throws ParseException{
		Token def = this.expect("KeywordDef");
		FunctionDefinitionNode node =  new FunctionDefinitionNode(def.getLine(), def.getColumn());
		node.setName(this.expect("Identifier"));
		this.expect("Separator(");
		if (this.show_next().getKind().compareTo("Identifier") == 0) {
			node.setArgs(this.parse_formal_arguments());
		}
		this.expect("Separator)");
		this.expect("Separator:");
		this.expect("CarriageReturn");
		this.current_tab ++;
		node.setBody(this.parse_body());
		this.current_tab --;
		return node;
	}
	
	/**
	 * Parses the argument of a function
	 * @return The node containing the formal arguments list of a function.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private FormalArgumentListNode parse_formal_arguments() throws ParseException{
		FormalArgumentListNode arglist = new FormalArgumentListNode(this.show_next().getLine(),this.show_next().getColumn()-1);
		MandatoryArgumentListNode mandatory = null;
		if (this.show_next().getKind().compareTo("Separator)") != 0) {// non void function
			mandatory = new MandatoryArgumentListNode(this.show_next().getLine(), this.show_next().getColumn());
		} 
		while (this.show_next().getKind().compareTo("Separator)") != 0) {
			Token name = this.expect("Identifier");
			Token token = this.show_next();
			if (token.getKind().compareTo("Separator)") == 0) {
				mandatory.addArg(name);
			}
			else if (token.getKind().compareTo("Separator,") == 0) {
				this.accept_it();
				mandatory.addArg(name);
			}
			else if (token.getKind().compareTo("Operator=") == 0) {
				arglist.setOptionalArgList(this.parse_optional_args(name));
				
			}
			else if (token.getKind().compareTo("Separator)") != 0) {
				System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Wrong syntax to define the arguments. Expected ',' / '=' or ')' but got " + token.getValue() + " instead.");
				throw new ParseException("", 0);
			}
		}
		arglist.setMandatoryArgList(mandatory);
		return arglist;
	}
	
	/**
	 * Parses the optional arguments of a function
	 * @param name identifier token of the first argument
	 * @return The parsed OptionalArgumentListNode
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private OptionalArgumentListNode parse_optional_args(Token name) throws ParseException{
		OptionalArgumentListNode optArgList = new OptionalArgumentListNode(name.getLine(),name.getColumn()-1);
		OptionalArgumentNode optArg = new OptionalArgumentNode(name.getLine(),name.getColumn());
		optArg.setName(name);
		Token token = this.show_next();
		while (token.getKind().compareTo("Separator)") != 0) {
			this.expect("Operator=");
			optArg.setDefault(this.parse_expression());
			token = this.show_next();
			if (token.getKind().compareTo("Separator)") == 0) {
				optArgList.addOptionalArg(optArg);
			}
			else if (token.getKind().compareTo("Separator,") == 0){
				this.accept_it();
				optArgList.addOptionalArg(optArg);
				Token arg = this.expect("Identifier");
				optArg = new OptionalArgumentNode(arg.getLine(),arg.getColumn());
				optArg.setName(arg);
			}
		}
		return optArgList;
	}
	
	/**
	 * Parses the concrete optional arguments of a function call
	 * @param name identifier token of the first argument
	 * @return The parsed OptionalArgumentListNode
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private ActualOptionalArgumentListNode parse_actual_optional_args(Token name) throws ParseException{
		ActualOptionalArgumentListNode optArgList = new ActualOptionalArgumentListNode(name.getLine(),name.getColumn()-1);
		ActualOptionalArgumentNode optArg = new ActualOptionalArgumentNode(name.getLine(),name.getColumn());
		optArg.setName(name);
		Token token = this.show_next();
		while (token.getKind().compareTo("Separator)") != 0) {
			this.expect("Operator=");
			optArg.setValue(this.parse_expression());
			token = this.show_next();
			if (token.getKind().compareTo("Separator)") == 0) {
				optArgList.addOptionalArg(optArg);
			}
			else if (token.getKind().compareTo("Separator,") == 0){
				this.accept_it();
				optArgList.addOptionalArg(optArg);
				Token arg = this.expect("Identifier");
				optArg = new ActualOptionalArgumentNode(arg.getLine(), arg.getColumn());
				optArg.setName(arg);
			}
		}
		return optArgList;
	}
	
	/**
	 * Parses the body of an indented block
	 * @return The BodyNode generated from the parsing.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private BodyNode parse_body() throws ParseException{
		//First Statement is mandatory
		Token token;
		token = this.show_next();
		BodyNode node = new BodyNode(token.getLine(), token.getColumn());
		while (token != null && token.getKind().compareTo("CarriageReturn") == 0) {
			this.accept_it();
			token = this.show_next();
		}
		if (token != null && token.getKind().compareTo("SeparatorTab") == 0 && this.sizeOfTab(token) == this.current_tab) {
			accept_it();
		}
		else {
			System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Expected Indent but got " + token.getValue() + " instead.");
			throw new ParseException("", 0);
		}
		node.addStatement(parse_statement());
		//Next statements
		token = this.show_next();
		while (token != null && token.getKind().compareTo("SeparatorTab") == 0 && this.sizeOfTab(token) == this.current_tab) {
			this.accept_it();
			node.addStatement(this.parse_statement());
			token = this.show_next();
		}
		return node;
	}
	
	/**
	 * Parses an assignment.
	 * @param name The token of the left member of the assignment
	 * @return The Assignment node generated from the parsing.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private AssignmentNode parse_assignment(Token name) throws ParseException{
		AssignmentNode node = new AssignmentNode(name.getLine(), name.getColumn());
		node.setLeftMember(new IdentifierNode(name));
		Token token = this.show_next();
		if (token != null && PythonParser.assignment_operators.contains(token.getKind())) {
			node.setOperator(this.accept_it());
			node.setRightMember(this.parse_expression());
		}
		else {
			System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Expected Assignment Operator but got " + token.getValue() + " instead.");
			throw new ParseException("", 0);
		}
		return node;
	}
	
	/**
	 * Parses an expression.
	 * @return The Expression node generated from the parsing.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private ExpressionNode parse_expression() throws ParseException{
		Token token = this.show_next();
		if (token != null && token.getKind().compareTo("Separator(") == 0) { //expression between parenthesis
			this.accept_it();
			ExpressionNode subExp = this.parse_expression();
			this.expect("Separator)");
			token = this.show_next();
			if (token == null || PythonParser.expression_enders.contains(token.getKind())) {
				return subExp;
			}
			if (PythonParser.operators.contains(token.getKind())){// operation between parenthesis
				OperationExpressionNode operationNode = new OperationExpressionNode(token.getLine(), token.getColumn());
				operationNode.setLeftMember(subExp);
				operationNode.setOperator(this.accept_it());
				operationNode.setRightMember(this.parse_expression());
				return operationNode;
			}
			else {
				System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Expected Operator but got " + token.getValue() + " instead.");
				throw new ParseException("", 0);
			}
			
		}
		else if (token != null && token.getKind().compareTo("Identifier") == 0) { // Expression starting with identifier
			Token name = this.accept_it();
			ExpressionNode expression = new IdentifierNode(name);
			token = this.show_next();
			if (token == null || PythonParser.expression_enders.contains(token.getKind())) { //End with the identifier
				return expression;
			}
			if (token.getKind().compareTo("Separator(") == 0) { // Function call
				expression = new FunctionCallNode(token.getLine(), token.getColumn());
				((FunctionCallNode)expression).setName(name);
				this.accept_it();
				((FunctionCallNode)expression).setArgList(this.parse_call_arguments());
				this.expect("Separator)");
				token = this.show_next();
				if (token == null || PythonParser.expression_enders.contains(token.getKind())) { //End with the function call
					return expression;
				}
			}
			if (PythonParser.operators.contains(token.getKind())){ //Operation
				OperationExpressionNode operation = new OperationExpressionNode(token.getLine(), token.getColumn());
				operation.setLeftMember(expression);
				operation.setOperator(this.accept_it());
				operation.setRightMember(this.parse_expression());
				return operation;
			}
			else {
				System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Expected Operator but got " + token.getValue() + " instead.");
				throw new ParseException("", 0);
			}	
		}
		else if (token != null && PythonParser.constants.contains(token.getKind())) { //Constants
			ConstantNode node = this.create_constant(this.accept_it());
			token = this.show_next();
			if (token == null || PythonParser.expression_enders.contains(token.getKind())) { //End with the constant
				return node;
			}
			if (PythonParser.operators.contains(token.getKind())){ //Operation
				OperationExpressionNode operation = new OperationExpressionNode(token.getLine(), token.getColumn());
				operation.setLeftMember(node);
				operation.setOperator(this.accept_it());
				operation.setRightMember(this.parse_expression());
				return operation;
			}
			else {
				System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Expected Operator but got " + token.getValue() + " instead.");
				throw new ParseException("", 0);
			}
		}
		return null;
	}
	
	/**
	 * Parses the arguments of a function call.
	 * @return The CallArgumentListNode generated from the parsing.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private CallArgumentListNode parse_call_arguments() throws ParseException{
		Token token = this.show_next();
		CallArgumentListNode argList = new CallArgumentListNode(token.getLine(), token.getColumn()-1);
		ActualArgumentListNode mandatory = null;
		if (token != null && token.getKind().compareTo("Separator)") != 0) {
			mandatory = new ActualArgumentListNode(token.getLine(),token.getColumn());
		}
		while (token != null && token.getKind().compareTo("Separator)") != 0) {
			if (PythonParser.expression_starters.contains(token.getKind())) {
				if (token.getKind().compareTo("Identifier") != 0) {
					ExpressionNode expression = this.parse_expression();
					token = this.show_next();
					if (token != null && token.getKind().compareTo("Separator)") == 0) {
						mandatory.addArg(expression);
					}
					else if  (token != null && token.getKind().compareTo("Separator,") == 0) {
						this.accept_it();
						mandatory.addArg(expression);
					}
				}
				else {
					Token name = this.accept_it();
					token = this.show_next();
					if (token.getKind().compareTo("Separator)") == 0) {
						mandatory.addArg(new IdentifierNode(name));
					}
					else if (token.getKind().compareTo("Operator,") == 0) {
						mandatory.addArg(new IdentifierNode(name));
						this.accept_it();
					}
					else if (token.getKind().compareTo("Operator=") == 0) {
						argList.addOptionalArgs(this.parse_actual_optional_args(name));
					}
					else if (PythonParser.operators.contains(token.getKind())){
						OperationExpressionNode operation = new OperationExpressionNode(token.getLine(), token.getColumn());
						operation.setLeftMember(new IdentifierNode(name));
						operation.setOperator(this.accept_it());
						operation.setRightMember(this.parse_expression());
						token = this.show_next();
						if (token != null && token.getKind().compareTo("Separator)") == 0) {
							mandatory.addArg(operation);
						}
						else if  (token != null && token.getKind().compareTo("Separator,") == 0) { // End with the operation
							this.accept_it();
							mandatory.addArg(operation);
						}
					}
					else if (token.getKind().compareTo("Separator(") == 0){
						FunctionCallNode function = new FunctionCallNode(token.getLine(), token.getColumn());
						function.setName(name);
						this.accept_it();
						function.setArgList(this.parse_call_arguments());
						this.expect("Separator)");
						token = this.show_next();
						if  (token != null && token.getKind().compareTo("Separator,") == 0) {
							this.accept_it();
						}
						mandatory.addArg(function);
					}
					
				}
			}
			else {
				System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Expected ) or Expression starter but got " + token.getValue() + " instead.");
				throw new ParseException("", 0);
			}
			token = this.show_next();
		}
		argList.addArgs(mandatory);
		return argList;
	}
	
	/**
	 * Parses a while statement.
	 * @return The WhileStatementNode generated from the parsing.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private WhileStatementNode parse_while_statement() throws ParseException {
		Token while_t = this.expect("KeywordWhile");
		WhileStatementNode node = new WhileStatementNode(while_t.getLine(), while_t.getColumn());
		node.setCondition(this.parse_expression());
		this.expect("Separator:");
		this.expect("CarriageReturn");
		this.current_tab ++;
		node.setBody(this.parse_body());
		this.current_tab --;
		return node;
	}
	
	/**
	 * Parses an if statement.
	 * @return The IfStatementNode generated from the parsing.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private IfStatementNode parse_if_statement() throws ParseException{
		Token if_t = this.expect("KeywordIf");
		IfStatementNode node = new IfStatementNode(if_t.getLine(), if_t.getColumn());
		IfBlockNode ifnode = new IfBlockNode(if_t.getLine(), if_t.getColumn());
		ifnode.setCondition(this.parse_expression());
		this.expect("Separator:");
		this.expect("CarriageReturn");
		this.current_tab ++;
		ifnode.setBody(this.parse_body());
		this.current_tab--;
		node.setIfBlock(ifnode);
		Token token = this.show_next();
		if (token.getKind() == null){
			System.err.println("Error while parsing : inconsistent token.");
			System.exit(1);
		}
		while (token != null && token.getKind().compareTo("CarriageReturn") == 0) { // Multiple CarriageReturn between the if and the potential elif or else
			this.accept_it();
			token = this.show_next();
		}
		if (token == null) { // End of file after if block
			return node;
		}
		if (this.current_tab > 0) { 
			if (token.getKind().compareTo("SeparatorTab") == 0 && this.sizeOfTab(token) == this.current_tab) { // Potential Tab before block if block in another block
				this.accept_it();
				token = this.show_next();
			}
			else if (token.getKind().compareTo("SeparatorTab") == 0){ //Change of block
				return node;
				
			}
		}
		while (token != null && token.getKind().compareTo("KeywordElif") == 0) { // Elif block(s)
			Token elif = this.accept_it();
			ElifBlockNode elifnode = new ElifBlockNode(elif.getLine(), elif.getColumn());
			elifnode.setCondition(this.parse_expression());
			this.expect("Separator:");
			this.expect("CarriageReturn");
			this.current_tab++;
			elifnode.setBody(this.parse_body());
			this.current_tab--;
			node.addElifBlock(elifnode);
			token = this.show_next();
			while (token != null && token.getKind().compareTo("CarriageReturn") == 0) { // Multiple CarriageReturn between elifs
				this.accept_it();
				token = this.show_next();
			}
			if (token == null) { // End of file after elif block
				return node;
			}
			if (this.current_tab > 0) { 
				if (token.getKind().compareTo("SeparatorTab") == 0 && this.sizeOfTab(token) == this.current_tab) { // Potential Tab before block elif block in another block
					this.accept_it();
					token = this.show_next();
				}
				else if (token.getKind().compareTo("SeparatorTab") == 0){ //Change of block
					return node;
					
				}
			}
		}
		if (token.getKind().compareTo("KeywordElse") == 0) {
			Token else_t = this.accept_it();
			ElseBlockNode elsenode = new ElseBlockNode(else_t.getLine(), else_t.getColumn());
			this.expect("Separator:");
			this.expect("CarriageReturn");
			this.current_tab++;
			elsenode.setBody(this.parse_body());
			this.current_tab--;
			node.addElseBlock(elsenode);
		}
		return node;
	}
	
	/**
	 * Parses a for statement.
	 * @return The ForStatementNode generated from the parsing.
	 * @throws ParseException When an error is encountered during the parsing
	 * @author C.Silva, R.Cuinat
	 */
	private ForStatementNode parse_for_statement() throws ParseException{
		Token for_t = this.expect("KeywordFor");
		ForStatementNode node = new ForStatementNode(for_t.getLine(), for_t.getColumn());
		node.setVariable(this.expect("Identifier"));
		this.expect("KeywordIn");
		node.setIterable(this.parse_expression());
		this.expect("Separator:");
		this.expect("CarriageReturn");
		this.current_tab++;
		node.setBody(this.parse_body());
		this.current_tab--;
		return node;
	}
	
	/**
	 * Updates the tokenized code by removing all comments.
	 * @throws ParseException When the removal of the comment shows a syntax error.
	 * @author C.Silva, R.Cuinat
	 */
	private void remove_comments() throws ParseException{
		ArrayList<Token> new_tokens = new ArrayList<>();
		boolean in_comment = false;
		boolean docstring = false;
		Token token;
		while (this.tokens.size() > 0) {
			token = this.tokens.remove(0);
			if (token.getKind().compareTo("Separator\"\"\"") == 0 || token.getKind().compareTo("Separator\'\'\'") == 0) {
				docstring = !docstring;
				if (docstring) {
					if (new_tokens.size() > 0 && new_tokens.get(new_tokens.size() - 1).getKind().compareTo("CarriageReturn") != 0 && new_tokens.get(new_tokens.size() - 1).getKind().compareTo("SeparatorTab") != 0 ) {
						System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Wrong syntax for docstring");
						throw new ParseException("",0);
					}
					else {
						while (new_tokens.size() > 0 && new_tokens.get(new_tokens.size() - 1).getKind().compareTo("SeparatorTab") == 0) {
							new_tokens.remove(new_tokens.size() - 1);
						}
					}
				}
				else {
					token = this.tokens.remove(0);
					if (token != null && token.getKind().compareTo("Separator#") == 0) {
						this.tokens.add(0, token);
					}
					else if (token != null && token.getKind().compareTo("CarriageReturn") != 0) {
						System.err.println("\nError at (" + token.getLine() + " , " + token.getColumn() + "): Wrong syntax for docstring");
						throw new ParseException("",0);
					}
				}	
			}
			else if (token.getKind().compareTo("Separator#") == 0) {
				in_comment = true;
			}
			else if (token.getKind().compareTo("CarriageReturn") == 0) {
				in_comment = false;
				if (!docstring) {
					new_tokens.add(token);
				}
			}
			else {
				if (!in_comment && !docstring) {
					new_tokens.add(token);
				}
			}
		}
		this.tokens = new_tokens;
	}

}
