package com.youdevise.test.scriptwriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class NarrativeTestScanner implements Scanner {

	private final FileUtils fileUtils;

	public NarrativeTestScanner(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
	}

	@Override
	public Collection<String> readFrom(String pathToTests) {
		Collection<String> contents = new ArrayList<String>();
		
		Iterator<File> tests = fileUtils.iterateFiles(new File(pathToTests), new String[] { "java" }, true);
	
		while (tests.hasNext()) {
			contents.add(read(tests.next()));
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
