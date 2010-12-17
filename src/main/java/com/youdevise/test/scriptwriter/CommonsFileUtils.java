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

    @Override
    public void write(File file, String content) {
        try {
            org.apache.commons.io.FileUtils.writeStringToFile(file, content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
