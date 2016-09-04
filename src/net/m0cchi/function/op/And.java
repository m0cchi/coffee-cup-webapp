package net.m0cchi.function.op;

import java.util.Iterator;

import net.m0cchi.function.ToBoolean;
import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Macro;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class And extends Macro {
	private static final long serialVersionUID = 5779046304016640400L;

	public And() {
		setArgs(REST, "and args");
	}

	@Override
	public Element invoke(Environment environment) {
		SList list = (SList) environment.getValue(getArgs()[0]);
		Iterator<Element> iterator = list.getNativeValue().iterator();
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(environment);
		while (iterator.hasNext()) {
			Element result = semanticAnalyzer.evaluate(iterator.next());
			if (!ToBoolean.isTrue(result)) {
				return result;
			}
		}
		return new Value<Boolean>(AtomicType.BOOL, true);
	}

}
