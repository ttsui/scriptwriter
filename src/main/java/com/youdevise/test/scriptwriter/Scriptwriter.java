package com.youdevise.test.scriptwriter;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Scriptwriter {
    private final Scanner scanner;
	private final ScriptPrinter printer;

	public Scriptwriter(Scanner scanner, ScriptPrinter printer) {
		this.scanner = scanner;
		this.printer = printer;
	}

	public void generate(String fromTestsLocatedHere, String andOutputScripsToHere) {
		scanner.readFrom(fromTestsLocatedHere);
		printer.writeTo(andOutputScripsToHere);
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