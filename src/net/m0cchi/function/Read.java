package net.m0cchi.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.Value;

public class Read extends Function {
	private static final long serialVersionUID = 5147354873993029386L;

	public Read() {
		setArgs(new String[] { "read stream" });
	}
	
	@Override
	public Element invoke(Environment environment) {
		Value<InputStream> inputStreamValue = environment.getValue(getArgs()[0]);
		InputStream is = inputStreamValue.getNativeValue();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] ret = new byte[0];
		byte[] buf = new byte[512 * 10];
		int len;
		try {
			while ((len = is.read(buf, 0, buf.length)) >= 0) {
				baos.write(buf, 0, len);
			}
			ret = baos.toByteArray();
		} catch (IOException e) {
			try {
				baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e1) {
			}
		} finally {
		}
		return new Value<byte[]>(AtomicType.JAVA, ret);
	}
}