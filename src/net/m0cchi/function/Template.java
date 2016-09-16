package net.m0cchi.function;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import net.m0cchi.parser.lexical.StringLexicalAnalyzer;
import net.m0cchi.parser.semantic.SemanticAnalyzer;
import net.m0cchi.parser.syntax.SyntaxAnalyzer;
import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.NULL.NIL;
import net.m0cchi.value.SList;
import net.m0cchi.value.Value;

public class Template extends Function {
	private static final long serialVersionUID = 894670098704045891L;

	public Template() {
		setArgs(new String[] { "template str" });
	}

	public static void parseCode(Environment environment, InputStream in, OutputStream out) throws IOException {
		int code;
		int countCodeBlock = 0;
		List<Integer> cache = new ArrayList<>();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((code = in.read()) >= 0) {

			if (code == '}') {
				countCodeBlock++;
				if (countCodeBlock == 3) {
					break;
				}
				cache.add(code);
				continue;
			} else {
				countCodeBlock = 0;
				if (!cache.isEmpty()) {
					for (int tmp : cache) {
						baos.write(tmp);
					}
					cache.clear();
				}
			}

			baos.write(code);
		}
		StringLexicalAnalyzer lexicalAnalyzer = new StringLexicalAnalyzer(baos.toString());
		SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(environment);
		SList list = syntaxAnalyzer.parse();
		Element ret = null;
		for (Element value : list.toArray()) {
			ret = semanticAnalyzer.evaluate(value);
		}

		if (ret instanceof Value) {
			Object object = ((Value<?>) ret).getNativeValue();
			if (object instanceof byte[]) {
				out.write((byte[]) object);
			} else if (object instanceof String) {
				out.write(((String) object).getBytes());
			} else {
				out.write(object.toString().getBytes());
			}
		}
	}

	public static void parse(Environment environment, InputStream in, OutputStream out) throws IOException {
		int code;
		List<Integer> cache = new ArrayList<>();
		int codeBlockFlag = 0;
		while ((code = in.read()) >= 0) {
			if (code == '#') {
				cache.add(code);
				continue;
			} else if (code == '{') {
				if (codeBlockFlag == 2) {
					parseCode(environment, in, out);
					cache.clear();
					codeBlockFlag = 0;
				} else {
					codeBlockFlag++;
					cache.add(code);
				}
			} else {
				if (!cache.isEmpty()) {
					for (int tmp : cache) {
						out.write(tmp);
					}
					cache.clear();
				}
				out.write(code);
			}
		}
	}

	public static byte[] toBytes(Value<?> value) {
		Object object = value.getNativeValue();
		return (byte[]) (object instanceof byte[] ? object : object.toString().getBytes());
	}

	@Override
	public Element invoke(Environment environment) {
		Value<?> templateValue = environment.getValue(getArgs()[0]);
		byte[] template = toBytes(templateValue);
		ByteArrayInputStream bais = new ByteArrayInputStream(template);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Element ret = null;
		PrintStream systemOut = System.out;
		System.setOut(new PrintStream(baos));
		try {
			parse(environment, bais, baos);
			ret = new Value<byte[]>(AtomicType.JAVA, baos.toByteArray());
		} catch (IOException e) {
			ret = NIL.NIL;
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bais.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.setOut(systemOut);
		}
		return ret;
	}

}
