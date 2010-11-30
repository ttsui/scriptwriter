package com.youdevise.test.scriptwriter;

import org.jmock.Mockery;
import org.junit.Test;

public class ScriptwriterTest {
	private final Mockery context = new Mockery();
	
	@Test public void
	readsAllNarrativeTestsFromGivenDirectory() {
		NarrativeTestsReader narrativeTestsReader = context.mock(NarrativeTestsReader.class);
		
		Scriptwriter writer = new Scriptwriter(narrativeTestsReader);
		
		writer.generate("narrative-tests/");
		
		context.assertIsSatisfied();
	}
}
