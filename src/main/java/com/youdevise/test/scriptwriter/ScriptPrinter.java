package com.youdevise.test.scriptwriter;

import java.io.File;

public class ScriptPrinter implements Printer {

	private final FileUtils fileUtils;
    private String outputDirectory;

	public ScriptPrinter(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
	}

	@Override
	public void writeTo(String outputDirectory) {
        this.outputDirectory = outputDirectory;
	}

	@Override
	public void write(String fileName, String content) {
	    fileUtils.write(new File(outputDirectory + "/" + fileName), content);
	}

}
