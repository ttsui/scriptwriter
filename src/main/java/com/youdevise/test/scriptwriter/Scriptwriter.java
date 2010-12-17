package com.youdevise.test.scriptwriter;

import java.util.Map.Entry;



public class Scriptwriter {
    private final Scanner scanner;
	private final Printer printer;

	public Scriptwriter(Scanner scanner, Printer printer) {
		this.scanner = scanner;
		this.printer = printer;
	}

	public void generate(String fromTestsLocatedHere, String andOutputScriptsToHere) {
		printer.writeTo(andOutputScriptsToHere);
		
		for (Entry<String, String> test: scanner.readFrom(fromTestsLocatedHere).entrySet()) {
			String name = test.getKey();
			String content = test.getValue();
			
			printer.write(name.replace(".java", ".html"), "<html>" + content + "</html>");
		}
	}

	public static void main(String[] args) {
		CommonsFileUtils fileUtils = new CommonsFileUtils();
		Scanner scanner = new NarrativeTestScanner(fileUtils);
		Printer printer = new ScriptPrinter(fileUtils);
		
		Scriptwriter scriptwriter = new Scriptwriter(scanner, printer);
		
		scriptwriter.generate(args[0], args[0] + "/output");
    }
}