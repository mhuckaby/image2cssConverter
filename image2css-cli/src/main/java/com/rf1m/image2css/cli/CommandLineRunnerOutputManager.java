package com.rf1m.image2css.cli;

import com.rf1m.image2css.cmn.domain.CssClass;
import com.rf1m.image2css.out.Output;
import com.rf1m.image2css.out.ReportOutput;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class CommandLineRunnerOutputManager {
    protected final Output consoleOutput;
    protected final Output cssOutput;
    protected final Output htmlOutput;

    protected final PrintStream printStream;

    protected final ReportOutput reportOutput;
    protected final String aboutProject;

    public CommandLineRunnerOutputManager(final Output consoleOutput, final Output cssOutput, final Output htmlOutput,
                                          final ReportOutput reportOutput, final PrintStream printStream, final String aboutProject) {

        this.consoleOutput = consoleOutput;
        this.cssOutput = cssOutput;
        this.htmlOutput = htmlOutput;
        this.reportOutput = reportOutput;
        this.printStream = printStream;
        this.aboutProject = aboutProject;
    }

    protected void doOutput(final Parameters parameters, final List<CssClass> cssClasses) throws IOException {
        if(parameters.isOutputToConsoleDesired()) {
            consoleOutput.out(parameters, cssClasses);
        }

        if(parameters.isCssFileOutputDesired()) {
            cssOutput.out(parameters, cssClasses);
        }

        if(parameters.isHtmlFileOutputDesired()) {
            htmlOutput.out(parameters, cssClasses);
        }

        if(parameters.isOutputToConsoleDesired()) {
            reportOutput.generateReportOutput(parameters, cssClasses);
        }
    }

    protected void showAbout() {
        printStream.println(aboutProject);
    }

}
