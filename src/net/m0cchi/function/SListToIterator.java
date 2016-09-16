package net.m0cchi.function;

import java.util.Iterator;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class SListToIterator extends Function {

	private static final long serialVersionUID = 5037302589920011129L;

	public SListToIterator() {
		setArgs("slist args");
	}

	@Override
	public Element invoke(Environment environment) {
		SList list = environment.getValue(getArgs()[0]);
		Iterator<Element> iterator = list.getNativeValue().iterator();

		return new Value<Iterator<Element>>(AtomicType.JAVA, iterator);
	}

}
