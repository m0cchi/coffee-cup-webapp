package net.m0cchi.function;

import java.util.Iterator;

import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Macro;
import net.m0cchi.value.SList;
import net.m0cchi.value.NULL.NIL;
import net.m0cchi.value.Value;

public class Each extends Macro {
	private static final long serialVersionUID = 3634156250535059933L;

	public Each() {
		setArgs("each arg", "each body");
	}

	@Override
	public Element invoke(Environment environment) {
		SList arg = environment.getValue(getArgs()[0]);
		Value<?> var = arg.get(0);
		String name = var.getNativeValue().toString();
		SList list = arg.get(1);
		Iterator<Element> it = list.getNativeValue().iterator();
		
		SList body = environment.getValue(getArgs()[1]);
		Element ret = NIL.NIL;
		
		Environment env = new Environment(environment);
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(env);
		while(it.hasNext()) {
			env.defineVariable(name, it.next());
			ret = semanticAnalyzer.evaluate(body);
		}

		return ret;
	}

}
