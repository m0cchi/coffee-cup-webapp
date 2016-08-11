package net.m0cchi.function;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import net.m0cchi.value.AtomicType;
import net.m0cchi.value.Element;
import net.m0cchi.value.Environment;
import net.m0cchi.value.Function;
import net.m0cchi.value.Value;

public class ReadStream extends Function {
	private static final long serialVersionUID = -9115979894059140825L;

	public ReadStream() {
		setArgs(new String[] { "read stream" });
	}

	@Override
	public Element invoke(Environment environment) {
		@SuppressWarnings("unchecked")
		Value<InputStream> inputStreamValue = (Value<InputStream>) environment.getValue(getArgs()[0]);
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
		} finally {
			try {
				baos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return new Value<byte[]>(AtomicType.JAVA, ret);
	}

}
