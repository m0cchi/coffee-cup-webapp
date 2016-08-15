package net.m0cchi.function;

import java.util.HashMap;
import java.util.Map;

import net.m0cchi.parser.lexical.StringLexicalAnalyzer;
import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.parser.syntax.SyntaxAnalyzer;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class Eval extends Function {
	private static final long serialVersionUID = 6317077209149111763L;
	private final Map<String, Function> hookFunction;
	private final Map<String, Element> hookVariable;

	public Eval() {
		setArgs(new String[] { "eval string" });
		this.hookFunction = new HashMap<>();
		this.hookVariable = new HashMap<>();
	}

	public Eval hook(String name, Function function) {
		this.hookFunction.put(name, function);
		return this;
	}

	public Eval hook(String name, Element element) {
		this.hookVariable.put(name, element);
		return this;
	}

	@Override
	public void hook(Environment env) {
		for (String key : this.hookFunction.keySet()) {
			env.defineFunction(key, this.hookFunction.get(key));
		}

		for (String key : this.hookVariable.keySet()) {
			env.defineVariable(key, this.hookVariable.get(key));
		}
	}

	@Override
	public Element invoke(Environment environment) {
		Value<?> codeValue = (Value<?>) environment.getValue(getArgs()[0]);
		StringLexicalAnalyzer lexicalAnalyzer = new StringLexicalAnalyzer(codeValue.getNativeValue().toString());
		SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);
		SList list = (SList) syntaxAnalyzer.parse();
		Element ret = null;
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(environment);
		for (Element element : list.toArray()) {
			ret = semanticAnalyzer.evaluate(element);
		}
		return ret;
	}

}
