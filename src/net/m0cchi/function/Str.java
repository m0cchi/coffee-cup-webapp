package net.m0cchi.function;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class Str extends Function {
	private static final long serialVersionUID = 1252108221339033107L;

	public Str() {
		setArgs(new String[] { REST, "str args" });
	}

	@Override
	public Element invoke(Environment environment) {
		SList list = environment.getValue(getArgs()[1]);
		Element[] values = list.toArray();
		StringBuilder sb = new StringBuilder();
		for (Element value : values) {
			sb.append(((Value<?>) value).getNativeValue());
		}

		Value<String> ret = new Value<>(AtomicType.LETTER, sb.toString());
		return ret;
	}

}
