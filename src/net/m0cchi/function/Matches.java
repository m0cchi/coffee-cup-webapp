package net.m0cchi.function;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.Value;

public class Matches extends Function {
	private static final long serialVersionUID = -5063377154043028007L;

	public Matches() {
		setArgs("regex str", "target str");
	}

	@Override
	public Element invoke(Environment environment) {
		Value<String> regex = environment.getValue(getArgs()[0]);
		Value<String> target = environment.getValue(getArgs()[1]);
		boolean result = target.getNativeValue().matches(regex.getNativeValue());

		return new Value<Boolean>(AtomicType.BOOL, result);
	}

}
