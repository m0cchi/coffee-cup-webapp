package net.m0cchi.function;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.NULL.NIL;
import net.m0cchi.value.Value;

public class Path2Stream extends Function {
	private static final long serialVersionUID = 4372928122331087456L;

	public Path2Stream() {
		setArgs(new String[] { "path string" });
	}

	@Override
	public Element invoke(Environment environment) {
		Value<?> pathValue = (Value<?>) environment.getValue(getArgs()[0]);
		String path = System.getProperty("user.dir") + "/" + pathValue.getNativeValue().toString();
		File file = new File(path);
		Element ret = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ret = new Value<InputStream>(AtomicType.JAVA, fis);
		} catch (FileNotFoundException e) {
			ret = new NIL();
		}
		return ret;
	}

}
