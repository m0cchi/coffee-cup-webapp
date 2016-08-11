package net.m0cchi.function;

import java.util.ArrayList;
import java.util.List;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Macro;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class DefRoutes extends Macro {
	private static final long serialVersionUID = -4631782356405117862L;

	public DefRoutes() {
		setArgs(new String[] { "app name", REST, "def route" });
	}

	/**
	 * (defroutes (path proc) (path proc)) | (defvar name '((path proc) (path
	 * proc)))
	 */
	@Override
	public Element invoke(Environment environment) {
		Value<?> nameValue = (Value<?>) environment.getValue(getArgs()[0]);
		SList context = (SList) environment.getValue(getArgs()[2]);
		List<Element> ret = new ArrayList<>();
		ret.add(new Value<String>(AtomicType.SYMBOL, "defvar")); // defvar ...?
		ret.add(nameValue);
		ret.add(new Value<SList>(AtomicType.QUOTE, context));
		return new SList(ret);
	}

}
