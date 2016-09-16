package net.m0cchi.function;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class ArrayToSList extends Function {
	private static final long serialVersionUID = -3216205245282052710L;

	public ArrayToSList() {
		setArgs("array list");
	}

	@Override
	public Element invoke(Environment environment) {
		Value<?> arrayValue = environment.getValue(getArgs()[0]);
		Object array = arrayValue.getNativeValue();
		SList list = new SList();
		for (Object value : (Object[]) array) {
			list.add(new Value<Object>(AtomicType.JAVA, value));
		}
		return list;
	}

}
