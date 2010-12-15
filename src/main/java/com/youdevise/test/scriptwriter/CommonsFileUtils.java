package com.youdevise.test.scriptwriter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class CommonsFileUtils implements FileUtils {

	@Override
	public Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive) {
		return org.apache.commons.io.FileUtils.iterateFiles(directory, extensions, recursive);
	}

	@Override
	public String readFileToString(File file) throws IOException {
		return org.apache.commons.io.FileUtils.readFileToString(file);
	}
}
