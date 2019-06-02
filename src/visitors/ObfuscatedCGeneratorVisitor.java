package visitors;

import common.ast.AST;
import common.ast.Node;
import common.ast.ProgramNode;
import common.lexer.Token;
import python.symbolTable.*;
import python.ast.other.BodyNode;
import python.ast.other.LeftMemberNode;
import python.ast.other.OperatorNode;
import python.ast.other.RightMemberNode;
import python.ast.other.arguments.actual.*;
import python.ast.statements.AssignmentNode;
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
import python.ast.statements.expressions.constants.*;
import python.ast.statements.keywords.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Visitor class visiting an AST to convert it into obfuscated C code.
 * @author C.Silva, R.Cuinat
 */
public class ObfuscatedCGeneratorVisitor implements Visitor {
	private final String output;
	private BufferedWriter bw;
	private SymbolTable symbolTable;
	private FunctionSymbol current_function;
	private FunctionSymbol declared_function;
	private boolean in_main;

	/** Constructor of the class.
	 * @param outputFile Name of the output c file. (without extension)
	 * @author C.Silva, R.Cuinat
	 */
	public ObfuscatedCGeneratorVisitor(String outputFile) {
		this.output = outputFile;
	}
		
	/**
	 * Visits the AST
	 * @param ast AST to visit.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(AST ast) {
		System.out.println("\nVisiting the AST (\""+ast.getSource()+"\") to generate obfuscated c code.\n" );
		try {
			this.bw = new BufferedWriter(new FileWriter(this.output + "_obf.c"));
			ast.accept(this);
			System.out.println("\nGeneration complete");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Print a space ending with the specified string;
	 * @param c String to end the space with
	 * @author C.Silva, R.Cuinat
	 */
	private void newSpace(String c) {
		this.write(" " + c);
	}

