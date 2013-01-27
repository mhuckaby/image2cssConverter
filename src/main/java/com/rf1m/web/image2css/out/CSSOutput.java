package com.rf1m.web.image2css.out;

import com.rf1m.web.image2css.domain.CSSClass;
import com.rf1m.web.image2css.cli.Parameters;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSSOutput extends AbstractOutput{
    public static final String NL = "\n";

    @Override
    public void out(Parameters parameters, List<CSSClass> cssClasses) throws IOException {
        if(validate(parameters) && validate(cssClasses)){
            final FileWriter fileWriter = fileWriterFactory.newFileWriter(parameters.getCssFile());
            final StringBuffer stringBuffer = stringBufferFactory.newStringBuffer();

            for(CSSClass cssClass : cssClasses){
                stringBuffer
                    .append(cssClass.getBody())
                    .append(NL);
			}

            fileWriter.write(stringBuffer.toString());
			fileWriter.close();
		}
    }

    protected boolean validate(Parameters parameters){
        if(null == parameters){
            throw new IllegalArgumentException("Parameters object is null");
        }else if(null == parameters.getCssFile()){
            throw new RuntimeException("CSS output file not specified");
        }else{
            return true;
        }
    }
}