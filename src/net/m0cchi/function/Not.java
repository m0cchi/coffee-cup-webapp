package net.m0cchi.function;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Value;

public class Not extends ToBoolean {

	private static final long serialVersionUID = -5401637429277028117L;

	public Not() {
		setArgs("not cond");
	}

	@Override
	public Element invoke(Environment environment) {
		@SuppressWarnings({ "unchecked" })
		Value<Boolean> result = (Value<Boolean>) super.invoke(environment);
		Boolean element = result.getNativeValue();
		Element ret = new Value<Boolean>(AtomicType.BOOL, !element);
		return ret;
	}

}
