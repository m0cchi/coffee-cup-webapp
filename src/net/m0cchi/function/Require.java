package net.m0cchi.function;

import java.io.File;
import java.io.FileNotFoundException;

import net.m0cchi.exception.handler.Abort;
import net.m0cchi.util.Program;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.Value;

public class Require extends Function {

	private static final long serialVersionUID = 8559854211233095577L;

	public Require() {
		setArgs("file path");
	}

	@Override
	public Element invoke(Environment environment) {
		Value<?> filePathValue = (Value<?>) environment.getValue(getArgs()[0]);
		Object filePath = filePathValue.getNativeValue();
		Program program = null;
		File file = null;
		String currentDir = System.getProperty("user.dir");
		if (filePath instanceof File) {
			file = (File) filePath;
		} else {
			String path = filePath.toString();
			System.out.println(path);
			file = new File(currentDir, path);
		}
		try {
			program = new Program(file);
			program.getEnvironment().setParent(environment);
		} catch (FileNotFoundException e) {
			Abort abort = new Abort();
			abort.initCause(e);
			throw abort;
		}
		Element ret = program.execute();
		System.setProperty("user.dir", currentDir);
		return ret;
	}

}
