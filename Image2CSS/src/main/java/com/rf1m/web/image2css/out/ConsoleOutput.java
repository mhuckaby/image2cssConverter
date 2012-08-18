package com.rf1m.web.image2css.out;

import com.rf1m.web.image2css.domain.CSSClass;
import com.rf1m.web.image2css.cli.Parameters;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static com.rf1m.web.image2css.ContentTemplates.REPORT_CSS_FILE;
import static com.rf1m.web.image2css.ContentTemplates.REPORT_CSS_TOTAL;
import static com.rf1m.web.image2css.ContentTemplates.REPORT_HTML_FILE;
import static java.lang.String.format;

public class ConsoleOutput extends AbstractOutput implements ReportOutput{
    protected PrintStream consoleOut;

    public ConsoleOutput(){
        super();
        this.consoleOut = System.out;
    }

    @Override
    public void out(Parameters parameters, List<CSSClass> cssClasses) throws IOException {
        if(validate(parameters) && validate(cssClasses) && parameters.isOutputToScreen()){
			for(CSSClass cssClass : cssClasses){
				consoleOut.println(cssClass.getBody());
			}
		}
    }

    @Override
    public void generateReportOutput(Parameters parameters, List<CSSClass> cssClasses){
        if(validate(parameters) && validate(cssClasses)){
            consoleOut.println(format(REPORT_CSS_TOTAL, cssClasses.size()));

            if(null != parameters.getCssFile()){
                consoleOut.println(format(REPORT_CSS_FILE, parameters.getCssFile().getName()));
            }

            if(null != parameters.getHtmlFile()){
                consoleOut.println(format(REPORT_HTML_FILE, parameters.getHtmlFile().getName()));
            }
        }
	}

    protected boolean validate(Parameters parameters) {
        if(null == parameters){
            throw new IllegalArgumentException("Parameters object is null");
        }else{
            return true;
        }
    }
}
