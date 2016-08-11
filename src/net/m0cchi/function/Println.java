package net.m0cchi.function;

import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class Println extends Function {
	private static final long serialVersionUID = 7745941862198456275L;

	public Println() {
		setArgs(new String[] { REST, "print args" });
	}

	@Override
	public Element invoke(Environment environment) {
		SList list = (SList) environment.getValue(getArgs()[1]);
		Element[] values = list.toArray();
		for (Element value : values) {
			System.out.print(((Value<?>) value).getNativeValue());
		}
		System.out.println();
		return new SList();
	}

}
