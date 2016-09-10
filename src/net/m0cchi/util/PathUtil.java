package net.m0cchi.util;

import java.io.File;

public class PathUtil {

	public static boolean isAbsolutePath(String path) {
		return path.matches("^/.*");
	}

	public static File path2File(String path) {
		File file = null;
		String currentDir = System.getProperty("user.dir");

		if (PathUtil.isAbsolutePath(path)) {
			file = new File(path);
		} else {
			file = new File(currentDir, path);
		}
		return file;
	}

}
