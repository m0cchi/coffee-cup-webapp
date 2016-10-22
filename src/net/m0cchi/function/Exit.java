package net.m0cchi.function;

import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.Value;

public class Exit extends Function {
	private static final long serialVersionUID = 4956787547481526169L;

	public Exit() {
		setArgs("status i");
	}

	@Override
	public Element invoke(Environment environment) {
		Value<Integer> status = environment.getValue(getArgs()[0]);
		System.exit(status.getNativeValue());
		return null;
	}

}