	/**
	 * Writes the specified string in the output file and in console.
	 * @param s String to save.
	 * @author C.Silva, R.Cuinat
	 */
	private void write(String s) {
		try {
			this.bw.write(s);
			System.out.print(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Converts to C the specified node.
	 * @param root Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ProgramNode root) {
		this.in_main = false;
		SymbolTableGenerator stg = new SymbolTableGenerator(new SymbolTable());
		this.symbolTable = stg.generateFrom(root);
		System.out.println("SymbolTable :\n" + this.symbolTable);
		this.write("??=include <stdlib.h>\n??=include <stdio.h>\n??=include <math.h>\n");
		for (FunctionSymbol func : this.symbolTable.getFunctions()) {
			if (func.getName().compareTo("print") != 0 && func.getNode() != null) {
				this.declared_function = func;
				func.getNode().accept(this);
			}
		}
		this.declared_function = null;
		this.in_main = true;
		this.write("int main()??<");
		this.newSpace("");
		for (VariableSymbol var : this.symbolTable.getVariables()) {
			if (var.getType().toString() == null) {
				System.err.println("Unable to proceed with the conversion : undefined type for variable " + var.getName());
				try {
					this.bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(1);
			}
			this.write(var.getType().toString() + " Obf" + Math.abs(var.getName().hashCode()));
			this.newSpace(";");
		}
		for (Node node : root.getChildren()) {
			node.accept(this);
			this.newSpace(";");
		}
		this.write("return 1");
		
		this.newSpace(";");
		this.write("??>");
		try {
			this.bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FunctionDefinitionNode node) {
		if (!in_main) {
			if (this.declared_function.getReturnedType() == null || this.declared_function.getReturnedType().getName().compareTo("Undefined") == 0) {
				this.write("void ");
			}
			else {
				this.write(this.declared_function.getReturnedType() + " ");
			}
			if (node.getName() != null){
				node.getName().accept(this);
			}
			this.write("(");
			for  (int i=0;i<this.declared_function.getMandatoryArgs().size();i++) {
				ArgumentSymbol arg = this.declared_function.getMandatoryArgs().get(i);
				if (arg.getType().getName().compareTo("Undefined") == 0) {
					System.err.println("Unable to proceed with conversion : Undefined type for argument (L" + arg.getNode().getLine() + ", C" + arg.getNode().getColumn() + ").");
					System.exit(1);
				}
				this.write(arg.getType() + " Obf" + Math.abs(arg.getName().hashCode()));
				if (i != this.declared_function.getMandatoryArgs().size() - 1) {
					this.write(", ");
				}
			}
			if (this.declared_function.getOptionals().size() != 0) {
				this.write(", ");
			}
			for  (int i=0;i<this.declared_function.getOptionals().size();i++) {
				OptionalArgumentSymbol opt = this.declared_function.getOptionals().get(i);
				if (opt.getType().getName().compareTo("Undefined") == 0) {
					System.err.println("Unable to proceed with conversion : Undefined type for argument (L" + opt.getNode().getLine() + ", C" + opt.getNode().getColumn() + ").");
					System.exit(1);
				}
				this.write(opt.getType() + " Obf" + Math.abs(opt.getName().hashCode()));
				if (i != this.declared_function.getOptionals().size() - 1) {
					this.write(", ");
				}
			}
			this.write(")??<");
			
			if (node.getBody() != null) {
				node.getBody().accept(this);
			}
			
			this.newSpace("");
			this.write("??>");
			this.newSpace("");
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FalseNode node) {
		this.write("0");
	}
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FloatNode node) {
		this.write(Double.toString(node.getValue()));
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IntegerNode node) {
		this.write("0x"+Integer.toHexString(node.getValue()));
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(NoneNode node) {
		this.write("NULL");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(StringNode node) {
		this.write("\"" + node.getValue().substring(1,node.getValue().length()-1) + "\"");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(TrueNode node) {
		this.write("1");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ExpressionNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			this.write(" ");
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(FunctionCallNode node) {
		FunctionSymbol func = this.symbolTable.getFunctionByName(node.getName());
		if (func  == null) {
			System.err.println("Unable to proceed with conversion : Call of unknown function (L" + node.getLine() + ", C" + node.getColumn() + ").");
			System.exit(1);
		}
		if (func.getName().compareTo("print") == 0) {
			this.write("printf");
			this.write("(\"%s\\n\", ");
			this.current_function = func;
			node.getArgList().accept(this);
			this.current_function = null;
			this.write(")");
		}
		else {
			node.getName().accept(this);
			this.write("(");
			this.current_function = func;
			node.getArgList().accept(this);
			this.current_function = null;
			this.write(")");
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IdentifierNode node) {
		this.write("Obf"+Math.abs(node.getName().hashCode()));
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(OperationExpressionNode node) {
		if (node.getOperator() != null && node.getOperator().getValue().compareTo("**") == 0){
			this.write("pow( ");
			node.getLeftMember().accept(this);
			this.write(", ");
			node.getRightMember().accept(this);
			this.write(")");
		}
		else{
			if (node.getLeftMember() != null) {
				this.write("(");
				node.getLeftMember().accept(this);
				this.write(")");
			}
			this.write(" ");
			if (node.getOperator() != null) {
				node.getOperator().accept(this);
			}
			this.write(" ");
			if (node.getRightMember() != null) {
				this.write("(");
				node.getRightMember().accept(this);
				this.write(")");
			}
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ElifBlockNode node) {
		this.write("else if (");
		if (node.getCondition() != null) {
			node.getCondition().accept(this);
		}
		this.write(") ??<");
		
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		
		this.newSpace("");
		this.write("??>");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ElseBlockNode node) {
		this.write("else ??<");
		
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		
		this.newSpace("");
		this.write("??>");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IfBlockNode node) {
		this.write("if (");
		if (node.getCondition() != null) {
			node.getCondition().accept(this);
		}
		this.write(" )??<");
		
		if (node.getBody() != null){
			node.getBody().accept(this);
		}
		
		this.newSpace("");
		this.write("??>");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(AssertStatementNode node) {
		System.err.println("Unable to proceed with the conversion : assert statement (L" + node.getLine() + ", C" + node.getColumn() + ").");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(AssignmentNode node) {
		if (node.getLeftMember() != null) {
			node.getLeftMember().accept(this);
		}
		this.write(" ");
		if (node.getOperator() != null) {
			switch (node.getOperator().getValue()) {
			case "+=" :
				TypeDiscovererVisitor td6 = new TypeDiscovererVisitor(this.symbolTable);
				td6.visit(node.getRightMember());
				String typeRight6 = td6.getResult().getName();
				VariableSymbol var6 = this.symbolTable.getVariableByName((IdentifierNode)node.getLeftMember().getExpression());
				if (var6 == null || (typeRight6.compareTo("Integer") != 0 && typeRight6.compareTo("Double") != 0) ||(var6.getType().getName().compareTo("Integer") != 0 && var6.getType().getName().compareTo("Double") != 0)) {
					System.err.println("Unable to proceed with conversion : Invalid type for operation - (L" + node.getLine() + ", C" + node.getColumn() + ")") ;
				}
				this.write("= ");
				node.getLeftMember().accept(this);
				this.write(" + (");
				if (node.getRightMember() != null) {
					node.getRightMember().accept(this);
				}
				this.write(")");
				break;
			case "*=" :
				TypeDiscovererVisitor td5 = new TypeDiscovererVisitor(this.symbolTable);
				td5.visit(node.getRightMember());
				String typeRight5 = td5.getResult().getName();
				VariableSymbol var5 = this.symbolTable.getVariableByName((IdentifierNode)node.getLeftMember().getExpression());
				if (var5 == null || (typeRight5.compareTo("Integer") != 0 && typeRight5.compareTo("Double") != 0) ||(var5.getType().getName().compareTo("Integer") != 0 && var5.getType().getName().compareTo("Double") != 0)) {
					System.err.println("Unable to proceed with conversion : Invalid type for operation * (L" + node.getLine() + ", C" + node.getColumn() + ")") ;
				}
				this.write("= ");
				node.getLeftMember().accept(this);
				this.write(" * (");
				if (node.getRightMember() != null) {
					node.getRightMember().accept(this);
				}
				this.write(")");
				break;
			case "/=" : 
				TypeDiscovererVisitor td4 = new TypeDiscovererVisitor(this.symbolTable);
				td4.visit(node.getRightMember());
				String typeRight4 = td4.getResult().getName();
				VariableSymbol var4 = this.symbolTable.getVariableByName((IdentifierNode)node.getLeftMember().getExpression());
				if (var4 == null || (typeRight4.compareTo("Integer") != 0 && typeRight4.compareTo("Double") != 0) ||(var4.getType().getName().compareTo("Integer") != 0 && var4.getType().getName().compareTo("Double") != 0)) {
					System.err.println("Unable to proceed with conversion : Invalid type for operation / (L" + node.getLine() + ", C" + node.getColumn() + ")") ;
				}
				this.write("= ");
				node.getLeftMember().accept(this);
				this.write(" / (");
				if (node.getRightMember() != null) {
					node.getRightMember().accept(this);
				}
				this.write(")");
				break;
			case "-=" :
				TypeDiscovererVisitor td3 = new TypeDiscovererVisitor(this.symbolTable);
				td3.visit(node.getRightMember());
				String typeRight3 = td3.getResult().getName();
				VariableSymbol var3 = this.symbolTable.getVariableByName((IdentifierNode)node.getLeftMember().getExpression());
				if (var3 == null || (typeRight3.compareTo("Integer") != 0 && typeRight3.compareTo("Double") != 0) ||(var3.getType().getName().compareTo("Integer") != 0 && var3.getType().getName().compareTo("Double") != 0)) {
					System.err.println("Unable to proceed with conversion : Invalid type for operation - (L" + node.getLine() + ", C" + node.getColumn() + ")") ;
				}
				this.write("= ");
				node.getLeftMember().accept(this);
				this.write(" - (");
				if (node.getRightMember() != null) {
					node.getRightMember().accept(this);
				}
				this.write(")");
				break;
			case "%=" :
				TypeDiscovererVisitor td2 = new TypeDiscovererVisitor(this.symbolTable);
				td2.visit(node.getRightMember());
				String typeRight2 = td2.getResult().getName();
				VariableSymbol var = this.symbolTable.getVariableByName((IdentifierNode)node.getLeftMember().getExpression());
				if (var == null || typeRight2.compareTo("Integer") != 0 ||var.getType().getName().compareTo("Integer") != 0) {
					System.err.println("Unable to proceed with conversion : Invalid operation % with non integer (L" + node.getLine() + ", C" + node.getColumn() + ")") ;
				}
				this.write("= ");
				node.getLeftMember().accept(this);
				this.write(" % (");
				if (node.getRightMember() != null) {
					node.getRightMember().accept(this);
				}
				this.write(")");
				break;
			case "**=" :
				TypeDiscovererVisitor td = new TypeDiscovererVisitor(this.symbolTable);
				td.visit(node.getRightMember());
				String typeRight = td.getResult().getName();
				VariableSymbol var2 = this.symbolTable.getVariableByName((IdentifierNode)node.getLeftMember().getExpression());
				if (var2 == null || typeRight.compareTo("Integer") != 0 ||(var2.getType().getName().compareTo("Integer") != 0 && var2.getType().getName().compareTo("Double") != 0)) {
					System.err.println("Unable to proceed with conversion : Invalid type for operation ** (L" + node.getLine() + ", C" + node.getColumn() + ")") ;
				}
				this.write("= pow(");
				node.getLeftMember().accept(this);
				this.write(", ");
				if (node.getRightMember() != null) {
					node.getRightMember().accept(this);
				}
				this.write(" )");
				break;
			case "=" :
				TypeDiscovererVisitor td7 = new TypeDiscovererVisitor(this.symbolTable);
				td7.visit(node.getRightMember());
				String typeRight7 = td7.getResult().getName();
				VariableSymbol var7 = this.symbolTable.getVariableByName((IdentifierNode)node.getLeftMember().getExpression());
				if (var7 == null || typeRight7.compareTo(var7.getType().getName()) != 0) {
					System.err.println("Unable to proceed with conversion : Invalid type for operation =  (L" + node.getLine() + ", C" + node.getColumn() + ")") ;
				}
				this.write( "= ");
				if (node.getRightMember() != null) {
					node.getRightMember().accept(this);
				}
				break;
			default :
				System.err.println("Unable to proceed with conversion : Unknown assignment operator (L"+node.getLine() + ", C" + node.getColumn() +").");
			}
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(BreakStatementNode node) {
		this.write("break");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ContinueStatementNode node) {
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ForStatementNode node) {
		VariableSymbol itvar = this.symbolTable.getVariableByName(node.getVariable());
		if (itvar == null) {
			this.write("int Obf" + Math.abs(node.getVariable().getName().hashCode()));
			this.newSpace(";");
		}
		else if (itvar.getType().getName().compareTo("Integer") != 0) {
			System.err.println("Unable to proceed with conversion : Wrong type for iteration variable (L"+node.getLine() + ", C" + node.getColumn() + ").");
			System.exit(1);
		}
		this.write("for (");
		if (node.getVariable() != null) {
			node.getVariable().accept(this);
		}
		// range
		try {
			if (((FunctionCallNode)node.getIterable().getExpression()).getName().getName().compareTo("range") == 0) {
				TypeDiscovererVisitor td = new TypeDiscovererVisitor(this.symbolTable);
				switch (((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().size()) {
				case 1 :
					this.write("= 0; ");
					node.getVariable().accept(this);
					this.write(" < (");
					(((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(0)).accept(td);
					if (td.getResult().getName().compareTo("Integer") != 0) {
						System.err.println("Unable to proceed with conversion : Invalid iterable argument (L" + node.getLine() + ", C" + node.getColumn() + ").");
					}
					td.clear();
					((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(0).accept(this);
					this.write("); ");
					node.getVariable().accept(this);
					this.write("++");
					break;
				case 2 :
					this.write("= ");
					(((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(0)).accept(td);
					if (td.getResult().getName().compareTo("Integer") != 0) {
						System.err.println("Unable to proceed with conversion : Invalid iterable argument (L" + node.getLine() + ", C" + node.getColumn() + ").");
					}
					td.clear();
					((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(0).accept(this);
					this.write("; "); 
					node.getVariable().accept(this);
					this.write(" < (");
					(((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(1)).accept(td);					if (td.getResult().getName().compareTo("Integer") != 0) {
						System.err.println("Unable to proceed with conversion : Invalid iterable argument (L" + node.getLine() + ", C" + node.getColumn() + ").");
					}
					td.clear();
					((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(1).accept(this);
					this.write("); ");
					node.getVariable().accept(this);
					this.write("++");
					break;
				case 3 :
					this.write("= ");
					(((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(0)).accept(td);
					if (td.getResult().getName().compareTo("Integer") != 0) {
						System.err.println("Unable to proceed with conversion : Invalid iterable argument (L" + node.getLine() + ", C" + node.getColumn() + ").");
					}
					td.clear();
					((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(0).accept(this);
					this.write("; "); 
					node.getVariable().accept(this);
					this.write(" < (");
					(((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(1)).accept(td);
					if (td.getResult().getName().compareTo("Integer") != 0) {
						System.err.println("Unable to proceed with conversion : Invalid iterable argument (L" + node.getLine() + ", C" + node.getColumn() + ").");
					}
					td.clear();
					((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(1).accept(this);
					this.write("); ");
					node.getVariable().accept(this);
					this.write(" = ");
					node.getVariable().accept(this);
					this.write(" + (");
					(((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(2)).accept(td);					
					if (td.getResult().getName().compareTo("Integer") != 0) {
						System.err.println("Unable to proceed with conversion : Invalid iterable argument (L" + node.getLine() + ", C" + node.getColumn() + ").");
					}
					td.clear();
					((FunctionCallNode)node.getIterable().getExpression()).getArgList().getActualArgumentList().getChildren().get(2).accept(this);
					this.write(")");
					break;
				default :
					System.err.println("Unable to proceed with conversion : Invalid iterable (L" + node.getLine() + ", C" + node.getColumn() + ").");
					break;
				}
			}
		}
		catch (Exception e) {
			System.err.println("Unable to proceed with conversion : Invalid iterable (L" + node.getLine() + ", C" + node.getColumn() + ").");
		}
		this.write(")??<");
		
		AssignmentNode n = new AssignmentNode(node.getLine(), node.getColumn());
		n.setLeftMember(node.getVariable());
		n.setRightMember(new IntegerNode(new Token("Integer", "1", node.getLine(), node.getColumn())));
		this.symbolTable.addVariable(new VariableSymbol(n, this.symbolTable));
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		
		this.newSpace("");
		this.write("??>");
		this.newSpace("");
	}	
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(IfStatementNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			if (node.getChildren().indexOf(n) != node.getChildren().size() - 1) {
				this.newSpace("");
			}
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(PassStatementNode node) {
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	@Override
	public void visit(ReturnStatementNode node) {
		this.write("return ");
		node.getExpression().accept(this);
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(WhileStatementNode node) {
		this.write("while (");
		if (node.getCondition() != null) {
			node.getCondition().accept(this);
		}
		this.write(" )??<");
		
		if (node.getBody() != null) {
			node.getBody().accept(this);
		}
		
		this.newSpace("");
		this.write("??>");
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(CallArgumentListNode node) {
		if (current_function == null) {
			System.err.println("Unable to proceed with conversion : The AST cannot be understood.");
			System.exit(1);
		}
		if (node.getActualArgumentList() != null) {
			node.getActualArgumentList().accept(this);
		}
		if (this.current_function.getOptionals().size() != 0){
			this.write(", ");
		}
		if (node.getActualOptionalArgumentList() != null) {
			boolean found;
			for (int i=0;i < this.current_function.getOptionals().size();i++) {
				OptionalArgumentSymbol opt = this.current_function.getOptionals().get(i);
				found = false;
				for (Node n : node.getActualOptionalArgumentList().getChildren()) {
					ActualOptionalArgumentNode actual = (ActualOptionalArgumentNode) n;
					if (actual.getName().getName().compareTo(opt.getName()) == 0) {
						found = true;
						actual.accept(this);
						break;
					}
				}
				if (!found) {
					opt.getNode().getDefault().accept(this);
				}
				if (i != this.current_function.getOptionals().size() -1) {
					this.write(", ");
				}
			}
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualArgumentNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(ActualArgumentListNode node) {
		for (Node n : node.getChildren()) {
			n.accept(this);
			if (node.getChildren().indexOf(n) != node.getChildren().size() - 1) {
				this.write(", ");
			}
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(BodyNode node) {
		this.newSpace("");
		SymbolTable st = this.symbolTable.save();
		SymbolTableGenerator stg = new SymbolTableGenerator(this.symbolTable);
		this.symbolTable = stg.generateFrom(node);
		for (int i=st.getVariables().size(); i< this.symbolTable.getVariables().size();i++) {
			VariableSymbol var = this.symbolTable.getVariables().get(i);
			if (var.getType() == null) {
				System.err.println("Unable to proceed with the conversion : undefined type for variable " + var.getName());
				try {
					this.bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.exit(1);
			}
			this.write(var.getType().toString() + " Obf" + Math.abs(var.getName().hashCode()));
			this.newSpace(";");
		}
		this.newSpace("");
		for (Node n : node.getChildren()) {
			n.accept(this);
			this.newSpace(";");
		}
		this.symbolTable = st;
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(LeftMemberNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(OperatorNode node) {
		switch (node.getValue()) {
		case "or" :
			this.write("??!??!");
			break;
		case "and" :
			this.write("&&");
			break;
		case "//" :
			this.write("/");
			break;
		default :
			this.write(node.getValue());
			break;
		}
	}
	
	/**
	 * Converts to C the specified node.
	 * @param node Node to display.
	 * @author C.Silva, R.Cuinat
	 */
	public void visit(RightMemberNode node) {
		if (node.getExpression() != null) {
			node.getExpression().accept(this);
		}
	}
}
