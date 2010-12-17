package com.youdevise.test.scriptwriter;

import java.io.File;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;


public class ScriptPrinterTest {
	private final Mockery context = new Mockery();
	
	@Test public void
	writesScriptWithGivenNameAndContent() {
		final FileUtils fileUtils = context.mock(FileUtils.class);
		
		context.checking(new Expectations() {{ 
		    oneOf(fileUtils).write(new File("output/BasicArithmeticTest.html"), "<html>...</html>");
		}});
	    
		ScriptPrinter printer = new ScriptPrinter(fileUtils);
		
		printer.writeTo("output");
		printer.write("BasicArithmeticTest.html", "<html>...</html>");
	    
		context.assertIsSatisfied();
	}
}
