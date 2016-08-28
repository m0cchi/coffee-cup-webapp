package net.m0cchi.function.op;

public class GTEq extends Comparator {
	private static final long serialVersionUID = -1451101422889646333L;

	@Override
	protected boolean check(int i) {
		return i >= 0;
	}

	public String getName() {
		return ">=";
	}
}
