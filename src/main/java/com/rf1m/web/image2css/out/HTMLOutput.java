package com.rf1m.web.image2css.out;

import com.rf1m.web.image2css.domain.CSSClass;
import com.rf1m.web.image2css.cli.Parameters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.rf1m.util.PropertiesUtils.getProperty;
import static com.rf1m.web.image2css.ContentTemplates.TEMPLATE.HTML_CSS_ENTRY;
import static com.rf1m.web.image2css.ContentTemplates.TEMPLATE.HTML_INDEX;
import static java.lang.String.format;

public class HTMLOutput extends AbstractOutput{

    @Override
    public void out(Parameters parameters, List<CSSClass> cssClasses) throws IOException {
        if(validate(parameters) && validate(cssClasses)){
            final String cssEntry = getProperty(HTML_CSS_ENTRY);
            final FileWriter fileWriter = fileWriterFactory.newFileWriter(parameters.getHtmlFile());
            final StringBuffer stringBuffer = stringBufferFactory.newStringBuffer();

            for(CSSClass cssClass : cssClasses){
                stringBuffer.append(
					format(cssEntry, cssClass.getName())
				);
            }

            fileWriter.write(
                    format(getProperty(HTML_INDEX), parameters.getCssFile().getName(), stringBuffer.toString())
            );

			fileWriter.close();
		}
    }

    protected boolean validate(Parameters parameters) {
        if(null == parameters){
            throw new IllegalArgumentException("Parameters object is null");
        }else if(null == parameters.getHtmlFile()){
            throw new IllegalArgumentException("HTML output file not specified");
        }else if(null == parameters.getCssFile()){
            throw new IllegalArgumentException("CSS file is null");
        }else{
            return true;
        }
    }
}