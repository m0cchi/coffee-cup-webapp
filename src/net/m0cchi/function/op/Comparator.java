package net.m0cchi.function.op;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.Value;

public abstract class Comparator extends Function {
	private static final long serialVersionUID = -8196285268114155403L;

	{
		setArgs("lte num1", "lte num2");
	}

	abstract protected boolean check(int i);

	@Override
	public Element invoke(Environment environment) {
		Value<?> num1Value = environment.getValue(getArgs()[0]);
		Value<?> num2Value = environment.getValue(getArgs()[1]);
		Object num1 = num1Value.getNativeValue();
		Object num2 = num2Value.getNativeValue();
		boolean ret = false;
		if (num1 instanceof Comparable) {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			int i = ((Comparable) num1).compareTo(num2);
			ret = check(i);
		}
		return new Value<Boolean>(AtomicType.BOOL, ret);
	}

}
