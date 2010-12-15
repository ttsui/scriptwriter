package com.youdevise.test.scriptwriter;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;


public class NarrativeTestScannerTest {
	private final Mockery context = new Mockery();
	
	@Test public void
	returnsContentOfTestsInGivenDirectory() throws Exception {
		final FileUtils fileUtils = context.mock(FileUtils.class);
		final File testFile = new File("");
		
		context.checking(new Expectations() {{ 
			Iterator<File> files = asList(testFile).iterator();
			oneOf(fileUtils).iterateFiles(new File("tests"), new String[] { "java" }, true); will(returnValue(files));
			
			oneOf(fileUtils).readFileToString(testFile); will(returnValue("public class Test1"));
		}});
		
		NarrativeTestScanner scanner = new NarrativeTestScanner(fileUtils);
		
		Collection<String> contents = scanner.readFrom("tests/");
	
		assertThat(contents, contains(containsString("public class Test1")));
		context.assertIsSatisfied();
	}

}
