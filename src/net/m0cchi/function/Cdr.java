package net.m0cchi.function;

import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;

public class Cdr extends Function {
	private static final long serialVersionUID = 2394849431433276784L;

	public Cdr() {
		setArgs("cdr list");
	}

	@Override
	public Element invoke(Environment environment) {
		SList sList = environment.getValue(getArgs()[0]);
		return sList.cdr();
	}

}
