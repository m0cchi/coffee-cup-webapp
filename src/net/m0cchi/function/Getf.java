package net.m0cchi.function;

import java.util.Iterator;

import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.NULL.NIL;
import net.m0cchi.value.SList;

public class Getf extends Function {
	private static final long serialVersionUID = -2644128120091523794L;

	public Getf() {
		setArgs("get target", "get index");
	}

	@Override
	public Element invoke(Environment environment) {
		SList target = (SList) environment.getValue(getArgs()[0]);
		Element index = environment.getValue(getArgs()[1]);
		
		Iterator<Element> iterator = target.getNativeValue().iterator();
		while(iterator.hasNext()) {
			Element element = iterator.next();
			if(element.equals(index) && iterator.hasNext()) {
				return iterator.next();
			}
		}
		return NIL.NIL;
	}

}
