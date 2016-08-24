package net.m0cchi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.m0cchi.value.Element;

public class Serializer {
	
	public static void dump(String path, Element element) throws IOException {
		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(element);
		oos.flush();
		oos.close();
	}

	public static Element deserialize(String path) throws ClassNotFoundException, IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Object object = ois.readObject();
		ois.close();
		return (Element) object;
	}

}
