package com.youdevise.test.scriptwriter;

import com.youdevise.test.narrative.Action;
import com.youdevise.test.narrative.Actor;
import com.youdevise.test.narrative.Extractor;
import com.youdevise.test.narrative.Given;
import com.youdevise.test.narrative.Then;
import com.youdevise.test.narrative.When;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;


public class UserGeneratesReadableNarrative {

    private File narrativeTestsDirectory;

    @Before public void createCodeDirectory() throws IOException {
        narrativeTestsDirectory = File.createTempFile("narrative-tests", Long.toString(System.nanoTime()));
        
        if (!narrativeTestsDirectory.delete() || !narrativeTestsDirectory.mkdir()) {
            throw new IOException("Failed to create code directory: " + narrativeTestsDirectory.getAbsolutePath()); 
        }
    }
    
    @After public void clean() {
        narrativeTestsDirectory.delete();
    }
    
    @Test public void
    generatesHtmlFromNarrativeStyleTest() {
         ScriptwriterActor writer = new ScriptwriterActor();
         
         Given.the(writer).was_able_to(source_narrative_tests_in(narrativeTestsDirectory));
         
         When.the(writer).attempts_to(write_a_script_based_on_the_tests());
         
         Then.the(writer).expects_that(the_script())
                         .should_be(a_readable_form_of_the_narrative());
    }
    
    private Action<Scriptwriter, ScriptwriterActor> source_narrative_tests_in(final File directory) {
        return new Action<Scriptwriter, ScriptwriterActor>() {
            @Override
            public void performFor(ScriptwriterActor writer) {
                writer.sourcesNarrativeTestsIn(directory);
            }
        };
    }

    private Action<Scriptwriter, ScriptwriterActor> write_a_script_based_on_the_tests() {
        return new Action<Scriptwriter, ScriptwriterActor>() {
            @Override
            public void performFor(ScriptwriterActor writer) {
                Scriptwriter.main(new String[] { writer.getSourceOfNarrativeTests() });
            }
        };
    }

    private Extractor<String, ScriptwriterActor> the_script() {
        return new Extractor<String, ScriptwriterActor>() {
            @Override
            public String grabFor(ScriptwriterActor writer) {
                if (!writer.getOutputPath().exists()) {
                   throw new RuntimeException("Output directory was not created.");
                }
                
                File firstOutput = FileUtils.iterateFiles(writer.getOutputPath(), new String[] { "html" }, false).next();
                
                try {
                    return FileUtils.readFileToString(firstOutput);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                return null;
            }
        }; 
    }

    private Matcher<String> a_readable_form_of_the_narrative() {
        return allOf(containsString("<html>"),
                     containsString("BasicArithmeticTest"),
                     containsString("the operator was able to"));
    }

    public static class ScriptwriterActor implements Actor<Scriptwriter, ScriptwriterActor> {
        private File testsDirectory;

        @Override
        public Scriptwriter tool() {
            return null;
        }

        public File getOutputPath() {
            return new File(testsDirectory, "output");
        }

        public String getSourceOfNarrativeTests() {
            return testsDirectory.getAbsolutePath();
        }

        public void sourcesNarrativeTestsIn(File directory) {
            this.testsDirectory = directory;
            try {
                FileUtils.copyFileToDirectory(new File("src/test/fixtures/BasicArithmeticTest.java"), directory);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void perform(Action<Scriptwriter, ScriptwriterActor> action) {
            action.performFor(this);
        }

        @Override
        public <DATA> DATA grabUsing(Extractor<DATA, ScriptwriterActor> extractor) {
            return extractor.grabFor(this);
        }
    }
    
    public static class Scriptwriter {
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
}
