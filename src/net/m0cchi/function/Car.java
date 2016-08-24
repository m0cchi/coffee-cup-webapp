package net.m0cchi.function;

import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;

public class Car extends Function {
	private static final long serialVersionUID = 7682769457102633719L;

	public Car() {
		setArgs("car list");
	}

	@Override
	public Element invoke(Environment environment) {
		SList list = (SList) environment.getValue(getArgs()[0]);
		return list.get(0);
	}

}
