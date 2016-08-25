package net.m0cchi.function;

import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class Funcall extends Function {

	private static final long serialVersionUID = 6244270260058681881L;

	public Funcall() {
		setArgs("func name", REST, "func args");
	}

	@Override
	public Element invoke(Environment environment) {
		Element name = environment.getValue(getArgs()[0]);
		SList sList = (SList) environment.getValue(getArgs()[2]);
		Element[] args = sList.getNativeValue().toArray(new Element[0]);

		Function function = null;
		if (name instanceof Function) {
			function = (Function) name;
		} else if (name instanceof Value) {
			String funcName = ((Value<?>) name).getNativeValue().toString();
			function = environment.getFunction(funcName);
		}

		return function.invoke(environment, args);
	}

}
