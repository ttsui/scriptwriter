package com.youdevise.test.scriptwriter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NarrativeTestScanner implements Scanner {

	private final FileUtils fileUtils;

	public NarrativeTestScanner(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
	}

	@Override
	public Map<String, String> readFrom(String pathToTests) {
		Map<String, String> contents = new HashMap<String, String>();
		
		Iterator<File> tests = fileUtils.iterateFiles(new File(pathToTests), new String[] { "java" }, true);
	
		while (tests.hasNext()) {
			File test = tests.next();
			contents.put(test.getName(), read(test));
		}
		
		return contents;
	}

	private String read(File test) {
		try {
		    return fileUtils.readFileToString(test);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
