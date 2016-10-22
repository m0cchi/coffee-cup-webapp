package net.m0cchi.startup;

import java.io.File;
import java.io.FileNotFoundException;

import net.m0cchi.exception.handler.ExitSignal;
import net.m0cchi.util.Program;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.NULL.NIL;

public class EnhancedProgram extends Program {
	private Thread thread;

	public EnhancedProgram(File file) throws FileNotFoundException {
		super(file);
	}

	public EnhancedProgram(String code) {
		super(code);
	}

	@Override
	public Element execute() {
		Element ret = null;

		try {
			super.execute();
		} catch (ExitSignal e) {
			ret = NIL.NIL;
		}

		return ret;
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@SuppressWarnings("deprecation")
	public void stop() {
		Environment environment = getEnvironment();
		String[] names = environment.getAllFunctionsName();
		Function function = new Function() {
			private static final long serialVersionUID = 7768573195183003860L;

			@Override
			public Element invoke(Environment environment) {
				throw new ExitSignal();
			}
		};
		for (String name : names) {
			environment.defineFunction(name, function);
		}
		try {
			thread.stop();
		} catch (Throwable e) {
		}
	}
}
