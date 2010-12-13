package com.youdevise.test.scriptwriter;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static java.util.Arrays.asList;

public class ScriptwriterTest {
	private final Mockery context = new Mockery();
	
	private Scanner scanner = context.mock(Scanner.class);
	private ScriptPrinter printer = context.mock(ScriptPrinter.class);
	
	@Test public void
	readsNarrativeTestsFromGivenDirectory() {
		
		context.checking(new Expectations() {{ 
			oneOf(scanner).readFrom("narrative-tests/"); will(returnValue(asList("test1", "test2")));
			
			ignoring(printer);
		}});
		
		Scriptwriter writer = new Scriptwriter(scanner, printer);
		
		writer.generate("narrative-tests/", "");
		
		context.assertIsSatisfied();
	}
	
	@Test public void
	writesScriptToGivenDirectory() {
		
		context.checking(new Expectations() {{  
			oneOf(printer).writeTo("output/");
			
			ignoring(scanner);
		}});
		
		Scriptwriter writer = new Scriptwriter(scanner, printer);
		writer.generate("narrativ-tests", "output/");
		
		context.assertIsSatisfied();
	}
}
