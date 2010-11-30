package com.youdevise.test.scriptwriter;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Scriptwriter {
    public Scriptwriter(NarrativeTestsReader narrativeTestsReader) {
		// TODO Auto-generated constructor stub
	}

	public void generate(String string) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
        File outputDir = new File(args[0], "output");
        outputDir.mkdir();
        
        File output = new File(outputDir, "t.html");
        try {
            FileUtils.writeStringToFile(output, "Hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}