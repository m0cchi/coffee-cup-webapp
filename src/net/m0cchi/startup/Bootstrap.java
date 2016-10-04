package net.m0cchi.startup;

import java.io.File;
import java.io.FileNotFoundException;

import net.m0cchi.function.ArrayToSList;
import net.m0cchi.function.DefRoutes;
import net.m0cchi.function.Defmacro;
import net.m0cchi.function.Defun;
import net.m0cchi.function.Defvar;
import net.m0cchi.function.DoList;
import net.m0cchi.function.Eval;
import net.m0cchi.function.Funcall;
import net.m0cchi.function.HttpService;
import net.m0cchi.function.MakeMap;
import net.m0cchi.function.Matches;
import net.m0cchi.function.NewList;
import net.m0cchi.function.Nop;
import net.m0cchi.function.Path2Stream;
import net.m0cchi.function.Println;
import net.m0cchi.function.Quote;
import net.m0cchi.function.ReadStream;
import net.m0cchi.function.SListToIterator;
import net.m0cchi.function.Setq;
import net.m0cchi.function.Str;
import net.m0cchi.function.Template;
import net.m0cchi.function.ToBase64;
import net.m0cchi.function.handler.If;
import net.m0cchi.function.handler.Kill;
import net.m0cchi.function.handler.Loop;
import net.m0cchi.function.java.Invoke;
import net.m0cchi.function.java.InvokeStatic;
import net.m0cchi.function.java.New;
import net.m0cchi.util.Program;
import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class Bootstrap {

	public static void main(String[] args) throws FileNotFoundException {
		Program program = new Program(new File("lisp/bootstrap.lisp"));
		Environment environment = program.getEnvironment();
		environment.naming("web-app");
		environment.defineFunction(new Defvar());
		environment.defineFunction(new Defmacro());
		environment.defineFunction(new Defun());
		environment.defineFunction(new DefRoutes());
		environment.defineFunction(new HttpService());
		environment.defineFunction(new DoList());
		environment.defineFunction(new Println());
		environment.defineFunction("list", new NewList());
		environment.defineFunction(new Kill());
		environment.defineFunction("file", new Path2Stream());
		environment.defineFunction("read", new ReadStream());
		environment.defineFunction(new Template());
		environment.defineFunction(new Str());
		environment.defineFunction(new ArrayToSList());
		environment.defineFunction(new SListToIterator());
		environment.defineFunction(new Matches());
		environment.defineFunction(new ToBase64());
		environment.defineFunction(new Loop());
		environment.defineFunction(new If());
		environment.defineFunction(new Quote());
		environment.defineFunction(new Nop());
		environment.defineFunction(new Funcall());
		environment.defineFunction(".", new Invoke());
		environment.defineFunction(new InvokeStatic());
		environment.defineFunction("new", new New());
		environment.defineFunction(new Setq());
		environment.defineFunction(new MakeMap());
		environment.defineVariable("env", new Value<Environment>(AtomicType.JAVA, environment));
		// safe eval sample
		Eval eval = new Eval();
		// easy
		eval.removeAllFunction(true);

		String message = "code runner\n" + "/code-runner?code=(greeting-message) ;;=> this message\n"
				+ "/code-runner?code=message ;;=> this message\n" + "/code-runner?code=(+ 1 2) ;;=> ???";
		eval.hook("message", new Value<String>(AtomicType.LETTER, message));
		eval.hook("greeting-message", new Function() {
			private static final long serialVersionUID = 8467543795727484536L;

			@Override
			public Element invoke(Environment environment) {
				return environment.getValue("message");
			}
		});
		eval.hook("+", new Function() {
			private static final long serialVersionUID = -6114148177013110076L;
			{
				setArgs("number 1", "number 2");
			}

			@Override
			public Element invoke(Environment environment) {
				Element num1Value = environment.getValue(getArgs()[0]);
				Element num2Value = environment.getValue(getArgs()[1]);
				Element ret = new SList();
				if (!(num1Value instanceof Value && num2Value instanceof Value)) {
					// error
					return ret;
				}
				Object obj1 = ((Value<?>) num1Value).getNativeValue();
				Object obj2 = ((Value<?>) num2Value).getNativeValue();
				Integer num1;
				Integer num2;
				if (obj1 instanceof Integer) {
					num1 = (Integer) obj1;
				} else if (obj1 instanceof Double) {
					num1 = ((Double) obj1).intValue();
				} else {
					return ret;
				}

				if (obj2 instanceof Integer) {
					num2 = (Integer) obj2;
				} else if (obj2 instanceof Double) {
					num2 = ((Double) obj2).intValue();
				} else {
					return ret;
				}
				return new Value<Integer>(AtomicType.DIGIT, num1 + num2);
			}
		});

		environment.defineFunction("eval", eval);
		program.run();
	}

}
