package net.m0cchi.function;

import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Macro;
import net.m0cchi.value.SList;

public class Nop extends Macro {
	private static final long serialVersionUID = -5638787543622103057L;

	public Nop() {
		setArgs(REST, "nop");
	}

	@Override
	public Element invoke(Environment environment) {
		return new SList();
	}

}
