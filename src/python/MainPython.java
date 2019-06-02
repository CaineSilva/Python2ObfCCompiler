package python;

import common.ast.AST;
import common.lexer.Lexer;
import python.lexer.PythonLexicalRules;
import python.parser.PythonParser;
import visitors.CGeneratorVisitor;
import visitors.ObfuscatedCGeneratorVisitor;
import visitors.PythonPrettyPrinterVisitor;
import visitors.VariableCheckerVisitor;

/**
 * Main class of the project.
 * @author C.Silva, R.Cuinat
 */
class MainPython {

	/**
	 * Main method. Creates the AST from the given file and convert it into c and obfuscated c code.
	 * @param args name of the py file (without extension)
	 * @author C.Silva, R.Cuinat
	 */
	public static void main(String[] args) {
		String source = null;
		if (args.length > 0) {
			source = args[0];
		}
		else {
			System.err.println("No file was given. Quitting ...");
			System.exit(1);
		}
		Lexer lexer = new Lexer(source + ".py", new PythonLexicalRules());
		lexer.tokenize();
		System.out.println(lexer.toString());
		System.out.print("\n");
		PythonParser parser = new PythonParser(lexer);
		AST ast = parser.parse();
		PythonPrettyPrinterVisitor pp = new PythonPrettyPrinterVisitor();
		pp.visit(ast);
		VariableCheckerVisitor vc = new VariableCheckerVisitor();
		vc.visit(ast);
		CGeneratorVisitor cg = new CGeneratorVisitor(source);
		cg.visit(ast);
		ObfuscatedCGeneratorVisitor Obfcg = new ObfuscatedCGeneratorVisitor(source);
		Obfcg.visit(ast);
	}
}