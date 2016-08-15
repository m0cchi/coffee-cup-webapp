package net.m0cchi.value;


public class SafeEnvironment extends Environment {
	private static final long serialVersionUID = 7602530787442241886L;

	public SafeEnvironment() {
		super();
	}

	public SafeEnvironment(Environment environment) {
		super(environment);
	}

	@Override
	public void setValue(String name, Element element) {
		super.defineVariable(name, element);
	}
	
	public static SafeEnvironment toSafe(Environment environment) {
		SafeEnvironment safe = new SafeEnvironment();
		move(environment,safe);
		return safe;
	}
}
