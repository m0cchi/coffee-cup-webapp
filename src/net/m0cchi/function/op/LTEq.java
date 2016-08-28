package net.m0cchi.function.op;

public class LTEq extends Comparator {

	private static final long serialVersionUID = 6474314987012741646L;

	@Override
	protected boolean check(int i) {
		return i <= 0;
	}

	public String getName() {
		return "<=";
	}

}
