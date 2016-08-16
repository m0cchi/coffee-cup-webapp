package net.m0cchi.value;

public class JValue<T> extends Value<T> {
	private static final long serialVersionUID = -7626232534869590893L;

	public JValue(T value) {
		super(AtomicType.JAVA, value);
	}

}
