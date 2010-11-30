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
import org.junit.Test;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;


public class UserGeneratesReadableNarrative {

    @Test public void
    generatesHtmlFromNarrativeStyleTest() {
         ScriptwriterActor writer = new ScriptwriterActor();
         
         Given.the(writer).was_able_to(source_narrative_tests_in("src/test/fixtures/"));
         
         When.the(writer).attempts_to(write_a_script_based_on_the_tests());
         
         Then.the(writer).expects_that(the_script())
                         .should_be(a_readable_form_of_the_narrative());
    }
    
    private Action<Scriptwriter, ScriptwriterActor> source_narrative_tests_in(final String directory) {
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
        private String narrativeTestsDir;

        @Override
        public Scriptwriter tool() {
            return null;
        }

        public File getOutputPath() {
            return new File(narrativeTestsDir, "output");
        }

        public String getSourceOfNarrativeTests() {
            return narrativeTestsDir;
        }

        public void sourcesNarrativeTestsIn(String directory) {
            this.narrativeTestsDir = directory;
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
}
