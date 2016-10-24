package net.m0cchi.function.op;

import java.util.Iterator;

import net.m0cchi.function.ToBoolean;
import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.NULL.NIL;
import net.m0cchi.value.SList;

public class Or extends Function {
	private static final long serialVersionUID = -6044942386518114678L;

	public Or() {
		setArgs(REST, "or values");
	}

	@Override
	public Element invoke(Environment environment) {
		SList list = environment.getValue(getArgs()[1]);
		Iterator<Element> iterator = list.getNativeValue().iterator();
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(environment);
		Element result = null;
		while (iterator.hasNext()) {
			result = semanticAnalyzer.evaluate(iterator.next());
			if (ToBoolean.isTrue(result)) {
				return result;
			}
		}
		return NIL.NIL;
	}

}
