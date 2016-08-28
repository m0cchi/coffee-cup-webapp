package net.m0cchi.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.m0cchi.parser.lexical.StringLexicalAnalyzer;
import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.parser.syntax.SyntaxAnalyzer;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.NULL.NIL;
import net.m0cchi.value.SList;
import net.m0cchi.value.SafeEnvironment;
import net.m0cchi.value.Value;

public class Eval extends Function {
	private static final long serialVersionUID = 6317077209149111763L;
	private final Map<String, Function> hookFunction;
	private final Map<String, Element> hookVariable;
	private final List<Function> postFunctions;
	private boolean removeAllFunction;
	private boolean removeAllVariable;

	public Eval() {
		setArgs(new String[] { "eval string" });
		this.hookFunction = new HashMap<>();
		this.hookVariable = new HashMap<>();
		this.postFunctions = new ArrayList<>();
	}

	public Eval hook(String name, Function function) {
		this.hookFunction.put(name, function);
		return this;
	}

	public Eval hook(String name, Element element) {
		this.hookVariable.put(name, element);
		return this;
	}

	public boolean canRemoveAllFunction() {
		return removeAllFunction;
	}

	public void removeAllFunction(boolean removeAllFunction) {
		this.removeAllFunction = removeAllFunction;
	}

	public boolean canRemoveAllVariable() {
		return removeAllVariable;
	}

	public void removeAllVariable(boolean removeAllVariable) {
		this.removeAllVariable = removeAllVariable;
	}

	public void addPostFunction(Function function) {
		this.postFunctions.add(function);
	}

	public Element postFilter(Environment environment, Element element) {
		Element[] ret = { element };

		for (Function function : postFunctions) {
			Element res = function.invoke(environment, ret);
			ret = new Element[] { res };
		}

		return ret[0];
	}

	@Override
	public void hook(Environment env) {
		if (removeAllFunction) {
			Nop nop = new Nop();
			Arrays.stream(env.getAllFunctionsName()).forEach(name -> env.defineFunction(name, nop));
		}

		if (removeAllVariable) {
			Element nil = NIL.NIL;
			Arrays.stream(env.getAllVariablesName()).forEach(name -> env.defineVariable(name, nil));
		}

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
		if (!(environment instanceof SafeEnvironment)) {
			environment = SafeEnvironment.toSafe(environment);
		}
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(environment);
		for (Element element : list.toArray()) {
			ret = semanticAnalyzer.evaluate(element);
		}
		return postFilter(environment, ret);
	}

}
