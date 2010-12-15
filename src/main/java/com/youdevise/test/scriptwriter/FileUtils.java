package com.youdevise.test.scriptwriter;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public interface FileUtils {

	public Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive);

	public String readFileToString(File file) throws IOException;
	
}