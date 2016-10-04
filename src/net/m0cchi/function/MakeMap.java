package net.m0cchi.function;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class MakeMap extends Function {
	private static final long serialVersionUID = 3303510889496504640L;

	public MakeMap() {
		setArgs(REST, "map args");
	}

	@Override
	public Element invoke(Environment environment) {
		SList args = environment.getValue(getArgs()[1]);
		Iterator<Element> iterator = args.getNativeValue().iterator();
		Map<Element, Element> map = new HashMap<>();
		while (iterator.hasNext()) {
			Element key = iterator.next();
			Element value = null;
			if (iterator.hasNext()) {
				value = iterator.next();
			}
			map.put(key, value);
		}

		return new Value<Map<Element, Element>>(AtomicType.JAVA, map);
	}

}
