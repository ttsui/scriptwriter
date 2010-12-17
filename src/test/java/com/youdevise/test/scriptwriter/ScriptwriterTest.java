package com.youdevise.test.scriptwriter;

import java.util.HashMap;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

public class ScriptwriterTest {
	private final Mockery context = new Mockery();
	
	private final Scanner scanner = context.mock(Scanner.class);
	private final Printer printer = context.mock(Printer.class);
	
	@Test public void
	readsNarrativeTestsFromGivenDirectory() {
		
		context.checking(new Expectations() {{ 
			Map<String, String> tests = new HashMap<String, String>();
			tests.put("test1.java", "public class Test1");
			tests.put("test2.java", "public class Test2");
			
			oneOf(scanner).readFrom("narrative-tests/"); will(returnValue(tests));
			
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
			oneOf(printer).write("BasicArithmeticTest.html", "<html>public class Test1</html>");
			
			Map<String, String> tests = new HashMap<String, String>();
			tests.put("BasicArithmeticTest.java", "public class Test1");
			
			oneOf(scanner).readFrom("narrative-tests/"); will(returnValue(tests));
		}});
		
		Scriptwriter writer = new Scriptwriter(scanner, printer);
		writer.generate("narrative-tests/", "output/");
		
		context.assertIsSatisfied();
	}
}
