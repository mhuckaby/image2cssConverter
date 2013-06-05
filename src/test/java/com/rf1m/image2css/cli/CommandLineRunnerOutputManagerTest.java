package com.rf1m.image2css.cli;

import com.rf1m.image2css.out.Output;
import com.rf1m.image2css.out.ReportOutput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CommandLineRunnerOutputManagerTest {

    @Mock
    Output consoleOutput;

    @Mock
    Output cssOutput;

    @Mock
    Output htmlOutput;

    @Mock
    ReportOutput reportOutput;

    @Mock
    PrintStream printStream;

    final String aboutProject = "aboutProject";

    CommandLineRunnerOutputManager commandLineRunnerOutputManager;

    @Before
    public void before() {
        commandLineRunnerOutputManager = spy(new CommandLineRunnerOutputManager(consoleOutput, cssOutput, htmlOutput, reportOutput, printStream, aboutProject));
    }

    @Test
    public void showAboutShouldPrintAboutStringToPrintWriter() {
        commandLineRunnerOutputManager.showAbout();

        verify(printStream, times(1))
            .println(anyString());
    }


}
