package net.m0cchi.startup;

import java.io.File;
import java.io.FileNotFoundException;

import net.m0cchi.function.DefRoutes;
import net.m0cchi.function.Defvar;
import net.m0cchi.function.DoList;
import net.m0cchi.function.HttpService;
import net.m0cchi.function.Path2Stream;
import net.m0cchi.function.Println;
import net.m0cchi.function.ReadStream;
import net.m0cchi.util.Program;
import net.m0cchi.value.Environment;

public class Bootstrap {

	public static void main(String[] args) throws FileNotFoundException {
		Program program = new Program(new File("lisp/bootstrap.lisp"));
		Environment environment = program.getEnvironment();
		environment.naming("web-app");
		environment.defineFunction("defvar", new Defvar());
		environment.defineFunction("defroutes", new DefRoutes());
		environment.defineFunction("http-service", new HttpService());
		environment.defineFunction("do", new DoList());
		environment.defineFunction("println", new Println());
		environment.defineFunction("file", new Path2Stream());
		environment.defineFunction("read", new ReadStream());
		program.run();
	}

}
