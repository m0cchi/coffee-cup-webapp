package net.m0cchi.function;

import java.util.Base64;
import java.util.Base64.Encoder;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.Value;

public class ToBase64 extends Function {
	private static final long serialVersionUID = -5462070026083654787L;
	private static Encoder encoder = Base64.getEncoder();

	public ToBase64() {
		setArgs("target text");
	}

	@Override
	public Element invoke(Environment environment) {
		Value<?> target = environment.getValue(getArgs()[0]);
		Object object = target.getNativeValue();
		byte[] bytes = null;
		if (object instanceof byte[]) {
			bytes = (byte[]) object;
		} else {
			bytes = target.getNativeValue().toString().getBytes();
		}

		String encoded = encoder.encodeToString(bytes);
		return new Value<String>(AtomicType.LETTER, encoded);
	}

}
