package net.m0cchi.function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Macro;
import net.m0cchi.value.SList;

public class Rope extends Macro {
	private static final long serialVersionUID = 3446282864982779158L;

	public Rope() {
		setArgs("rope value", REST, "rope list");
	}

	@Override
	public Element invoke(Environment environment) {
		Element value = environment.getValue(getArgs()[0]);
		SList sList = (SList) environment.getValue(getArgs()[2]);
		Iterator<Element> iterator = sList.getNativeValue().iterator();

		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(environment);

		while (iterator.hasNext()) {
			Element element = iterator.next();
			if (element instanceof SList) {
				((SList) element).add(value);
			} else {
				List<Element> list = new ArrayList<>();
				list.add(element);
				list.add(value);
				element = new SList(list);
			}

			value = semanticAnalyzer.evaluate(element);
		}
		return value;
	}

}
